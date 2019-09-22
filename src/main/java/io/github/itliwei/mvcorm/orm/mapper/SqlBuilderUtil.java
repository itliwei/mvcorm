package io.github.itliwei.mvcorm.orm.mapper;

import com.google.common.base.CaseFormat;
import io.github.itliwei.mvcorm.orm.IdEntity;
import io.github.itliwei.mvcorm.orm.annotation.Column;
import io.github.itliwei.mvcorm.orm.annotation.Table;
import io.github.itliwei.mvcorm.orm.opt.Condition;
import io.github.itliwei.mvcorm.orm.opt.Field;
import io.github.itliwei.mvcorm.orm.opt.OrderBy;
import org.apache.ibatis.jdbc.SQL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * SqlBuilderUtil
 * Created by cheshun on 17/8/25.
 */
public class SqlBuilderUtil {
    private static final Logger logger = LoggerFactory.getLogger(SqlBuilderUtil.class);
    private static final ThreadLocal<Map<String, Class<?>>> localEntities = new ThreadLocal<>();
    private static final ThreadLocal<Map<Class<?>, Map<String, PropertyDescriptor>>> localMultiProperties = new ThreadLocal<>();


    private static final ThreadLocal<SQL> localSQL = new ThreadLocal<>();

    static void BEGIN() {
        localSQL.set(new SQL());
    }

    static String SQL() {
        String sql = localSQL.get().toString();
        logger.debug("CORM SQL ---> \n{}", sql);
        return sql;
    }

    static void INSERT_INTO(String tableName) {
        SQL sql = localSQL.get();
        sql.INSERT_INTO(tableName);
    }

    static void VALUES(IdEntity entity) {
        SQL sql = localSQL.get();
        Class<?> clazz = localEntities.get().get("");
        Map<String, PropertyDescriptor> properties = findProperties(clazz);
        for (String key : properties.keySet()) {
            PropertyDescriptor property = properties.get(key);
            Method getter = property.getReadMethod();
            Object obj = getValue(getter, entity);
            if (obj == null) {
                continue;
            }
            String columnName = findColumnName(clazz, getter, property.getName());
            String jdbcType = findJdbcType(clazz, getter, property.getName());
            if (jdbcType != null && !"".equals(jdbcType)) {
                sql.VALUES(columnName, String.format("#{%s,jdbcType=%s}", property.getName(), jdbcType));
            } else {
                sql.VALUES(columnName, String.format("#{%s}", property.getName()));
            }
        }
    }

    static void UPDATE(String tableName) {
        SQL sql = localSQL.get();
        sql.UPDATE(tableName);
    }

    static void SET(Set<Field> fields, IdEntity entity) {
        for (Field field : fields) {
            String alias = field.getAlias();
            Class<?> clazz = localEntities.get().get(alias);
            Map<String, PropertyDescriptor> properties = findProperties(clazz);
            PropertyDescriptor property = properties.get(field.getName());
            Method getter = property.getReadMethod();
            Object obj = getValue(getter, entity);
            setX(clazz, obj, property, getter);
        }
    }

    static void SET(IdEntity entity, boolean useNull) {
        Class<?> clazz = localEntities.get().get("");
        Map<String, PropertyDescriptor> properties = findProperties(clazz);
        for (String propertyName : properties.keySet()) {
            PropertyDescriptor property = properties.get(propertyName);
            Method getter = property.getReadMethod();
            if (property.getName().equals(IdEntity.ID_PN)) {
                continue;
            }
            Object obj = getValue(getter, entity);
            if (!useNull && obj == null) {
                continue;
            }
            setX(clazz, obj, property, getter);
        }
    }

    private static void setX(Class<?> clazz, Object obj, PropertyDescriptor property, Method getter) {
        SQL sql = localSQL.get();
        String columnName = findColumnName(clazz, getter, property.getName());
        String jdbcType = findJdbcType(clazz, getter, property.getName());
        if (obj == null) {
            sql.SET(String.format("%s = null", columnName));
        } else {
            if ("".equals(jdbcType)) {
                sql.SET(String.format("%s = #{param.entity.%s}", columnName, property.getName()));
            } else {
                sql.SET(String.format("%s = #{param.entity.%s,jdbcType=%s}", columnName, property.getName(), jdbcType));
            }
        }
    }

    static void DELETE_FROM(String tableName) {
        SQL sql = localSQL.get();
        sql.DELETE_FROM(tableName);
    }

    static void select(Set<Field> fields) {
        for (Field field : fields) {
            String alias = field.getAlias();
            Class<?> clazz = localEntities.get().get(alias);
            Map<String, PropertyDescriptor> properties = findProperties(clazz);
            PropertyDescriptor property = properties.get(field.getName());
            if (property == null) {
                throw new IllegalArgumentException(
                        String.format("The property '%s' is not in properties of the entity '%s'.",
                                field.getName(), clazz.getName()));
            }
            select(clazz, property, alias);
        }
    }

    static void selectAll() {
        Map<String, Class<?>> classMap = localEntities.get();
        for (Map.Entry<String, Class<?>> entry : classMap.entrySet()) {
            String alias = entry.getKey();
            Class<?> clazz = entry.getValue();
            Map<String, PropertyDescriptor> properties = findProperties(clazz);
            for (Map.Entry<String, PropertyDescriptor> x : properties.entrySet()) {
                PropertyDescriptor property = x.getValue();
                select(clazz, property, alias);
            }
        }
    }

    static void selectCountX() {
        SQL sql = localSQL.get();
        sql.SELECT("count(*)");
    }

    static void selectSumX(Set<Field> fields) {
        SQL sql = localSQL.get();
        for (Field field : fields) {
            String alias = field.getAlias();
            String propertyName = field.getName();
            Class<?> clazz = localEntities.get().get(alias);
            Method getter = findGetterMethod(propertyName, clazz);
            String columnName = findColumnName(clazz, getter, propertyName);
            if (alias != null && alias.trim().length() > 0) {
                sql.SELECT(String.format("SUM(%s.%s)", alias, columnName));
            } else {
                sql.SELECT(String.format("SUM(%s)", columnName));
            }
        }
    }

    private static void select(Class<?> clazz, PropertyDescriptor property, String alias) {
        SQL sql = localSQL.get();
        Method getter = property.getReadMethod();
        String columnName = findColumnName(clazz, getter, property.getName());
        if (alias != null && alias.trim().length() > 0) {
            sql.SELECT(String.format("%s.%s AS \"%s_%s\"", alias, columnName, alias, property.getName()));
        } else {
            sql.SELECT(String.format("%s AS \"%s\"", columnName, property.getName()));
        }
    }

    static void FORM(List<String> tableNameList) {
        SQL sql = localSQL.get();
        for (String tableName : tableNameList) {
            sql.FROM(tableName);
        }
    }

    static void where(List<Condition> conditions) {
        SQL sql = localSQL.get();
        for (int i = 0; i < conditions.size(); i++) {
            Condition condition = conditions.get(i);

            // on条件前置处理
            if (condition.getOperator() == Condition.Operator.on) {
                String lProperty = buildOnColumn(condition.getProperty());
                String rProperty = buildOnColumn(condition.getValue().toString());

                sql.WHERE(String.format("%s %s %s",
                        lProperty, condition.getOperator().getValue(), rProperty));
                continue;
            }

            String alias = condition.getAlias();
            Class<?> clazz = localEntities.get().get(alias);

            if (condition.getOperator() == Condition.Operator.or) {
                String or = whereOr(clazz, condition, i);
                sql.WHERE(or);
                continue;
            }

            Method getter = findGetterMethod(condition.getProperty(), clazz);
            String columnName = findColumnName(clazz, getter, condition.getProperty());
            String jdbcType = findJdbcType(clazz, getter, condition.getProperty());
            if (condition.getOperator() == Condition.Operator.in && condition.getValue() != null) {
                String in = whereIn(columnName, condition, jdbcType, i, "param.conditionList[%d].value[%d]");
                sql.WHERE(in);
            }
            else if (condition.getOperator() == Condition.Operator.isNull
                    || condition.getOperator() == Condition.Operator.isNotNull) {
                if (alias != null && alias.trim().length() > 0) {
                    sql.WHERE(String.format("%s.%s %s", alias, columnName, condition.getOperator().getValue()));
                } else {
                    sql.WHERE(String.format("%s %s", columnName, condition.getOperator().getValue()));
                }
            }
            else {
                String dyadic = whereDyadic(columnName, condition, jdbcType, i, "param.conditionList[%d].value");
                sql.WHERE(dyadic);
            }
        }
    }

    private static String buildOnColumn(String onProperty) {
        String[] s = onProperty.split("\\.");
        if (s.length != 2) {
            throw new IllegalArgumentException("on condition param error.");
        }
        String alias = s[0];
        String property = s[1];
        Class<?> clazz = localEntities.get().get(alias);

        Method getter = findGetterMethod(property, clazz);
        String columnName = findColumnName(clazz, getter, property);
        return alias + "." + columnName;
    }

    private static String whereOr(Class<?> clazz, Condition condition, int i) {
        StringBuilder builder = new StringBuilder("( ");
        List<Condition> orList = condition.getOrList();
        for (int j = 0; j < orList.size(); j++) {
            Condition c = orList.get(j);
            String alias = c.getAlias();
            Class<?> localClazz = localEntities.get().get(alias);
            Method getter = findGetterMethod(c.getProperty(), localClazz);
            String columnName = findColumnName(localClazz, getter, c.getProperty());
            String jdbcType = findJdbcType(localClazz, getter, c.getProperty());
            if (c.getOperator() == Condition.Operator.in) {
                String in = whereIn(columnName, c, jdbcType, i, "param.conditionList[%d].orList[" + j + "].value[%d]");
                builder.append(in);
            } else if (c.getOperator() == Condition.Operator.isNull || c.getOperator() == Condition.Operator.isNotNull) {
                if (alias != null && alias.trim().length() > 0) {
                    builder.append(String.format("%s.%s %s", alias, columnName, c.getOperator().getValue()));
                } else {
                    builder.append(String.format("%s %s", columnName, c.getOperator().getValue()));
                }
            } else {
                String dyadic = whereDyadic(columnName, c, jdbcType, i, "param.conditionList[%d].orList[" + j + "].value");
                builder.append(dyadic);
            }
            if (j < orList.size() - 1) {
                builder.append(" OR ");
            }
        }
        builder.append(" )");
        return builder.toString();
    }

    private static String whereIn(String columnName, Condition condition, String jdbcType, int i, String format) {
        StringBuilder builder = new StringBuilder();
        String alias = condition.getAlias();
        if (alias != null && alias.trim().length() > 0) {
            builder.append(String.format("%s.%s %s (", alias, columnName, Condition.Operator.in.getValue()));
        } else {
            builder.append(String.format("%s %s (", columnName, Condition.Operator.in.getValue()));
        }
        Collection<?> collections = (Collection<?>) condition.getValue();
        for (int j = 0; j < collections.size(); j++) {
            builder.append("#{").append(String.format(format, i, j));
            if (!jdbcType.equals("")) {
                builder.append(String.format(",jdbcType=%s", jdbcType));
            }
            builder.append("}");
            if (j < collections.size() - 1) {
                builder.append(", ");
            }
        }
        builder.append(") ");
        return builder.toString();
    }

    private static String whereDyadic(String columnName, Condition condition, String jdbcType, int i, String format) {
        StringBuilder builder = new StringBuilder();
        String alias = condition.getAlias();
        if (alias != null && alias.trim().length() > 0) {
            builder.append(String.format("%s.%s %s #{", alias, columnName, condition.getOperator().getValue()));
        } else {
            builder.append(String.format("%s %s #{", columnName, condition.getOperator().getValue()));
        }
        builder.append(String.format(format, i));
        if (!jdbcType.equals("")) {
            builder.append(String.format(",jdbcType=%s", jdbcType));
        }
        builder.append("}");
        return builder.toString();
    }

    static void orderBy(List<OrderBy> orderBys) {
        SQL sql = localSQL.get();
        // 默认为id顺排
        /*if (orderBys.isEmpty()) {
            sql.ORDER_BY("id ASC");
        }*/
        for (OrderBy orderBy : orderBys) {
            String alias = orderBy.getAlias();
            String propertyName = orderBy.getProperty();
            Class<?> clazz = localEntities.get().get(alias);
            String columnName = findColumnName(clazz, findGetterMethod(propertyName, clazz), propertyName);


            String direction = "";
            if (orderBy.getDirection() == OrderBy.Direction.asc) {
                direction = "ASC";
            } else if (orderBy.getDirection() == OrderBy.Direction.desc) {
                direction = "DESC";
            }

            if (alias != null && alias.trim().length() > 0) {
                sql.ORDER_BY(String.format("%s.%s %s", alias, columnName, direction));
            } else {
                sql.ORDER_BY(String.format("%s %s", columnName, direction));
            }
        }
    }

    static void initEntities(Map<String, Class<?>> clazzMap) {
        localEntities.set(clazzMap);
        localMultiProperties.set(null);
    }

    static List<String> findTableNameList() {
        List<String> tableNameList = new ArrayList<>();
        for (Map.Entry<String, Class<?>> entry : localEntities.get().entrySet()) {
            Class<?> clazz = entry.getValue();
            String tableName  = findTableName(clazz);
            if (entry.getKey() != null && entry.getKey().trim().length() > 0) {
                tableName = tableName + " " + entry.getKey();
            }
            tableNameList.add(tableName);
        }
        return tableNameList;
    }

    static String findTableName(Class<?> clazz) {
        Table table = clazz.getAnnotation(Table.class);
        if (table == null) {
            throw new IllegalArgumentException(String.format("Entity '%s' is not bound some table name.", clazz.getName()));
        }
        String value = table.value();
        if ("".equals(value)) {
            value = CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, clazz.getSimpleName());
        }
        return value;
    }

    private static String findColumnName(Class<?> clazz, Method getter, String propertyName) {
        Column column = findColumn(clazz, getter, propertyName);
        String value = column == null ? "" : column.value();
        if ("".equals(value)) {
            value = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, propertyName);
        }
        return value;
    }

    private static Column findColumn(Class<?> clazz, Method getter, String propertyName) {
        Column column = getter.getAnnotation(Column.class);
        if (column == null) {
            java.lang.reflect.Field field = findField(clazz, propertyName);
            if (field != null) {
                column = field.getAnnotation(Column.class);
            }
        }
        return column;
    }

    private static java.lang.reflect.Field findField(Class<?> clazz, String propertyName) {
        if (clazz.equals(Object.class)) {
            return null;
        }
        if (propertyName.equals(IdEntity.ID_PN)){
            return findField(clazz.getSuperclass(), propertyName);
        }
        try {
            return clazz.getDeclaredField(propertyName);
        } catch (NoSuchFieldException e) {
            logger.info("class[{}] no field[{}]", clazz.getName(), propertyName);
        }
        return findField(clazz.getSuperclass(), propertyName);
    }

    private static Method findGetterMethod(String propertyName, Class<?> clazz) {
        Map<String, PropertyDescriptor> properties = findProperties(clazz);
        PropertyDescriptor descriptor = properties.get(propertyName);
        if (descriptor == null) {
            throw new IllegalArgumentException(
                    String.format("The property '%s' is not in properties of the entity '%s'.",
                            propertyName, clazz.getName()));
        }
        return descriptor.getReadMethod();
    }

    private static String findJdbcType(Class<?> clazz, Method getter, String propertyName) {
        Column column = findColumn(clazz, getter, propertyName);
        return column == null ? "" : column.jdbcType();
    }

    private static Map<String, PropertyDescriptor> findProperties(Class<?> clazz) {
        Map<Class<?>, Map<String, PropertyDescriptor>> mm = localMultiProperties.get();
        if (mm == null) {
            mm = new HashMap<>();
        }

        Map<String, PropertyDescriptor> properties = mm.get(clazz);

        if (properties != null) {
            return properties;
        }
        properties = new LinkedHashMap<>();
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(clazz);
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor property : propertyDescriptors) {
                if ("class".equals(property.getName())) {
                    continue;
                }
                Method getter = property.getReadMethod();
                Transient t = getter.getAnnotation(Transient.class);
                if (t != null && t.value()) {
                    continue;
                }
                properties.put(property.getName(), property);
            }
        } catch (IntrospectionException e) {
            logger.error(String.format("Entity '%s' can not get bean info.", clazz.getName()), e);
        }
        mm.put(clazz, properties);
        localMultiProperties.set(mm);
        return properties;
    }

    private static Object getValue(Method getter, IdEntity entity) {
        Object obj = null;
        try {
            obj = getter.invoke(entity);
        } catch (IllegalAccessException | InvocationTargetException e) {
            logger.error(String.format("Entity '%s' can't get value by method(%s).", entity.getClass(), getter.toGenericString()), e);
        }
        return obj;
    }
}
