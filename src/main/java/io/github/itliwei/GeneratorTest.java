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
        config.setUrl("jdbc:mysql://127.0.0.1:3306/test?useSSL=false");
        config.setEntityPackage("io.github.itliwei.mvcorm.entity");
        config.setQueryModelPackage("io.github.itliwei.mvcorm.query");
        config.setVoPackage("io.github.itliwei.mvcorm.vo");
        config.setServicePackage("io.github.itliwei.mvcorm.service");
        config.setControllerPackage("io.github.itliwei.mvcorm.controller");

        config.setUsername("root");
        config.setPassword("test");
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
