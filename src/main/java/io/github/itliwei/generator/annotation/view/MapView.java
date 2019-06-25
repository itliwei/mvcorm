package io.github.itliwei.generator.annotation.view;

import io.github.itliwei.generator.annotation.Field;

import java.lang.annotation.*;
import java.util.HashMap;

/**
 * Created by admin on 16/8/31.
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Repeatable(MapViews.class)
public @interface MapView {

	String name();


	String[] groups() default {};

	Class<?> type() default HashMap.class;

	Class<?> keyType() default String.class;

	Class<?> valueType() default Object.class;

	String keyGroup() default "";

	String valueGroup() default "";

	Field field() default @Field(label = "");

}

