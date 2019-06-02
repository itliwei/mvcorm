package ${meta.servicePackage};


import ${meta.serviceTypePackage};
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import io.swagger.annotations.*;
import io.github.itliwei.mvcorm.orm.opt.Page;


<#if meta.importFullTypes??>
    <#list meta.importFullTypes as importFullType>
    import ${importFullType};
    </#list>
</#if>

@Slf4j
@RestController()
@RequestMapping("${meta.path}")
@Api(value = "${meta.apiValue}",description = "${meta.apiDesc}")
public class ${meta.name} {

    @Autowired
    private ${meta.type}+Service ${meta.serviceName}e;

    @GetMapping("/info/{id}")
    @ApiOperation(value = "根据ID查找",httpMethod = "GET")
    public Resp<${meta.name}> getById(@PathVariable long id) {
        ${meta.name} result = ${meta.serviceName}.findById(id);
       return Resp.success(result)
    }


    @PostMapping("/page")
    @ApiOperation(value = "分页查找内容",httpMethod = "POST")
    public Resp<Page<${meta.name}>> pageQuery(${meta.name}+QueryModel queryModel) {
        Page<${meta.name}> result = ${meta.serviceName}.findPage(queryModel);
        return Resp.success(result)
    }


}