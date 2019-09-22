package io.github.itliwei.mvcorm.entity;


import io.github.itliwei.generator.annotation.Field;
import io.github.itliwei.generator.annotation.Type;
import io.github.itliwei.generator.annotation.controller.ControllerClass;
import io.github.itliwei.generator.annotation.elementui.ElementClass;
import io.github.itliwei.generator.annotation.query.Query;
import io.github.itliwei.generator.annotation.query.QueryModel;
import io.github.itliwei.generator.annotation.service.ServiceClass;
import io.github.itliwei.generator.annotation.view.View;
import io.github.itliwei.generator.annotation.view.ViewObject;
import io.github.itliwei.mvcorm.orm.IdEntity;
import io.github.itliwei.mvcorm.orm.annotation.Table;
import io.github.itliwei.mvcorm.orm.opt.Condition;
import lombok.*;

import javax.persistence.Entity;

import static io.github.itliwei.mvcorm.entity.TestEntity.SIMPLE_DTO;
import static io.github.itliwei.mvcorm.entity.TestEntity.SIMPLE_VO;

/**
 * 用户表
 * Created by liwei on 18/6/14.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Type(label = "用户")
@Table(value = "tmp_user")
@QueryModel
@ServiceClass(name = "TestService")
@ControllerClass(path = "api/user",desc = "用户接口",name = "TestController")
@ElementClass
@ViewObject(groups = {SIMPLE_VO,SIMPLE_DTO})
public class TestEntity extends IdEntity {

    public static final String SIMPLE_VO = "TestVo";

    public static final String SIMPLE_DTO = "TestDto";

    @Field(label = "姓名")
    @Query(value = {Condition.Operator.eq, Condition.Operator.in})
    @View(groups = {SIMPLE_VO,SIMPLE_DTO})
    private String username;

    @Field(label = "jobId")
    @Query({Condition.Operator.eq, Condition.Operator.in})
    @View(groups = {SIMPLE_VO})
    private Long jobId;

    @Field(label = "年龄")
    @Query({Condition.Operator.eq, Condition.Operator.in})
    @View(groups = {SIMPLE_VO})
    private Integer age;

    @Field(label = "地址")
    @Query({Condition.Operator.eq, Condition.Operator.in})
    @View(groups = {SIMPLE_VO})
    private String address;
}
