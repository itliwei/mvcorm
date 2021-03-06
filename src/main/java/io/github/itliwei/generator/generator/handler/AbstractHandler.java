package io.github.itliwei.generator.generator.handler;

import freemarker.core.ParseException;
import freemarker.template.*;
import io.github.itliwei.generator.generator.Config;
import io.github.itliwei.generator.generator.util.ClassHelper;
import io.github.itliwei.generator.generator.GenLog;
import io.github.itliwei.generator.generator.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.Entity;
import java.io.IOException;
import java.io.StringWriter;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public abstract class AbstractHandler implements Handler {

	protected Config config;
	protected Configuration freemarkerConfiguration;
	protected GenLog genLog;
	Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public void start(Config config) throws Exception {
		Objects.requireNonNull(config, " config cat not be null");
		this.config = config;
		try {
			Set<Class<?>> classes = new LinkedHashSet<>();
			if (StringUtils.isNotBlank(config.getEntityName())){
				if (!config.getEntityName().startsWith(config.getEntityPackage())){
					throw new RuntimeException("file not in package:"+config.getEntityPackage());
				}
				Class<?> aClass = Class.forName(config.getEntityName());
				classes.add(aClass);
			}else {
				classes = ClassHelper.getClasses(config.getEntityPackage());
			}
			init();
			doHandle(classes);
		} catch (Exception e) {
			logger.error("generate error", e);
		} finally {
			try {
				destroy();
			} catch (Exception e) {
				logger.error("destroy error", e);
			}
		}
	}

	protected void init() throws Exception {
		freemarkerConfiguration = new Configuration(new Version(config.getFreemakerVersion()));
		freemarkerConfiguration.setDefaultEncoding(config.getDefaultEncoding());
		freemarkerConfiguration.setClassForTemplateLoading(getClass(), config.getTemplate());
		genLog = new GenLog(config.getGenLogFile());
	}

	protected void destroy() throws Exception {
		genLog.flush();
	}

	protected void doHandle(Set<Class<?>> entityClasses) {
		/* 获取所有 entity */
		/* 遍历生成 */
		entityClasses.stream().forEach(entityClass -> {
			if (entityClass.getDeclaredAnnotation(Entity.class) != null) {
				try {
					/* 顺次生成每一个 */
					doHandleOne(entityClass);
					logger.info("generate " + entityClass.getName() + " success");
				} catch (Exception e) {
					logger.error("generate " + entityClass.getName() + " error", e);
				}
			} else {
				logger.info("generate " + entityClass.getName() + " skipped");
			}
		});

	}

	protected void doHandleOne(Class<?> entityClass) throws Exception {

	}

	protected String renderTemplate(String tmplName, Map<String, Object> model) throws TemplateNotFoundException, MalformedTemplateNameException, ParseException, IOException, TemplateException {
		StringWriter out = new StringWriter();
		Template template = freemarkerConfiguration.getTemplate(tmplName + ".ftl");
		template.process(model, out);
		return out.toString();
	}

}
