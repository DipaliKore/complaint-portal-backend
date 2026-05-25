package com.complaint.complaint_portal.controller;

import com.complaint.complaint_portal.dto.ComplaintRequest;
import com.complaint.complaint_portal.dto.ComplaintResponse;
import com.complaint.complaint_portal.service.ComplaintService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

@RestController
@RequestMapping("/api/complaints")
@CrossOrigin(origins = "*")
public class ComplaintController {

    @Autowired
    private ComplaintService complaintService;

    @PostMapping("/submit")
    public ResponseEntity<ComplaintResponse> submit(
            @RequestPart("title") String title,
            @RequestPart("description") String description,
            @RequestPart("category") String category,
            @RequestPart(value = "file", required = false) MultipartFile file,
            Authentication authentication) {
        String email = authentication.getName();
        ComplaintRequest request = new ComplaintRequest();
        request.setTitle(title);
        request.setDescription(description);
        request.setCategory(category);
        ComplaintResponse response = complaintService.submitComplaint(request, email, file);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/my")
    public ResponseEntity<List<ComplaintResponse>> getMyComplaints(
            Authentication authentication) {
        String email = authentication.getName();
        List<ComplaintResponse> list = complaintService.getMyComplaints(email);
        return ResponseEntity.ok(list);
    }

    @GetMapping("/all")
    public ResponseEntity<List<ComplaintResponse>> getAllComplaints() {
        List<ComplaintResponse> list = complaintService.getAllComplaints();
        return ResponseEntity.ok(list);
    }

    @PutMapping("/status/{id}")
    public ResponseEntity<ComplaintResponse> updateStatus(
            @PathVariable Long id,
            @RequestParam String status) {
        ComplaintResponse response = complaintService.updateStatus(id, status);
        return ResponseEntity.ok(response);
    }
}