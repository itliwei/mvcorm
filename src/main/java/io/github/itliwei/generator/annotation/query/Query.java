package io.github.itliwei.generator.annotation.query;


import io.github.itliwei.mvcorm.orm.opt.Condition;

import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Query {
    Condition.Operator[] value() default {};
}