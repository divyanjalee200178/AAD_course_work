package org.example.back_end.service;

import org.example.back_end.dto.ResultDTO;
import org.example.back_end.dto.ResultViewDTO;

import java.util.List;

public interface ResultService {
    boolean addResult(ResultDTO resultDTO);
    int getNextResultId();
    public List<ResultViewDTO> getAllResults();
    boolean updateResult(int id, ResultDTO resultDTO);
    boolean deleteResult(int id);

    public boolean saveResult(ResultDTO resultDTO);
}
