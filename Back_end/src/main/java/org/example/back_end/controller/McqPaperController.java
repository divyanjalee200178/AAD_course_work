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


//    @PostMapping("/submit")
//    public ResponseEntity<Integer> submitAnswers(@RequestBody Map<Integer, List<Integer>> userAnswers) {
//        int score = paperService.calculateScore(userAnswers);
//        return ResponseEntity.ok(score);
//    }

    @PostMapping("/submit")
    public ResponseEntity<Map<String, Object>> submitAnswers(@RequestBody Map<Integer, List<Integer>> userAnswers) {
        int score = paperService.calculateScore(userAnswers);  // Calculate score based on answers
        int totalQuestions = paperService.getTotalQuestions(); // Total number of questions
        double percentage = (score * 100.0) / totalQuestions;   // Calculate percentage (based on score and total questions)

        // Calculate grade based on percentage
        String grade = calculateGrade(percentage);

        // Prepare the response object
        Map<String, Object> result = new HashMap<>();
        result.put("score", score);
        result.put("totalQuestions", totalQuestions);
        result.put("percentage", percentage);  // Send the percentage along with score and totalQuestions
        result.put("grade", grade);  // Add grade to the result

        return ResponseEntity.ok(result); // Return the result as a JSON response
    }

    private String calculateGrade(double percentage) {
        if (percentage >= 75) {
            return "A";
        } else if (percentage >= 65) {
            return "B";
        } else if (percentage >= 55) {
            return "C";
        } else if (percentage >= 40) {
            return "S"; // S for Pass
        } else {
            return "F"; // Failed
        }
    }


    private int getTotalQuestions() {

        return 10;
    }

    private boolean isCorrectAnswer(int qid, List<Integer> selectedAnswers) {
        List<Integer> correctAnswers = questionRepo.findCorrectAnswersByQid(qid);


        return new HashSet<>(correctAnswers).equals(new HashSet<>(selectedAnswers));
    }


}
