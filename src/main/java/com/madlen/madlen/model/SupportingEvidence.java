package com.madlen.madlen.model;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class SupportingEvidence {
    private String point;
    private Integer pageReference;
}
