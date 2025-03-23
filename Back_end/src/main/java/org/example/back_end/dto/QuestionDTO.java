package org.example.back_end.dto;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;
import org.example.back_end.entity.Exam;
import org.example.back_end.entity.User;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class QuestionDTO {
    private String name;
    private String content;
    private String option1;
    private String option2;
    private String option3;
    private String option4;
    private String answer;
    private int mcqNumber;
    private int examId;
    private int userId;

}
