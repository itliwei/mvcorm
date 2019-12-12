package io.github.itliwei.mvcorm.orm.opt;

import com.google.common.collect.Lists;
import io.github.itliwei.mvcorm.orm.opt.OrderBy;

import java.util.List;

/**
 * @author : liwei
 * @date 2019/05/11 15:14
 */
public abstract class QueryModel{

    private int pageNumber = 1;

    private int pageSize = 10;

    private List<OrderBy> orderBys;

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public List<OrderBy> getOrderBys() {
        return orderBys;
    }

    public void setOrderBys(List<OrderBy> orderBys) {
        this.orderBys = orderBys;
    }
}