package io.github.itliwei.generator.generator.handler;



import io.github.itliwei.generator.annotation.controller.ControllerClass;
import io.github.itliwei.generator.annotation.query.QueryModel;
import io.github.itliwei.generator.annotation.service.ServiceClass;
import io.github.itliwei.generator.annotation.view.ViewObject;
import io.github.itliwei.generator.generator.meta.ControllerMeta;
import io.github.itliwei.generator.generator.util.ConfigChecker;
import io.github.itliwei.generator.generator.util.PackageUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
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
		if (dir == null){
			throw new IllegalArgumentException("entityPackage is null");
		}
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
		ControllerClass controllerClass = entityClass.getAnnotation(ControllerClass.class);
		String name = controllerClass.name();
		String fileName = name.isEmpty() ? entityClass.getSimpleName()+ config.getControllerSuffix() : name;
		return controllerPath + File.separator + fileName + ".java";
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
			String name = controllerClass.name();
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
				typeName = String.valueOf(Character.toLowerCase(entityClass.getSimpleName().charAt(0))) + entityClass.getSimpleName().substring(1);
			}
			meta.setTypeName(typeName);
			meta.setControllerTypePackage(config.getEntityPackage()+".*");
			meta.setEntityClass(entityClass.getName());

			QueryModel queryModel = entityClass.getAnnotation(QueryModel.class);
			if (queryModel != null && !"".equals(queryModel.name())){
				String queryModelName = queryModel.name();
				meta.setQueryModelClass(config.getQueryModelPackage()+"."+queryModelName);
				meta.setQueryModelName(queryModelName);
			}else{
				meta.setQueryModelClass(config.getQueryModelPackage()+"."+entityClass.getSimpleName()+config.getQueryModelSuffix());
				meta.setQueryModelName(entityClass.getSimpleName()+config.getQueryModelSuffix());
			}

			ServiceClass serviceClass = entityClass.getAnnotation(ServiceClass.class);
			if (serviceClass != null && !"".equals(serviceClass.name())){
				meta.setServiceClass(config.getServicePackage() + "." + serviceClass.name());
				meta.setServiceName(serviceClass.name());
			}else {
				meta.setServiceClass(config.getServicePackage() + "." + entityClass.getSimpleName() + config.getServiceSuffix());
				meta.setServiceName(entityClass.getSimpleName() + config.getServiceSuffix());
			}
			String s = entityClass.getSimpleName() + config.getControllerSuffix();
			meta.setName(name.isEmpty()?s:name);
			meta.setControllerPackage(controllerPackage);
			Set<String> set = new HashSet<>();
			meta.setImportFullTypes(set);
			//是否指定了VO和DTO
			ViewObject viewAnnotation = entityClass.getAnnotation(ViewObject.class);
			if (viewAnnotation != null){
				String[] groups = viewAnnotation.groups();
				for (String str:groups){
					if (meta.getVoName() == null && str.endsWith(config.getVoSuffix())){
						set.add(config.getVoPackage()+"."+str);
						meta.setVoName(str);
					}
					if (meta.getDtoName() == null && str.endsWith(config.getDtoSuffix())){
						set.add(config.getVoPackage()+"."+str);
						meta.setDtoName(str);
					}
				}
			}
			//默认使用类名+suffix
			if (meta.getVoName() == null) {
				set.add(config.getVoPackage()+"."+entityClass.getSimpleName()+config.getVoSuffix());
				meta.setVoName(entityClass.getSimpleName() + config.getVoSuffix());
			}
			if (meta.getDtoName() == null) {
				set.add(config.getVoPackage()+"."+entityClass.getSimpleName()+config.getDtoSuffix());
				meta.setDtoName(entityClass.getSimpleName() + config.getDtoSuffix());
			}

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
