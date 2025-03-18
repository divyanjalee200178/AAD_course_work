package org.example.back_end.service;

import org.example.back_end.entity.User;

public interface AnswerService {
    public int checkAnswers(int examId, User student);
}
