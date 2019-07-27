package io.github.itliwei.mvcorm.util;

import io.github.itliwei.mvcorm.orm.opt.Page;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * author itliwei
 */
public class PageBuilder {
	/**
	 * 分页实体转换
	 * @param page
	 * @param function
	 * @param <S>
	 * @param <T>
	 * @return
	 */
	public static <S, T> Page<T> copyAndConvert(Page<S> page, Function<S, T> function) {
		List<S> list = page.getList();
		List<T> collect = list.stream().map(function).collect(Collectors.toList());
		return new Page<T>(collect,page.getTotal(),page.getPageNumber(),page.getPageSize());
	}
	
}
