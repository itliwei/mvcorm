package io.github.itliwei.mvcorm.orm.r;

import io.github.itliwei.mvcorm.orm.CormContext;
import io.github.itliwei.mvcorm.orm.opt.Condition;
import io.github.itliwei.mvcorm.orm.opt.OrderBy;
import io.github.itliwei.mvcorm.orm.result.ListResult;
import io.github.itliwei.mvcorm.orm.result.OneResult;

/**
 * Created by cheshun on 17/8/23.
 */
public class WhereOpt<T> implements OneResult<T>, ListResult<T> {

    public WhereOpt<T> and(Condition condition) {
        CormContext.SELECT_PARAM_THREAD_LOCAL.get().addCondition(condition);
        return this;
    }

    public OrderByOpt<T> orderBy(OrderBy orderBy) {
        CormContext.SELECT_PARAM_THREAD_LOCAL.get().addOrderBy(orderBy);
        return new OrderByOpt<>();
    }

    public LimitOpt<T> limit(int skip, int size) {
        CormContext.SELECT_PARAM_THREAD_LOCAL.get().limit(skip, size);
        return new LimitOpt<>();
    }

    public PageOpt<T> pageable(int pn, int pz) {
        CormContext.SELECT_PARAM_THREAD_LOCAL.get().limit((pn - 1) * pz, pz);
        return new PageOpt<>();
    }
}
