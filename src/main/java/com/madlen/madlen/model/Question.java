package com.madlen.madlen.model;

import com.madlen.madlen.enums.CognitiveLevel;
import com.madlen.madlen.enums.DifficultyLevel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String questionText;
    private List<Integer> contextPages;
    @Enumerated(EnumType.STRING)
    private DifficultyLevel difficultyLevel;
    @Enumerated(EnumType.STRING)
    private CognitiveLevel cognitiveLevel;
    private List<String> keyConcepts;
    private String courseName;
    @Embedded
    private ModelAnswer modelAnswer;
    private List<String> gradingCriteria;
}
