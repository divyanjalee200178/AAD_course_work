package org.example.back_end.service.impl;

import jakarta.transaction.Transactional;
import org.example.back_end.entity.Exam;
import org.example.back_end.entity.Question;
import org.example.back_end.entity.User;
import org.example.back_end.repo.ExamRepo;
import org.example.back_end.repo.QuestionRepo;
import org.example.back_end.repo.UserRepo;
import org.example.back_end.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class QuestionServiceImpl implements QuestionService {

    @Autowired
    private QuestionRepo questionRepo;

    @Autowired
    private ExamRepo examRepo;

    @Autowired
    private UserRepo userRepo;

    @Override
    @Transactional
    public Question addQuestion(Question question) {
        // Fetch the Exam and User objects from the database
        Exam exam = examRepo.findById(question.getExam().getId())
                .orElseThrow(() -> new IllegalArgumentException("Exam with ID " + question.getExam().getId() + " not found"));
        User user = userRepo.findById(question.getUser().getU_id())
                .orElseThrow(() -> new IllegalArgumentException("User with ID " + question.getUser().getU_id() + " not found"));

        // Set the Exam and User to the Question entity
        question.setExam(exam);
        question.setUser(user);

        // Ensure no more than 20 questions exist for the given MCQ Number
        List<Question> existingQuestions = questionRepo.findByMcqNumber(question.getMcqNumber());
        if (existingQuestions.size() >= 20) {
            throw new IllegalArgumentException("Cannot add more than 20 questions for this MCQ Number.");
        }

        // Save the question and return
        return questionRepo.save(question);
    }



    @Override
    @Transactional
    public Question updateQuestion(Question question) {
        if (question.getExam() == null || question.getUser() == null) {
            throw new IllegalArgumentException("Exam and User must be provided");
        }
        return this.questionRepo.save(question);
    }

    @Override
    public void deleteQuestion(Long questionId) {
        this.questionRepo.deleteById(questionId);
    }

    @Override
    public Set<Question> getQuestions() {
        return new HashSet<>(this.questionRepo.findAll());
    }

    @Override
    public Question getQuestion(Long questionId) {
        return this.questionRepo.findById(questionId).orElse(null);
    }

    @Override
    public List<Question> getQuestionsByMcq(int mcqNumber) {
        return questionRepo.findByMcqNumber(mcqNumber);
    }

    @Override
    public List<Question> findQuestionsByMcqNumber(int mcqNumber) {
        return questionRepo.findByMcqNumber(mcqNumber);  // Call to the repository method
    }
}
