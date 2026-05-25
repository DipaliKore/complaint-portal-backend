package com.complaint.complaint_portal.repository;

import com.complaint.complaint_portal.entity.Complaint;
import com.complaint.complaint_portal.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ComplaintRepository extends JpaRepository<Complaint, Long> {
    List<Complaint> findByUser(User user);
    List<Complaint> findByStatus(Complaint.Status status);
}
