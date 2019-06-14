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
    
    
    
    
    