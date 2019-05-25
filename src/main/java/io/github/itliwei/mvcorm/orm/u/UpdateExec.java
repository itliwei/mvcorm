package io.github.itliwei.mvcorm.orm.u;

import io.github.itliwei.mvcorm.orm.CormContext;

/**
 * 提交操作
 * Created by cheshun on 17/8/11.
 */
public interface UpdateExec {

    default int exec() {
        UpdateParam param = CormContext.UPDATE_PARAM_THREAD_LOCAL.get();
        return CormContext.LOCAL_MAPPER.get().update(param);
    }
}
