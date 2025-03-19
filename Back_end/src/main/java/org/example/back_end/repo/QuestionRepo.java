package org.example.back_end.repo;

import org.example.back_end.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionRepo extends JpaRepository<Question, Long> {
    List<Question> findByMcqNumber(int mcqNumber);  // Method to find questions by MCQ number
}
