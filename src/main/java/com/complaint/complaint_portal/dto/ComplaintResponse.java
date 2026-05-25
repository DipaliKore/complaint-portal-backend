package com.complaint.complaint_portal.dto;

import com.complaint.complaint_portal.entity.Complaint;
import java.time.LocalDateTime;

public class ComplaintResponse {
    private Long id;
    private String title;
    private String description;
    private String category;
    private String status;
    private String userName;
    private String filePath;
    private LocalDateTime createdAt;

    public ComplaintResponse(Complaint c) {
        this.id = c.getId();
        this.title = c.getTitle();
        this.description = c.getDescription();
        this.category = c.getCategory();
        this.status = c.getStatus().name();
        this.userName = c.getUser().getName();
        this.filePath = c.getFilePath();
        this.createdAt = c.getCreatedAt();
    }

    public Long getId() { return id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public String getCategory() { return category; }
    public String getStatus() { return status; }
    public String getUserName() { return userName; }
    public String getFilePath() { return filePath; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}