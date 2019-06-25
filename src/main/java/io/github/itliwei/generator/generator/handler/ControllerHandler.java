package io.github.itliwei.generator.generator.handler;



import io.github.itliwei.generator.annotation.controller.ControllerClass;
import io.github.itliwei.generator.generator.meta.ControllerMeta;
import io.github.itliwei.generator.generator.util.ConfigChecker;
import io.github.itliwei.generator.generator.util.PackageUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class ControllerHandler extends ScopedHandler<ControllerMeta> {

	private String controllerPackage ;
	private String controllerPath ;

	@Override
	protected void init() throws Exception {
		super.init();
		ConfigChecker.notBlank(config.getControllerSuffix(), "config controllerSuffix is miss");
		/* 初始化文件夹 */
		controllerPackage = config.getControllerPackage();
		String dir = PackageUtil.getDir(config.getEntityPackage());
		controllerPath = dir.substring(0,dir.indexOf("target/classes"))
				+"src"+File.separator+"main"+File.separator+"java"
				+File.separator+controllerPackage.replace(".",File.separator);

		File path = new File(controllerPath);
		if (!path.exists()) {
			path.mkdirs();
		} else if (!path.isDirectory()) {
			throw new IllegalArgumentException("queryModelPath is not a directory");
		}

	}

	private String getControllerFilePath(Class<?> entityClass) {
		return controllerPath + File.separator + entityClass.getSimpleName() + config.getControllerSuffix() + ".java";
	}

	@Override
	protected void preRead(Class<?> entityClass) throws Exception {
	}

	@Override
	protected ControllerMeta read(Class<?> entityClass) throws Exception {
		return null;
	}

	@Override
	protected ControllerMeta parse(Class<?> entityClass) throws Exception {
		ControllerClass controllerClass = entityClass.getAnnotation(ControllerClass.class);
		if (controllerClass == null) {
			return null;
		} else {
			String path = controllerClass.path();
			String desc = controllerClass.desc();

			ControllerMeta meta = new ControllerMeta();
			meta.setPath(path);
			meta.setApiValue(desc);
			meta.setApiDesc(desc);
			meta.setType(entityClass.getSimpleName());
			String typeName = null;
			if(Character.isLowerCase(entityClass.getSimpleName().charAt(0)))
				typeName = entityClass.getSimpleName();
			else{
				typeName = (new StringBuilder()).append(Character.toLowerCase(entityClass.getSimpleName().charAt(0))).append(entityClass.getSimpleName().substring(1)).toString();
			}
			meta.setTypeName(typeName);
			meta.setControllerTypePackage(config.getEntityPackage()+".*");
			meta.setEntityClass(entityClass.getName());
			meta.setQueryModelClass(config.getQueryModelPackage()+"."+entityClass.getSimpleName()+config.getQueryModelSuffix());
			meta.setServiceClass(config.getServicePackage()+"."+entityClass.getSimpleName()+config.getServiceSuffix());
			meta.setName(entityClass.getSimpleName() + config.getControllerSuffix());
			meta.setControllerPackage(controllerPackage);
			return meta;
		}
	}

	@Override
	protected ControllerMeta merge(ControllerMeta parsed, ControllerMeta read, Class<?> entityClass) throws Exception {
		if (parsed != null) {
			parsed.setImportFullTypes(parsed.getImportFullTypes().stream().filter(v -> !v.startsWith("java.lang.")).collect(Collectors.toSet()));
		}
		return parsed;
	}

	@Override
	protected void write(ControllerMeta merged, Class<?> entityClass) throws Exception {
		if (merged != null) {
			Map<String, Object> model = new HashMap<>();
			model.put("meta", merged);
			String mapper = renderTemplate("controller", model);
			File file = new File(getControllerFilePath(entityClass));

			if (file.exists()) {
				return;
			}
			file.createNewFile();
			try (FileOutputStream os = new FileOutputStream(file)) {
				os.write(mapper.getBytes());
			}
		}
	}

	@Override
	protected void postWrite(Class<?> entityClass) throws Exception {

	}

}
