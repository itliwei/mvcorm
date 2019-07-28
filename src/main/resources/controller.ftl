package ${meta.controllerPackage};


import ${meta.controllerTypePackage};
import org.springframework.beans.factory.annotation.Autowired;
import io.github.itliwei.mvcorm.mvc.constants.ErrorCode;
import io.github.itliwei.mvcorm.mvc.Resp;
import io.github.itliwei.mvcorm.util.PageBuilder;
import ${meta.entityClass};
import ${meta.serviceClass};
import ${meta.queryModelClass};
import ${meta.componentClass};
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

    @Autowired
    private ${meta.type}Component ${meta.typeName}Component;

    @GetMapping("/info/{id}")
    @ApiOperation(value = "根据ID查找",httpMethod = "GET")
    public Resp<${meta.type}Vo> getById(@PathVariable long id) {
        ${meta.type} result = ${meta.typeName}Service.findById(id);
        if (result != null){
            ${meta.type}Vo resultVo =  ${meta.typeName}Component.convert2${meta.type}Vo(result);
            return Resp.success(resultVo);
        }
        return Resp.error(ErrorCode.DATA_NOT_EXIST,"id:"+id);
    }


    @PostMapping("/page/query")
    @ApiOperation(value = "分页查找内容",httpMethod = "POST")
    public Resp<Page<${meta.type}Vo>> pageQuery(@RequestBody ${meta.type}QueryModel queryModel) {
        Page<${meta.type}> result = ${meta.typeName}Service.findPage(queryModel);
        Page<${meta.type}Vo> voPage = PageBuilder.copyAndConvert(result, ${meta.typeName}Component::convert2${meta.type}Vo);
        return Resp.success(voPage);
    }

    @PostMapping("/save")
    @ApiOperation(value = "保存",httpMethod = "POST")
    public Resp<${meta.type}Vo> save(@RequestBody ${meta.type}Dto ${meta.typeName}Dto) {
        ${meta.type} entity = ${meta.typeName}Component.convert2${meta.type}(${meta.typeName}Dto);
        int result = ${meta.typeName}Service.save(entity);
        if (result > 0){
            ${meta.type}Vo entityVo = ${meta.typeName}Component.convert2${meta.type}Vo(entity);
            return Resp.success(entityVo);
        }
        return Resp.error(ErrorCode.SERVER,"保存数据失败");
    }

    @PostMapping("/update")
    @ApiOperation(value = "修改",httpMethod = "POST")
    public Resp update(@RequestBody ${meta.type}Dto ${meta.typeName}Dto) {
        ${meta.type} entity = ${meta.typeName}Component.convert2${meta.type}(${meta.typeName}Dto);
        int result = ${meta.typeName}Service.update(entity);
        if (result > 0) {
            return Resp.success(result);
        }
        return Resp.error(ErrorCode.SERVER,"修改数据失败");
    }

    @GetMapping("/delete/{id}")
    @ApiOperation(value = "根据ID删除",httpMethod = "GET")
    public Resp delete(@PathVariable long id) {
        int result = ${meta.typeName}Service.delete(id);
        if (result > 0) {
            return Resp.success(result);
        }
        return Resp.error(ErrorCode.SERVER,"删除数据失败");
    }


}