package io.github.itliwei.mvcorm.orm.u;

import io.github.itliwei.mvcorm.orm.CormContext;
import io.github.itliwei.mvcorm.orm.opt.Condition;

/**
 * Created by cheshun on 17/8/9.
 */
public class UpdateWhere implements UpdateExec {

    public UpdateWhere and(Condition condition) {
        UpdateParam param = CormContext.UPDATE_PARAM_THREAD_LOCAL.get();
        param.addCondition(condition);
        return this;
    }
}
