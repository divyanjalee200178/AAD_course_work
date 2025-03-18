package org.example.back_end.service;
import org.example.back_end.entity.Exam;
import org.example.back_end.entity.Question;
import java.util.List;

public interface ExamService {
        List<Exam> getAllExams();
        public boolean createExam(Exam exam);
        public Exam getExamById(Integer id);
        public boolean updateExam(Integer id, Exam exam);
        public boolean deleteExam(Integer id);


}
