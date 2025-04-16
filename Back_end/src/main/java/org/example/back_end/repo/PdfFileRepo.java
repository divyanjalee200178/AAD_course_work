package org.example.back_end.repo;

import org.example.back_end.entity.PdfFile;
import org.example.back_end.entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PdfFileRepo extends JpaRepository<PdfFile, Integer> {


}
