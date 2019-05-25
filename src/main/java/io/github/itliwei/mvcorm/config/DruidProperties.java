package io.github.itliwei.mvcorm.config;

import com.alibaba.druid.pool.DruidDataSource;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 数据库数据源配置
 *
 * @author fengshuonan
 * @date 2017-05-21 11:18
 */
@Component
@ConfigurationProperties(prefix = "spring.datasource")
@Getter
@Setter
public class DruidProperties {

    private String dialect = "mysql";

    private String domian = "";

    private String databaseName = "";

    private String url = "";

    private String username = "";

    private String password = "";

    private String driverClassName = "com.mysql.jdbc.Driver";

    private Integer initialSize = 2;

    private Integer minIdle = 1;

    private Integer maxActive = 20;

    private Integer maxWait = 60000;

    private Integer timeBetweenEvictionRunsMillis = 60000;

    private Integer minEvictableIdleTimeMillis = 300000;

    private String validationQuery = "SELECT 'x'";

    private Boolean testWhileIdle = true;

    private Boolean testOnBorrow = false;

    private Boolean testOnReturn = false;

    private Boolean poolPreparedStatements = false;

    private Integer maxPoolPreparedStatementPerConnectionSize = 20;

    private String filters = "stat,log4j,wall,mergeStat";

    public void config(DruidDataSource dataSource) throws Exception {

        if ("mysql".equals(dialect)) {
            if (StringUtils.isBlank(url)) {
                url = "jdbc:mysql://" + domian + "/" + databaseName + "?useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull";
            }
        }
        if ("oracle".equals(dialect)) {
            url = "jdbc:oracle:thin:@ " + domian + ":" + databaseName;
        }

        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);

        dataSource.setDriverClassName(driverClassName);
        dataSource.setInitialSize(initialSize);     //定义初始连接数
        dataSource.setMinIdle(minIdle);             //最小空闲
        dataSource.setMaxActive(maxActive);         //定义最大连接数
        dataSource.setMaxWait(maxWait);             //最长等待时间

        // 配置间隔多久才进行一次检测，检测需要关闭的空闲连接，单位是毫秒
        dataSource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);

        // 配置一个连接在池中最小生存的时间，单位是毫秒
        dataSource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
        dataSource.setValidationQuery(validationQuery);
        dataSource.setTestWhileIdle(testWhileIdle);
        dataSource.setTestOnBorrow(testOnBorrow);
        dataSource.setTestOnReturn(testOnReturn);

        // 打开PSCache，并且指定每个连接上PSCache的大小
        dataSource.setPoolPreparedStatements(poolPreparedStatements);
        dataSource.setMaxPoolPreparedStatementPerConnectionSize(maxPoolPreparedStatementPerConnectionSize);

        dataSource.setFilters(filters);
    }

}
