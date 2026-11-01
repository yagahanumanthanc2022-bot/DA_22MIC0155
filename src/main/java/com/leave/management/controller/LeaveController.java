package com.leave.management.controller;

import com.leave.management.dto.LeaveRequestDto;
import com.leave.management.model.LeaveRequest;
import com.leave.management.service.LeaveService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/leave")
@CrossOrigin(origins = "*")
public class LeaveController {
    
    private final LeaveService leaveService;
    
    public LeaveController(LeaveService leaveService) {
        this.leaveService = leaveService;
    }
    
    // Apply for leave
    @PostMapping("/apply")
    public ResponseEntity<LeaveRequest> applyLeave(@RequestBody LeaveRequestDto dto, Authentication auth) {
        String email = (auth != null && auth.getName() != null) ? auth.getName() : "employee@leave.com";
        LeaveRequest leave = leaveService.applyLeave(email, dto);
        return ResponseEntity.ok(leave);
    }
    
    // Employee views their own leave requests
    @GetMapping("/my-leaves")
    public ResponseEntity<List<LeaveRequest>> getMyLeaves(Authentication auth) {
        String email = (auth != null && auth.getName() != null) ? auth.getName() : "employee@leave.com";
        List<LeaveRequest> leaves = leaveService.getMyLeaves(email);
        return ResponseEntity.ok(leaves);
    }
    
    // Manager views pending employee leave requests to review
    @GetMapping("/pending")
    public ResponseEntity<List<LeaveRequest>> getPendingLeaves() {
        List<LeaveRequest> leaves = leaveService.getPendingLeaves();
        return ResponseEntity.ok(leaves);
    }
    
    // Manager approves a leave (supports POST and GET)
    @RequestMapping(value = "/approve/{id}", method = {RequestMethod.POST, RequestMethod.GET})
    public ResponseEntity<LeaveRequest> approveLeave(@PathVariable Long id, 
                                                      @RequestBody(required = false) Map<String, String> request, 
                                                      Authentication auth) {
        String email = (auth != null && auth.getName() != null) ? auth.getName() : "manager@leave.com";
        String comments = (request != null && request.containsKey("comments")) ? request.get("comments") : "Approved by manager";
        LeaveRequest leave = leaveService.approveLeave(id, email, comments);
        return ResponseEntity.ok(leave);
    }
    
    // Manager rejects a leave (supports POST and GET)
    @RequestMapping(value = "/reject/{id}", method = {RequestMethod.POST, RequestMethod.GET})
    public ResponseEntity<LeaveRequest> rejectLeave(@PathVariable Long id, 
                                                      @RequestBody(required = false) Map<String, String> request, 
                                                      Authentication auth) {
        String email = (auth != null && auth.getName() != null) ? auth.getName() : "manager@leave.com";
        String comments = (request != null && request.containsKey("comments")) ? request.get("comments") : "Rejected by manager";
        LeaveRequest leave = leaveService.rejectLeave(id, email, comments);
        return ResponseEntity.ok(leave);
    }
    
    // Admin / Manager views all leaves
    @GetMapping("/all")
    public ResponseEntity<List<LeaveRequest>> getAllLeaves() {
        List<LeaveRequest> leaves = leaveService.getAllLeaves();
        return ResponseEntity.ok(leaves);
    }
    
    // Clear all leave records (supports DELETE, POST, and GET)
    @RequestMapping(value = "/clear-all", method = {RequestMethod.DELETE, RequestMethod.POST, RequestMethod.GET})
    public ResponseEntity<String> clearAllLeaves() {
        leaveService.clearAllLeaves();
        return ResponseEntity.ok("All leave records have been erased.");
    }
}
