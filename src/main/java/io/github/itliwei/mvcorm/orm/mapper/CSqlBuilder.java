package io.github.itliwei.mvcorm.orm.mapper;

import io.github.itliwei.mvcorm.orm.IdEntity;
import io.github.itliwei.mvcorm.orm.d.DeleteParam;
import io.github.itliwei.mvcorm.orm.r.SelectParam;
import io.github.itliwei.mvcorm.orm.u.UpdateParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.github.itliwei.mvcorm.orm.mapper.SqlBuilderUtil.*;

/**
 * Created by cheshun on 17/8/23.
 */
public class CSqlBuilder {

    private static final Logger logger = LoggerFactory.getLogger(CSqlBuilder.class);

    public String insert(IdEntity obj) {
        Map<String, Class<?>> clazzMap = new HashMap<>();
        clazzMap.put("", obj.getClass());
        initEntities(clazzMap);
        String tableName = findTableName(obj.getClass());
        BEGIN();
        INSERT_INTO(tableName);
        VALUES(obj);
        return SQL();
    }

    public String update(Map<String, Object> parameter) {
        UpdateParam param = (UpdateParam) parameter.get("param");
        Map<String, Class<?>> clazzMap = new HashMap<>();
        clazzMap.put("", param.getClazz());
        initEntities(clazzMap);
        String tableName = findTableName(param.getClazz());
        BEGIN();
        UPDATE(tableName);
        if (param.isAppointUpdate()) {
            SET(param.getFieldList(), param.getEntity());
        } else {
            SET(param.getEntity(), param.isUseNull());
        }
        if (param.getConditionList() != null) {
            where(param.getConditionList());
        }
        return SQL();
    }

    public String delete(Map<String, Object> parameter) {
        DeleteParam param = (DeleteParam) parameter.get("param");
        Map<String, Class<?>> clazzMap = new HashMap<>();
        clazzMap.put("", param.getClazz());
        initEntities(clazzMap);
        String tableName = findTableName(param.getClazz());
        BEGIN();
        DELETE_FROM(tableName);
        if (param.getConditionList() != null) {
            where(param.getConditionList());
        }
        return SQL();
    }

    public String selectMapList(Map<String, Object> parameter) {
        SelectParam param = (SelectParam) parameter.get("param");
        Map<String, Class<?>> clazzMap = param.getClazzMap();
        initEntities(clazzMap);
        List<String> tableNameList = findTableNameList();
        BEGIN();
        if (param.isSelectMap()) {
            select(param.getFieldList());
        } else {
            selectAll();
        }
        FORM(tableNameList);
        if (param.getConditionList() != null) {
            where(param.getConditionList());
        }
        if (param.getOrderByList() != null) {
            orderBy(param.getOrderByList());
        }

        return SQL();
    }

    public String selectCount(Map<String, Object> parameter) {
        SelectParam param = (SelectParam) parameter.get("param");
        Map<String, Class<?>> clazzMap = param.getClazzMap();
        initEntities(clazzMap);
        List<String> tableNameList = findTableNameList();
        BEGIN();
        selectCountX();
        FORM(tableNameList);
        if (param.getConditionList() != null) {
            where(param.getConditionList());
        }
        return SQL();
    }

    public String selectSum(Map<String, Object> parameter) {
        SelectParam param = (SelectParam) parameter.get("param");
        Map<String, Class<?>> clazzMap = param.getClazzMap();
        initEntities(clazzMap);
        List<String> tableNameList = findTableNameList();
        BEGIN();
        selectSumX(param.getFieldList());
        FORM(tableNameList);
        if (param.getConditionList() != null) {
            where(param.getConditionList());
        }
        return SQL();
    }
}
