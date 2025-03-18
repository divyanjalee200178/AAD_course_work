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
    private Long qid;

    @NotNull
    private String content;

    private int mcqNumber;

    @NotNull
    private String option1;

    @NotNull
    private String option2;

    @NotNull
    private String option3;

    @NotNull
    private String option4;

    @NotNull
    private String answer;

    private Exam exam;

    private User user;

}
