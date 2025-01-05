package com.madlen.madlen.service;

import com.madlen.madlen.dao.QuestionDao;
import com.madlen.madlen.dto.CreateQuestionRequestDto;
import com.madlen.madlen.dto.FilterDto;
import com.madlen.madlen.model.Question;
import com.madlen.madlen.repository.QuestionRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final MetadataService metadataService;
    private final QuestionDao questionDao;
    private static final Logger logger = LoggerFactory.getLogger(QuestionService.class);

    public QuestionService(QuestionRepository questionRepository, MetadataService metadataService, QuestionDao questionDao) {
        this.questionRepository = questionRepository;
        this.metadataService = metadataService;
        this.questionDao = questionDao;
    }

    public Page<Question> getAll(List<FilterDto> filterDto, Integer pageNum) {
        Integer size = 50;
        logger.info("Fetching questions with filters: {} on page number: {}", filterDto, pageNum);
        return this.questionDao.getPageByCriteria(pageNum, size, filterDto);
    }

    public Question getById(Integer id) {
        if (id == null) {
            logger.error("Id cannot be null.");
            throw new IllegalArgumentException("Id cannot be null");
        }

        logger.info("Fetching question with id: {}", id);
        return this.questionRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Question not found for id: {}", id);
                    return null;
                });
    }

    @Transactional
    public void create(CreateQuestionRequestDto dto) {
        logger.info("Creating a new question with text: {}", dto.getQuestionText());

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

        logger.info("Question created successfully. Updating metadata.");
        this.metadataService.updateMetadata(dto.getContextPages(), false);
    }

    @Transactional
    public void deleteById(Integer id) {
        logger.info("Attempting to delete question with id: {}", id);

        Question question = this.getById(id);
        if (question == null) {
            logger.error("Attempted to delete a question that does not exist. id: {}", id);
            throw new NoSuchElementException("Question with id " + id + " does not exist.");
        }

        this.questionRepository.deleteById(id);
        logger.info("Question with id: {} deleted successfully.", id);

        //TODO SEARCH QUESTION WITH TOPIC

        List<Integer> pages = new ArrayList<>();
        logger.info("Checking context pages for the deleted question.");

        for (Integer pageNumber : question.getContextPages()) {
            if (this.questionRepository.countQuestionsByPageNumber(pageNumber) == 0) {
                pages.add(pageNumber);
            }
        }

        logger.info("Updating metadata with pages: {}.", pages);
        this.metadataService.updateMetadata(pages, true);
    }

}
