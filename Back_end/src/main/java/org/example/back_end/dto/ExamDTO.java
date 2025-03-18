package org.example.back_end.dto;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.back_end.entity.Question;
import org.example.back_end.entity.Result;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ExamDTO {
    private Integer id;
    private String duration;
    private String description;
    private LocalDateTime startDate;
    private LocalDateTime endDate;


    private List<Question> questions;

    private List<Result> answers;


}
