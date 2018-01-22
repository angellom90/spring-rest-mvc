package com.angello.api.v1.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDTO {

    //private Long id;
    @ApiModelProperty(value = "This is the first name", required = true)
    @JsonProperty("firstname")
    private String firstName;

    @ApiModelProperty(required = true )
    @JsonProperty("lastname")
    private String lastName;

    @JsonProperty("customer_url")
    private String customerUrl;
}
