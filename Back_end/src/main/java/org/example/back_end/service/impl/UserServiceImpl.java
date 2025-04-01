package org.example.back_end.service.impl;

import org.example.back_end.dto.UserDTO;
import org.example.back_end.entity.User;
import org.example.back_end.repo.UserRepo;
import org.example.back_end.service.UserService;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class UserServiceImpl implements UserDetailsService, UserService {
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

    public boolean updateUsers(int u_id, UserDTO userDTO) {
        User existingUser = userRepo.findById(u_id).orElse(null);

        if (existingUser == null) {
            return false; // User not found
        }

        // Update user details
        existingUser.setName(userDTO.getName());
        existingUser.setContact(userDTO.getContact());
        existingUser.setAddress(userDTO.getAddress());
        existingUser.setEmail(userDTO.getEmail());
        existingUser.setRole(userDTO.getRole());
        existingUser.setPassword(userDTO.getPassword());

        userRepo.save(existingUser);
        return true; // Update successful
    }

    public boolean deleteUser(int u_id) {
        if (userRepo.existsById(u_id)){
            userRepo.deleteById(u_id);
            return true;
        }
        return false;
    }

    //jwt

    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepo.findByEmail(email);
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), getAuthority(user));
    }

    public UserDTO loadUserDetailsByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByEmail(username);
        return modelMapper.map(user,UserDTO.class);
    }



    private Set<SimpleGrantedAuthority> getAuthority(User user) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority(user.getRole()));
        return authorities;
    }

    @Override
    public UserDTO searchUser(String username) {
        if (userRepo.existsByEmail(username)) {
            User user=userRepo.findByEmail(username);
            return modelMapper.map(user,UserDTO.class);
        } else {
            return null;
        }
    }


    public boolean saveUser(UserDTO userDTO) {
        if (userRepo.existsById(userDTO.getU_id())) {
            return false;
        }

        // Create PasswordEncoder manually (Not recommended)
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        // Encrypt the password before saving it
        userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));

        // Map the DTO to the User entity
        User user = modelMapper.map(userDTO, User.class);

        // Save the user
        userRepo.save(user);

        return true;
    }
}
