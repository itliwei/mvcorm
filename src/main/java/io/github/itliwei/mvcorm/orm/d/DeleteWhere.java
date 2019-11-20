package io.github.itliwei.mvcorm.orm.d;

import io.github.itliwei.mvcorm.orm.CormContext;
import io.github.itliwei.mvcorm.orm.opt.Condition;

/**
 * Created by liwei on 17/9/26.
 */
public class DeleteWhere implements DeleteExec {

    public DeleteWhere and(Condition condition) {
        CormContext.DELETE_PARAM_THREAD_LOCAL.get().addCondition(condition);
        return this;
    }
}
