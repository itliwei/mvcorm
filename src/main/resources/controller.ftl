package ${meta.controllerPackage};


import ${meta.controllerTypePackage};
import org.springframework.beans.factory.annotation.Autowired;
import io.github.itliwei.mvcorm.mvc.Resp;
import ${meta.entityClass};
import ${meta.serviceClass};
import ${meta.queryModelClass};
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
@RestController
@RequestMapping("${meta.path}")
@Api(value = "${meta.apiValue}",description = "${meta.apiDesc}")
public class ${meta.name} {

    @Autowired
    private ${meta.type}Service ${meta.typeName}Service;

    @GetMapping("/info/{id}")
    @ApiOperation(value = "根据ID查找",httpMethod = "GET")
    public Resp<${meta.type}> getById(@PathVariable long id) {
        ${meta.type} result = ${meta.typeName}Service.findById(id);
       return Resp.success(result);
    }


    @PostMapping("/page/query")
    @ApiOperation(value = "分页查找内容",httpMethod = "POST")
    public Resp<Page<${meta.type}>> pageQuery(${meta.type}QueryModel queryModel) {
        Page<${meta.type}> result = ${meta.typeName}Service.findPage(queryModel);
        return Resp.success(result);
    }

    @PostMapping("/save")
    @ApiOperation(value = "保存",httpMethod = "POST")
    public Resp<${meta.type}> save(${meta.type} ${meta.typeName}) {
        ${meta.type} result = ${meta.typeName}Service.save(${meta.typeName});
        return Resp.success(result);
    }

    @PostMapping("/update")
    @ApiOperation(value = "保存",httpMethod = "POST")
    public Resp<${meta.type}> update(${meta.type} ${meta.typeName}) {
        ${meta.type} result = ${meta.typeName}Service.update(${meta.typeName});
        return Resp.success(result);
    }

    @GetMapping("/delete/{id}")
    @ApiOperation(value = "根据ID删除",httpMethod = "GET")
    public Resp<Integer> delete(@PathVariable long id) {
        int result = ${meta.typeName}Service.delete(id);
        return Resp.success(result);
    }


}