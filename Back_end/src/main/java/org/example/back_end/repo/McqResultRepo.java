package org.example.back_end.repo;

import org.example.back_end.entity.Result;
import org.springframework.data.jpa.repository.JpaRepository;

public interface McqResultRepo extends JpaRepository<Result, Integer> {
}
