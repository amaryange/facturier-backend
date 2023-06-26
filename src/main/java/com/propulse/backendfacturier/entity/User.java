package com.propulse.backendfacturier.entity;

import com.fasterxml.jackson.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

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
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date dateAddedUser;
    @JsonProperty
    private String password;
    @JsonProperty
    private boolean active = true;
    @ManyToOne
    @JsonBackReference
    private City city;
    @ManyToOne
    @JsonBackReference
    private Country country;
    @ManyToMany(fetch = FetchType.EAGER)
    private Collection<Role> roles = new ArrayList<>();

    @PrePersist
    public void prePersist() {
        dateAddedUser = new Date();
    }


}
