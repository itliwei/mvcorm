package io.github.itliwei.mvcorm.orm;

import io.github.itliwei.mvcorm.orm.mapper.CMapper;

import java.util.ArrayList;
import java.util.List;

/**
 * Corm 配置
 * 主要配置CMapper对象
 * Created by cheshun on 17/9/3.
 */
public class CormConfig {

    public void addDefaultMasterMapper(CMapper mapper) {
        List<CMapper> mapperList = CormContext.masterMapperList.get();
        if (mapperList == null) {
            mapperList = new ArrayList<>();
            CormContext.masterMapperList.set(mapperList);
        }
        mapperList.add(mapper);
    }

    public void addDefaultSlaveMapper(CMapper mapper) {
        List<CMapper> mapperList = CormContext.slaveMapperList.get();
        if (mapperList == null) {
            mapperList = new ArrayList<>();
            CormContext.slaveMapperList.set(mapperList);
        }
        mapperList.add(mapper);
    }
}
