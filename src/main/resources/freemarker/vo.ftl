package ${meta.voPackage};

<#if meta.importOther??>
	<#list meta.importOther as import>
import ${import};
	</#list>
</#if>
<#if meta.useLombok>
import lombok.Getter;
import lombok.Setter;
</#if>

import java.io.Serializable;
import io.github.itliwei.mvcorm.generator.annotation.Field;

<#if meta.importJava??>
	<#list meta.importJava as import>
import ${import};
	</#list>
</#if>

<#if meta.useLombok>
@Getter
@Setter
</#if>
public class ${meta.className} implements Serializable {
<#if meta.isIdEntity??>
	@Field(label = "ID")
	private Long id;
</#if>
<#list meta.fields as field>
	@Field(label = "${field.label}"<#if field.order != 999>, order = ${field.order}</#if>)
	private ${field.type} ${field.name};
</#list>

<#list meta.associationFields as field>
	@Field(label = "${field.label}"<#if field.order != 999>, order = ${field.order}</#if>)
	private ${field.type} ${field.name};
</#list>
<#list meta.collectionFields as field>
	@Field(label = "${field.label}"<#if field.order != 999>, order = ${field.order}</#if>)
	private ${field._interface}${field.elementGroup} ${field.name} = new ${field.type}<>();
</#list>
<#list meta.mapFields as field>
	@Field(label = "${field.label}"<#if field.order != 999>, order = ${field.order}</#if>)
	private ${field._interface}<${field.key},${field.value}> ${field.name} = new ${field.type}<>();
</#list>

}