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

}