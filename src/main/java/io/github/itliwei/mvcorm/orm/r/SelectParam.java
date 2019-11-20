package io.github.itliwei.mvcorm.orm.r;

import io.github.itliwei.mvcorm.orm.opt.Condition;
import io.github.itliwei.mvcorm.orm.opt.Field;
import io.github.itliwei.mvcorm.orm.opt.OrderBy;
import io.github.itliwei.mvcorm.orm.opt.Skipped;

import java.util.*;

/**
 * 查询语句构造参数
 * Created by liwei on 17/8/22.
 */
public class SelectParam {

    private Map<String, Class<?>> clazzMap;

    private Class<?> resultType;

    private boolean isSelectMap;

    private boolean hasLimit;

    private int skip;

    private int size;

    private boolean isUseMaster;

    private List<Condition> conditionList;

    private List<OrderBy> orderByList;

    private Set<Field> fieldList;

    public void addClazzMap(Class<?> clazz, String alias) {
        if (clazzMap == null) {
            clazzMap = new LinkedHashMap<>();
        }
        clazzMap.put(alias, clazz);
    }

    public void addField(Field field) {
        if (fieldList == null) {
            fieldList = new HashSet<>();
        }
        fieldList.add(field);
        isSelectMap = true;
    }

    public void addField(Collection<Field> fields) {
        if (fieldList == null) {
            fieldList = new HashSet<>();
        }
        fieldList.addAll(fields);
        isSelectMap = true;
    }

    public void addCondition(Condition condition) {
        if (conditionList == null) {
            conditionList = new ArrayList<>();
        }
        conditionList.add(condition);
    }

    public void addCondition(Collection<Condition> conditions) {
        if (conditionList == null) {
            conditionList = new ArrayList<>();
        }
        conditionList.addAll(conditions);
    }

    public void addOrderBy(OrderBy orderBy) {
        if (orderByList == null) {
            orderByList = new ArrayList<>();
        }
        orderByList.add(orderBy);
    }

    public void addOrderBy(Collection<OrderBy> orderBys) {
        if (orderByList == null) {
            orderByList = new ArrayList<>();
        }
        orderByList.addAll(orderBys);
    }

    public void limit(int skip, int size) {
        this.skip = skip;
        this.size = size;
        hasLimit = true;
    }

    public void useMaster() {
        this.isUseMaster = true;
    }

    public Map<String, Class<?>> getClazzMap() {
        return clazzMap;
    }

    public Class<?> getResultType() {
        return resultType;
    }

    public void setResultType(Class<?> resultType) {
        this.resultType = resultType;
    }

    public boolean isSelectMap() {
        return isSelectMap;
    }

    public Skipped getSkipped() {
        if (hasLimit) {
            return new Skipped(skip, size);
        }
        return null;
    }

    public boolean isUseMaster() {
        return isUseMaster;
    }

    public Set<Field> getFieldList() {
        return fieldList;
    }

    public List<Condition> getConditionList() {
        return conditionList;
    }

    public List<OrderBy> getOrderByList() {
        return orderByList;
    }

}
