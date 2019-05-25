package io.github.itliwei.mvcorm.orm.u;

import io.github.itliwei.mvcorm.orm.CormContext;
import io.github.itliwei.mvcorm.orm.opt.Condition;

import java.util.Arrays;
import java.util.Collection;

/**
 * Created by cheshun on 17/10/25.
 */
public class WhereOpt {

    public UpdateWhere where(Condition condition) {
        UpdateParam param = CormContext.UPDATE_PARAM_THREAD_LOCAL.get();
        param.addCondition(condition);
        return new UpdateWhere();
    }

    public UpdateWhere where(Collection<Condition> conditions) {
        UpdateParam param = CormContext.UPDATE_PARAM_THREAD_LOCAL.get();
        param.addCondition(conditions);
        return new UpdateWhere();
    }

    public UpdateWhere where(Condition ... conditions) {
        UpdateParam param = CormContext.UPDATE_PARAM_THREAD_LOCAL.get();
        param.addCondition(Arrays.asList(conditions));
        return new UpdateWhere();
    }
}
