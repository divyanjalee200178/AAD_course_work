package org.example.back_end.controller;

import org.example.back_end.dto.QuestionDTO;
import org.example.back_end.dto.SubjectDTO;
import org.example.back_end.entity.Exam;
import org.example.back_end.entity.Question;
import org.example.back_end.entity.User;
import org.example.back_end.repo.ExamRepo;
import org.example.back_end.repo.UserRepo;
import org.example.back_end.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/questions")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private ExamRepo examRepo;

    @Autowired
    private UserRepo userRepo;


    @PostMapping("/post")
    public ResponseEntity<?> addQuestion(@RequestBody QuestionDTO questionDTO) {
        try {
            // Convert DTO to Entity
            Question question = new Question();
            question.setName(questionDTO.getName());
            question.setContent(questionDTO.getContent());
            question.setOption1(questionDTO.getOption1());
            question.setOption2(questionDTO.getOption2());
            question.setOption3(questionDTO.getOption3());
            question.setOption4(questionDTO.getOption4());
            question.setAnswer(questionDTO.getAnswer());
            question.setMcqNumber(questionDTO.getMcqNumber());

            // Validate Exam & User
            Exam exam = examRepo.findById(questionDTO.getExamId())
                    .orElseThrow(() -> new IllegalArgumentException("Exam ID Not Found"));
            User user = userRepo.findById(questionDTO.getUserId())
                    .orElseThrow(() -> new IllegalArgumentException("User ID Not Found"));

            question.setExam(exam);
            question.setUser(user);

            Question savedQuestion = questionService.addQuestion(question);
            return ResponseEntity.ok(savedQuestion);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/search")
    public ResponseEntity<List<Question>> searchQuestionsByName(@RequestParam String name)  {
        List<Question> questions = questionService.findByName(name);
        return ResponseEntity.ok(questions);
    }

    @GetMapping("/mcq/{mcqNumber}")
    public List<Question> getQuestionsByMcq(@PathVariable int mcqNumber) {
        return questionService.findQuestionsByMcqNumber(mcqNumber);
    }

    @GetMapping("get")
    public List<QuestionDTO> getQuestion() {
        List<QuestionDTO> questionDTOS = questionService.getAllQuestions();
        return questionDTOS;
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
