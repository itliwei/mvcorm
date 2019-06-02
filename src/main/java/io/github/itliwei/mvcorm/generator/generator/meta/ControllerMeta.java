package io.github.itliwei.mvcorm.generator.generator.meta;

import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

/**
 * @author : liwei
 * @date : 2019/05/13 09:18
 * @description : service meta
 */
@Getter
@Setter
public class ControllerMeta {
    private String controllerPackage;

    private String controllerTypePackage;

    private String type;
    private String typeName;
    private String name;
    private String path;
    private String entityClass;
    private String serviceClass;

    private String apiValue;
    private String apiDesc;


    private Set<String> importFullTypes = new HashSet<>();

}
