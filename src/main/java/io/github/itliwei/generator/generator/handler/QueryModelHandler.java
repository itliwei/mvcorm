package io.github.itliwei.generator.generator.handler;


import io.github.itliwei.generator.annotation.query.Query;
import io.github.itliwei.generator.annotation.query.QueryModel;
import io.github.itliwei.generator.annotation.service.ServiceClass;
import io.github.itliwei.generator.generator.util.ClassHelper;
import io.github.itliwei.generator.generator.util.PackageUtil;
import io.github.itliwei.generator.generator.util.StringUtils;
import io.github.itliwei.mvcorm.orm.IdEntity;
import io.github.itliwei.generator.generator.meta.querymodel.QueryModelMeta;
import io.github.itliwei.generator.generator.util.ConfigChecker;
import io.github.itliwei.mvcorm.orm.opt.Condition;

import java.io.File;
import java.io.FileOutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static java.lang.String.format;

public class QueryModelHandler extends ScopedHandler<QueryModelMeta> {

	private String queryModelPackage ;
	private String queryModelPath ;

	@Override
	protected void init() throws Exception {
		super.init();
		ConfigChecker.notBlank(config.getQueryModelSuffix(), "config queryModelSuffix is miss");
		/* 初始化文件夹 */
		queryModelPackage = config.getQueryModelPackage();
		String dir = PackageUtil.getDir(config.getEntityPackage());
		if (dir == null){
			throw new IllegalArgumentException("entityPackage is null");
		}
		queryModelPath = dir.substring(0,dir.indexOf("target/classes"))
				+"src"+File.separator+"main"+File.separator+"java"
				+File.separator+queryModelPackage.replace(".",File.separator);

		File path = new File(queryModelPath);
		if (!path.exists()) {
			path.mkdirs();
		} else if (!path.isDirectory()) {
			throw new IllegalArgumentException("queryModelPath is not a directory");
		}

	}

	private String getQueryModelFilePath(Class<?> entityClass) {
		QueryModel queryModel = entityClass.getAnnotation(QueryModel.class);
		String name = queryModel.name();
		String fileName = name.isEmpty() ? entityClass.getSimpleName() + config.getQueryModelSuffix() : name;
		return queryModelPath + File.separator + fileName + ".java";
	}

	@Override
	protected void preRead(Class<?> entityClass) throws Exception {
	}

	@Override
	protected QueryModelMeta read(Class<?> entityClass) throws Exception {
		return null;
	}

	@Override
	protected QueryModelMeta parse(Class<?> entityClass) throws Exception {
		QueryModel queryModel = entityClass.getAnnotation(QueryModel.class);
		if (queryModel == null) {
			return null;
		} else {
			QueryModelMeta meta = new QueryModelMeta();
			String name = queryModel.name();
			if (StringUtils.isNotBlank(name)){
				meta.setType(name);
			}else {
				meta.setType(entityClass.getSimpleName() + config.getQueryModelSuffix());
			}
			meta.setQueryModelPackage(queryModelPackage);
			meta.setUseLombok(config.isUseLombok());
			meta.setIdEntity(entityClass.isAssignableFrom(IdEntity.class));
			ClassHelper.getFields(entityClass).stream().filter(ClassHelper::isNotStaticField)
					.forEach(v -> {
						meta.getFieldNames().add(v.getName());
						Query query = v.getAnnotation(Query.class);
						if (query != null) {
							for (Condition.Operator operator : query.value()) {
								QueryModelMeta.QueryModelField queryModelField = new QueryModelMeta.QueryModelField();
								if (operator == Condition.Operator.isNotNull || operator == Condition.Operator.isNull){
									queryModelField.setType("Boolean");
								}else {
									queryModelField.setType(v.getType().getSimpleName());
								}
								queryModelField.setName(v.getName() + operator.getName());
								io.github.itliwei.generator.annotation.Field field = v.getAnnotation(io.github.itliwei.generator.annotation.Field.class);
								if (field != null && !"".equals(field.label())) {
									queryModelField.setLabel(field.label());
								}
								if (operator.getName().equals(Condition.Operator.in.getName())) {
									queryModelField.setArray(true);
								}
								meta.getQueryModelFields().add(queryModelField);
								meta.getImportFullTypes().add(v.getType().getName().replaceAll("\\$", "."));
							}
						}

					});

			return meta;
		}
	}

	@Override
	protected QueryModelMeta merge(QueryModelMeta parsed, QueryModelMeta read, Class<?> entityClass) throws Exception {
		if (parsed != null) {
			parsed.setImportFullTypes(parsed.getImportFullTypes().stream().filter(v -> !v.startsWith("java.lang.")).collect(Collectors.toSet()));
		}
		return parsed;
	}

	@Override
	protected void write(QueryModelMeta merged, Class<?> entityClass) throws Exception {
		if (merged != null) {
			Map<String, Object> model = new HashMap<>();
			model.put("meta", merged);
			String mapper = renderTemplate("queryModel", model);
			File file = new File(getQueryModelFilePath(entityClass));

			if (file.exists()) {
				file.delete();
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


	private String handleFieldLabel(Field field, String label, String format) {
		if (StringUtils.isBlank(label)) {
			if (field.isAnnotationPresent(io.github.itliwei.generator.annotation.Field.class)) {
				final io.github.itliwei.generator.annotation.Field fieldAnno = field.getDeclaredAnnotation(io.github.itliwei.generator.annotation.Field.class);
				return fieldAnno.label();
			} else {
				logger.warn(format + ",默认使用 " + field.getName());
				return field.getName();
			}
		} else {
			return label;
		}
	}

}
