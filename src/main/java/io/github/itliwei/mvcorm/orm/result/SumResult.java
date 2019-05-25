package io.github.itliwei.mvcorm.orm.result;

import io.github.itliwei.mvcorm.orm.CormContext;
import io.github.itliwei.mvcorm.orm.r.SelectParam;

import java.util.Map;

/**
 * SumResult
 * Created by cheshun on 17/8/23.
 */
public interface SumResult {

    default Number number() {
        SelectParam param = CormContext.SELECT_PARAM_THREAD_LOCAL.get();
        Map<String, Object> result = CormContext.LOCAL_MAPPER.get().selectSum(param);
        if (result == null || result.isEmpty()) {
            return 0;
        }
        return (Number) result.entrySet().iterator().next().getValue();
    }
}
