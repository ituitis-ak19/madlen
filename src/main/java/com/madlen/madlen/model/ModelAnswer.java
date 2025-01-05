package com.madlen.madlen.model;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.util.List;

@Data
@Embeddable
public class ModelAnswer {
    @NotNull
    private String mainArgument;
    @NotNull
    private List<String> keyPoints;
    @ElementCollection
    private List<SupportingEvidence> supportingEvidence;
    @NotNull
    private String conclusion;
}
