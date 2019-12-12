package io.github.itliwei.mvcorm.orm;

import com.google.common.collect.Lists;
import io.github.itliwei.mvcorm.orm.opt.OrderBy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author : liwei
 * @date : 2019/12/02 22:08
 * @description :
 */
public class OrderByList {

    public static List<OrderBy> add(OrderBy ... orderBy){
        return Arrays.asList(orderBy);
    }
}
