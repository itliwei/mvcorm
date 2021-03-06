package io.github.itliwei.generator.annotation.controller;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ControllerClass {
    String path() default "";
    String name() default "";
    String desc() default "api文档";
}