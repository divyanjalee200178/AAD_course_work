package org.example.back_end.service.impl;

import org.example.back_end.dto.SubjectDTO;
import org.example.back_end.entity.Subject;
import org.example.back_end.entity.User;
import org.example.back_end.repo.SubjectRepo;
import org.example.back_end.repo.UserRepo;
import org.example.back_end.service.SubjectService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SubjectServiceImpl implements SubjectService {
    @Autowired
    private SubjectRepo subjectRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserRepo userRepo;

    public boolean addSubject(SubjectDTO subjectDTO) {
        if (subjectDTO.getUserId() == null || subjectDTO.getUserId() == 0) {
            throw new RuntimeException("User cannot be null or zero!");
        }

        int userId = subjectDTO.getUserId();


        User user = userRepo.findById(userId).orElseThrow(() ->
                new RuntimeException("User with ID " + userId + " does not exist!")
        );

        Subject subject = modelMapper.map(subjectDTO, Subject.class);
        subject.setUser(user);

        subjectRepo.save(subject);
        return true;
    }


    public int getNextSubjectId() {
        List<Integer> allIds = subjectRepo.findAllIds();

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



//    public int getNextSubjectId() {
//        Integer maxId = subjectRepo.findMaxId();
//        return maxId != null ? maxId + 1 : 1;
//    }

    public List<SubjectDTO> getAllSubjects() {
        List<Subject> subjects = subjectRepo.findAll();
        return subjects.stream().map(subject -> {
            SubjectDTO dto = modelMapper.map(subject, SubjectDTO.class);
            dto.setUserId(subject.getUser() != null ? subject.getUser().getU_id() : null);
            return dto;
        }).collect(Collectors.toList());
    }



    public List<SubjectDTO> updateSubject(int id, SubjectDTO subjectDTO) {
        Subject subject = subjectRepo.findById(id).get();
        subject.setName(subjectDTO.getName());
        subject.setSt_count(subjectDTO.getSt_count());
        subject.setDate(subjectDTO.getDate());
        subject.setTime(subjectDTO.getTime());
        subjectRepo.save(subject);
        return getAllSubjects();
    }

    public boolean deleteSubject(int id) {
        if (subjectRepo.existsById(id)) {
            subjectRepo.deleteById(id);
            return true;
        }
        return false;
    }
}
