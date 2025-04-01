package org.example.back_end.controller;

import org.example.back_end.dto.SubjectDTO;
import org.example.back_end.service.impl.SubjectServiceImpl;
import org.example.back_end.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("api/v1/subject")
public class SubjectController {
    @Autowired
    private SubjectServiceImpl subjectService;

    @PostMapping("save")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseUtil saveSubject(@RequestBody SubjectDTO subjectDTO) {
        System.out.println("Received Subject: " + subjectDTO);
        try {
            boolean res = subjectService.addSubject(subjectDTO);
            if (res) {
                return new ResponseUtil(201, "Subject saved successfully", null);
            } else {
                return new ResponseUtil(200, "Subject already exists", null);
            }
        } catch (Exception e) {
            return new ResponseUtil(500, "Error saving subject: " + e.getMessage(), null);
        }
    }


    @GetMapping("next-id")
    public int getNextSubjectId() {
        return subjectService.getNextSubjectId();
    }


    @GetMapping("get")
    public List<SubjectDTO> getSubject() {
        List<SubjectDTO> subjectDTOS = subjectService.getAllSubjects();
        return subjectDTOS;
    }


    @PutMapping("update")
    public ResponseUtil updateSubject(@RequestBody SubjectDTO subjectDTO) {
        System.out.println(subjectDTO);  // Log the incoming request body
        boolean isUpdated = subjectService.updateSubject(subjectDTO);
        if (isUpdated) {
                return new ResponseUtil(200, "Subject updated successfully!", null);
            }
                return new ResponseUtil(500, "Error updating subject!", null);
//        try {
//            SubjectDTO existingSubject = subjectService.getSubjectById(id);
//
//            if (existingSubject == null) {
//                return new ResponseUtil(404, "Subject not found!", null);
//            }
//
//            // Update the subject properties
//            existingSubject.setName(subjectDTO.getName());
//            existingSubject.setSt_count(subjectDTO.getSt_count());
//            existingSubject.setDate(subjectDTO.getDate());
//            existingSubject.setTime(subjectDTO.getTime());
//            existingSubject.setUserId(subjectDTO.getUserId());  // Assuming userId is provided
//
//            // Save the updated subject
//            boolean isUpdated = subjectService.updateSubject(id, existingSubject);
//
//            if (isUpdated) {
//                return new ResponseUtil(200, "Subject updated successfully!", existingSubject);
//            }
//
//            return new ResponseUtil(500, "Error updating subject!", null);
//
//        } catch (Exception e) {
//            return new ResponseUtil(500, "Error updating subject: " + e.getMessage(), null);
//        }
    }

    @DeleteMapping("delete/{id}")
    public boolean deleteSubject(@PathVariable int id) {
        return subjectService.deleteSubject(id);
    }
}
