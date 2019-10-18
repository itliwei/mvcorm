package io.github.itliwei.generator.generator.meta;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author : liwei
 * @date : 2019/05/13 09:18
 * @description : service meta
 */
@Getter
@Setter
public class ElementMeta {
    private String elementPackage;

    private String name;

    private String queryName;

    private List<Field> queryFields = new ArrayList<>();

    private String dtoName;

    private List<Field> dtoFields = new ArrayList<>();

    private String voName;

    private List<Field> voFields = new ArrayList<>();

    private String path;

    private String group;


    public void addVoFiled(Field field){
        this.voFields.add(field);
    }

    public void addDtoFiled(Field field){
        this.dtoFields.add(field);
    }

    public void addQueryField(Field field){
        this.queryFields.add(field);
    }

    public static class Field {
        protected String name;
        protected String type;
        protected String label;
        protected String queryName;

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

        public String getQueryName() {
            return queryName;
        }

        public void setQueryName(String queryName) {
            this.queryName = queryName;
        }
    }
}
