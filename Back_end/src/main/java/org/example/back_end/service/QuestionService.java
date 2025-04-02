package org.example.back_end.service;

import org.example.back_end.dto.QuestionDTO;
import org.example.back_end.dto.QuestionsDTO;
import org.example.back_end.entity.Question;

import java.util.List;
import java.util.Set;

public interface QuestionService {
    Question addQuestion(Question question);
    boolean updateQuestion(QuestionsDTO questionDTO);
    void deleteQuestion(Long questionId);
    Set<Question> getQuestions();
    Question getQuestion(Long questionId);
    List<Question> getQuestionsByMcq(int mcqNumber);
    List<Question> findQuestionsByMcqNumber(int mcqNumber);

    List<Question> findByName(String name);

    public List<QuestionDTO> getAllQuestions();
//******************************************


    public List<Question> getAllQuestion();
//    public List<Question> getQuestionsByExam(Long examId);

    public List<Question> getQuestionsByExams(Long examId);
}
