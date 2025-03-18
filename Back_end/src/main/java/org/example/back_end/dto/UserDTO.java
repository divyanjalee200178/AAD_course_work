package org.example.back_end.dto;

import jakarta.persistence.*;
import lombok.*;
import org.example.back_end.entity.Subject;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class UserDTO {
    private int u_id;
    private String name;
    private String contact;
    private String address;
    private String email;
    private String role;
    private String password;


}
