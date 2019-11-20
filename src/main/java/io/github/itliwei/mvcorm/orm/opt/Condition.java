package io.github.itliwei.mvcorm.orm.opt;

import com.google.common.collect.Lists;

import java.util.Collection;
import java.util.List;

/**
 * 条件
 * Created by liwei on 2016/5/15.
 */
public class Condition {

    /**
     * 运算符
     */
    public enum Operator {

        /**
         * 多表关联
         */
        on("=","ON"),

        /**
         * 或
         */
        or("OR","OR"),

        /**
         * 等于
         */
        eq("=","EQ"),

        /**
         * 不等于
         */
        ne("!=","NE"),

        /**
         * 大于
         */
        gt(">","GT"),

        /**
         * 小于
         */
        lt("<","LT"),

        /**
         * 大于等于
         */
        ge(">=","GE"),

        /**
         * 小于等于
         */
        le("<=","LE"),

        /**
         * 相似
         */
        like("LIKE","LK"),

        /**
         * 包含
         */
        in("IN","IN"),

        /**
         * 为Null
         */
        isNull("IS NULL","NU"),

        /**
         * 不为Null
         */
        isNotNull("IS NOT NULL","NN");

        private String value;

        private String name;

        Operator(String value,String name) {
            this.value = value;
            this.name = name;
        }

        public String getValue() {
            return value;
        }

        public String getName() {
            return name;
        }
        /**
         * 从String中获取Operator
         *
         * @param value 值
         * @return String对应的operator
         */
        public static Operator fromString(String value) {
            return Operator.valueOf(value.toLowerCase());
        }

        public static io.github.itliwei.mvcorm.orm.opt.Condition fromName(String fieldName, String operateName, Object fieldValue) {
            Operator[] values = Operator.values();
            for (Operator operator : values){
                if (operator.name.equals(operateName)){
                    return new io.github.itliwei.mvcorm.orm.opt.Condition(fieldName,"",operator,fieldValue);
                }
            }
            return null;
        }
    }

    /** 属性 */
    private String property;

    /** 别名 */
    private String alias;

    /** 运算符 */
    private Operator operator;

    /** 值 */
    private Object value;

    /** 或条件 */
    private List<io.github.itliwei.mvcorm.orm.opt.Condition> orList;

    private Condition() {}

    /**
     * 初始化一个新创建的Condition对象
     *
     * @param property
     *            属性
     * @param operator
     *            运算符
     * @param value
     *            值
     */
    private Condition(String property, String alias, Operator operator, Object value) {
        this.property = property;
        this.alias = alias;
        this.operator = operator;
        this.value = value;
    }

    /**
     * 返回多表关联条件,只有在多表查询的时候使用
     * 表A的别名a,表B的别名b
     * 使用关联条件时如下
     * on("a.xxx", "b.yyy")
     * @param lProperty 左字段
     * @param rProperty 右字段
     */
    public static io.github.itliwei.mvcorm.orm.opt.Condition on(String lProperty, String rProperty) {
        return new io.github.itliwei.mvcorm.orm.opt.Condition(lProperty, "", Operator.on, rProperty);
    }

    /**
     * 返回或条件
     *
     * @param conditions
     *          多个或条件
     * @return 或条件
     */
    public static io.github.itliwei.mvcorm.orm.opt.Condition or(io.github.itliwei.mvcorm.orm.opt.Condition... conditions) {
        io.github.itliwei.mvcorm.orm.opt.Condition condition = new io.github.itliwei.mvcorm.orm.opt.Condition();
        condition.operator = Operator.or;
        condition.orList = Lists.newArrayList();
        for (io.github.itliwei.mvcorm.orm.opt.Condition c : conditions) {
            if (c.getOperator() != Operator.or) {
                condition.orList.add(c);
            }
        }
        return condition;
    }

    /**
     * 返回等于条件
     *
     * @param property
     *            属性
     * @param alias
     *            别名
     * @param value
     *            值
     * @return 等于条件
     */
    public static io.github.itliwei.mvcorm.orm.opt.Condition eq(String property, String alias, Object value) {
        return new io.github.itliwei.mvcorm.orm.opt.Condition(property, alias, Operator.eq, value);
    }

    public static io.github.itliwei.mvcorm.orm.opt.Condition eq(String property, Object value) {
        return new io.github.itliwei.mvcorm.orm.opt.Condition(property, "", Operator.eq, value);
    }

    /**
     * 返回等于条件
     *
     * @param property
     *            属性
     * @param alias
     *            别名
     * @param value
     *            值
     * @param ignoreCase
     *            忽略大小写
     * @return 等于条件
     */
    public static io.github.itliwei.mvcorm.orm.opt.Condition eq(String property, String alias, Object value, boolean ignoreCase) {
        if (ignoreCase && value instanceof String) {
            return new io.github.itliwei.mvcorm.orm.opt.Condition(property, alias, Operator.eq, ((String) value).toLowerCase());
        }
        return new io.github.itliwei.mvcorm.orm.opt.Condition(property, alias, Operator.eq, value);
    }

    public static io.github.itliwei.mvcorm.orm.opt.Condition eq(String property, Object value, boolean ignoreCase) {
        if (ignoreCase && value instanceof String) {
            return new io.github.itliwei.mvcorm.orm.opt.Condition(property, "", Operator.eq, ((String) value).toLowerCase());
        }
        return new io.github.itliwei.mvcorm.orm.opt.Condition(property, "", Operator.eq, value);
    }

    /**
     * 返回不等于条件
     *
     * @param property
     *            属性
     * @param alias
     *            别名
     * @param value
     *            值
     * @return 不等于条件
     */
    public static io.github.itliwei.mvcorm.orm.opt.Condition ne(String property, String alias, Object value) {
        return new io.github.itliwei.mvcorm.orm.opt.Condition(property, alias, Operator.ne, value);
    }

    public static io.github.itliwei.mvcorm.orm.opt.Condition ne(String property, Object value) {
        return new io.github.itliwei.mvcorm.orm.opt.Condition(property, "", Operator.ne, value);
    }

    /**
     * 返回不等于条件
     *
     * @param property
     *            属性
     * @param alias
     *            别名
     * @param value
     *            值
     * @param ignoreCase
     *            忽略大小写
     * @return 不等于条件
     */
    public static io.github.itliwei.mvcorm.orm.opt.Condition ne(String property, String alias, Object value, boolean ignoreCase) {
        if (ignoreCase && value instanceof String) {
            return new io.github.itliwei.mvcorm.orm.opt.Condition(property, alias, Operator.ne, ((String) value).toLowerCase());
        }
        return new io.github.itliwei.mvcorm.orm.opt.Condition(property, alias, Operator.ne, value);
    }

    public static io.github.itliwei.mvcorm.orm.opt.Condition ne(String property, Object value, boolean ignoreCase) {
        if (ignoreCase && value instanceof String) {
            return new io.github.itliwei.mvcorm.orm.opt.Condition(property, "", Operator.ne, ((String) value).toLowerCase());
        }
        return new io.github.itliwei.mvcorm.orm.opt.Condition(property, "", Operator.ne, value);
    }

    /**
     * 返回大于条件
     *
     * @param property
     *            属性
     * @param alias
     *            别名
     * @param value
     *            值
     * @return 大于条件
     */
    public static io.github.itliwei.mvcorm.orm.opt.Condition gt(String property, String alias, Object value) {
        return new io.github.itliwei.mvcorm.orm.opt.Condition(property, alias, Operator.gt, value);
    }

    public static io.github.itliwei.mvcorm.orm.opt.Condition gt(String property, Object value) {
        return new io.github.itliwei.mvcorm.orm.opt.Condition(property, "", Operator.gt, value);
    }

    /**
     * 返回小于条件
     *
     * @param property
     *            属性
     * @param alias
     *            别名
     * @param value
     *            值
     * @return 小于条件
     */
    public static io.github.itliwei.mvcorm.orm.opt.Condition lt(String property, String alias, Object value) {
        return new io.github.itliwei.mvcorm.orm.opt.Condition(property, alias, Operator.lt, value);
    }

    public static io.github.itliwei.mvcorm.orm.opt.Condition lt(String property, Object value) {
        return new io.github.itliwei.mvcorm.orm.opt.Condition(property, "", Operator.lt, value);
    }

    /**
     * 返回大于等于条件
     *
     * @param property
     *            属性
     * @param alias
     *            别名
     * @param value
     *            值
     * @return 大于等于条件
     */
    public static io.github.itliwei.mvcorm.orm.opt.Condition ge(String property, String alias, Object value) {
        return new io.github.itliwei.mvcorm.orm.opt.Condition(property, alias, Operator.ge, value);
    }

    public static io.github.itliwei.mvcorm.orm.opt.Condition ge(String property, Object value) {
        return new io.github.itliwei.mvcorm.orm.opt.Condition(property, "", Operator.ge, value);
    }

    /**
     * 返回小于等于条件
     *
     * @param property
     *            属性
     * @param alias
     *            别名
     * @param value
     *            值
     * @return 小于等于条件
     */
    public static io.github.itliwei.mvcorm.orm.opt.Condition le(String property, String alias, Object value) {
        return new io.github.itliwei.mvcorm.orm.opt.Condition(property, alias, Operator.le, value);
    }

    public static io.github.itliwei.mvcorm.orm.opt.Condition le(String property, Object value) {
        return new io.github.itliwei.mvcorm.orm.opt.Condition(property, "", Operator.le, value);
    }

    /**
     * 返回相似条件
     *
     * @param property
     *            属性
     * @param alias
     *            别名
     * @param value
     *            值
     * @return 相似条件
     */
    public static io.github.itliwei.mvcorm.orm.opt.Condition like(String property, String alias, Object value) {
        return new io.github.itliwei.mvcorm.orm.opt.Condition(property, alias, Operator.like, value);
    }

    public static io.github.itliwei.mvcorm.orm.opt.Condition like(String property, Object value) {
        return new io.github.itliwei.mvcorm.orm.opt.Condition(property, "", Operator.like, value);
    }

    /**
     * 返回包含条件
     *
     * @param property
     *            属性
     * @param alias
     *            别名
     * @param value
     *            值
     * @return 包含条件
     */
    public static io.github.itliwei.mvcorm.orm.opt.Condition in(String property, String alias, Object... value) {
        if (value.length == 1 && Collection.class.isAssignableFrom(value[0].getClass())) {
            return new io.github.itliwei.mvcorm.orm.opt.Condition(property, alias, Operator.in, value[0]);
        }
        return new io.github.itliwei.mvcorm.orm.opt.Condition(property, alias, Operator.in, Lists.newArrayList(value));
    }

    public static io.github.itliwei.mvcorm.orm.opt.Condition in(String property, Object... value) {
        if (value.length == 1 && Collection.class.isAssignableFrom(value[0].getClass())) {
            return new io.github.itliwei.mvcorm.orm.opt.Condition(property, "", Operator.in, value[0]);
        }
        return new io.github.itliwei.mvcorm.orm.opt.Condition(property, "", Operator.in, Lists.newArrayList(value));
    }

    /**
     * 返回为Null条件
     *
     * @param property
     *            属性
     * @param alias
     *            别名
     * @return 为Null条件
     */
    public static io.github.itliwei.mvcorm.orm.opt.Condition isNull(String property, String alias) {
        return new io.github.itliwei.mvcorm.orm.opt.Condition(property, alias, Operator.isNull, null);
    }

    public static io.github.itliwei.mvcorm.orm.opt.Condition isNull(String property) {
        return new io.github.itliwei.mvcorm.orm.opt.Condition(property, "", Operator.isNull, null);
    }

    /**
     * 返回不为Null条件
     *
     * @param property
     *            属性
     * @param alias
     *            别名
     * @return 不为Null条件
     */
    public static io.github.itliwei.mvcorm.orm.opt.Condition isNotNull(String property, String alias) {
        return new io.github.itliwei.mvcorm.orm.opt.Condition(property, alias, Operator.isNotNull, null);
    }

    public static io.github.itliwei.mvcorm.orm.opt.Condition isNotNull(String property) {
        return new io.github.itliwei.mvcorm.orm.opt.Condition(property, "", Operator.isNotNull, null);
    }

    /**
     * 获取属性
     *
     * @return 属性
     */
    public String getProperty() {
        return property;
    }

    /**
     * 获取别名
     * @return 别名
     */
    public String getAlias() {
        return alias;
    }

    /**
     * 获取运算符
     *
     * @return 运算符
     */
    public Operator getOperator() {
        return operator;
    }

    /**
     * 获取值
     *
     * @return 值
     */
    public Object getValue() {
        return value;
    }

    public List<io.github.itliwei.mvcorm.orm.opt.Condition> getOrList() {
        return orList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        io.github.itliwei.mvcorm.orm.opt.Condition condition = (io.github.itliwei.mvcorm.orm.opt.Condition) o;

        if (!property.equals(condition.property)) return false;
        if (!alias.equals(condition.alias)) return false;
        if (operator != condition.operator) return false;
        if (!value.equals(condition.value)) return false;
        return orList.equals(condition.orList);
    }

    @Override
    public int hashCode() {
        int result = property.hashCode();
        result = 31 * result + alias.hashCode();
        result = 31 * result + operator.hashCode();
        result = 31 * result + value.hashCode();
        result = 31 * result + orList.hashCode();
        return result;
    }
}
