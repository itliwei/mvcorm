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
import java.util.Date;

import static io.github.itliwei.mvcorm.entity.UserRecord.SIMPLE_DTO;
import static io.github.itliwei.mvcorm.entity.UserRecord.SIMPLE_VO;


/**
 * @author : liwei
 * @date : 2019/06/24 09:38
 * @description : 用户记录表
 */

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Type(label = "用户")
@QueryModel
@Table(value = "t_user_record")
@ServiceClass
@ControllerClass
@ViewObject(groups = {SIMPLE_VO,SIMPLE_DTO})
public class UserRecord extends IdEntity {
    public static final String SIMPLE_VO = "UserRecordVo";
    public static final String SIMPLE_DTO = "UserRecordDto";

    @Field(label = "邮箱编码")
    @Query({Condition.Operator.eq})
    @View(groups = {SIMPLE_VO,SIMPLE_DTO})
    private String adCode;

    @Field(label = "系统号")
    @Query({Condition.Operator.eq})
    @View(groups = {SIMPLE_VO,SIMPLE_DTO})
    private String empCode;

    @Field(label = "姓名")
    @Query({Condition.Operator.eq})
    @View(groups = {SIMPLE_VO,SIMPLE_DTO})
    private String empName;

    @Field(label = "手机号")
    @Query({Condition.Operator.eq})
    @View(groups = {SIMPLE_VO,SIMPLE_DTO})
    private String phoneNum;

    @Field(label = "密钥编码")
    @Query({Condition.Operator.eq})
    @View(groups = {SIMPLE_VO,SIMPLE_DTO})
    private String secretCode;

    @Field(label = "设备号")
    @Query({Condition.Operator.eq})
    @View(groups = {SIMPLE_VO,SIMPLE_DTO})
    private String imei;

    @Field(label = "可用状态")
    @Query({Condition.Operator.eq})
    @View(groups = {SIMPLE_VO,SIMPLE_DTO})
    private int isEnable;

    @Field(label = "创建时间")
    @Query({Condition.Operator.le, Condition.Operator.ge})
    @View(groups = {SIMPLE_VO,SIMPLE_DTO})
    private Date createDate;

    @Field(label = "修改时间")
    @Query({Condition.Operator.le, Condition.Operator.ge})
    @View(groups = {SIMPLE_VO,SIMPLE_DTO})
    private Date modifyDate;



}
