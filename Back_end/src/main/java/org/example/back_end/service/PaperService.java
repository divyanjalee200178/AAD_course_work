package org.example.back_end.service;

import org.example.back_end.dto.QuestionDTO;
import org.example.back_end.entity.Question;
import org.example.back_end.entity.User;

import java.util.List;
import java.util.Map;

public interface PaperService {

    public List<QuestionDTO> getQuestionsByExamId(Integer examId);

    public List<QuestionDTO> getAllQuestions();

    public List<Question> getAllQuestion();
    public List<Map<String, Object>> getQuestion();
    public int calculateScore(Map<Integer, List<Integer>> userAnswers);
    public int getTotalQuestions();
}
