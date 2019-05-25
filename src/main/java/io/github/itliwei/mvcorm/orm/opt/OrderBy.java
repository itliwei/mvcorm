package io.github.itliwei.mvcorm.orm.opt;

/**
 * 排序
 * Created by cheshun on 2016/5/16.
 */
public class OrderBy {

    /**
     * 方向
     */
    public enum Direction {

        /** 递增 */
        asc,

        /** 递减 */
        desc;

        /**
         * 从String中获取Direction
         *
         * @param value
         *            值
         * @return String对应的Direction
         */
        public static Direction fromString(String value) {
            return Direction.valueOf(value.toLowerCase());
        }
    }

    /** 默认方向 */
    private static final Direction DEFAULT_DIRECTION = Direction.desc;

    /** 属性 */
    private String property;

    private String alias;

    /** 方向 */
    private Direction direction = DEFAULT_DIRECTION;

    /**
     * @param property
     *            属性
     * @param direction
     *            方向
     */
    private OrderBy(String property, String alias, Direction direction) {
        this.property = property;
        this.alias = alias;
        this.direction = direction;
    }

    /**
     * 返回递增排序
     *
     * @param property
     *            属性
     * @return 递增排序
     */
    public static io.github.itliwei.mvcorm.orm.opt.OrderBy asc(String property) {
        return new io.github.itliwei.mvcorm.orm.opt.OrderBy(property, "", Direction.asc);
    }

    public static io.github.itliwei.mvcorm.orm.opt.OrderBy asc(String property, String alias) {
        return new io.github.itliwei.mvcorm.orm.opt.OrderBy(property, alias, Direction.asc);
    }

    /**
     * 返回递减排序
     *
     * @param property
     *            属性
     * @return 递减排序
     */
    public static io.github.itliwei.mvcorm.orm.opt.OrderBy desc(String property) {
        return new io.github.itliwei.mvcorm.orm.opt.OrderBy(property, "", Direction.desc);
    }

    public static io.github.itliwei.mvcorm.orm.opt.OrderBy desc(String property, String alias) {
        return new io.github.itliwei.mvcorm.orm.opt.OrderBy(property, alias, Direction.desc);
    }

    /**
     * 获取属性
     *
     * @return 属性
     */
    public String getProperty() {
        return property;
    }

    public String getAlias() {
        return alias;
    }

    /**
     * 获取方向
     *
     * @return 方向
     */
    public Direction getDirection() {
        return direction;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        io.github.itliwei.mvcorm.orm.opt.OrderBy orderBy = (io.github.itliwei.mvcorm.orm.opt.OrderBy) o;

        if (!property.equals(orderBy.property)) return false;
        if (!alias.equals(orderBy.alias)) return false;
        return direction == orderBy.direction;
    }

    @Override
    public int hashCode() {
        int result = property.hashCode();
        result = 31 * result + alias.hashCode();
        result = 31 * result + direction.hashCode();
        return result;
    }
}
