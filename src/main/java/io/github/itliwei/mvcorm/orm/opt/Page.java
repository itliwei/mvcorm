package io.github.itliwei.mvcorm.orm.opt;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 分页
 * Created by cheshun on 2016/5/23.
 */
public class Page<T> implements Serializable {
    private static final long serialVersionUID = -8719253484057263862L;

    private static final int DEFAULT_PAGE_NUMBER = 1;

    private static final int DEFAULT_PAGE_SIZE = 20;

    /** 内容 */
    private final List<T> list = new ArrayList<>();

    /** 总记录数 */
    private final long total;

    /** 分页信息 */
    private int pageSize = DEFAULT_PAGE_SIZE;

    private int pageNumber = DEFAULT_PAGE_NUMBER;

    /**
     * 初始化一个新创建的Page对象
     */
    public Page() {
        this.total = 0L;
    }

    /**
     * @param list
     *            内容
     * @param total
     *            总记录数
     * @param pn
     *            页码
     * @param ps
     *            每页数量
     */
    public Page(List<T> list, long total, int pn, int ps) {
        this.list.addAll(list);
        this.total = total;
        this.pageNumber = pn;
        this.pageSize = ps;
    }

    /**
     * 获取页码
     *
     * @return 页码
     */
    public int getPageNumber() {
        return pageNumber;
    }

    /**
     * 获取每页记录数
     *
     * @return 每页记录数
     */
    public int getPageSize() {
        return pageSize;
    }

    /**
     * 获取总页数
     *
     * @return 总页数
     */
    public int getTotalPages() {
        return (int) Math.ceil((double) getTotal() / (double) getPageSize());
    }

    /**
     * 获取内容
     *
     * @return 内容
     */
    public List<T> getList() {
        return list;
    }

    /**
     * 获取总记录数
     *
     * @return 总记录数
     */
    public long getTotal() {
        return total;
    }
}


