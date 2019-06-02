package ${meta.queryModelPackage};

import java.io.Serializable;

import io.github.itliwei.mvcorm.orm.opt.QueryModel;
<#if meta.useLombok>

import lombok.Getter;
import lombok.Setter;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
</#if>
<#if meta.importFullTypes??>
<#list meta.importFullTypes as importFullType>
import ${importFullType};
</#list>
</#if>

<#if meta.useLombok>
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
</#if>
public class ${meta.type}  extends QueryModel implements Serializable {
	<#if meta.idEntity??>
	private Long idEQ;
	</#if>
	<#if meta.queryModelFields??>
	<#list meta.queryModelFields as queryModelField>

	private ${queryModelField.type}<#if queryModelField.array>[]</#if> ${queryModelField.name};
	</#list>
	</#if>

	<#if !meta.useLombok>
	<#if meta.queryModelFields??>
	<#list meta.queryModelFields as queryModelField>

	public ${queryModelField.type}<#if queryModelField.array>[]</#if> get${queryModelField.name?cap_first}() {
		return ${queryModelField.name};
	}

	public void set${queryModelField.name?cap_first}(${queryModelField.type}<#if queryModelField.array>[]</#if> ${queryModelField.name}) {
		this.${queryModelField.name} = ${queryModelField.name};
	}
	</#list>
	</#if>
	</#if>

}