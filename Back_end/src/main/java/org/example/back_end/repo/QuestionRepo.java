package org.example.back_end.repo;

import org.example.back_end.dto.QuestionDTO;
import org.example.back_end.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionRepo extends JpaRepository<Question, Long> {
    List<Question> findByMcqNumber(int mcqNumber);

    List<Question> findByNameContainingIgnoreCase(String keyword);

    List<Question> findByExamId(Long examId);
    List<QuestionDTO> findByExamId(Integer examId);



}
