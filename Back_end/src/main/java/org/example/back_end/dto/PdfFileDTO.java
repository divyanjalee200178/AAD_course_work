package org.example.back_end.dto;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.back_end.entity.User;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PdfFileDTO {
    private int id;

    private String fileName;

    private String fileType;

    private String filePath;


    private User user;


}
