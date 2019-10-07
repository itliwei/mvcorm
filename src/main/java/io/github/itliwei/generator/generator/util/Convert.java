package io.github.itliwei.generator.generator.util;

/**
 * @author : liwei
 * @date : 2019/10/01 15:56
 * @description : 装欢方法
 */
public interface Convert<S,T> {
    /**
     * 转换方法
     * @param s
     * @return
     */
    T convert(S s);
}
