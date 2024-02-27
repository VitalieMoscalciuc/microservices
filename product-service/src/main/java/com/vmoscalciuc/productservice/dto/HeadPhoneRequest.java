package com.vmoscalciuc.productservice.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HeadPhoneRequest extends ProductRequest{
    @NotNull
    private String connectionType;
    @NotNull
    private Boolean noiseCancellation;
    @NotNull
    private String frequencyInterval;

}
