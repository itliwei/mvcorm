package io.github.itliwei.mvcorm.orm.annotation;

import java.lang.annotation.*;

/**
 * 实体表映射
 * Created by admin on 2019/4/16.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Table {

    /**
     * 表名
     */
    String value() default "";

    /**
     * 注释
     * @return
     */
    String comment() default "";

    /**
     * ID序列
     */
    String sequence() default "";
}
