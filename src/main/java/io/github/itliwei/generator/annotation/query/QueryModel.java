package io.github.itliwei.generator.annotation.query;

import io.github.itliwei.generator.annotation.Field;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface QueryModel {
    String name() default "";
}