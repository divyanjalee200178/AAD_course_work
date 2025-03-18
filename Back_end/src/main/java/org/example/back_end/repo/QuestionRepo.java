package org.example.back_end.repo;

import org.example.back_end.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface QuestionRepo extends JpaRepository<Question, Long> {
    List<Question> findByExamId(Long examId);

        List<Question> findByMcqNumber(int mcqNumber);


}
