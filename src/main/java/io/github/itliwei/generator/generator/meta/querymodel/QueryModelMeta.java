package io.github.itliwei.generator.generator.meta.querymodel;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
public class QueryModelMeta {

	private boolean useLombok;

	private String queryModelPackage;

	private String type;

	private boolean idEntity;

	private Set<String> fieldNames = new HashSet<>();

	private List<QueryModelField> queryModelFields = new ArrayList<>();

	/**
	 * import全名
	 */
	private Set<String> importFullTypes = new HashSet<>();


	public static class QueryModelField {

		private String name;

		private String type;

		private String label;

		private boolean array;

		public boolean isArray() {
			return array;
		}

		public void setArray(boolean array) {
			this.array = array;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}

		public String getLabel() {
			return label;
		}

		public void setLabel(String label) {
			this.label = label;
		}
	}

}
