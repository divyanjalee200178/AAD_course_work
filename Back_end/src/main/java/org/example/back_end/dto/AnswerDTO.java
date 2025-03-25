package org.example.back_end.dto;

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
public class AnswerDTO {
        private int qid;
        private List<Integer> answers;

        // Getters and Setters
        public int getQid() {
            return qid;
        }

        public void setQid(int qid) {
            this.qid = qid;
        }

        public List<Integer> getAnswers() {
            return answers;
        }

        public void setAnswers(List<Integer> answers) {
            this.answers = answers;
        }


}
