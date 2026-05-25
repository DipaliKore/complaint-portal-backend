package com.complaint.complaint_portal.service;

import com.complaint.complaint_portal.dto.ComplaintRequest;
import com.complaint.complaint_portal.dto.ComplaintResponse;
import com.complaint.complaint_portal.entity.Complaint;
import com.complaint.complaint_portal.entity.User;
import com.complaint.complaint_portal.repository.ComplaintRepository;
import com.complaint.complaint_portal.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.*;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ComplaintService {

    @Autowired
    private ComplaintRepository complaintRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailService emailService;

    @Value("${file.upload.dir}")
    private String uploadDir;

    public ComplaintResponse submitComplaint(ComplaintRequest request, String email, MultipartFile file) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found!"));

        Complaint complaint = new Complaint();
        complaint.setTitle(request.getTitle());
        complaint.setDescription(request.getDescription());
        complaint.setCategory(request.getCategory());
        complaint.setUser(user);

        if (file != null && !file.isEmpty()) {
            try {
                Path uploadPath = Paths.get(uploadDir);
                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                }
                String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
                Path filePath = uploadPath.resolve(fileName);
                Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
                complaint.setFilePath(fileName);
            } catch (IOException e) {
                throw new RuntimeException("File upload failed!");
            }
        }

        Complaint saved = complaintRepository.save(complaint);
        return new ComplaintResponse(saved);
    }

    public List<ComplaintResponse> getMyComplaints(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found!"));
        return complaintRepository.findByUser(user)
                .stream()
                .map(ComplaintResponse::new)
                .collect(Collectors.toList());
    }

    public List<ComplaintResponse> getAllComplaints() {
        return complaintRepository.findAll()
                .stream()
                .map(ComplaintResponse::new)
                .collect(Collectors.toList());
    }

    public ComplaintResponse updateStatus(Long id, String status) {
        Complaint complaint = complaintRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Complaint not found!"));

        complaint.setStatus(Complaint.Status.valueOf(status));
        Complaint updated = complaintRepository.save(complaint);

        // Email notification bhejo
        try {
            emailService.sendStatusUpdateEmail(
                updated.getUser().getEmail(),
                updated.getUser().getName(),
                updated.getTitle(),
                status
            );
        } catch (Exception e) {
            System.out.println("Email send failed: " + e.getMessage());
        }

        return new ComplaintResponse(updated);
    }
}