package com.psu.kurs.demo.entity;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.Set;

@Entity
@Table(name = "roles", schema = "cursovaya", catalog = "kurss")
public class Role extends AbstractEntity {
    private String name;
    @ManyToMany(mappedBy = "roles")
    private Set<User> users;
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
