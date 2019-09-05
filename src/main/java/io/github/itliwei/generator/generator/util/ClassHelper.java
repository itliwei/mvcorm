package io.github.itliwei.generator.generator.util;

import com.google.common.collect.Lists;
import io.github.itliwei.mvcorm.orm.annotation.Table;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.stream.Collectors;

public class ClassHelper {

    private static final Logger logger = LoggerFactory.getLogger(ClassHelper.class);


    public static Set<Class<?>> getClasses(String pack) {
        return PackageUtil.getClasses(pack);

    }

    public static Map<String, Class<?>> getQuerysClasses(String queryModelPackage) {
        return getClasses(queryModelPackage).stream().collect(Collectors.toMap(Class::getSimpleName, k -> newIns(k.getSimpleName())));
    }

    private static Class<?> newIns(String className) {
        try {
            return Class.forName(className);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public static String resolveTableName(Class<?> entityClass) {
        Table table = entityClass.getDeclaredAnnotation(Table.class);
        if (table == null) {
            throw new IllegalArgumentException("@Table注解缺失");
        }
        if (StringUtils.isNotBlank(table.value())) {
            return table.value();
        } else {
            return StringUtils.camelToUnderline(entityClass.getSimpleName());
        }
    }

    public static boolean isNotStaticField(Field field) {
        return !Modifier.isStatic(field.getModifiers());
    }

    public static List<Field> getFields(Class<?> clazz) {
        List<Field> fields = new ArrayList<>();
        List<Class<?>> classes = Lists.newArrayList();
        for (; clazz != null && clazz != Object.class; clazz = clazz.getSuperclass()) {
            classes.add(clazz);
        }
        ListIterator<Class<?>> itr=classes.listIterator();
        for(int i=classes.size();i>0;i--){
            try {
                Field[] declaredFields = classes.get(i-1).getDeclaredFields();
                fields.addAll(Arrays.asList(declaredFields));
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }
        return fields;
    }
}
