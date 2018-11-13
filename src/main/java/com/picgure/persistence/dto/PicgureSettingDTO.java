package com.picgure.persistence.dto;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name="picguresetting")
@NamedQueries({
        @NamedQuery(name = "picguresetting.findByName",
                    query = "select p from PicgureSettingDTO p where p.name = :name"),
        @NamedQuery(name = "picguresetting.findAll",
                    query = "select p from PicgureSettingDTO p")
})
public class PicgureSettingDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private String value;

    public PicgureSettingDTO() {}

    public PicgureSettingDTO(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PicgureSettingDTO)) return false;
        PicgureSettingDTO that = (PicgureSettingDTO) o;
        return Objects.equals(name, that.name) &&
                Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, value);
    }

    @Override
    public String toString() {
        return "PicgureSettingDTO{" +
                "name='" + name + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
