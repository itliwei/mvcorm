package io.github.itliwei.mvcorm.orm.r;

import io.github.itliwei.mvcorm.orm.CormContext;
import io.github.itliwei.mvcorm.orm.opt.Condition;
import io.github.itliwei.mvcorm.orm.result.CountResult;

/**
 * Created by liwei on 17/9/18.
 */
public class CountOpt implements CountResult {

    public CountWhere where(Condition condition) {
        CormContext.SELECT_PARAM_THREAD_LOCAL.get().addCondition(condition);
        return new CountWhere();
    }

    public class CountWhere implements CountResult {
        public CountWhere and(Condition condition) {
            CormContext.SELECT_PARAM_THREAD_LOCAL.get().addCondition(condition);
            return this;
        }
    }

}
