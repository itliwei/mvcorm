package io.github.itliwei.mvcorm.orm.u;

import io.github.itliwei.mvcorm.orm.CormContext;
import io.github.itliwei.mvcorm.orm.IdEntity;
import io.github.itliwei.mvcorm.orm.opt.Condition;
import io.github.itliwei.mvcorm.orm.opt.Field;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;

/**
 * Created by liwei on 17/8/9.
 */
public class AppointUpdate<T> implements UpdateExec {
    private static final Logger logger = LoggerFactory.getLogger(AppointUpdate.class);

    static void setField(Field field, Object value) {
        UpdateParam param = CormContext.UPDATE_PARAM_THREAD_LOCAL.get();
        param.addField(field);

        IdEntity entity = param.getEntity();
        if (entity == null) {
            try {
                entity = param.getClazz().newInstance();
                param.setEntity(entity);
            } catch (InstantiationException | IllegalAccessException e) {
                logger.error("Can not new instance class[{}]", param.getClazz().getName(), e);
                throw new RuntimeException("set field exception");
            }
        }

        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(param.getClazz());
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor property : propertyDescriptors) {
                if (!property.getName().equals(field.getName())) {
                    continue;
                }
                Method setter = property.getWriteMethod();
                if (setter != null) {
                    setter.invoke(entity, value);
                    break;
                }
            }
        } catch (IntrospectionException | IllegalAccessException | InvocationTargetException e) {
            logger.error("Can not set field[{}]", field.getName(), e);
            throw new RuntimeException("set field exception");
        }
    }

    public AppointUpdate<T> field(Field field, Object value) {
        setField(field, value);
        return this;
    }

    public UpdateWhere where(Condition condition) {
        UpdateParam param = CormContext.UPDATE_PARAM_THREAD_LOCAL.get();
        param.addCondition(condition);
        return new UpdateWhere();
    }

    public UpdateWhere where(Collection<Condition> conditions) {
        UpdateParam param = CormContext.UPDATE_PARAM_THREAD_LOCAL.get();
        param.addCondition(conditions);
        return new UpdateWhere();
    }

    public UpdateWhere where(Condition ... conditions) {
        UpdateParam param = CormContext.UPDATE_PARAM_THREAD_LOCAL.get();
        param.addCondition(Arrays.asList(conditions));
        return new UpdateWhere();
    }

}
