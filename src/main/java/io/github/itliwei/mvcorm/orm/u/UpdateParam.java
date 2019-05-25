package io.github.itliwei.mvcorm.orm.u;

import io.github.itliwei.mvcorm.orm.IdEntity;
import io.github.itliwei.mvcorm.orm.opt.Condition;
import io.github.itliwei.mvcorm.orm.opt.Field;

import java.util.*;

/**
 * Created by cheshun on 17/9/25.
 */
public class UpdateParam {

    private Class<? extends IdEntity> clazz;

    private IdEntity entity;

    private boolean isAppointUpdate;

    private boolean useNull;

    private List<Condition> conditionList;

    private Set<Field> fieldList;

    public Class<? extends IdEntity> getClazz() {
        return clazz;
    }

    public void setClazz(Class<? extends IdEntity> clazz) {
        this.clazz = clazz;
    }

    public IdEntity getEntity() {
        return entity;
    }

    public void setEntity(IdEntity entity) {
        this.entity = entity;
    }

    public void addCondition(Condition condition) {
        if (conditionList == null) {
            conditionList = new ArrayList<>();
        }
        conditionList.add(condition);
    }

    public void addCondition(Collection<Condition> conditions) {
        if (conditionList == null) {
            conditionList = new ArrayList<>();
        }
        conditionList.addAll(conditions);
    }

    public void addField(Field field) {
        if (fieldList == null) {
            fieldList = new HashSet<>();
        }
        fieldList.add(field);
        isAppointUpdate = true;
    }

    public void setUseNull(boolean useNull) {
        this.useNull = useNull;
    }

    public boolean isAppointUpdate() {
        return isAppointUpdate;
    }

    public boolean isUseNull() {
        return useNull;
    }

    public List<Condition> getConditionList() {
        return conditionList;
    }

    public Set<Field> getFieldList() {
        return fieldList;
    }
}
