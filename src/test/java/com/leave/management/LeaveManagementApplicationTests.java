package com.leave.management;

import com.leave.management.dto.LeaveRequestDto;
import com.leave.management.dto.LoginRequest;
import com.leave.management.dto.LoginResponse;
import com.leave.management.model.LeaveRequest;
import com.leave.management.service.AuthService;
import com.leave.management.service.LeaveService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class LeaveManagementApplicationTests {
    
    @Autowired
    private AuthService authService;
    
    @Autowired
    private LeaveService leaveService;
    
    @Test
    void testApproveAndRejectWorkflow() {
        // 1. Login as employee
        LoginResponse empLogin = authService.login(new LoginRequest("employee@leave.com", "Employee@123"));
        assertNotNull(empLogin.getToken());
        
        // 2. Apply for leave
        LeaveRequestDto dto = new LeaveRequestDto();
        dto.setLeaveType("Annual");
        dto.setStartDate(LocalDate.now().plusDays(1));
        dto.setEndDate(LocalDate.now().plusDays(3));
        dto.setReason("Vacation");
        LeaveRequest applied = leaveService.applyLeave("employee@leave.com", dto);
        assertNotNull(applied.getId());
        assertEquals("PENDING", applied.getStatus());
        
        // 3. Manager approves
        LeaveRequest approved = leaveService.approveLeave(applied.getId(), "manager@leave.com", "Approved by manager");
        assertNotNull(approved);
        assertEquals("APPROVED", approved.getStatus());
        assertEquals("Approved by manager", approved.getManagerComments());
        assertNotNull(approved.getReviewedBy());
        assertEquals("Manager User", approved.getReviewedBy().getName());
        
        // 4. Employee sees the approved leave
        List<LeaveRequest> myLeaves = leaveService.getMyLeaves("employee@leave.com");
        assertFalse(myLeaves.isEmpty());
        LeaveRequest latest = myLeaves.get(myLeaves.size() - 1);
        assertEquals("APPROVED", latest.getStatus());
        
        // 5. Manager rejects another
        LeaveRequest applied2 = leaveService.applyLeave("employee@leave.com", dto);
        LeaveRequest rejected = leaveService.rejectLeave(applied2.getId(), "manager@leave.com", "Schedule conflict");
        assertEquals("REJECTED", rejected.getStatus());
    }
}
