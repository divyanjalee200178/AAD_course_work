package org.example.back_end.repo;

import org.example.back_end.entity.Email;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailRepo extends JpaRepository<Email, Integer> {

}
