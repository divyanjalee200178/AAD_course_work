package org.example.back_end.repo;

import org.example.back_end.entity.Result;
import org.example.back_end.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResultRepo extends JpaRepository<Result, Integer> {
    @Query("SELECT r.id FROM Result r")
    List<Integer> findAllIds();

}
