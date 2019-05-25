package io.github.itliwei.mvcorm.generator.generator.handler;


import io.github.itliwei.mvcorm.generator.generator.Config;

@FunctionalInterface
public interface Handler {

	void start(Config config) throws Exception;


}
