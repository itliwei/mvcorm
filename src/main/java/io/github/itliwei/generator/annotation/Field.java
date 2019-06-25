package io.github.itliwei.generator.annotation;

import java.lang.annotation.*;

/**
 * Created by admin on 16/8/31.
 */

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Field {

	String label() default "";

	String description() default "";

	int order() default 999;
}
