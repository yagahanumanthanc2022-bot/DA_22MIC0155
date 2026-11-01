package com.leave.management.config;

import com.leave.management.model.User;
import com.leave.management.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataSeeder implements CommandLineRunner {
    
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    
    public DataSeeder(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
    
    @Override
    public void run(String... args) throws Exception {
        // Create default admin if not exists
        if (!userRepository.existsByEmail("admin@leave.com")) {
            User admin = new User();
            admin.setName("Admin User");
            admin.setEmail("admin@leave.com");
            admin.setPassword(passwordEncoder.encode("Admin@123"));
            admin.setRole("ADMIN");
            admin.setDepartment("IT");
            userRepository.save(admin);
            System.out.println("✅ Default admin created: admin@leave.com / Admin@123");
        }
        
        // Create default manager if not exists
        if (!userRepository.existsByEmail("manager@leave.com")) {
            User manager = new User();
            manager.setName("Manager User");
            manager.setEmail("manager@leave.com");
            manager.setPassword(passwordEncoder.encode("Manager@123"));
            manager.setRole("MANAGER");
            manager.setDepartment("IT");
            userRepository.save(manager);
            System.out.println("✅ Default manager created: manager@leave.com / Manager@123");
        }
        
        // Create default employee if not exists
        if (!userRepository.existsByEmail("employee@leave.com")) {
            User employee = new User();
            employee.setName("Employee User");
            employee.setEmail("employee@leave.com");
            employee.setPassword(passwordEncoder.encode("Employee@123"));
            employee.setRole("EMPLOYEE");
            employee.setDepartment("IT");
            userRepository.save(employee);
            System.out.println("✅ Default employee created: employee@leave.com / Employee@123");
        }

        String activePort = System.getProperty("server.port", "8081");
        System.out.println("\n" +
            "======================================================================\n" +
            "   APPLICATION IS READY & RUNNING ACTIVE ON PORT " + activePort + "!\n" +
            "----------------------------------------------------------------------\n" +
            "   -> LOGIN URL   : http://localhost:" + activePort + "/login.html\n" +
            "   -> STATUS      : SERVING WEB REQUESTS (DO NOT CLOSE NETBEANS)\n" +
            "   -> ACCOUNTS    : \n" +
            "      - Employee  : employee@leave.com / Employee@123\n" +
            "      - Manager   : manager@leave.com  / Manager@123\n" +
            "      - Admin     : admin@leave.com    / Admin@123\n" +
            "======================================================================\n"
        );
    }
}
