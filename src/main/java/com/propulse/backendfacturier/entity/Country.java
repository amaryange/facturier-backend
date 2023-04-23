package com.propulse.backendfacturier.entity;

import com.fasterxml.jackson.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Collection;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Country {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JsonProperty
    private String name;
    @OneToMany(mappedBy = "country")
    @JsonManagedReference
    private Collection<User> user;

    public Country(String name) {
        this.name = name;
    }

    public Country(Long id) {
        this.id = id;
    }
}
