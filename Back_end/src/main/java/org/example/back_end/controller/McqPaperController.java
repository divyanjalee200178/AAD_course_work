package org.example.back_end.controller;

import org.example.back_end.dto.AnswerDTO;
import org.example.back_end.dto.QuestionDTO;
import org.example.back_end.entity.Question;
import org.example.back_end.repo.QuestionRepo;
import org.example.back_end.service.PaperService;
import org.example.back_end.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/v1/question")
@CrossOrigin(origins = "*")
public class McqPaperController {

    @Autowired
    private PaperService paperService;

    @Autowired
    private QuestionRepo questionRepo;


    @GetMapping("get")
    public List<QuestionDTO> getQuestion() {
        List<QuestionDTO> questionDTOS = paperService.getAllQuestions();
        return questionDTOS;
    }


    @GetMapping("/{examId}")
    public List<QuestionDTO> getQuestionsByExamId(@PathVariable Integer examId) {
        return paperService.getQuestionsByExamId(examId);
    }


    @PostMapping("/submit")
    public ResponseEntity<Integer> submitAnswers(@RequestBody Map<Integer, List<Integer>> userAnswers) {
        int score = paperService.calculateScore(userAnswers);
        return ResponseEntity.ok(score);
    }

    private int getTotalQuestions() {

        return 10;
    }

    private boolean isCorrectAnswer(int qid, List<Integer> selectedAnswers) {
        List<Integer> correctAnswers = questionRepo.findCorrectAnswersByQid(qid);


        return new HashSet<>(correctAnswers).equals(new HashSet<>(selectedAnswers));
    }


}
