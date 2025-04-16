package org.example.back_end.dto;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
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
    @Pattern(regexp = "^[0-9]{10}$",message = "Phone number must be digits")
    private String contact;
    private String address;
    @Email(message = "Invalid Email address")
    private String email;
    private String role;
    private String password;


}
