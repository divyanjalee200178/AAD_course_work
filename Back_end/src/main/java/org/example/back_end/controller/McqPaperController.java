package org.example.back_end.controller;

import org.example.back_end.dto.QuestionDTO;
import org.example.back_end.entity.Question;
import org.example.back_end.service.PaperService;
import org.example.back_end.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/question")
@CrossOrigin(origins = "*")
public class McqPaperController {

    @Autowired
    private PaperService paperService;


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
    public Map<String, Object> checkAnswers(@RequestBody Map<Long, String> userAnswers) {
        List<Map<String, Object>> questions = paperService.getQuestion();
        int score = 0;

        for (Map<String, Object> q : questions) {
            Long qid = (Long) q.get("qid");
            String correctAnswer = (String) q.get("answer");

            if (userAnswers.containsKey(qid) && correctAnswer.equals(userAnswers.get(qid))) {
                score++;
            }
        }

        return Map.of("score", score, "total", questions.size());
    }

}
