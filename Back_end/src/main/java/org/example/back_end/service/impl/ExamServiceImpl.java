package org.example.back_end.service.impl;

import org.example.back_end.entity.Exam;
import org.example.back_end.repo.ExamRepo;
import org.example.back_end.service.ExamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ExamServiceImpl implements ExamService {

    @Autowired
    private ExamRepo examRepository;

    @Override
    public boolean createExam(Exam exam) {
        examRepository.save(exam);
        return true;
    }

    @Override
    public List<Exam> getAllExams() {
        return examRepository.findAll();
    }

    @Override
    public Exam getExamById(Integer id) {
        Optional<Exam> exam = examRepository.findById(id);
        return exam.orElse(null); // Return null if exam not found
    }

    @Override
    public boolean updateExam(Integer id, Exam exam) {
        if (examRepository.existsById(id)) {
            exam.setId(id);
            examRepository.save(exam);
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteExam(Integer id) {
        if (examRepository.existsById(id)) {
            examRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
