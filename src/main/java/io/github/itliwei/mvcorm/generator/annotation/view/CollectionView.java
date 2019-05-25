package io.github.itliwei.mvcorm.generator.annotation.view;

import io.github.itliwei.mvcorm.generator.annotation.Field;

import java.lang.annotation.*;
import java.util.ArrayList;

/**
 * Created by admin on 16/8/31.
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Repeatable(CollectionViews.class)
public @interface CollectionView {

	String name();


	String[] groups() default {};

	Class<?> type() default ArrayList.class;

	Class<?> elementType() default Object.class;

	String elementGroup() default "";

	Field field() default @Field(label = "");

}
