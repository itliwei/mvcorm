package io.github.itliwei.mvcorm.generator.generator.handler;



import io.github.itliwei.mvcorm.generator.annotation.elementui.ElementClass;
import io.github.itliwei.mvcorm.generator.annotation.query.Query;
import io.github.itliwei.mvcorm.generator.annotation.view.View;
import io.github.itliwei.mvcorm.generator.generator.meta.ElementMeta;
import io.github.itliwei.mvcorm.generator.generator.util.ConfigChecker;
import org.apache.commons.collections.CollectionUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class ElementHandler extends ScopedHandler<ElementMeta> {

	private String elementPackage ;
	private String elementPath ;

	@Override
	protected void init() throws Exception {
		super.init();
		ConfigChecker.notBlank(config.getElementSuffix(), "config controllerSuffix is miss");
		/* 初始化文件夹 */
		elementPackage = config.getElementPackage();
		elementPath = config.getElementPath();

		File path = new File(elementPath);
		if (!path.exists()) {
			path.mkdirs();
		} else if (!path.isDirectory()) {
			throw new IllegalArgumentException("queryModelPath is not a directory");
		}

	}

	private String getElementFilePath(Class<?> entityClass) {
		String s = elementPath + File.separator + entityClass.getSimpleName() + File.separator;
		File path = new File(s);
		if (!path.exists()){
			path.mkdirs();
		}
		return elementPath + File.separator + entityClass.getSimpleName()+ File.separator
				+ config.getElementSuffix() + ".vue";
	}

	private String getElementJsFilePath(Class<?> entityClass) {
		return elementPath + File.separator + entityClass.getSimpleName() + ".js";
	}

	@Override
	protected void preRead(Class<?> entityClass) throws Exception {
	}

	@Override
	protected ElementMeta read(Class<?> entityClass) throws Exception {
		return null;
	}

	@Override
	protected ElementMeta parse(Class<?> entityClass) throws Exception {
		ElementClass elementClass = entityClass.getAnnotation(ElementClass.class);
		if (elementClass == null) {
			return null;
		} else {
			String path = elementClass.path();

			ElementMeta meta = new ElementMeta();
			meta.setPath(path);
			meta.setName(entityClass.getSimpleName());
			
			String typeName = null;
			if(Character.isLowerCase(entityClass.getSimpleName().charAt(0)))
				typeName = entityClass.getSimpleName();
			else{
				typeName = (new StringBuilder()).append(Character.toLowerCase(entityClass.getSimpleName().charAt(0))).append(entityClass.getSimpleName().substring(1)).toString();
			}
			
			meta.setName(typeName);
			meta.setDtoName(typeName+"Dto");
			meta.setVoName(typeName+"Vo");
			meta.setQueryName(typeName+"QueryModel");

			Field[] declaredFields = entityClass.getDeclaredFields();
			for (Field declaredField : declaredFields) {
				View[] views = declaredField.getAnnotationsByType(View.class);
				String label = null;
				io.github.itliwei.mvcorm.generator.annotation.Field[] annotationsByType = declaredField.getAnnotationsByType(io.github.itliwei.mvcorm.generator.annotation.Field.class);
				for (io.github.itliwei.mvcorm.generator.annotation.Field field : annotationsByType) {
					label = field.label();
				}
				ElementMeta.Field field = new ElementMeta.Field();
				field.setName(declaredField.getName());
				field.setType(declaredField.getType().getSimpleName());
				field.setLabel(label);
				if (views.length > 0){
					for (View view : views) {
						io.github.itliwei.mvcorm.generator.annotation.Field field1 = view.field();

						String[] groups = view.groups();
						for (String group : groups) {
							if (group.indexOf("Vo")>0){
								meta.addVoFiled(field);
							}
							if (group.indexOf("Dto")>0){
								meta.addDtoFiled(field);
							}
						}
					}

				}
				Query[] queries = declaredField.getAnnotationsByType(Query.class);
				if (queries.length > 0){
					meta.addQueryField(field);
				}
			}
			return meta;
		}
	}

	@Override
	protected ElementMeta merge(ElementMeta parsed, ElementMeta read, Class<?> entityClass) throws Exception {
		
		return parsed;
	}

	@Override
	protected void write(ElementMeta merged, Class<?> entityClass) throws Exception {
		if (merged != null) {
			Map<String, Object> model = new HashMap<>();
			model.put("meta", merged);
			String mapper = renderTemplate("elementIndex", model);
			File file = new File(getElementFilePath(entityClass));

			if (file.exists()) {
				return;
			}
			file.createNewFile();
			try (FileOutputStream os = new FileOutputStream(file)) {
				os.write(mapper.getBytes());
			}

			String jsmapper = renderTemplate("elementJs", model);
			File jsfile = new File(getElementJsFilePath(entityClass));

			if (jsfile.exists()) {
				return;
			}
			jsfile.createNewFile();
			try (FileOutputStream os = new FileOutputStream(jsfile)) {
				os.write(jsmapper.getBytes());
			}
		}
	}

	@Override
	protected void postWrite(Class<?> entityClass) throws Exception {

	}

}
