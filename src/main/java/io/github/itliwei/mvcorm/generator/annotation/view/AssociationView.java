package io.github.itliwei.mvcorm.generator.annotation.view;

import io.github.itliwei.mvcorm.generator.annotation.Field;

import java.lang.annotation.*;

/**
 * Created by admin on 16/8/31.
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Repeatable(AssociationViews.class)
public @interface AssociationView {

	String name() default "";

	String[] groups() default {};

	Class<?> type() default Object.class;

	String associationGroup() default "";

	Field field() default @Field(label = "");
}
