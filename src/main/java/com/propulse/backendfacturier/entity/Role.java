package com.propulse.backendfacturier.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JsonProperty
    private String name;
    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "roles")
    private Collection<User> users = new ArrayList<>();

    public Role(String role){
        this.name = role;
    }

}
