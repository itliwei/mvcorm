package ${meta.servicePackage};


import ${meta.serviceTypePackage};
import io.github.itliwei.mvcorm.orm.BaseService;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
import java.lang.reflect.InvocationTargetException;
import org.apache.commons.beanutils.BeanUtils;

<#if meta.importFullTypes??>
    <#list meta.importFullTypes as importFullType>
import ${importFullType};
    </#list>
</#if>

@Service
@Slf4j
public class ${meta.name} extends BaseService<${meta.type}> {

}