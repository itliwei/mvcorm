package io.github.itliwei.generator.generator.util;

public class ConfigChecker {

	public static void notBlank(String string, String message) {
		if (string == null || "".equals(string)) {
			throw new IllegalArgumentException(message);
		}
	}


}
