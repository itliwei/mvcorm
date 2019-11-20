package io.github.itliwei.mvcorm.orm.result;

import io.github.itliwei.mvcorm.orm.CormContext;
import io.github.itliwei.mvcorm.orm.Utils;
import io.github.itliwei.mvcorm.orm.opt.Skipped;
import io.github.itliwei.mvcorm.orm.r.SelectParam;

import java.util.List;
import java.util.Map;

/**
 * OneResult
 * Created by liwei on 17/8/23.
 */
public interface OneResult<T> {

    @SuppressWarnings("unchecked")
    default T one() {
        SelectParam param = CormContext.SELECT_PARAM_THREAD_LOCAL.get();
        List<Map<String, Object>> result = CormContext.LOCAL_MAPPER.get().selectMapListPage(param, new Skipped(0, 1));
        if (result == null || result.isEmpty()) {
            return null;
        }
        Class<?> type = param.getResultType();
        if (type == Map.class) {
            return (T) result.get(0);
        }
        return Utils.hashToObject(result.get(0), (Class<T>) type);
    }
}
