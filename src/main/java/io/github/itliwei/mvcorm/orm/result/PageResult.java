package io.github.itliwei.mvcorm.orm.result;

import io.github.itliwei.mvcorm.orm.CormContext;
import io.github.itliwei.mvcorm.orm.Utils;
import io.github.itliwei.mvcorm.orm.opt.Page;
import io.github.itliwei.mvcorm.orm.opt.Skipped;
import io.github.itliwei.mvcorm.orm.r.SelectParam;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * PageResult
 * Created by cheshun on 17/8/23.
 */
public interface PageResult<T> {

    @SuppressWarnings("unchecked")
    default Page<T> page() {
        SelectParam param = CormContext.SELECT_PARAM_THREAD_LOCAL.get();
        Skipped skipped = param.getSkipped();
        List<Map<String, Object>> result = CormContext.LOCAL_MAPPER.get().selectMapListPage(param, skipped);
        List<T> ts = null;
        if (result == null || result.isEmpty()) {
            ts = Collections.emptyList();
        } else {
            Class<?> type = param.getResultType();
            if (type == Map.class) {
                ts = (List<T>) result;
            } else {
                ts = Utils.entityList((Class<T>) type, result);
            }
        }
        long count = CormContext.getCMapper().selectCount(param);
        return new Page<T>(ts, count, skipped.getSkip() / skipped.getCount() + 1, skipped.getCount());
    }
}
