package io.github.itliwei.mvcorm.orm.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author : liwei
 * @date : 2019/05/11 15:56
 */
public class BeanUtil {
    /**
     * 获取属性名数组
     * */
    public static List<String> getFiledNames(Object o){
        Field[] fields=o.getClass().getDeclaredFields();
        List<String> fieldNames =
                Arrays.stream(fields).map(c -> c.getName()).collect(Collectors.toList());

        return fieldNames;
    }

    /* 根据属性名获取属性值
    * */
    public static Object getFieldValueByName(String fieldName, Object o) {
        try {
            String firstLetter = fieldName.substring(0, 1).toUpperCase();
            String getter = "get" + firstLetter + fieldName.substring(1);
            Method method = o.getClass().getMethod(getter, new Class[] {});
            Object value = method.invoke(o, new Object[] {});
            return value;
        } catch (Exception e) {

            return null;
        }
    }
}
