package com.propulse.backendfacturier.entity;

import com.fasterxml.jackson.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Operator {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JsonProperty
    private String name;
    @JsonProperty
    private String label;
    @JsonProperty
    private String picture;
    @JsonProperty
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date dateAdded;
    @OneToMany(mappedBy = "operator")
    @JsonManagedReference
    private Collection<Fee> fee;

    public Operator(String name, String label){
        this.name = name;
        this.label = label;
    }

    public Operator(Long id){
        this.id = id;
    }

    @PrePersist
    public void prePersist() {
        dateAdded = new Date();
    }

}
