package org.example.back_end.controller;

import jakarta.validation.Valid;
import org.example.back_end.dto.AuthDTO;
import org.example.back_end.dto.ResponseDTO;
import org.example.back_end.dto.SubjectDTO;
import org.example.back_end.dto.UserDTO;
import org.example.back_end.service.UserService;
import org.example.back_end.service.impl.UserServiceImpl;
import org.example.back_end.util.JwtUtil;
import org.example.back_end.util.ResponseUtil;
import org.example.back_end.util.VarList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
@RestController
@RequestMapping("api/v1/user")
public class UserController {
    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private UserService userServices;

    private final JwtUtil jwtUtil;
    @PostMapping("save")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseUtil saveUser(@RequestBody UserDTO userDTO) {
        try {
            boolean res = userService.addUser(userDTO);
            if (res) {
                return new ResponseUtil(201, "User saved successfully", null);
            } else {
                return new ResponseUtil(200, "User already exists", null);
            }
        } catch (Exception e) {
            return new ResponseUtil(500, "Error saving user: " + e.getMessage(), null);
        }
    }

    @PostMapping("reset")
    public ResponseUtil resetPassword(@RequestBody UserDTO userDTO) {
        try {
            boolean res = userService.updatePassword(userDTO.getU_id(), userDTO.getPassword());
            if (res) {
                return new ResponseUtil(200, "Password updated successfully", null);
            } else {
                return new ResponseUtil(404, "User not found", null);
            }
        } catch (Exception e) {
            return new ResponseUtil(500, "Error updating password: " + e.getMessage(), null);
        }
    }

    @GetMapping("get-ids-by-role")
    public List<Integer> getUserIdsByRole() {
        return userService.getUserIdsByRole();
    }


    @GetMapping("next-id")
    public int getNextSubjectId() {
        return userService.getNextUserId();
    }

    @GetMapping("get")
    public List<UserDTO> getUsers(){
        List<UserDTO> userDTOS=userService.getAllUsers();
        return userDTOS;
    }


    @PutMapping("update")
    public ResponseUtil updateUser(@RequestBody UserDTO userDTO){
        System.out.println(userDTO);  // Log the incoming request body
        boolean isUpdated = userService.updateUsers(userDTO);
        if (isUpdated) {
            return new ResponseUtil(200, "User updated successfully!", null);
        }
        return new ResponseUtil(500, "Error updating User!", null);
    }

    @DeleteMapping("delete/{u_id}")
    public boolean deleteUser(@PathVariable int u_id){
        return userService.deleteUser(u_id);
    }

    //jwt
    public UserController(UserService userServices, JwtUtil jwtUtil) {
        this.userServices = userServices;
        this.jwtUtil = jwtUtil;
    }
    @PostMapping("/register")
    public ResponseEntity<ResponseDTO> registerUser(@RequestBody UserDTO userDTO) {
        boolean saved = userService.saveUser(userDTO);
        if (saved) {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ResponseDTO(VarList.Created, "Registration successful!", null));
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new ResponseDTO(VarList.Conflict, "User ID already exists!", null));
        }
    }


}
