package org.example.userstoragespi.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
@Entity
@Table(name = "of1branch")
public class Branch implements Serializable {
    @Id
    @Column(name="O1001ID")
    private Long id;

    @Column(name="o1001cod")
    private String code;

    @Column(name="o1001nam")
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
