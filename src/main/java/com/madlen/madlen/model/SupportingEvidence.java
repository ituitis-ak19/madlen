package com.madlen.madlen.model;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Embeddable
public class SupportingEvidence {
    @NotNull
    private String point;
    @NotNull
    private Integer pageReference;
}
