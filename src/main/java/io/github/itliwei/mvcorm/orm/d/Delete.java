package io.github.itliwei.mvcorm.orm.d;

import io.github.itliwei.mvcorm.orm.CormContext;
import io.github.itliwei.mvcorm.orm.opt.Condition;

import java.util.Arrays;
import java.util.Collection;

/**
 * Delete
 * Created by cheshun on 17/8/9.
 */
public class Delete implements DeleteExec {

    public DeleteWhere where(Condition condition) {
        CormContext.DELETE_PARAM_THREAD_LOCAL.get().addCondition(condition);
        return new DeleteWhere();
    }

    public DeleteWhere where(Collection<Condition> conditions) {
        CormContext.DELETE_PARAM_THREAD_LOCAL.get().addCondition(conditions);
        return new DeleteWhere();
    }

    public DeleteWhere where(Condition ... conditions) {
        CormContext.DELETE_PARAM_THREAD_LOCAL.get().addCondition(Arrays.asList(conditions));
        return new DeleteWhere();
    }
}
