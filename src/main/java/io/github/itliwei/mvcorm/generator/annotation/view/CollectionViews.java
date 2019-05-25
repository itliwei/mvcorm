package io.github.itliwei.mvcorm.generator.annotation.view;

import java.lang.annotation.*;

/**
 * Created by admin on 16/8/31.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CollectionViews {

	CollectionView[] value();
}
