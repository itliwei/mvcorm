package ${meta.voPackage};

<#if meta.importOther??>
	<#list meta.importOther as import>
import ${import};
	</#list>
</#if>
<#if meta.useLombok>
import lombok.*;
</#if>

import java.io.Serializable;
import io.swagger.annotations.*;
import com.google.common.base.Converter;
import io.github.itliwei.generator.generator.util.BeanConvertUtil;

<#if meta.importJava??>
	<#list meta.importJava as import>
import ${import};
	</#list>
</#if>

<#if meta.useLombok>
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
</#if>
@ApiModel(value = "${meta.className}", description = "${meta.className}")
public class ${meta.className} implements Serializable {
<#if meta.isIdEntity??>
	@ApiModelProperty(value = "id")
	private Long id;
</#if>
<#list meta.fields as field>
	@ApiModelProperty(value = "${field.label}")
	private ${field.type} ${field.name};
</#list>
	/* 扩展 */
<#list meta.associationFields as field>
	@ApiModelProperty(value = "${field.label}")
	private ${field.type} ${field.name};
</#list>
<#list meta.collectionFields as field>
	@ApiModelProperty(value = "${field.label}")
	private ${field._interface}${field.elementGroup} ${field.name} = new ${field.type}<>();
</#list>
<#list meta.mapFields as field>
	@ApiModelProperty(value = "${field.label}")
	private ${field._interface}<${field.key},${field.value}> ${field.name} = new ${field.type}<>();
</#list>

	/* 转换 */
<#if meta.dto>
	public ${meta.entityName} convert2${meta.entityName}(){
		${meta.className}Convert convert = new ${meta.className}Convert();
		${meta.entityName} target = convert.convert(this);
		return target;
	}


	private static class ${meta.className}Convert extends Converter<${meta.className}, ${meta.entityName}> {
		@Override
		protected ${meta.entityName} doForward(${meta.className} source) {
			${meta.entityName} target = new ${meta.entityName}();
			BeanConvertUtil.convert(target,source);
			return target;
		}

		@Override
		protected ${meta.className} doBackward(${meta.entityName} source) {
			throw new AssertionError("不支持逆向转化方法!");
		}
	}
</#if>

<#if meta.vo>
	public static ${meta.className} convert2${meta.className}(${meta.entityName} source){
		${meta.className}Convert convert = new ${meta.className}Convert();
		return  convert.reverse().convert(source);
	}


	private static class ${meta.className}Convert extends Converter<${meta.className}, ${meta.entityName}> {
		@Override
		protected ${meta.entityName} doForward(${meta.className} source) {
		throw new AssertionError("不支持逆向转化方法!");
	}

	@Override
	protected ${meta.className} doBackward(${meta.entityName} source) {
		${meta.className} target = new ${meta.className}();
		BeanConvertUtil.convert(target,source);
		return target;
	}
	}
</#if>
}