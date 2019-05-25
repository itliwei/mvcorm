package io.github.itliwei.mvcorm.orm.result;

import io.github.itliwei.mvcorm.orm.CormContext;
import io.github.itliwei.mvcorm.orm.Utils;
import io.github.itliwei.mvcorm.orm.r.SelectParam;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * ListResult
 * Created by cheshun on 17/8/23.
 */
public interface ListResult<T> {

    @SuppressWarnings("unchecked")
    default List<T> list() {
        SelectParam param = CormContext.SELECT_PARAM_THREAD_LOCAL.get();
        List<Map<String, Object>> result = CormContext.LOCAL_MAPPER.get().selectMapListPage(param, param.getSkipped());
        if (result == null || result.isEmpty()) {
            return Collections.emptyList();
        }
        Class<?> type = param.getResultType();
        if (type == Map.class) {
            return (List<T>) result;
        }
        return Utils.entityList((Class<T>) type, result);
    }
}
