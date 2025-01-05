package com.madlen.madlen.model;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embeddable;
import lombok.Data;
import java.util.List;

@Data
@Embeddable
public class ModelAnswer {
    private String mainArgument;
    private List<String> keyPoints;
    @ElementCollection
    private List<SupportingEvidence> supportingEvidence;
    private String conclusion;
}
