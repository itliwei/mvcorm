package io.github.itliwei.mvcorm.orm.result;

import io.github.itliwei.mvcorm.orm.CormContext;
import io.github.itliwei.mvcorm.orm.r.SelectParam;

/**
 * CountResult
 * Created by liwei on 17/8/23.
 */
public interface CountResult {

    default long number() {
        SelectParam param = CormContext.SELECT_PARAM_THREAD_LOCAL.get();
        return CormContext.LOCAL_MAPPER.get().selectCount(param);
    }
}
