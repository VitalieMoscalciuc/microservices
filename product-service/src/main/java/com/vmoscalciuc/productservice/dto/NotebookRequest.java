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
public class NotebookRequest extends ProductRequest {
    @NotNull
    private String processor;
    @NotNull
    private int ramSize;
    @NotNull
    private String videoCard;
    @NotNull
    private String storageType;
    @NotNull
    private Integer storageSize;
    @NotNull
    private Double displaySize;
}

