package com.propulse.backendfacturier.entity;

import com.fasterxml.jackson.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="users")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JsonProperty
    private String firstname;
    @JsonProperty
    private String lastname;
    @JsonProperty
    private String index;
    @JsonProperty
    private String street;
    @JsonProperty
    @Column(name = "phone", nullable = false, length = 100, unique = true)
    private String phone;
    @JsonProperty
    @Column(name = "email", nullable = false, length = 100, unique = true)
    private String email;
    @JsonProperty
    private String password;
    @ManyToOne
    @JsonBackReference
    private City city;
    @ManyToOne
    @JsonBackReference
    private Country country;
    @ManyToMany(fetch = FetchType.EAGER)
    private Collection<Role> roles = new ArrayList<>();

}
