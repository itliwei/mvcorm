package io.github.itliwei.mvcorm.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * @author : liwei
 * @date : 2019/07/07 12:24
 * @description : Druid数据源
 */
@Configuration
public class DruidConfig {

    @ConfigurationProperties(prefix="spring.datasource")
    @Bean(name="dataSource")
    public DataSource druidMasterDataSource() {
        return   new DruidDataSource();
    }
}
