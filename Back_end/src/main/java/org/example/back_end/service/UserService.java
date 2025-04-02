package org.example.back_end.service;

import org.example.back_end.dto.UserDTO;

import java.util.List;

public interface UserService {
    public boolean addUser(UserDTO userDTO);
    public boolean deleteUser(int u_id);
//    public List<UserDTO> updateUsers(int u_id, UserDTO userDTO);
    public boolean updateUsers(UserDTO userDTO);
    public List<UserDTO> getAllUsers();

    //jwt
    boolean saveUser(UserDTO userDTO);
    UserDTO searchUser(String username);
}
