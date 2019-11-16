package ${meta.controllerPackage};


import ${meta.controllerTypePackage};
import org.springframework.beans.factory.annotation.Autowired;
import io.github.itliwei.mvcorm.mvc.constants.ErrorCode;
import io.github.itliwei.mvcorm.mvc.Resp;
import io.github.itliwei.mvcorm.util.PageBuilder;
import ${meta.entityClass};
import ${meta.serviceClass};
import ${meta.queryModelClass};
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import io.swagger.annotations.*;
import io.github.itliwei.mvcorm.orm.opt.Page;
import javax.validation.Valid;



<#if meta.importFullTypes??>
    <#list meta.importFullTypes as importFullType>
import ${importFullType};
    </#list>
</#if>

@Slf4j
@RestController
@RequestMapping("${meta.path}")
@Api(tags = "${meta.apiValue}",description = "${meta.apiDesc}")
public class ${meta.name} {

    @Autowired
    private ${meta.serviceName} ${meta.serviceName?uncap_first};


    @GetMapping("/info/{id}")
    @ApiOperation(value = "根据ID查找",httpMethod = "GET")
    public Resp<${meta.voName}> getById(@PathVariable long id) {
        ${meta.type} result = ${meta.serviceName?uncap_first}.findById(id);
        if (result != null){
            ${meta.voName} ${meta.typeName}Vo =  ${meta.voName}.convert2${meta.voName}(result);
            return Resp.success(${meta.typeName}Vo);
        }
        return Resp.error(ErrorCode.DATA_NOT_EXIST,"id:"+id);
    }


    @PostMapping("/page/query")
    @ApiOperation(value = "分页查找内容",httpMethod = "POST")
    public Resp<Page<${meta.voName}>> pageQuery(@RequestBody ${meta.queryModelName} queryModel) {
        Page<${meta.type}> result = ${meta.serviceName?uncap_first}.findPage(queryModel);
        Page<${meta.voName}> voPage = PageBuilder.copyAndConvert(result, ${meta.voName}::convert2${meta.voName});
        return Resp.success(voPage);
    }

    @PostMapping("/save")
    @ApiOperation(value = "保存",httpMethod = "POST")
    public Resp<${meta.voName}> save(@Valid @RequestBody ${meta.dtoName} ${meta.typeName}Dto) {
        ${meta.type} entity = ${meta.typeName}Dto.convert2${meta.type}();
        int result = ${meta.serviceName?uncap_first}.save(entity);
        if (result > 0){
            ${meta.voName} ${meta.typeName}Vo =  ${meta.voName}.convert2${meta.voName}(entity);
            return Resp.success(${meta.typeName}Vo);
        }
        return Resp.error(ErrorCode.SERVER,"保存数据失败");
    }

    @PostMapping("/update")
    @ApiOperation(value = "修改",httpMethod = "POST")
    public Resp update(@Valid @RequestBody ${meta.dtoName} ${meta.typeName}Dto) {
        ${meta.type} entity = ${meta.typeName}Dto.convert2${meta.type}();
        int result = ${meta.serviceName?uncap_first}.update(entity);
        if (result > 0) {
            return Resp.success();
        }
        return Resp.error(ErrorCode.SERVER,"修改数据失败");
    }

    @GetMapping("/delete/{id}")
    @ApiOperation(value = "根据ID删除",httpMethod = "GET")
    public Resp delete(@PathVariable long id) {
        int result = ${meta.serviceName?uncap_first}.delete(id);
        if (result > 0) {
            return Resp.success();
        }
        return Resp.error(ErrorCode.SERVER,"删除数据失败");
    }


}