package org.example.back_end.service;

import org.example.back_end.entity.Question;
import java.util.List;
import java.util.Set;

public interface QuestionService {
    Question addQuestion(Question question);
    Question updateQuestion(Question question);
    void deleteQuestion(Long questionId);
    Set<Question> getQuestions();
    Question getQuestion(Long questionId);

    List<Question> getQuestionsByExam(Long examId);

}
