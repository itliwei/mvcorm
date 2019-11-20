package io.github.itliwei.mvcorm.orm.r;

import io.github.itliwei.mvcorm.orm.CormContext;
import io.github.itliwei.mvcorm.orm.opt.Condition;
import io.github.itliwei.mvcorm.orm.result.SumResult;

import java.util.Arrays;
import java.util.Collection;

/**
 * Created by liwei on 17/8/23.
 */
public class SumOpt implements SumResult {

    public SumWhere where(Condition condition) {
        CormContext.SELECT_PARAM_THREAD_LOCAL.get().addCondition(condition);
        return this.new SumWhere();
    }

    public SumWhere where(Collection<Condition> conditions) {
        CormContext.SELECT_PARAM_THREAD_LOCAL.get().addCondition(conditions);
        return this.new SumWhere();
    }

    public SumWhere where(Condition ... conditions) {
        CormContext.SELECT_PARAM_THREAD_LOCAL.get().addCondition(Arrays.asList(conditions));
        return this.new SumWhere();
    }

    public class SumWhere implements SumResult {
        public SumWhere and(Condition condition) {
            CormContext.SELECT_PARAM_THREAD_LOCAL.get().addCondition(condition);
            return this;
        }
    }
}
