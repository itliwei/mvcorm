package ${meta.componentPackage};


import ${meta.voPackage}.*;
import ${meta.packageName}.${meta.entityName};
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.stereotype.Component;
import java.lang.reflect.InvocationTargetException;
import io.github.itliwei.generator.generator.util.BeanConvertUtil;


<#if meta.importFullTypes??>
    <#list meta.importFullTypes as importFullType>
import ${importFullType};
    </#list>
</#if>

@Component
@Slf4j
public class ${meta.name} {
<#list meta.groups as group>
    /**
    * @return ${meta.entityName}
    * @param source ${group}
    */
    public ${meta.entityName} convert2${meta.entityName}(${group} source) {
        ${meta.entityName} target = new ${meta.entityName}();
        BeanConvertUtil.convert(target,source);
        return target;
    }

    /**
    * @return ${group}
    * @param source ${meta.entityName}
    */
    public ${group} convert2${group}(${meta.entityName} source){
        ${group} target = new ${group}();
        BeanConvertUtil.convert(target,source);
        return target;
    }
</#list>
}