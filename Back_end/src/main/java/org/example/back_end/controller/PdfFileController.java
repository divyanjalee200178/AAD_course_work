package org.example.back_end.controller;

import jakarta.annotation.Resource;
import org.example.back_end.dto.SubjectDTO;
import org.example.back_end.entity.PdfFile;
import org.example.back_end.entity.User;
import org.example.back_end.service.PdfFileService;
import org.example.back_end.service.UserService;
import org.example.back_end.service.impl.SubjectServiceImpl;
import org.example.back_end.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.InputStreamResource;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("api/v1/pdf")
public class PdfFileController {
    @Autowired
    private PdfFileService pdfFileService;

    @Autowired
    private UserService userService;

    @PostMapping("/upload")
    @PreAuthorize("hasAnyAuthority('ADMIN'|| 'Admin')")
    public ResponseEntity<String> uploadPdf(@RequestParam("file") MultipartFile file,
                                            @RequestParam("userId") int userId) {
        try {
            pdfFileService.savePdf(file, userId);
            return ResponseEntity.ok("PDF uploaded and saved to server successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("PDF upload failed: " + e.getMessage());
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<Map<String, Object>>> getAllPdfs() {
        List<PdfFile> pdfFiles = pdfFileService.getAllPdfs();
        List<Map<String, Object>> pdfList = pdfFiles.stream().map(pdf -> {
            Map<String, Object> pdfMap = new HashMap<>();
            pdfMap.put("id", pdf.getId());
            pdfMap.put("fileName", pdf.getFileName());
            pdfMap.put("filePath", pdf.getFilePath()); // You can omit this if you don't need it
            if (pdf.getUser() != null) {
                pdfMap.put("userId", pdf.getUser().getU_id());
                pdfMap.put("userName", pdf.getUser().getName()); // Optional if you want to show the name too
            } else {
                pdfMap.put("userId", null); // or 0 or "N/A"
            }
            return pdfMap;
        }).collect(Collectors.toList());

        return ResponseEntity.ok(pdfList);
    }


    @GetMapping("/view/{id}")
    public ResponseEntity<InputStreamResource> viewPdf(@PathVariable int id) throws IOException {
        PdfFile pdf = pdfFileService.getPdfById(id);
        if (pdf == null) return ResponseEntity.notFound().build();

        File file = new File(pdf.getFilePath());
        if (!file.exists()) return ResponseEntity.notFound().build();

        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
        HttpHeaders headers = new HttpHeaders();
        headers.setContentDisposition(ContentDisposition.inline().filename(pdf.getFileName()).build());
        headers.setContentType(MediaType.APPLICATION_PDF);

        return new ResponseEntity<>(resource, headers, HttpStatus.OK);
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<InputStreamResource> downloadPdf(@PathVariable int id) throws IOException {
        PdfFile pdf = pdfFileService.getPdfById(id);
        if (pdf == null) return ResponseEntity.notFound().build();

        File file = new File(pdf.getFilePath());
        if (!file.exists()) return ResponseEntity.notFound().build();

        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
        HttpHeaders headers = new HttpHeaders();
        headers.setContentDisposition(ContentDisposition.attachment().filename(pdf.getFileName()).build());
        headers.setContentType(MediaType.APPLICATION_PDF);

        return new ResponseEntity<>(resource, headers, HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'Admin')")
    public ResponseEntity<String> updatePdf(@PathVariable int id, @RequestParam("file") MultipartFile file) {
        try {
            PdfFile existingPdf = pdfFileService.getPdfById(id);
            if (existingPdf == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("PDF not found");
            }

            // Call service method to handle the update
            boolean updated = pdfFileService.updatePdf(id, file);
            if (updated) {
                return ResponseEntity.ok("PDF updated successfully!");
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("PDF update failed");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("PDF update failed: " + e.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'Admin')")
    public ResponseEntity<String> deletePdf(@PathVariable int id) {
        try {
            PdfFile existingPdf = pdfFileService.getPdfById(id);
            if (existingPdf == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("PDF not found");
            }

            boolean deleted = pdfFileService.deletePdf(id);
            if (deleted) {
                return ResponseEntity.ok("PDF deleted successfully!");
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("PDF deletion failed");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("PDF deletion failed: " + e.getMessage());
        }
    }


}
