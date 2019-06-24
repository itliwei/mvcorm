package io.github.itliwei.mvcorm.config;

import com.alibaba.druid.pool.DruidDataSource;
import io.github.itliwei.mvcorm.orm.CService;
import io.github.itliwei.mvcorm.orm.CormConfig;
import io.github.itliwei.mvcorm.orm.mapper.CMapper;
import io.github.itliwei.mvcorm.orm.plugins.SkipInterceptor;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.mapper.MapperFactoryBean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;


/**
 * @author chengys4
 *         2017-08-28 17:00
 **/
@Configuration
@EnableConfigurationProperties({DruidProperties.class})
public class DataSourceConfig {
    private ResourceLoader resourceLoader = new DefaultResourceLoader();

    @Autowired
    private DruidProperties druidProperties;


    @Bean(name = "masterDataSource")
    @Primary
    public DataSource getMasterDataSource() throws Exception {
        DruidDataSource dataSource = new DruidDataSource();
        druidProperties.config(dataSource);
        return dataSource;
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
        return new DataSourceTransactionManager(getMasterDataSource());
    }

    @Bean
    public CormConfig initConfig() throws Exception {
        CormConfig cormConfig = new CormConfig();
        cormConfig.addDefaultMasterMapper(mapper(getMasterDataSource()));
        cormConfig.addDefaultSlaveMapper(mapper(getMasterDataSource()));
        return cormConfig;
    }

    @Bean
    public CService cService() throws Exception {
        return new CService(mapper(getMasterDataSource()));
    }


}
