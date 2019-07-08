package io.github.itliwei.mvcorm.config;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import io.github.itliwei.mvcorm.orm.CService;
import io.github.itliwei.mvcorm.orm.CormConfig;
import io.github.itliwei.mvcorm.orm.mapper.CMapper;
import io.github.itliwei.mvcorm.orm.plugins.SkipInterceptor;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.mapper.MapperFactoryBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

/**
 * @author : liwei
 * @date : 2019/07/08 20:22
 * @description : ${TODO}
 */
@Configuration
public class DataSourceConfig {
    private ResourceLoader resourceLoader = new DefaultResourceLoader();

    private DataSource masterDataSource;

    private DataSource slaveDataSource;

    @Primary
    @Bean
    @ConfigurationProperties("spring.datasource")
    public DataSource dataSource(){
        masterDataSource =  DruidDataSourceBuilder.create().build();
        slaveDataSource =  DruidDataSourceBuilder.create().build();
        return masterDataSource;
    }

    @Bean
    @ConfigurationProperties("spring.datasource.master")
    public DataSource dataSourceMaster(){
        masterDataSource =  DruidDataSourceBuilder.create().build();
        return masterDataSource;
    }

    @Bean
    @ConfigurationProperties("spring.datasource.slave")
    public DataSource dataSourceSlave(){
        slaveDataSource =  DruidDataSourceBuilder.create().build();
        return slaveDataSource;
    }


    private Interceptor[] interceptors() {
        return new Interceptor[]{new SkipInterceptor()};
    }

    public SqlSessionFactory buildSqlSessionFactory(DataSource dataSource) throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(dataSource);
        factoryBean.setPlugins(interceptors());
        factoryBean.setConfigLocation(resourceLoader.getResource("classpath:mybatis-config.xml"));
        return factoryBean.getObject();
    }

    public CMapper mapper(DataSource dataSource) throws Exception {
        MapperFactoryBean<CMapper> mfb = new MapperFactoryBean<>();
        mfb.setMapperInterface(CMapper.class);
        mfb.setSqlSessionFactory(buildSqlSessionFactory(dataSource));
        return mfb.getObject();
    }

    @Bean(name = "tm")
    public DataSourceTransactionManager transactionManager() throws Exception {
        return new DataSourceTransactionManager(masterDataSource);
    }

    @Bean
    public CormConfig initConfig() throws Exception {
        CormConfig cormConfig = new CormConfig();
        cormConfig.addDefaultMasterMapper(mapper(masterDataSource));
        cormConfig.addDefaultSlaveMapper(mapper(slaveDataSource));
        return cormConfig;
    }

    @Bean
    public CService cService() throws Exception {
        return new CService(mapper(masterDataSource));
    }

}
