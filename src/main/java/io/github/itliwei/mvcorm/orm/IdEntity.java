package io.github.itliwei.mvcorm.orm;

import io.github.itliwei.mvcorm.orm.annotation.Column;

import java.util.Objects;

/**
 * Entity -ID
 * Created by liwei on 2016/5/16.
 */
public abstract class IdEntity {

    /**
     * "ID" 属性名称
     */
    public static final String ID_PN = "id";

    @Column(value = "id")
    protected Long id;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IdEntity idEntity = (IdEntity) o;
        return Objects.equals(id, idEntity.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "IdEntity{" +
                "id=" + id +
                '}';
    }
}
