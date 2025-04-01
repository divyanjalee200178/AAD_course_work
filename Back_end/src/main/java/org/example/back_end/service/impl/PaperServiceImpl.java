package org.example.back_end.service.impl;

import jakarta.transaction.Transactional;
import org.example.back_end.dto.QuestionDTO;
import org.example.back_end.entity.Question;
import org.example.back_end.repo.ExamRepo;
import org.example.back_end.repo.QuestionRepo;
import org.example.back_end.repo.UserRepo;
import org.example.back_end.service.PaperService;
import org.example.back_end.service.QuestionService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class PaperServiceImpl implements PaperService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private QuestionRepo questionRepository;

    @Override
    public List<QuestionDTO> getAllQuestions() {
        List<Question> questions = questionRepository.findAll();
        return questions.stream().map(question -> {
            QuestionDTO dto = modelMapper.map(question, QuestionDTO.class);

            // Set userId in the DTO. Handle case where user might be null
            dto.setUserId(question.getUser() != null ? question.getUser().getU_id() : 0);  // Assuming userId is set as 0 when user is null

            // Optionally, set other fields if needed. For example, if examId is missing, you can set it here:
            dto.setExamId(question.getExam() != null ? question.getExam().getId() : 0);  // Assuming examId is set as 0 when exam is null

            return dto;
        }).collect(Collectors.toList());
    }

    @Override
    public List<Question> getAllQuestion() {
        return null;
    }

    @Override
    public List<Map<String, Object>> getQuestion() {
        List<Question> questions = questionRepository.findAll();

        return questions.stream().map(question -> {
            Map<String, Object> questionMap = new HashMap<>();
            questionMap.put("qid", question.getQid());
            questionMap.put("name", question.getName());
            questionMap.put("content", question.getContent());
            questionMap.put("option1", question.getOption1());
            questionMap.put("option2", question.getOption2());
            questionMap.put("option3", question.getOption3());
            questionMap.put("option4", question.getOption4());
            questionMap.put("answer", question.getAnswer());
            questionMap.put("mcqNumber", question.getMcqNumber());

            // Manually add userId and examId
            questionMap.put("userId", question.getUser() != null ? question.getUser().getU_id() : 0);
            questionMap.put("examId", question.getExam() != null ? question.getExam().getId() : 0);

            return questionMap;
        }).collect(Collectors.toList());
    }

    public List<QuestionDTO> getQuestionsByExamId(Integer examId) {
        return questionRepository.findByExamId(examId);
    }

    @Override
    public int getTotalQuestions() {
        return (int) questionRepository.count(); // or any other logic to get the total number of questions
    }

    @Override
    public int calculateScore(Map<Integer, List<Integer>> userAnswers) {
        int score = 0;

        for (Map.Entry<Integer, List<Integer>> entry : userAnswers.entrySet()) {
            int qid = entry.getKey();
            List<Integer> selectedAnswers = entry.getValue();

            // Retrieve correct answers from the database
            List<Integer> correctAnswers = questionRepository.findCorrectAnswersByQid(qid);

            // Check if selected answers match the correct ones
            if (selectedAnswers.containsAll(correctAnswers) && correctAnswers.containsAll(selectedAnswers)) {
                score++; // Increment score for correct answers
            }
        }

        return score;
    }

    //*********************************************************
    @Override
    public int calculateScores(Map<Integer, List<Integer>> userAnswers, int examId) {
        int score = 0;

        for (Map.Entry<Integer, List<Integer>> entry : userAnswers.entrySet()) {
            int qid = entry.getKey();
            List<Integer> selectedAnswers = entry.getValue();

            // Retrieve correct answers from the database
            List<Integer> correctAnswers = questionRepository.findCorrectAnswersByQid(qid);

            // Check if selected answers match the correct ones
            if (selectedAnswers.containsAll(correctAnswers) && correctAnswers.containsAll(selectedAnswers)) {
                score++; // Increment score for correct answers
            }
        }

        return score;
    }


    public int getTotalQuestionsForExam(int examId) {
        return questionRepository.countQuestionsByExamId(examId);
    }

}
