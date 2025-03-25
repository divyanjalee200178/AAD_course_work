package org.example.back_end.controller;

import org.example.back_end.dto.SubjectDTO;
import org.example.back_end.service.impl.SubjectServiceImpl;
import org.example.back_end.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("api/v1/subject")
public class SubjectController {
    @Autowired
    private SubjectServiceImpl subjectService;

    @PostMapping("save")
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

    @PutMapping("update/{id}")
    public List<SubjectDTO> updateSubject(@PathVariable int id, @RequestBody SubjectDTO subjectDTO) {
        return subjectService.updateSubject(id, subjectDTO);
    }

    @DeleteMapping("delete/{id}")
    public boolean deleteSubject(@PathVariable int id) {
        return subjectService.deleteSubject(id);
    }
}
