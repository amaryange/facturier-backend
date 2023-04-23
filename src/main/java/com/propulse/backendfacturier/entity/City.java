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
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JsonProperty
    private String name;
    @OneToMany(mappedBy = "city")
    @JsonManagedReference
    private Collection<User> users;

    public City(Long city) {
        this.id = city;
    }

    public City(String name) {
        this.name = name;
    }
}
