package org.example.back_end.service;

import org.example.back_end.dto.ResultDTO;
import java.util.List;

public interface ResultService {
    boolean addResult(ResultDTO resultDTO);
    int getNextResultId();
    List<ResultDTO> getAllResults();
    boolean updateResult(int id, ResultDTO resultDTO);
    boolean deleteResult(int id);
}
