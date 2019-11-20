package io.github.itliwei.mvcorm.orm;

import io.github.itliwei.mvcorm.orm.d.DeleteParam;
import io.github.itliwei.mvcorm.orm.mapper.CMapper;
import io.github.itliwei.mvcorm.orm.r.SelectParam;
import io.github.itliwei.mvcorm.orm.u.UpdateParam;

import java.util.List;
import java.util.Random;

/**
 * Corm 上下文环境
 * Created by liwei on 17/9/8.
 */
public class CormContext {
    /**
     * 查询数据参数
     */
    public static final ThreadLocal<SelectParam> SELECT_PARAM_THREAD_LOCAL = new ThreadLocal<>();

    /**
     * 更新数据参数
     */
    public static final ThreadLocal<UpdateParam> UPDATE_PARAM_THREAD_LOCAL = new ThreadLocal<>();

    /**
     * 删除数据参数
     */
    public static final ThreadLocal<DeleteParam> DELETE_PARAM_THREAD_LOCAL = new ThreadLocal<>();

    /**
     * 强制使用master的标识
     */
    public static final ThreadLocal<Boolean> IS_MASTER = new ThreadLocal<>();

    public static final ThreadLocal<CMapper> LOCAL_MAPPER = new ThreadLocal<>();

    static final NullableImmutableHolder<List<CMapper>> masterMapperList = new NullableImmutableHolder<>();

    static final NullableImmutableHolder<List<CMapper>> slaveMapperList = new NullableImmutableHolder<>();

    /**
     * 查询获取一个CMapper
     */
    public static CMapper getCMapper() {

        List<CMapper> mapperList;
        // 1.如果设置了IS_MASTER=true会使用master mapper
        // 2.如果没有设置IS_MASTER，但是设置了SelectParam.userMaster=true会使用master mapper
        if ((IS_MASTER.get() != null && IS_MASTER.get()) ||
                (IS_MASTER.get() == null && SELECT_PARAM_THREAD_LOCAL.get().isUseMaster())) {
            mapperList = masterMapperList.get();
        } else {
            mapperList = slaveMapperList.get();
            // 如果没有配置slave mapper就使用master mapper
            if (mapperList == null || mapperList.isEmpty()) {
                mapperList = masterMapperList.get();
            }
        }

        return getCMapper(mapperList);
    }

    /**
     * 指定主从获取一个CMapper
     * @param useMaster 是否使用master
     */
    public static CMapper getCMapper(boolean useMaster) {
        if (useMaster) {
            return getCMapper(masterMapperList.get());
        } else {
            return getCMapper();
        }
    }

    private static CMapper getCMapper(List<CMapper> mapperList) {
        if (mapperList == null || mapperList.isEmpty()) {
            throw new RuntimeException("mapper not set.");
        }
        // 随机获取mapper list中的一条mapper数据
        return mapperList.get(new Random().nextInt(mapperList.size()));
    }
}
