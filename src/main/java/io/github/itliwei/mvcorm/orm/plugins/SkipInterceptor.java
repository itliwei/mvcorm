package io.github.itliwei.mvcorm.orm.plugins;

import io.github.itliwei.mvcorm.orm.opt.Skipped;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.DefaultReflectorFactory;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.ReflectorFactory;
import org.apache.ibatis.reflection.factory.DefaultObjectFactory;
import org.apache.ibatis.reflection.factory.ObjectFactory;
import org.apache.ibatis.reflection.wrapper.DefaultObjectWrapperFactory;
import org.apache.ibatis.reflection.wrapper.ObjectWrapperFactory;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.util.Properties;

/**
 * Mybatis 分页拦截器
 * Created by cheshun on 2016/4/14.
 */
@Intercepts({@Signature(type =StatementHandler.class, method = "prepare", args = {Connection.class, Integer.class})})
public class SkipInterceptor implements Interceptor {
    private static final Logger logger = LoggerFactory.getLogger(io.github.itliwei.mvcorm.orm.plugins.SkipInterceptor.class);
    private static final ObjectFactory DEFAULT_OBJECT_FACTORY = new DefaultObjectFactory();
    private static final ObjectWrapperFactory DEFAULT_OBJECT_WRAPPER_FACTORY = new DefaultObjectWrapperFactory();
    private static final ReflectorFactory DEFAULT_REFLECTOR_FACTORY = new DefaultReflectorFactory();
    /**
     * 数据库类型(默认为mysql)
     */
    private static final String DEFAULT_DIALECT = "mysql";
    /**
     * 需要拦截的ID(正则匹配)
     */
    private static final String DEFAULT_PAGE_SQL_ID = ".*Page$";

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        StatementHandler statementHandler = (StatementHandler) invocation.getTarget();
        MetaObject metaStatementHandler = MetaObject.forObject(statementHandler,
                DEFAULT_OBJECT_FACTORY, DEFAULT_OBJECT_WRAPPER_FACTORY, DEFAULT_REFLECTOR_FACTORY);
        // 分离代理对象链(由于目标类可能被多个拦截器拦截，从而形成多次代理，通过下面的两次循环
        // 可以分离出最原始的的目标类)
        while (metaStatementHandler.hasGetter("h")) {
            Object object = metaStatementHandler.getValue("h");
            metaStatementHandler = MetaObject.forObject(object, DEFAULT_OBJECT_FACTORY,
                    DEFAULT_OBJECT_WRAPPER_FACTORY, DEFAULT_REFLECTOR_FACTORY);
        }
        // 分离最后一个代理对象的目标类
        while (metaStatementHandler.hasGetter("target")) {
            Object object = metaStatementHandler.getValue("target");
            metaStatementHandler = MetaObject.forObject(object, DEFAULT_OBJECT_FACTORY,
                    DEFAULT_OBJECT_WRAPPER_FACTORY, DEFAULT_REFLECTOR_FACTORY);
        }
        Configuration configuration = (Configuration) metaStatementHandler.
                getValue("delegate.configuration");
        Properties variables = configuration.getVariables();
        // 数据库类型(默认为mysql)
        String dialect = "";
        // 需要拦截的ID(正则匹配)
        String pageSqlId = "";
        if (variables != null) {
            dialect = variables.getProperty("dialect");
            pageSqlId = variables.getProperty("pageSqlId");
        }
        if (null == dialect || "".equals(dialect)) {
            logger.debug("Property dialect is not setted,use default 'mysql' ");
            dialect = DEFAULT_DIALECT;
        }
        if (null == pageSqlId || "".equals(pageSqlId)) {
            logger.debug("Property pageSqlId is not setted,use default '.*Page$' ");
            pageSqlId = DEFAULT_PAGE_SQL_ID;
        }
        MappedStatement mappedStatement = (MappedStatement)
                metaStatementHandler.getValue("delegate.mappedStatement");
        // 只重写需要分页的sql语句。通过MappedStatement的ID匹配，默认重写以Page结尾的
        //  MappedStatement的sql
        if (mappedStatement.getId().matches(pageSqlId)) {
            BoundSql boundSql = (BoundSql) metaStatementHandler.getValue("delegate.boundSql");
            Object parameterObject = boundSql.getParameterObject();
            if (parameterObject == null) {
                throw new NullPointerException("parameterObject is null!");
            } else {
                // 分页参数作为参数对象parameterObject的一个属性
                Skipped skipped = (Skipped) metaStatementHandler.getValue("delegate.boundSql.parameterObject.skipped");
                String sql = boundSql.getSql();
                // 重写sql
                String pageSql = buildPageSql(sql, skipped, dialect);
                metaStatementHandler.setValue("delegate.boundSql.sql", pageSql);
                // 采用物理分页后，就不需要mybatis的内存分页了，所以重置下面的两个参数
                metaStatementHandler.setValue("delegate.rowBounds.offset",
                        RowBounds.NO_ROW_OFFSET);
                metaStatementHandler.setValue("delegate.rowBounds.limit", RowBounds.NO_ROW_LIMIT);
            }
        }
        // 将执行权交给下一个拦截器
        return invocation.proceed();
    }

    private String buildPageSql(String sql, Skipped skipped, String dialect) {
        String pageSql = sql;
        if (skipped != null) {
            int skip = skipped.getSkip();
            int count = skipped.getCount();
            if ("mysql".equals(dialect)) {
                pageSql = buildPageSqlForMysql(sql, skip, count);
            } else if ("oracle".equals(dialect)) {
                pageSql = buildPageSqlForOracle(sql, skip, skip + count);
            }
        }
        return pageSql;
    }

    private String buildPageSqlForMysql(String sql, int skip, int count) {
        return sql + " limit " + skip + "," + count;
    }

    private String buildPageSqlForOracle(String sql, int begin, int end) {
        return "SELECT * FROM ( SELECT temp.*, ROWNUM row_id from ( " + sql +
                " ) temp WHERE ROWNUM <= " + end +
                ") WHERE row_id > " + begin;
    }

    @Override
    public Object plugin(Object target) {
        // 当目标类是StatementHandler类型时，才包装目标类，否者直接返回目标本身,减少目标被代理的
        // 次数
        if (target instanceof StatementHandler) {
            return Plugin.wrap(target, this);
        } else {
            return target;
        }
    }

    @Override
    public void setProperties(Properties properties) {

    }
}
