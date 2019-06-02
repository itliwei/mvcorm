package io.github.itliwei.mvcorm.generator.annotation.elementui;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ElementClass {
    String path() default "";
}