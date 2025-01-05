package com.madlen.madlen.service;

import com.madlen.madlen.dao.QuestionDao;
import com.madlen.madlen.dto.CreateQuestionRequestDto;
import com.madlen.madlen.dto.FilterDto;
import com.madlen.madlen.model.Question;
import com.madlen.madlen.repository.QuestionRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final MetadataService metadataService;
    private final QuestionDao questionDao;

    public QuestionService(QuestionRepository questionRepository, MetadataService metadataService, QuestionDao questionDao) {
        this.questionRepository = questionRepository;
        this.metadataService = metadataService;
        this.questionDao = questionDao;
    }

    public Page<Question> getAll(List<FilterDto> filterDto, Integer pageNum){
        Integer size = 50;
        return this.questionDao.getPageByCriteria(pageNum, size, filterDto);
    }

    public Question getById(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("Id cannot be null");
        }

        return this.questionRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Question not found for id: " + id));
    }
    @Transactional
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

        this.metadataService.updateMetadata(dto.getContextPages(), false);
    }
    @Transactional
    public void deleteById(Integer id){
        Question question = this.getById(id);

        this.questionRepository.deleteById(id);
        //TODO SEARCH QUESTION WITH TOPIC
        List<Integer> pages = new ArrayList<>();

        for(Integer pageNumber : question.getContextPages()){
            if(this.questionRepository.countQuestionsByPageNumber(pageNumber) == 0){
                pages.add(pageNumber);
            }
        }

        this.metadataService.updateMetadata(pages, true);
    }
}
