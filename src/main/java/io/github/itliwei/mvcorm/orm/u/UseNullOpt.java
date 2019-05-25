package io.github.itliwei.mvcorm.orm.u;

import io.github.itliwei.mvcorm.orm.CormContext;
import io.github.itliwei.mvcorm.orm.IdEntity;
import io.github.itliwei.mvcorm.orm.opt.Condition;

/**
 * Created by cheshun on 17/9/28.
 */
public class UseNullOpt<T extends IdEntity> {
    public int obj(T t) {
        UpdateParam param = CormContext.UPDATE_PARAM_THREAD_LOCAL.get();
        param.setEntity(t);
        param.addCondition(Condition.eq(IdEntity.ID_PN, t.getId()));
        return CormContext.LOCAL_MAPPER.get().update(param);
    }

    public WhereOpt entity(T t) {
        UpdateParam param = CormContext.UPDATE_PARAM_THREAD_LOCAL.get();
        param.setEntity(t);
        param.addCondition(Condition.eq(IdEntity.ID_PN, t.getId()));
        return new WhereOpt();
    }
}
