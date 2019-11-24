package io.github.itliwei.mvcorm.orm;

import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;
import io.github.itliwei.mvcorm.orm.mapper.CMapper;
import io.github.itliwei.mvcorm.orm.opt.*;
import io.github.itliwei.mvcorm.orm.r.Select;
import io.github.itliwei.mvcorm.orm.r.SumOpt;
import io.github.itliwei.mvcorm.orm.u.Update;
import io.github.itliwei.mvcorm.orm.utils.BeanUtil;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * C Service
 * Created by liwei on 17/10/19.
 */
@SuppressWarnings("unchecked")
public class CService {

    private CMapper mapper;

    public CService(CMapper mapper) {
        this.mapper = mapper;
    }

    /**
     * 保存实体
     * @param entity 实体对象
     * @return int
     */
    public <T extends IdEntity> int save(T entity) {
        return Corm.switchM(mapper).insert((Class<IdEntity>) entity.getClass()).incId().obj(entity);
    }

    /**
     * 更新实体
     * @param entity 实体对象
     * @return int
     */
    public <T extends IdEntity> int update(T entity) {
        return Corm.switchM(mapper).update((Class<IdEntity>) entity.getClass()).obj(entity);
    }

    /**
     * 更新实体,{useNUll=true}时可以把对应字段更新为null
     * @param entity 实体对象
     * @param useNull 是否要更新null字段
     * @return int
     */
    public <T extends IdEntity> int update(T entity, boolean useNull) {
        if (useNull) {
            return Corm.switchM(mapper).update((Class<IdEntity>) entity.getClass()).useNull().obj(entity);
        }
        return update(entity);
    }

    /**
     * 条件更新
     * @param entity 实体对象
     * @param conditions 条件 {@link Condition}
     * @return int
     */
    public <T extends IdEntity> int update(T entity, Condition... conditions) {
        return Corm.switchM(mapper).update((Class<IdEntity>) entity.getClass()).entity(entity).where(conditions).exec();
    }

    /**
     * 条件更新
     * @param entity 实体对象
     * @param useNull 是否要更新null字段
     * @param conditions 条件 {@link Condition}
     * @return int
     */
    public <T extends IdEntity> int update(T entity, boolean useNull, Condition... conditions) {
        Update update = Corm.switchM(mapper).update((Class<IdEntity>) entity.getClass());
        if (useNull) {
            update.useNull();
        }
        return update.entity(entity).where(conditions).exec();
    }

    /**
     * 查询单个实体对象
     * @param id id
     * @return entity
     */
    public <T extends IdEntity> T find(Class<T> clazz, Long id) {
        return (T) Corm.switchM(mapper).select((Class<IdEntity>) clazz).where(Condition.eq(IdEntity.ID_PN, id)).one();
    }

    /**
     * 查询单个实体对象
     * @param conditions 条件 {@link Condition}
     * @return entity
     */
    public <T extends IdEntity> T find(Class<T> clazz, Condition... conditions) {
        return (T) Corm.switchM(mapper).select((Class<IdEntity>) clazz).where(conditions).one();
    }

    public <T extends IdEntity> T find(Class<T> clazz,QueryModel queryModel) {
        List<Condition> conditions = this.getConditions(queryModel);
        List<OrderBy> orderBys = queryModel.getOrderBys();
        Select select = Corm.switchM(mapper).select((Class<IdEntity>) clazz);
        if (!CollectionUtils.isEmpty(conditions)){
            select.where(conditions);
        }
        if (!CollectionUtils.isEmpty(orderBys)){
            select.orderBy(orderBys);
        }
        return (T) select.one();
    }

    /**
     * 查询所有实体列表
     * @return list
     */
    public <T extends IdEntity> List<T> findAll(Class<T> clazz) {
        return (List<T>) Corm.switchM(mapper).select((Class<IdEntity>) clazz).list();
    }

    /**
     * 查询实体列表
     * @param ids 一组id
     * @return list
     */
    public <T extends IdEntity> List<T> findList(Class<T> clazz, List<Long> ids) {
        return (List<T>) Corm.switchM(mapper).select((Class<IdEntity>) clazz)
                .where(Condition.in(IdEntity.ID_PN, ids)).list();
    }

    /**
     * 查询实体列表
     * @param conditions 条件 {@link Condition}
     * @return list
     */
    public <T extends IdEntity> List<T> findList(Class<T> clazz, Condition... conditions) {
        Select select = Corm.switchM(mapper).select((Class<IdEntity>) clazz);
        for (Condition condition : conditions) {
            select.where(condition);
        }
        return select.list();
    }

    public <T extends IdEntity> List<T> findList(Class<T> clazz, QueryModel queryModel) {
        Select select = Corm.switchM(mapper).select((Class<IdEntity>) clazz);
        List<Condition> conditions = this.getConditions(queryModel);
        for (Condition condition : conditions) {
            select.where(condition);
        }
        List<OrderBy> orderBys = queryModel.getOrderBys();
        if (!CollectionUtils.isEmpty(orderBys)){
            select.orderBy(orderBys);
        }
        return select.list();
    }

    /**
     * 查询实体列表
     * @param conditions 条件 {@link Condition}
     * @param orderBys 排序 {@link OrderBy}
     * @return list
     */
    public <T extends IdEntity> List<T> findList(Class<T> clazz, List<Condition> conditions, List<OrderBy> orderBys) {
        Select select = Corm.switchM(mapper).select((Class<IdEntity>) clazz);
        if (conditions != null) {
            select.where(conditions);
        }
        if (orderBys != null) {
            select.orderBy(orderBys);
        }
        return select.list();
    }

    /**
     * 查询实体列表
     * @param count 数量
     * @param conditions 条件 {@link Condition}
     * @param orderBys 排序 {@link OrderBy}
     * @return list
     */
    public <T extends IdEntity> List<T> findList(Class<T> clazz, List<Condition> conditions, List<OrderBy> orderBys, int count) {
        Select select = Corm.switchM(mapper).select((Class<IdEntity>) clazz);
        select.limit(0, count);
        if (conditions != null) {
            select.where(conditions);
        }
        if (orderBys != null) {
            select.orderBy(orderBys);
        }
        return select.list();
    }

    /**
     * 查询实体列表
     * @param conditions 条件 {@link Condition}
     * @param orderBys 排序 {@link OrderBy}
     * @param skipped skipped {@link Skipped}
     * @return list
     */
    public <T extends IdEntity> List<T> findList(Class<T> clazz, List<Condition> conditions, List<OrderBy> orderBys, Skipped skipped) {
        Select select = Corm.switchM(mapper).select((Class<IdEntity>) clazz);
        if (skipped != null) {
            select.limit(skipped.getSkip(), skipped.getCount());
        }
        if (conditions != null) {
            select.where(conditions);
        }
        if (orderBys != null) {
            select.orderBy(orderBys);
        }
        return select.list();
    }

    /**
     * 查询 Page 对象
     * @return page {@link Page}
     */
    public <T extends IdEntity> Page<T> findPage(Class<T> clazz, int pn, int pz) {
        Select select = Corm.switchM(mapper).select((Class<IdEntity>) clazz);
        return select.pageable(pn, pz).page();
    }

    public <T extends IdEntity> Page<T> findPage(Class<T> clazz, List<Condition> conditions, List<OrderBy> orderBys, int pn, int pz) {
        Select select = Corm.switchM(mapper).select((Class<IdEntity>) clazz);
        if (conditions != null) {
            select.where(conditions);
        }
        if (orderBys != null) {
            select.orderBy(orderBys);
        }
        return select.pageable(pn, pz).page();
    }

    public <T extends IdEntity> Page<T> findPage(Class<T> clazz,QueryModel queryModel) {
        Select select = Corm.switchM(mapper).select((Class<IdEntity>) clazz);
        List conditions = this.getConditions(queryModel);
        if (conditions != null) {
            select.where(conditions);
        }
        List<OrderBy> orderBys = queryModel.getOrderBys();
        if (orderBys != null) {
            select.orderBy(orderBys);
        }
        return select.pageable(queryModel.getPageNumber(), queryModel.getPageSize()).page();
    }

    /**
     * 删除实体
     * @param id id
     * @return int
     */
    public <T extends IdEntity> int delete(Class<T> clazz, Long id) {
        return Corm.switchM(mapper).delete((Class<IdEntity>) clazz).where(Condition.eq(IdEntity.ID_PN, id)).exec();
    }

    /**
     * 删除一组实体
     * @param ids 一组id
     * @return int
     */
    public <T extends IdEntity> int delete(Class<T> clazz, List<Long> ids) {
        return Corm.switchM(mapper).delete((Class<IdEntity>) clazz).where(Condition.in(IdEntity.ID_PN, ids)).exec();
    }

    /**
     * 条件删除
     * @param clazz
     * @param queryModel
     * @param <T>
     * @return
     */
    public <T extends IdEntity> int delete(Class<T> clazz, QueryModel queryModel) {
        List conditions = this.getConditions(queryModel);
        return Corm.switchM(mapper).delete((Class<IdEntity>) clazz).where(conditions).exec();
    }

    /**
     * 查询实体数量
     * @return count
     */
    public <T extends IdEntity> long count(Class<T> clazz) {
        return Corm.switchM(mapper).select((Class<IdEntity>) clazz).count().number();
    }

    /**
     * 查询实体数量
     * @param conditions 条件 {@link Condition}
     * @return count
     */
    public <T extends IdEntity> long count(Class<T> clazz, List<Condition> conditions) {
        Select select = Corm.switchM(mapper).select((Class<IdEntity>) clazz);
        if (conditions != null) {
            select.where(conditions);
        }
        return select.count().number();
    }

    /**
     * 查询实体数量
     * @param conditions 条件 {@link Condition}
     * @return count
     */
    public <T extends IdEntity> long count(Class<T> clazz, Condition... conditions) {
        Select select = Corm.switchM(mapper).select((Class<IdEntity>) clazz);
        if (conditions != null) {
            select.where(conditions);
        }
        return select.count().number();
    }

    /**
     * 某属性求和
     * @param sumProperty 属性名
     * @return {@link Number}
     */
    public <T extends IdEntity> Number sum(Class<T> clazz, String sumProperty) {
        return Corm.switchM(mapper).select((Class<IdEntity>) clazz).sum(Field.of(sumProperty)).number();
    }

    /**
     * 某属性求和
     * @param sumProperty 属性名
     * @param conditions 条件 {@link Condition}
     * @return {@link Number}
     */
    public <T extends IdEntity> Number sum(Class<T> clazz, String sumProperty, List<Condition> conditions) {
        SumOpt opt = Corm.switchM(mapper).select((Class<IdEntity>) clazz).sum(Field.of(sumProperty));
        if (conditions != null) {
            opt.where(conditions);
        }
        return opt.number();
    }

    /**
     * 某属性求和
     * @param sumProperty 属性名
     * @param conditions 条件 {@link Condition}
     * @return {@link Number}
     */
    public <T extends IdEntity> Number sum(Class<T> clazz, String sumProperty, Condition... conditions) {
        SumOpt opt = Corm.switchM(mapper).select((Class<IdEntity>) clazz).sum(Field.of(sumProperty));
        if (conditions != null) {
            for (Condition condition : conditions) {
                opt.where(condition);
            }
        }
        return opt.number();
    }

    /**
     * 实体是否存在
     * @param id id
     * @return boolean
     */
    public <T extends IdEntity> boolean exist(Class<T> clazz, Long id) {
        long count = count(clazz, Condition.eq(IdEntity.ID_PN, id));
        return count > 0;
    }

    /**
     * 实体是否存在
     * @param conditions 条件 {@link Condition}
     * @return boolean
     */
    public <T extends IdEntity> boolean exist(Class<T> clazz, Condition... conditions) {
        long count = count(clazz, conditions);
        return count > 0;
    }

    private List<Condition> getConditions(QueryModel queryModel) {
        List<String> filedNames = BeanUtil.getFiledNames(queryModel);
        return filedNames.stream()
                .filter(filedName->{
                    Object fieldValue = BeanUtil.getFieldValueByName(filedName, queryModel);
                    return fieldValue != null && !"".equals(fieldValue);
                })
                .map(filedName->{
                    Object fieldValue = BeanUtil.getFieldValueByName(filedName, queryModel);
                    String operateName = filedName.substring(filedName.length() -2,filedName.length());
                    String objFileName = filedName.substring(0,filedName.length() -2);
                    return Condition.Operator.fromName(objFileName, operateName, fieldValue);
                }).collect(Collectors.toList());
    }
}
