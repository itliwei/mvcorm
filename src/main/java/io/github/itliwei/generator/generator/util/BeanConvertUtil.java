package io.github.itliwei.generator.generator.util;

import io.github.itliwei.mvcorm.mvc.Exception.BusinessException;
import io.github.itliwei.mvcorm.mvc.constants.ErrorCode;
import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.InvocationTargetException;

/**
 * @author : liwei
 * @date : 2019/09/05 20:51
 * @description : bean类型转换
 */
public class BeanConvertUtil {

    /**
     * 数据转换类
     * @param target
     * @param source
     * @param <T>
     * @return
     */
    public static <T> T convert(T target,Object source){
        try{
            BeanUtils.copyProperties(target,source);
        }catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            throw new BusinessException(ErrorCode.SERVER,"数据转换异常："+target.getClass());
        }
        return target;
    }
}

