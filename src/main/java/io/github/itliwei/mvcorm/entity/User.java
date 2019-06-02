package io.github.itliwei.mvcorm.entity;


import io.github.itliwei.mvcorm.generator.annotation.Field;
import io.github.itliwei.mvcorm.generator.annotation.Type;
import io.github.itliwei.mvcorm.generator.annotation.controller.ControllerClass;
import io.github.itliwei.mvcorm.generator.annotation.query.Query;
import io.github.itliwei.mvcorm.generator.annotation.query.QueryModel;
import io.github.itliwei.mvcorm.generator.annotation.service.ServiceClass;
import io.github.itliwei.mvcorm.generator.annotation.view.View;
import io.github.itliwei.mvcorm.generator.annotation.view.ViewObject;
import io.github.itliwei.mvcorm.orm.IdEntity;
import io.github.itliwei.mvcorm.orm.annotation.Table;
import io.github.itliwei.mvcorm.orm.opt.Condition;
import lombok.*;

import javax.persistence.Entity;

import static io.github.itliwei.mvcorm.entity.User.SIMPLE_DTO;
import static io.github.itliwei.mvcorm.entity.User.SIMPLE_VO;

/**
 * 用户表
 * Created by cheshun on 17/9/14.
 */
@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Type(label = "用户")
@QueryModel
@Table(value = "tmp_user")
@ServiceClass
@ControllerClass(path = "api/user",desc = "用户接口")
@ViewObject(groups = {SIMPLE_VO,SIMPLE_DTO})
public class User extends IdEntity {

    public static final String SIMPLE_VO = "ApplicationVo";

    public static final String SIMPLE_DTO = "ApplicationDto";

    @Field(label = "姓名")
    @Query({Condition.Operator.eq, Condition.Operator.in})
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getJobId() {
        return jobId;
    }

    public void setJobId(Long jobId) {
        this.jobId = jobId;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", jobId=" + jobId +
                "} " + super.toString();
    }
}
