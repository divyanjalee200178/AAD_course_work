package org.example.back_end.controller;

import org.example.back_end.dto.ExamDTO;
import org.example.back_end.dto.UserDTO;
import org.example.back_end.entity.Exam;
import org.example.back_end.service.ExamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/v1/exam")
public class ExamController {

    @Autowired
    private ExamService examService;

    @PostMapping("/create")
    public ResponseEntity<String> createExam(@RequestBody Exam exam) {
        boolean success = examService.createExam(exam);
        if (success) {
            return ResponseEntity.status(HttpStatus.CREATED).body("Exam created successfully.");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error creating exam.");
    }

    @GetMapping("/get")
    public List<ExamDTO> getExams() {
        List<ExamDTO> exams = examService.getAllExams();
        System.out.println("Returning exams: " + exams);
        return exams;
    }


    @GetMapping("/getExam/{id}")
    public ResponseEntity<Exam> getExamById(@PathVariable Integer id) {
        return ResponseEntity.ok(examService.getExamById(id));
    }

    @GetMapping("/getAllExamIds")
    public ResponseEntity<List<Integer>> getAllExamIds() {
        List<Integer> examIds = examService.getAllExamIds();
        return ResponseEntity.ok(examIds);
    }


    @GetMapping("/next-id")
    public ResponseEntity<Integer> getNextExamId() {
        return ResponseEntity.ok(examService.getNextExamId());
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateExam(@PathVariable Integer id, @RequestBody Exam exam) {
        boolean success = examService.updateExam(id, exam);
        if (success) {
            return ResponseEntity.ok("Exam updated successfully.");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error updating exam.");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteExam(@PathVariable Integer id) {
        boolean success = examService.deleteExam(id);
        if (success) {
            return ResponseEntity.ok("Exam deleted successfully.");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error deleting exam.");
    }
}