package org.example.back_end.service.impl;

import jakarta.transaction.Transactional;
import org.example.back_end.entity.Exam;
import org.example.back_end.entity.Question;
import org.example.back_end.repo.ExamRepo;
import org.example.back_end.repo.QuestionRepo;
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

    @Override
    @Transactional
    public Question addQuestion(Question question) {
        List<Question> existingQuestions = questionRepo.findByMcqNumber(question.getMcqNumber());
        if (existingQuestions.size() >= 20) {
            throw new IllegalArgumentException("Cannot add more than 20 questions for this MCQ Number.");
        }
        return questionRepo.save(question);
    }


    public List<Question> getQuestionsByMcq(int mcqNumber) {
        return questionRepo.findByMcqNumber(mcqNumber);
    }



    @Override
    public Question updateQuestion(Question question) {
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
    public List<Question> getQuestionsByExam(Long examId) {
        return questionRepo.findByExamId(examId);
    }


}
