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

        Generator.generate(config
                , new VoHandler()
                , new QueryModelHandler()
                , new ServiceHandler()
                , new ControllerHandler()
//               , new MysqlHandler(true)
        );

    }
}
