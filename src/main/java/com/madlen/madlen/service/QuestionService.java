package com.madlen.madlen.service;

import com.madlen.madlen.dto.CreateQuestionRequestDto;
import com.madlen.madlen.model.Question;
import com.madlen.madlen.repository.QuestionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final MetadataService metadataService;

    public QuestionService(QuestionRepository questionRepository, MetadataService metadataService) {
        this.questionRepository = questionRepository;
        this.metadataService = metadataService;
    }

    public List<Question> getAll(){
        return this.questionRepository.findAll();
    }

    public Question getById(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("Id cannot be null");
        }

        return this.questionRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Question not found for id: " + id));
    }

    public void create(CreateQuestionRequestDto dto){
        this.questionRepository.save(new Question(
                null,
                dto.getQuestionText(),
                dto.getContextPages(),
                dto.getDifficultyLevel(),
                dto.getCognitiveLevel(),
                dto.getKeyConcepts(),
                dto.getCourseName(),
                dto.getModelAnswer(),
                dto.getGradingCriteria()
        ));

        metadataService.updateMetadata(dto.getContextPages());
    }

    public void deleteById(Integer id){
        this.questionRepository.deleteById(id);
    }
}
