package io.github.itliwei.generator.generator.handler;


import io.github.itliwei.generator.generator.Config;

@FunctionalInterface
public interface Handler {

	void start(Config config) throws Exception;


}
