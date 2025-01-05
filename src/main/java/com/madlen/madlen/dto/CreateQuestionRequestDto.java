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
    @NotEmpty
    private List<Integer> contextPages;

    @NotNull
    @NotEmpty
    private DifficultyLevel difficultyLevel;

    @NotNull
    @NotEmpty
    private CognitiveLevel cognitiveLevel;

    @NotNull
    @NotEmpty
    private List<String> keyConcepts;

    private String courseName;

    @NotNull
    @NotEmpty
    private ModelAnswer modelAnswer;

    @NotNull
    @NotEmpty
    private List<String> gradingCriteria;
}
