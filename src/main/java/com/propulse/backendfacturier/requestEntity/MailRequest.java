package com.propulse.backendfacturier.requestEntity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MailRequest {

    @JsonProperty
    private String lastname;
    @JsonProperty
    private String firstname;
    @JsonProperty
    private String object;
    @JsonProperty
    private String idProblem;
    @JsonProperty
    private String idOperator;
    @JsonProperty
    private String message;

}
