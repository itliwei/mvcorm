package io.github.itliwei.mvcorm.orm;

import io.github.itliwei.mvcorm.orm.IdEntity;
import io.github.itliwei.mvcorm.orm.c.Insert;
import io.github.itliwei.mvcorm.orm.d.Delete;
import io.github.itliwei.mvcorm.orm.d.DeleteParam;
import io.github.itliwei.mvcorm.orm.mapper.CMapper;
import io.github.itliwei.mvcorm.orm.r.Select;
import io.github.itliwei.mvcorm.orm.r.SelectMulti;
import io.github.itliwei.mvcorm.orm.r.SelectParam;
import io.github.itliwei.mvcorm.orm.u.Update;
import io.github.itliwei.mvcorm.orm.u.UpdateParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Corm 使用API
 * Created by liwei on 17/8/9.
 */
public class Corm {
    private static final Logger logger = LoggerFactory.getLogger(Corm.class);

    /**
     * 单表插入数据
     * @param clazz 对应实体class
     */
    public static <T extends IdEntity> Insert<T> insert(Class<T> clazz) {
        CormContext.LOCAL_MAPPER.set(CormContext.getCMapper(true));
        return new CormProxy<T>().insert(clazz);
    }

    /**
     * 单表更新数据
     * @param clazz 对应实体class
     */
    public static <T extends IdEntity> Update<T> update(Class<T> clazz) {
        CormContext.UPDATE_PARAM_THREAD_LOCAL.set(new UpdateParam());
        CormContext.LOCAL_MAPPER.set(CormContext.getCMapper(true));
        return new CormProxy<T>().update(clazz);
    }

    /**
     * 单表删除数据
     * @param clazz 对应实体class
     */
    public static <T extends IdEntity> Delete delete(Class<T> clazz) {
        CormContext.DELETE_PARAM_THREAD_LOCAL.set(new DeleteParam());
        CormContext.LOCAL_MAPPER.set(CormContext.getCMapper(true));
        return new CormProxy<T>().delete(clazz);
    }

    /**
     * 单表查询数据
     * @param clazz 对应实体class
     */
    public static <T extends IdEntity> Select<T> select(Class<T> clazz) {
        CormContext.SELECT_PARAM_THREAD_LOCAL.set(new SelectParam());
        CormContext.LOCAL_MAPPER.set(CormContext.getCMapper());
        return new CormProxy<T>().select(clazz);
    }

    /**
     * 多表查询数据
     * @param clazz 对应实体class
     * @param alias 对应实体别名
     */
    public static SelectMulti selectMulti(Class<? extends IdEntity> clazz, String alias) {
        CormContext.SELECT_PARAM_THREAD_LOCAL.set(new SelectParam());
        CormContext.LOCAL_MAPPER.set(CormContext.getCMapper());
        return new CormProxy<>().selectMulti(clazz, alias);
    }

    /**
     * 指定master操作数据
     * 在事务中，需要调用该方法，强制指定到master上操作
     */
    public static void master() {
        CormContext.IS_MASTER.set(true);
    }

    /**
     * 释放指定master操作
     * 在事务方法调用完成后，调用commit结束
     */
    public static void commit() {
        CormContext.IS_MASTER.remove();
    }

    public static <T extends IdEntity> CormProxy<T> switchM(CMapper mapper) {
        CormContext.UPDATE_PARAM_THREAD_LOCAL.set(null);
        CormContext.DELETE_PARAM_THREAD_LOCAL.set(null);
        CormContext.SELECT_PARAM_THREAD_LOCAL.set(null);
        CormContext.LOCAL_MAPPER.set(mapper);
        return new CormProxy<T>();
    }
}
