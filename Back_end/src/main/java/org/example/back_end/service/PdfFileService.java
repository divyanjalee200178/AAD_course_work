package org.example.back_end.service;

import org.example.back_end.dto.SubjectDTO;
import org.example.back_end.entity.PdfFile;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PdfFileService {
    void savePdf(MultipartFile file, int userId) throws Exception;

    List<PdfFile> getAllPdfs();

//    List<PdfFile> getPdfsByUser(int userId);

    PdfFile getPdfById(int id);
    boolean updatePdf(int id, MultipartFile file);
    boolean deletePdf(int id);

}
