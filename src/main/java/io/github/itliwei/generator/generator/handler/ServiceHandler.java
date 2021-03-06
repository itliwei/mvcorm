package io.github.itliwei.generator.generator.handler;



import io.github.itliwei.generator.annotation.service.ServiceClass;
import io.github.itliwei.generator.generator.util.PackageUtil;
import io.github.itliwei.generator.generator.meta.ServiceMeta;
import io.github.itliwei.generator.generator.util.ConfigChecker;

import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class ServiceHandler extends ScopedHandler<ServiceMeta> {

	private String servicePackage ;
	private String servicePath ;

	@Override
	protected void init() throws Exception {
		super.init();
		ConfigChecker.notBlank(config.getServiceSuffix(), "config serviceSuffix is miss");
		/* 初始化文件夹 */
		servicePackage = config.getServicePackage();
		String dir = PackageUtil.getDir(config.getEntityPackage());
		servicePath = dir.substring(0,dir.indexOf("target/classes"))
				+"src"+File.separator+"main"+File.separator+"java"
				+File.separator+servicePackage.replace(".",File.separator);

		File path = new File(servicePath);
		if (!path.exists()) {
			path.mkdirs();
		} else if (!path.isDirectory()) {
			throw new IllegalArgumentException("queryModelPath is not a directory");
		}
	}

	private String getServiceFilePath(Class<?> entityClass) {
		ServiceClass serviceClass = entityClass.getAnnotation(ServiceClass.class);
		String name = serviceClass.name();
		String fileName = name.isEmpty() ? entityClass.getSimpleName() + config.getServiceSuffix() : name;
		return servicePath + File.separator + fileName + ".java";
	}

	@Override
	protected void preRead(Class<?> entityClass) throws Exception {
	}

	@Override
	protected ServiceMeta read(Class<?> entityClass) throws Exception {
		return null;
	}

	@Override
	protected ServiceMeta parse(Class<?> entityClass) throws Exception {
		ServiceClass serviceClass = entityClass.getAnnotation(ServiceClass.class);
		if (serviceClass == null) {
			return null;
		} else {
			String name = serviceClass.name();
			ServiceMeta meta = new ServiceMeta();
			meta.setType(entityClass.getSimpleName());
			meta.setServiceTypePackage(config.getEntityPackage()+".*");
			meta.setName(name.isEmpty()?entityClass.getSimpleName()+ config.getServiceSuffix():name);
			meta.setServicePackage(servicePackage);
			return meta;
		}
	}

	@Override
	protected ServiceMeta merge(ServiceMeta parsed, ServiceMeta read, Class<?> entityClass) throws Exception {
		if (parsed != null) {
			parsed.setImportFullTypes(parsed.getImportFullTypes().stream().filter(v -> !v.startsWith("java.lang.")).collect(Collectors.toSet()));
		}
		return parsed;
	}

	@Override
	protected void write(ServiceMeta merged, Class<?> entityClass) throws Exception {
		if (merged != null) {
			Map<String, Object> model = new HashMap<>();
			model.put("meta", merged);
			String mapper = renderTemplate("service", model);
			File file = new File(getServiceFilePath(entityClass));

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
