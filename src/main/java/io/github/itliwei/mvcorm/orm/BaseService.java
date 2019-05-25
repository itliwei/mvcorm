package io.github.itliwei.mvcorm.orm;

import io.github.itliwei.mvcorm.orm.CService;
import io.github.itliwei.mvcorm.orm.IdEntity;
import io.github.itliwei.mvcorm.orm.opt.Page;
import io.github.itliwei.mvcorm.orm.opt.QueryModel;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.ParameterizedType;
import java.util.List;

/**
 * @author : liwei
 * @date : 2019/05/13 09:19
 */
public class BaseService<T> {
    @Autowired
    private CService cService;

    public Class<T> getTClass() {
        return (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    public <T extends IdEntity> T save(T entity){
        cService.save(entity);
        return entity;
    }

    public <T extends IdEntity> T update(T entity){
        cService.update(entity);
        return entity;
    }

    public <T extends IdEntity> T update(T entity,boolean useNull){
        cService.update(entity,useNull);
        return entity;
    }

    public <T extends IdEntity> T findById(Long id){
        return cService.find((Class<T>)getTClass(),id);
    }

    public <T extends IdEntity> T findOne(QueryModel queryModel){
        return cService.find((Class<T>) this.getTClass(), queryModel);
    }


    public <T extends IdEntity> List<T> findList(QueryModel queryModel){
        return cService.findList((Class<T>) this.getTClass(), queryModel);
    }

    public <T extends IdEntity> Page<T> findPage(QueryModel queryModel){
        return cService.findPage((Class<T>) this.getTClass(), queryModel);
    }

    public <T extends IdEntity> int delete(Long id){
        return cService.delete((Class<T>)getTClass(),id);
    }

    public <T extends IdEntity> int deleteBatch(List<Long> ids){
        return cService.delete((Class<T>)getTClass(),ids);
    }

}