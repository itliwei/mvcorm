##一、功能介绍

1、基于DDD模型，设计domain，自动生成数据库SQL脚本、DO、VO、Dao、Service、Controller以及基于ElementUI的前端页面与JS代码。
生成后，根据自己的业务需要修改调整对应的DO、VO以及Service、Controller逻辑。实现DDD设计模型，domain变更后，可重新生成，无任何影响。

2、基于Springboot统一API日志输出

3、基于Springboot统一异常处理

4、基于Springboot、Swagger统一接口文档

5、基于Springboot统一返回结果响应码

6、Controller提供最基本的根据ID查询、分页条件查询、修改、新增、删除API

7、基于Mybatis自实现CORM框架，无需生成Mapper文件，提供基本SQL语句查询，也支持自定义SQL语句


##二、技术介绍

1、Springboot

2、SpringAOP

3、Spring Advice

4、Mybatis

5、Freemarker

##三、使用方式
1、引入jar包
    
    
    <dependency>
        <groupId>io.github.itliwei</groupId>
        <artifactId>mvcorm-spring-boot-starter</artifactId>
        <version>0.0.2-RELEASE</version>
    </dependency>

    
2、定义实体

    package io.github.itliwei.mvcorm.entity;
    
    
    import io.github.itliwei.mvcorm.generator.annotation.Field;
    import io.github.itliwei.mvcorm.generator.annotation.Type;
    import io.github.itliwei.mvcorm.generator.annotation.controller.ControllerClass;
    import io.github.itliwei.mvcorm.generator.annotation.elementui.ElementClass;
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
    @ElementClass
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


注解介绍：

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    以上5个为lombok注解，无需过多介绍
    
    @Entity
    @Type(label = "用户")
    @Table(value = "tmp_user")
    这三个为基本注解，主要是用来生成mapper，生成数据库
    
    @QueryModel
    生成queryModel对象
    @ViewObject(groups = {SIMPLE_VO,SIMPLE_DTO})
    生成基本的VO（viewObject 视图层对象）Dto（Data Transiation Objece 数据转化对象） 
    @ServiceClass
    生成service,继承baseService,提供基本的增删改查，只生成一次
    @ControllerClass(path = "api/user",desc = "用户接口")
    生成controller，提供基本的增删改查接口，只生成一次
    @ElementClass
    生成ElementUi的基本文件，配合elementUi Template（暂未上传）使用
   
    
    
3、定义generator的main方法

    package io.github.itliwei;
    
    import io.github.itliwei.mvcorm.generator.generator.Config;
    import io.github.itliwei.mvcorm.generator.generator.Generator;
    import io.github.itliwei.mvcorm.generator.generator.handler.*;
    
    import java.io.File;
    import java.nio.file.Paths;
    
    /**
     * @author : liwei
     * @date : 2019/05/21 21:50
     */
    public class GeneratorTest {
        private static final String S = File.separator;
    
        public static void main(String[] args) {
            String projectPath = new File("").getAbsolutePath();
            Config config = new Config();
            //定义的实体对象的路径，会扫描该路径下的文件
            config.setEntityPackage("io.github.itliwei.mvcorm.entity");
            config.setGenLogFile(Paths.get(System.getProperty("user.home"), "gen.log").toString());
            //数据库相关配置，如果不反向生成数据库表，可无需
            config.setUrl("jdbc:mysql://127.0.0.1:3306/test?useSSL=false");
    
            config.setUsername("root");
            config.setPassword("root");
            config.setUseLombok(true);
    
            config.setElementPackage("/Users/vince/myproject/mvcorm/vue");
            config.setElementPath("/Users/vince/myproject/mvcorm/vue");
    
            Generator.generate(config
                  , new VoHandler()
                    , new QueryModelHandler()
                    , new ServiceHandler()
                    , new ControllerHandler()
                    , new ElementHandler()
    //               , new MysqlHandler(true) 
            );
    
        }
    }


介绍：
    
    VoHandler ： 生成vo和dto
    QueryModelHandler：生成querymodel
    ServiceHandler:生成service
    ControllerHandler:生成controller
    ElementHandler:生成emement用的index,js
    MysqlHandler:生成mysql数据库表结构
    

4、执行main方法

5、实例展示

    1、定义实体
    
    package io.github.itliwei.mvcorm.entity;
    
    
    import io.github.itliwei.mvcorm.generator.annotation.Field;
    import io.github.itliwei.mvcorm.generator.annotation.Type;
    import io.github.itliwei.mvcorm.generator.annotation.controller.ControllerClass;
    import io.github.itliwei.mvcorm.generator.annotation.elementui.ElementClass;
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
    @ServiceClass
    @ControllerClass(path = "api/user",desc = "用户接口")
    @ElementClass
    @ViewObject(groups = {SIMPLE_VO,SIMPLE_DTO})
    public class User extends IdEntity {
    
        public static final String SIMPLE_VO = "UserVo";
    
        public static final String SIMPLE_DTO = "UserDto";
    
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
    }


    2、手写GeneratorTest类
    package io.github.itliwei;
    
    
    
    import io.github.itliwei.mvcorm.generator.generator.Config;
    import io.github.itliwei.mvcorm.generator.generator.Generator;
    import io.github.itliwei.mvcorm.generator.generator.handler.*;
    
    import java.io.File;
    import java.nio.file.Paths;
    
    /**
     * @author : liwei
     * @date : 2019/05/21 21:50
     */
    public class GeneratorTest {
        private static final String S = File.separator;
    
        public static void main(String[] args) {
            String projectPath = new File("").getAbsolutePath();
            Config config = new Config();
            config.setGenLogFile(Paths.get(System.getProperty("user.home"), "gen.log").toString());
            config.setUrl("jdbc:mysql://106.13.146.82:3306/test?useSSL=false");
            config.setEntityPackage("io.github.itliwei.mvcorm.entity");
    
            config.setUsername("root");
            config.setPassword("Mysql2ol9");
            config.setUseLombok(true);
    
            config.setElementPackage("/Users/vince/myproject/mvcorm/vue");
            config.setElementPath("/Users/vince/myproject/mvcorm/vue");
    
            Generator.generate(config
                    , new VoHandler()
                    , new QueryModelHandler()
                    , new ServiceHandler()
                    , new ControllerHandler()
                    , new ElementHandler()
    //               , new MysqlHandler(true)//这里不再演示
            );
    
        }
    }


    3、执行main方法后的代码
    
    VO对象
    
    package io.github.itliwei.mvcorm.entity.vo;
    
    import lombok.Getter;
    import lombok.Setter;
    
    import java.io.Serializable;
    import io.github.itliwei.mvcorm.generator.annotation.Field;
    
    
    @Getter
    @Setter
    public class UserDto implements Serializable {
    	@Field(label = "ID")
    	private Long id;
    	@Field(label = "姓名")
    	private String username;
    
    
    }
    
    Dto对象
    
    package io.github.itliwei.mvcorm.entity.vo;
    
    import lombok.Getter;
    import lombok.Setter;
    
    import java.io.Serializable;
    import io.github.itliwei.mvcorm.generator.annotation.Field;
    
    
    @Getter
    @Setter
    public class UserDto implements Serializable {
    	@Field(label = "ID")
    	private Long id;
    	@Field(label = "姓名")
    	private String username;
    
    
    }
    
    queryModel对象
    
    package io.github.itliwei.mvcorm.entity.query;
    
    import java.io.Serializable;
    
    import io.github.itliwei.mvcorm.orm.opt.QueryModel;
    
    import lombok.Getter;
    import lombok.Setter;
    import lombok.Builder;
    import lombok.NoArgsConstructor;
    import lombok.AllArgsConstructor;
    
    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public class UserQueryModel  extends QueryModel implements Serializable {
    	private Long idEQ;
    
    	private String usernameEQ;
    
    	private String usernameIN;
    
    	private Long jobIdEQ;
    
    	private Long jobIdIN;
    
    	private Integer ageEQ;
    
    	private Integer ageIN;
    
    	private String addressEQ;
    
    	private String addressIN;
    
    }
    
    service对象
    
    package io.github.itliwei.mvcorm.service;
    
    
    import io.github.itliwei.mvcorm.entity.*;
    import io.github.itliwei.mvcorm.orm.BaseService;
    import org.springframework.stereotype.Service;
    
    
    @Service
    public class UserService extends BaseService<User> {
    }
    
    
    controller对象
    
    package io.github.itliwei.mvcorm.controller;
    
    
    import io.github.itliwei.mvcorm.entity.*;
    import org.springframework.beans.factory.annotation.Autowired;
    import io.github.itliwei.mvcorm.mvc.Resp;
    import io.github.itliwei.mvcorm.service.UserService;
    import io.github.itliwei.mvcorm.entity.query.UserQueryModel;
    import org.springframework.web.bind.annotation.*;
    import lombok.extern.slf4j.Slf4j;
    import io.swagger.annotations.*;
    import io.github.itliwei.mvcorm.orm.opt.Page;
    
    
    
    @Slf4j
    @RestController
    @RequestMapping("api/user")
    @Api(value = "用户接口",description = "用户接口")
    public class UserController {
    
        @Autowired
        private UserService userService;
    
        @GetMapping("/info/{id}")
        @ApiOperation(value = "根据ID查找",httpMethod = "GET")
        public Resp<User> getById(@PathVariable long id) {
            User result = userService.findById(id);
           return Resp.success(result);
        }
    
    
        @PostMapping("/page/query")
        @ApiOperation(value = "分页查找内容",httpMethod = "POST")
        public Resp<Page<User>> pageQuery(UserQueryModel queryModel) {
            Page<User> result = userService.findPage(queryModel);
            return Resp.success(result);
        }
    
        @PostMapping("/save")
        @ApiOperation(value = "保存",httpMethod = "POST")
        public Resp<User> save(User user) {
            User result = userService.save(user);
            return Resp.success(result);
        }
    
        @PostMapping("/update")
        @ApiOperation(value = "保存",httpMethod = "POST")
        public Resp<User> update(User user) {
            User result = userService.update(user);
            return Resp.success(result);
        }
    
        @GetMapping("/delete/{id}")
        @ApiOperation(value = "根据ID删除",httpMethod = "GET")
        public Resp<Integer> delete(@PathVariable long id) {
            int result = userService.delete(id);
            return Resp.success(result);
        }
    }
    
    
    Element文件
    
    import request from '@/utils/request'
    
    export function getList(params) {
        return request({
            url: '/page/query',
            method: 'POST',
            params
        })
    }
    
    export function getInfo(id) {
        return request({
            url: '/info/'+id,
            method: 'GET',
        })
    }
    
    
    export function update(params) {
        return request({
            url: '/update',
            method: 'POST',
            params
        })
    }
    
    export function add(params) {
        return request({
            url: '/add',
            method: 'POST',
            params
        })
    }
    
    export function del(id){
        return request({
            url: '/info/'+id,
            method: 'GET',
        })
    }
    
    
    <template>
        <div class="user-container">
            <el-col :span="24" class="toolbar">
                <el-form :inline="true" :model="userQueryModel"  size="mini">
                    <el-form-item :span="6" label="姓名">
                        <el-input v-model="userQueryModel.username" placeholder=""></el-input>
                    </el-form-item>
                    <el-form-item :span="6" label="jobId">
                        <el-input v-model="userQueryModel.jobId" placeholder=""></el-input>
                    </el-form-item>
                    <el-form-item :span="6" label="年龄">
                        <el-input v-model="userQueryModel.age" placeholder=""></el-input>
                    </el-form-item>
                    <el-form-item :span="6" label="地址">
                        <el-input v-model="userQueryModel.address" placeholder=""></el-input>
                    </el-form-item>
                <el-form-item :span="6" >
                    <el-button type="primary" v-on:click="getList" size="mini">查询</el-button>
                </el-form-item>
                </el-form>
            </el-col>
            <div>
                <el-button type="danger" @click="addVisible=true" size="mini">添加</el-button>
            </div>
    
            <el-table :data="tableData" highlight-current-row v-loading="listLoading" style="width: 100%;" size="mini">
                <el-table-column type="index" width="50"></el-table-column>
                    <el-table-column prop="username" label="姓名"></el-table-column>
                    <el-table-column prop="jobId" label="jobId"></el-table-column>
                    <el-table-column prop="age" label="年龄"></el-table-column>
                    <el-table-column prop="address" label="地址"></el-table-column>
                <el-table-column
                        label="操作"
                        width="100">
                    <template slot-scope="scope">
                        <el-button @click="getInfo(scope.row.id)" type="text" size="mini">查看</el-button>
                        <el-button @click="updateDialog(scope.row)" type="text" size="mini">修改</el-button>
                    </template>
                </el-table-column>
            </el-table>
            <el-pagination  style="float:right" background
                            @current-change="handleCurrentChange"
                            layout="prev, pager, next"
                            :total="total">
            </el-pagination>
    
            <el-dialog title="添加" :visible.sync="addVisible" >
                <el-form :model="userDtoAdd" label-width="80px" ref="addForm" :rules="addFormRules" size="mini">
                        <el-form-item label="姓名" prop="姓名">
                            <el-input v-model="userDtoAdd.username" ></el-input>
                        </el-form-item>
                </el-form>
                <div slot="footer" class="dialog-footer">
                    <el-button @click="handleClose('addForm')" size="mini">取 消</el-button>
                    <el-button type="primary" @click="handlerSubmit('addForm')" size="mini">确 定</el-button>
                </div>
            </el-dialog>
    
            <el-dialog title="修改" :visible.sync="updateVisible" >
                <el-form :model="userDtoUpdate" label-width="80px" ref="form" :rules="updateFormRules">
                        <el-form-item label="姓名" prop="姓名">
                            <el-input v-model="userDtoUpdate.username" ></el-input>
                        </el-form-item>
                </el-form>
                <div slot="footer" class="dialog-footer">
                    <el-button @click="handleClose('updateForm')" size="mini">取 消</el-button>
                    <el-button type="primary" @click="handlerSubmit('updateForm')" size="mini">确 定</el-button>
                </div>
            </el-dialog>
        </div>
    </template>
    
    <script>
        import { mapGetters } from 'vuex'
        import { Message, MessageBox } from 'element-ui'
        import {getInfo,add,update,delete,getList} from '@/api/user'
        import Axios from  'axios'
    
        const tableData = []
        export default {
            name: 'Org',
            data() {
                return {
                    tableData,
                    total : 0,
                    addVisible: false,
                    updateVisible: false,
                    listLoading: false,
                    userQueryModel:{
                        username,
                        jobId,
                        age,
                        address,
                        pageNumber: 1,
                        pageSize: 10,
                    },
                    userDtoAdd:{
                            username,
                    },
                    userDtoUpdate:{
                            username,
                    },
                    addFormRules: {
    //                    ownerCode: [
    //                        {required: true, message: '不能为空', trigger: 'blur'},
    //                    ],
    
                    },
                    updateFormRules: {
        //                    ownerCode: [
        //                        {required: true, message: '不能为空', trigger: 'blur'},
        //                    ],
    
                    },
                }
            },
    
            created () {
                this.init()
            },
    
            methods:{
                init() {
                    this.getList();
                },
    
                getList(){
                    this.tableData = [];
                    this.listLoading = true;
                    getList(this.userQueryModel).then((res) => {
                        res.data.data.forEach(item => {
                            this.tableData.push(item)
                        })
                        this.total = res.data.total;
                        this.listLoading = false;
                    });
                },
    
                getInfo(id){
                    this.$router.push({path: '/user/info',query: {id: id}})
    
                },
    
                handleCurrentChange(val) {
                    this.userQueryModel.pageNumber = val;
                    this.getList();
                },
    
                updateDialog(val){
                    this.updateVisible = true;
                },
    
                handlerSubmit(formName){
                    this.$refs[formName].validate((valid) => {
                        if (valid){
                            if (formName ==="updateForm"){
                                update(this.userDtoUpdate).then(response => {
                                    Message({
                                        message: "修改成功",
                                        type: 'success',
                                    });
                                });
                                this.init();
                            }else if (formName === "addForm"){
                                add(this.userDtoAdd).then(response => {
                                    Message({
                                        message: "添加成功",
                                        type: 'success',
                                    });
                                });
                                this.init();
                            }
    
                        }
                    });
                },
    
                handleClose(formName) {
                    this.addVisible = false;
                    this.updateVisible = false;
                    this.$refs[formName].resetFields();//将form表单重置
                    this.$refs[formName].clearValidate();//将form表单重置
                },
    
            }
        }
    </script>
    
    <style rel="stylesheet/scss" lang="scss" scoped>
        .applicaton {
        &-container {
             margin: 30px;
         }
        &-text {
             font-size: 30px;
             line-height: 46px;
         }
        }
    </style>

   
   
6、拓展

    如果有些文件需要修改，比如controller，service对外接口一般会有一些自定义的。可以在生成后进行修改，重新生成不会覆盖
        
    
    
    
    
    