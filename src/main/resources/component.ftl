package ${meta.componentPackage};


import ${meta.voPackage}.*;
import ${meta.packageName}.${meta.entityName};
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.stereotype.Component;
import java.lang.reflect.InvocationTargetException;


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
    * @return
    * @throws InvocationTargetException
    * @throws IllegalAccessException
    */
    public ${meta.entityName} convert2${meta.entityName}(${group} source) {
        ${meta.entityName} target = new ${meta.entityName}();
        try{
            BeanUtils.copyProperties(target,source);
            return target;
        }catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ${group} convert2${group}(${meta.entityName} source){
        ${group} target = new ${group}();
        try{
            BeanUtils.copyProperties(target,source);
            return target;
        }catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }
</#list>
}