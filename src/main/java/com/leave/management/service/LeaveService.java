package com.leave.management.service;

import com.leave.management.dto.LeaveRequestDto;
import com.leave.management.model.LeaveRequest;
import com.leave.management.model.User;
import com.leave.management.repository.LeaveRequestRepository;
import com.leave.management.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class LeaveService {
    
    private final LeaveRequestRepository leaveRequestRepository;
    private final UserRepository userRepository;
    
    public LeaveService(LeaveRequestRepository leaveRequestRepository, UserRepository userRepository) {
        this.leaveRequestRepository = leaveRequestRepository;
        this.userRepository = userRepository;
    }
    
    public LeaveRequest applyLeave(String email, LeaveRequestDto dto) {
        User user = null;
        if (email != null) {
            user = userRepository.findByEmail(email).orElse(null);
        }
        if (user == null) {
            user = userRepository.findByEmail("employee@leave.com")
                    .orElseThrow(() -> new RuntimeException("Default employee not found"));
        }
        
        LeaveRequest leaveRequest = new LeaveRequest();
        leaveRequest.setUser(user);
        leaveRequest.setLeaveType(dto.getLeaveType() != null ? dto.getLeaveType() : "Annual");
        leaveRequest.setStartDate(dto.getStartDate());
        leaveRequest.setEndDate(dto.getEndDate());
        leaveRequest.setReason(dto.getReason());
        
        long days = 1;
        if (dto.getStartDate() != null && dto.getEndDate() != null) {
            days = Math.max(1, ChronoUnit.DAYS.between(dto.getStartDate(), dto.getEndDate()) + 1);
        }
        leaveRequest.setDays((int) days);
        leaveRequest.setStatus("PENDING");
        
        return leaveRequestRepository.save(leaveRequest);
    }
    
    public List<LeaveRequest> getMyLeaves(String email) {
        User user = null;
        if (email != null) {
            user = userRepository.findByEmail(email).orElse(null);
        }
        if (user == null) {
            return leaveRequestRepository.findAll();
        }
        return leaveRequestRepository.findByUser(user);
    }
    
    public List<LeaveRequest> getPendingLeaves() {
        return leaveRequestRepository.findByStatus("PENDING");
    }
    
    public LeaveRequest approveLeave(Long id, String managerEmail, String comments) {
        LeaveRequest leave = leaveRequestRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Leave request not found with id: " + id));
        
        User manager = null;
        if (managerEmail != null) {
            manager = userRepository.findByEmail(managerEmail).orElse(null);
        }
        if (manager == null) {
            manager = userRepository.findByEmail("manager@leave.com").orElse(null);
        }
        
        leave.setStatus("APPROVED");
        leave.setReviewedBy(manager);
        leave.setManagerComments(comments != null && !comments.isBlank() ? comments : "Approved by manager");
        
        return leaveRequestRepository.save(leave);
    }
    
    public LeaveRequest rejectLeave(Long id, String managerEmail, String comments) {
        LeaveRequest leave = leaveRequestRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Leave request not found with id: " + id));
        
        User manager = null;
        if (managerEmail != null) {
            manager = userRepository.findByEmail(managerEmail).orElse(null);
        }
        if (manager == null) {
            manager = userRepository.findByEmail("manager@leave.com").orElse(null);
        }
        
        leave.setStatus("REJECTED");
        leave.setReviewedBy(manager);
        leave.setManagerComments(comments != null && !comments.isBlank() ? comments : "Rejected by manager");
        
        return leaveRequestRepository.save(leave);
    }
    
    public List<LeaveRequest> getAllLeaves() {
        return leaveRequestRepository.findAll();
    }
    
    public void clearAllLeaves() {
        leaveRequestRepository.deleteAll();
    }
}
