package org.example.back_end.controller;

import org.example.back_end.dto.ResultDTO;
import org.example.back_end.service.ResultService;
import org.example.back_end.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/results")
public class ResultController {

    @Autowired
    private ResultService resultService;


    @PostMapping("save")
    public ResponseUtil saveResult(@RequestBody ResultDTO resultDTO) {
        try {
            System.out.println("Received data: " + resultDTO);
            boolean res = resultService.addResult(resultDTO);
            if (res) {
                return new ResponseUtil(201, "Result saved successfully", null);
            } else {
                return new ResponseUtil(200, "Result already exists", null);
            }
        } catch (Exception e) {
            return new ResponseUtil(500, "Error saving Result: " + e.getMessage(), null);
        }
    }



    @GetMapping("next-id")
    public int getNextResultId() {
        return resultService.getNextResultId();
    }

    @GetMapping("get")
    public List<ResultDTO> getResult(){
        List<ResultDTO> resultDTOS=resultService.getAllResults();
        return resultDTOS;
    }

//    @PutMapping("update/{id}")
//    public List<ResultDTO> updateResult(@PathVariable int id, @RequestBody ResultDTO resultDTO){
//        return resultService.updateResult(id, resultDTO);
//    }

    @DeleteMapping("delete/{id}")
    public boolean deleteResult(@PathVariable int id){
        return resultService.deleteResult(id);
    }
}
