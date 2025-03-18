package org.example.back_end.controller;

import org.example.back_end.entity.Question;
import org.example.back_end.service.QuestionService;
import org.example.back_end.service.impl.QuestionServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/question")
@CrossOrigin(origins = "*")
public class QuestionController {

    @Autowired
    private QuestionServiceImpl questionService;

    @PostMapping("/post")
    public ResponseEntity<Question> add(@RequestBody Question question) {
        System.out.println("Received Question: " + question); // Debugging
        return ResponseEntity.ok(this.questionService.addQuestion(question));
    }


    @PutMapping("/put")
    public ResponseEntity<Question> update(@RequestBody Question question) {
        return ResponseEntity.ok(this.questionService.updateQuestion(question));
    }

    @DeleteMapping("/{questionId}")
    public ResponseEntity<Void> delete(@PathVariable("questionId") Long questionId) {
        this.questionService.deleteQuestion(questionId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/exam/{examId}")
    public ResponseEntity<List<Question>> getQuestionsByExam(@PathVariable Long examId) {
        return ResponseEntity.ok(this.questionService.getQuestionsByExam(examId));
    }
    @GetMapping("/mcq/{mcqNumber}")
    public ResponseEntity<List<Question>> getQuestionsByMcq(@PathVariable int mcqNumber) {
        return ResponseEntity.ok(this.questionService.getQuestionsByMcq(mcqNumber));
    }
}
