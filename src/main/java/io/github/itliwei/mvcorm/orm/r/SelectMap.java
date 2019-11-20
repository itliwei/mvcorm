package io.github.itliwei.mvcorm.orm.r;

import io.github.itliwei.mvcorm.orm.CormContext;
import io.github.itliwei.mvcorm.orm.opt.Condition;
import io.github.itliwei.mvcorm.orm.opt.Field;
import io.github.itliwei.mvcorm.orm.opt.OrderBy;
import io.github.itliwei.mvcorm.orm.result.ListResult;
import io.github.itliwei.mvcorm.orm.result.OneResult;

import java.util.Map;

/**
 * 查询Map结果
 * Created by liwei on 17/8/11.
 */
public class SelectMap implements OneResult<Map<String, Object>>, ListResult<Map<String, Object>> {

    public SelectMap field(Field field) {
        SelectParam param = CormContext.SELECT_PARAM_THREAD_LOCAL.get();
        param.addField(field);
        return this;
    }

    public WhereOpt<Map<String, Object>> where(Condition condition) {
        CormContext.SELECT_PARAM_THREAD_LOCAL.get().addCondition(condition);
        return new WhereOpt<>();
    }

    public OrderByOpt<Map<String, Object>> orderBy(OrderBy orderBy) {
        CormContext.SELECT_PARAM_THREAD_LOCAL.get().addOrderBy(orderBy);
        return new OrderByOpt<>();
    }

    public LimitOpt<Map<String, Object>> limit(int skip, int size) {
        CormContext.SELECT_PARAM_THREAD_LOCAL.get().limit(skip, size);
        return new LimitOpt<>();
    }

    public PageOpt<Map<String, Object>> pageable(int pn, int pz) {
        CormContext.SELECT_PARAM_THREAD_LOCAL.get().limit((pn - 1) * pz, pz);
        return new PageOpt<>();
    }
}
