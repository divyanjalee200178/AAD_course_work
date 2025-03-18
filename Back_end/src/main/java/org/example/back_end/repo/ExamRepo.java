package org.example.back_end.repo;

import org.example.back_end.entity.Exam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExamRepo extends JpaRepository<Exam, Integer> {
}
