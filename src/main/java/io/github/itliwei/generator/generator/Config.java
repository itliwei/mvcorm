package io.github.itliwei.generator.generator;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Config {

	private boolean useLombok = true;

	private String genLogFile;

	private String entityPackage;

	private String entityName;

	private boolean useEnumOrdinalTypeHandlerByDefault = true;
	private boolean escapeColumn = false;

	/* java query model */
	private String queryModelPackage;
	private String queryModelSuffix = "QueryModel";

	/* mysql */
	private String url = "jdbc:mysql://127.0.0.1/test";
	private String username = "root";
	private String password = "123456";

	private String freemakerVersion = "2.3.0";
	private String template = "/";
	private String defaultEncoding = "UTF-8";

	/* nodejs */
	private String nodeDestFile;
	private String nodeDocFile;
	private String nodeServicePackage;

	private String voPackage;
	private String voSuffix = "Vo";
	private String dtoSuffix = "Dto";

	private String servicePackage;
	private String serviceSuffix = "Service";

	private String controllerPackage;
	private String controllerSuffix = "Controller";

	private String componentPackage;
	private String componentSuffix = "Component";

	private String elementPackage = "/vue";
	private String elementPath = "/vue";
	private String elementSuffix = "index";
	private String elementJSSuffix = "js";
}
