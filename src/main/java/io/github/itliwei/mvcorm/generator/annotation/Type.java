package io.github.itliwei.mvcorm.generator.annotation;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Type {

	String label();

	String description() default "";

}