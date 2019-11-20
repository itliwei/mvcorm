package io.github.itliwei.mvcorm.orm.c;

import io.github.itliwei.mvcorm.orm.CormContext;
import io.github.itliwei.mvcorm.orm.IdEntity;

/**
 * Insert
 * Created by liwei on 17/8/9.
 */
public class Insert<T extends IdEntity> {

    /**
     * 保存数据对象
     * 需要设置数据主键
     */
    public int obj(T t) {
        return CormContext.LOCAL_MAPPER.get().insert(t);
    }

    /**
     * 数据库自增主键方式需要调用
     */
    public IncIdOpt<T> incId() {
        return new IncIdOpt<>();
    }

}
