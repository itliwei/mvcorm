package ${meta.servicePackage};


import ${meta.serviceTypePackage};
import io.github.itliwei.mvcorm.orm.BaseService;
import org.springframework.stereotype.Service;

<#if meta.importFullTypes??>
    <#list meta.importFullTypes as importFullType>
    import ${importFullType};
    </#list>
</#if>

@Service
public class ${meta.name} extends BaseService<${meta.type}> {
}