package com.propulse.backendfacturier.requestEntity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Mail {

    @JsonProperty
    private String mail;

}
