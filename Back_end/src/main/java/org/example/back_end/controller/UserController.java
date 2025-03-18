package org.example.back_end.controller;

import org.example.back_end.dto.UserDTO;
import org.example.back_end.service.impl.UserServiceImpl;
import org.example.back_end.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("api/v1/user")
public class UserController {
    @Autowired
    private UserServiceImpl userService;

    @PostMapping("save")
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

    @GetMapping("next-id")
    public int getNextSubjectId() {
        return userService.getNextUserId();
    }

    @GetMapping("get")
    public List<UserDTO> getUsers(){
        List<UserDTO> userDTOS=userService.getAllUsers();
        return userDTOS;
    }

    @PutMapping("update/{u_id}")
    public List<UserDTO> updateUsers(@PathVariable int u_id, @RequestBody UserDTO userDTO){
       return userService.updateUsers(u_id, userDTO);
    }

    @DeleteMapping("delete/{u_id}")
    public boolean deleteUser(@PathVariable int u_id){
        return userService.deleteUser(u_id);
    }
}
