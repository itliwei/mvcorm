package io.github.itliwei.mvcorm.orm;

import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by liwei on 17/9/13.
 */
public class Utils {
    private static final Logger logger = LoggerFactory.getLogger(Utils.class);

    public static Class<?>[] findGenericType(Class<?> thisClass, Class<?> clazz) {
        List<Type[]> typesList = Arrays.stream(thisClass.getGenericInterfaces())
                .map(type -> {
                    if (type instanceof ParameterizedType) {
                        return (ParameterizedType) type;
                    }
                    return null;
                })
                .filter(parameterizedType -> parameterizedType != null && parameterizedType.getRawType() == clazz)
                .map(ParameterizedType::getActualTypeArguments)
                .collect(Collectors.toList());
        if (typesList.isEmpty()) {
            typesList.add(new Type[]{Object.class});
        }
        Type[] types = typesList.get(0);

        return Arrays.stream(types)
                .map(type -> {
                    if (type instanceof ParameterizedType) {
                        return ((ParameterizedType) type).getRawType();
                    }
                    return type;
                })
                .toArray(Class<?>[]::new);
    }

    public static <T> List<T> entityList(Class<T> clazz, List<Map<String, Object>> list) {
        List<T> ts = Lists.newArrayListWithCapacity(list.size());
        for (Map<String, Object> result : list) {
            ts.add(hashToObject(result, clazz));
        }
        return ts;
    }

    public static <T> T hashToObject(Map<String, Object> map, Class<T> clazz) {
        if (map == null || map.isEmpty()) {
            return null;
        }
        try {
            T obj = clazz.newInstance();
            org.apache.commons.beanutils.BeanUtils.populate(obj, map);
            return obj;
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException e) {
            logger.error("To object error. class:{}", clazz.getName());
            throw new RuntimeException(e);
        }
    }
}
