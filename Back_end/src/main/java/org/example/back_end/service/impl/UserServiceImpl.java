package org.example.back_end.service.impl;

import org.example.back_end.dto.UserDTO;
import org.example.back_end.entity.User;
import org.example.back_end.repo.UserRepo;
import org.example.back_end.service.UserService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ModelMapper modelMapper;

    public boolean addUser(UserDTO userDTO) {
        if (userRepo.existsById(userDTO.getU_id())) {
            return false;
        }

        User user = modelMapper.map(userDTO, User.class);
        userRepo.save(user);
        return true;
    }
    public boolean updatePassword(int u_id, String newPassword) {
        User user = userRepo.findById(u_id).orElse(null);

        if (user != null) {

            user.setPassword(newPassword);
            userRepo.save(user);
            return true;
        }

        return false;
    }


    public int getNextUserId() {
        List<Integer> allIds = userRepo.findAllIds();

        if (allIds.isEmpty()) {
            return 1;
        }
        for (int i = 1; i <= allIds.size(); i++) {
            if (!allIds.contains(i)) {
                return i;
            }
        }

        return allIds.size() + 1;
    }

    public List<Integer> getUserIdsByRole() {
        return userRepo.findAllIdsByRole();
    }


    public List<UserDTO> getAllUsers(){
        return modelMapper.map(userRepo.findAll(),new TypeToken<List<UserDTO>>() {}.getType());
    }

    public List<UserDTO> updateUsers(int u_id, UserDTO userDTO) {
        User user = userRepo.findById(u_id).orElse(null);
        if (user != null) {
            user.setName(userDTO.getName());
            user.setContact(userDTO.getContact());
            user.setAddress(userDTO.getAddress());
            user.setEmail(userDTO.getEmail());
            user.setRole(userDTO.getRole());
            user.setPassword(userDTO.getPassword());

            System.out.println("Updated User: " + user);

            userRepo.save(user);
        } else {
            System.out.println("User not found with ID: " + u_id);
        }

        return getAllUsers();
    }


    public boolean deleteUser(int u_id) {
        if (userRepo.existsById(u_id)){
            userRepo.deleteById(u_id);
            return true;
        }
        return false;
    }
}
