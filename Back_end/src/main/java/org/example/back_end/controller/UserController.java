package org.example.back_end.controller;

import org.example.back_end.dto.UserDTO;
import org.example.back_end.service.UserService;
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

    @Autowired
    private UserService userServices;

//    private final JwtUtil jwtUtil;
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

    @PutMapping("update/{u_id}")
    public List<UserDTO> updateUsers(@PathVariable int u_id, @RequestBody UserDTO userDTO){
       return userService.updateUsers(u_id, userDTO);
    }

    @DeleteMapping("delete/{u_id}")
    public boolean deleteUser(@PathVariable int u_id){
        return userService.deleteUser(u_id);
    }

    //jwt
//    public UserController(UserService userServices, JwtUtil jwtUtil) {
//        this.userServices = userServices;
//        this.jwtUtil = jwtUtil;
//    }
//    @PostMapping(value = "/register")
//    public ResponseEntity<ResponseDTO> registerUser(@RequestBody @Valid UserDTO userDTO) {
//        try {
//            int res = userService.saveUser(userDTO);
//            switch (res) {
//                case VarList.Created -> {
//                    String token = jwtUtil.generateToken(userDTO);
//                    AuthDTO authDTO = new AuthDTO();
//                    authDTO.setEmail(userDTO.getEmail());
//                    authDTO.setToken(token);
//                    return ResponseEntity.status(HttpStatus.CREATED)
//                            .body(new ResponseDTO(VarList.Created, "Success", authDTO));
//                }
//                case VarList.Not_Acceptable -> {
//                    return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
//                            .body(new ResponseDTO(VarList.Not_Acceptable, "Email Already Used", null));
//                }
//                default -> {
//                    return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
//                            .body(new ResponseDTO(VarList.Bad_Gateway, "Error", null));
//                }
//            }
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body(new ResponseDTO(VarList.Internal_Server_Error, e.getMessage(), null));
//        }
//    }
}
