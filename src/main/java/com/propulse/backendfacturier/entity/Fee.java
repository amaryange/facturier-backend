package com.propulse.backendfacturier.entity;

import com.fasterxml.jackson.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Fee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JsonProperty
    private int price;
    @JsonProperty
    private String phone;
    @JsonProperty
    private String feeId;
    @JsonProperty
    @Column(unique = true)
    private String numberBill;
    @JsonProperty
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date periodFee;
    @JsonProperty
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date limitDate;
    @JsonProperty
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private Date paymentDate;
    @JsonProperty
    private boolean feeStatus = false;
    @JsonProperty
    private String debtor;
    @ManyToOne
    @JsonIgnore // Utiliser @JsonIgnore au lieu de @JsonBackReference
    private Operator operator;

    public boolean getFeeStatus(){
        return this.feeStatus;
    }

}
