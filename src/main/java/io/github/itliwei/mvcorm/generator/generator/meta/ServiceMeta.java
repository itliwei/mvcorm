package io.github.itliwei.mvcorm.generator.generator.meta;

import java.util.HashSet;
import java.util.Set;

/**
 * @author : liwei
 * @date : 2019/05/13 09:18
 * @description : service meta
 */

public class ServiceMeta {
    private String servicePackage;

    private String serviceTypePackage;

    private String type;
    private String name;

    private Set<String> importFullTypes = new HashSet<>();

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getServicePackage() {
        return servicePackage;
    }

    public void setServicePackage(String servicePackage) {
        this.servicePackage = servicePackage;
    }

    public String getServiceTypePackage() {
        return serviceTypePackage;
    }

    public void setServiceTypePackage(String serviceTypePackage) {
        this.serviceTypePackage = serviceTypePackage;
    }

    public Set<String> getImportFullTypes() {
        return importFullTypes;
    }

    public void setImportFullTypes(Set<String> importFullTypes) {
        this.importFullTypes = importFullTypes;
    }
}
