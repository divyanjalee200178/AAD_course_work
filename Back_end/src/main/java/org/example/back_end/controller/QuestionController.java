package org.example.back_end.controller;

import org.example.back_end.entity.Question;
import org.example.back_end.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/questions")
public class QuestionController {

    @Autowired
    private QuestionService questionService;


    @PostMapping("post")
    public Question addQuestion(@RequestBody Question question) {
        return questionService.addQuestion(question);
    }


    @GetMapping("/mcq/{mcqNumber}")
    public List<Question> getQuestionsByMcq(@PathVariable int mcqNumber) {
        return questionService.findQuestionsByMcqNumber(mcqNumber);
    }


    @GetMapping("/{id}")
    public Question getQuestion(@PathVariable Long id) {
        return questionService.getQuestion(id);
    }


    @PutMapping("/{id}")
    public Question updateQuestion(@PathVariable Long id, @RequestBody Question question) {
        question.setQid(id);
        return questionService.updateQuestion(question);
    }

    @DeleteMapping("/{id}")
    public void deleteQuestion(@PathVariable Long id) {
        questionService.deleteQuestion(id);
    }
}
