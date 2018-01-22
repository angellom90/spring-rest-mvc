package com.angello.api.v1.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VendorDTO {

    private Long id;
    @ApiModelProperty(value = "Vendor Name", required = true)
    private String name;
    @ApiModelProperty(value = "Vendor URL", required = true)
    @JsonProperty("vendor_url")
    private String vendorUrl;
}
