package io.github.itliwei.mvcorm.orm.d;

import io.github.itliwei.mvcorm.orm.IdEntity;
import io.github.itliwei.mvcorm.orm.opt.Condition;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by cheshun on 17/9/26.
 */
public class DeleteParam {

    private Class<? extends IdEntity> clazz;

    private List<Condition> conditionList;

    public Class<? extends IdEntity> getClazz() {
        return clazz;
    }

    public void setClazz(Class<? extends IdEntity> clazz) {
        this.clazz = clazz;
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

    public List<Condition> getConditionList() {
        return conditionList;
    }
}
