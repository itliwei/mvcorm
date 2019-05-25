package io.github.itliwei.mvcorm.orm.c;

import io.github.itliwei.mvcorm.orm.CormContext;
import io.github.itliwei.mvcorm.orm.IdEntity;

/**
 * Created by cheshun on 17/9/23.
 */
public class IncIdOpt<T extends IdEntity> {
    public int obj(T t) {
        return CormContext.LOCAL_MAPPER.get().insertIncId(t);
    }
}
