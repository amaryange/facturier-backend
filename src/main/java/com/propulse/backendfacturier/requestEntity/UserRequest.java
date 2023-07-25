package com.propulse.backendfacturier.requestEntity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.web.bind.annotation.RequestBody;
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {
    @JsonProperty
    private String lastname;
    @JsonProperty
    private String firstname;
    @JsonProperty
    private String index;
    @JsonProperty
    private String phone;
    @JsonProperty
    private String email;

}
