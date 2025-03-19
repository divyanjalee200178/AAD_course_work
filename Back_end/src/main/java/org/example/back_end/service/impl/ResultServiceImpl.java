package org.example.back_end.service.impl;

import org.example.back_end.dto.ResultDTO;
import org.example.back_end.entity.Result;
import org.example.back_end.entity.User;
import org.example.back_end.repo.ResultRepo;
import org.example.back_end.repo.UserRepo;
import org.example.back_end.service.ResultService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ResultServiceImpl implements ResultService {

    @Autowired
    private ResultRepo resultRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public boolean addResult(ResultDTO resultDTO) {
        User student = resultDTO.getStudent();

        if (student.getU_id() == 0) {
            student = userRepo.save(student);
        } else {
            student = userRepo.findById(student.getU_id())
                    .orElseThrow(() -> new RuntimeException("Student not found"));
        }

        Result result = modelMapper.map(resultDTO, Result.class);
        result.setStudent(student);
        resultRepo.save(result);

        return true;
    }

    @Override
    public int getNextResultId() {
        List<Integer> allIds = resultRepo.findAllIds();
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

    @Override
    public List<ResultDTO> getAllResults() {
        return modelMapper.map(resultRepo.findAll(), new TypeToken<List<ResultDTO>>() {}.getType());
    }

    @Override
    public boolean updateResult(int id, ResultDTO resultDTO) {
        Result result = resultRepo.findById(id).orElse(null);
        if (result != null) {
            result.setMsg(resultDTO.getMsg());
            result.setTotalMark(resultDTO.getTotalMark());
            result.setExam(resultDTO.getExam());
            result.setStudent(resultDTO.getStudent());

            resultRepo.save(result);
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteResult(int id) {
        if (resultRepo.existsById(id)) {
            resultRepo.deleteById(id);
            return true;
        }
        return false;
    }
}
