package com.madlen.madlen.dto;

import com.madlen.madlen.enums.CognitiveLevel;
import com.madlen.madlen.enums.DifficultyLevel;
import com.madlen.madlen.model.ModelAnswer;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class CreateQuestionRequestDto {
    @NotNull
    @NotEmpty
    private String questionText;

    @NotNull
    private List<Integer> contextPages;

    @NotNull
    private DifficultyLevel difficultyLevel;

    @NotNull
    private CognitiveLevel cognitiveLevel;

    @NotNull
    private List<String> keyConcepts;

    private String courseName;

    @NotNull
    private ModelAnswer modelAnswer;

    @NotNull
    private List<String> gradingCriteria;
}
