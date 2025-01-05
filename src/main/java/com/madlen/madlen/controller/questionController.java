package com.madlen.madlen.controller;

import com.madlen.madlen.dto.CreateQuestionRequestDto;
import com.madlen.madlen.dto.FilterDto;
import com.madlen.madlen.model.Question;
import com.madlen.madlen.service.QuestionService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/questions")
public class questionController {

    private final QuestionService questionService;

    public questionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @GetMapping
    public ResponseEntity<Page<Question>> getAll(@RequestParam Integer page, @RequestBody(required = false) List<FilterDto> filters){
        return ResponseEntity.ok(this.questionService.getAll(filters, page));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Question> getById(@PathVariable Integer id){
        return ResponseEntity.ok(this.questionService.getById(id));
    }

    @PostMapping
    public void create(@RequestBody @Valid CreateQuestionRequestDto dto){
        this.questionService.create(dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id){
        this.questionService.deleteById(id);
    }
}
