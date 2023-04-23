package com.propulse.backendfacturier.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.propulse.backendfacturier.entity.City;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.ManyToOne;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    @JsonProperty
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
    private String phone;
    @JsonProperty
    private String email;
    @JsonProperty
    private String password;
    @JsonProperty
    private Long city;
    @JsonProperty
    private Long country;

}
