package com.project.w3t.model.user;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "privileges")
public class Privilege {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    @ManyToMany(mappedBy = "privileges")
    private Set<Role> roles;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
