# Employee Leave Management System

**Student:** Yagahanumanthan.C  
**Regno:** 22MIC0155  
**Course:** CSI3025 - Application Development and Deployment Architecture

## Project Overview
Simple Employee Leave Management System with JWT authentication, role-based access control, and H2 in-memory database.

## Technology Stack
- Java 17
- Spring Boot 3.2.5
- Spring Security with JWT
- Spring Data JPA
- H2 Database (In-Memory)
- Maven
- JUnit 5

## Features
✅ User Authentication (Login/Register)  
✅ JWT Token-based Security  
✅ Role-Based Access Control (Employee, Manager, Admin)  
✅ Leave Application by Employees  
✅ Leave Approval/Rejection by Managers  
✅ Leave History and Reports  
✅ H2 In-Memory Database  
✅ RESTful API Design

## How to Build in NetBeans

### Step 1: Open Project
1. Open NetBeans IDE
2. File → Open Project
3. Navigate to: `C:\Users\Yaga Hanumanthan\OneDrive\Desktop\EmployeeLeaveManagement`
4. Click "Open Project"

### Step 2: Clean and Build
1. Right-click on project name in Projects panel
2. Click **"Clean and Build"**
3. Wait for **BUILD SUCCESS** message

### Step 3: Run the Application
1. Right-click on project
2. Click **"Run"**
3. Application will start on: http://localhost:8081

## Default Users

| Role | Email | Password |
|------|-------|----------|
| Admin | admin@leave.com | Admin@123 |
| Manager | manager@leave.com | Manager@123 |
| Employee | employee@leave.com | Employee@123 |

## API Endpoints

### Authentication
- POST `/api/auth/login` - User login
- POST `/api/auth/register` - Register new user

### Leave Management
- POST `/api/leave/apply` - Apply for leave (Employee)
- GET `/api/leave/my-leaves` - Get my leave requests
- GET `/api/leave/pending` - Get pending leaves (Manager/Admin)
- POST `/api/leave/approve/{id}` - Approve leave (Manager/Admin)
- POST `/api/leave/reject/{id}` - Reject leave (Manager/Admin)
- GET `/api/leave/all` - Get all leaves (Admin only)

## Database
- **Type:** H2 In-Memory Database
- **Console:** http://localhost:8081/h2-console
- **JDBC URL:** `jdbc:h2:mem:leavedb`
- **Username:** `sa`
- **Password:** (empty)

## Project Structure
```
src/main/java/com/leave/management/
├── LeaveManagementApplication.java  # Main application
├── config/                           # Configuration classes
│   ├── SecurityConfig.java
│   └── DataSeeder.java
├── controller/                       # REST Controllers
│   ├── AuthController.java
│   └── LeaveController.java
├── dto/                              # Data Transfer Objects
│   ├── LoginRequest.java
│   ├── LoginResponse.java
│   └── LeaveRequestDto.java
├── model/                            # Entity classes
│   ├── User.java
│   └── LeaveRequest.java
├── repository/                       # JPA Repositories
│   ├── UserRepository.java
│   └── LeaveRequestRepository.java
├── security/                         # Security components
│   ├── JwtUtil.java
│   └── JwtAuthFilter.java
└── service/                          # Business logic
    ├── AuthService.java
    └── LeaveService.java
```

## Testing the API

### 1. Login
```bash
POST http://localhost:8081/api/auth/login
Content-Type: application/json

{
  "email": "employee@leave.com",
  "password": "Employee@123"
}
```

### 2. Apply Leave
```bash
POST http://localhost:8081/api/leave/apply
Authorization: Bearer <your-jwt-token>
Content-Type: application/json

{
  "leaveType": "Annual",
  "startDate": "2024-12-01",
  "endDate": "2024-12-05",
  "reason": "Family vacation"
}
```

## Troubleshooting

### Build Failure
1. Make sure Maven is configured in NetBeans
2. Check Java version: `java -version` (should be Java 17 or higher)
3. Clean project: Right-click → Clean
4. Rebuild: Right-click → Build

### Port Already in Use
If port 8081 is busy, change it in `application.properties`:
```properties
server.port=8081
```

### Database Issues
H2 database is in-memory, so all data is lost when application stops. This is perfect for testing!

## Author
**Yagahanumanthan.C**  
Registration Number: 22MIC0155  
CSI3025 - Application Development and Deployment Architecture
