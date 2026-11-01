package com.leave.management;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import java.net.InetSocketAddress;
import java.net.ServerSocket;

@SpringBootApplication
public class LeaveManagementApplication {
    
    private static int activePort = 8081;
    
    public static void main(String[] args) {
        activePort = findAvailablePort(8081, 8082, 8083, 8085, 8888);
        System.setProperty("server.port", String.valueOf(activePort));
        
        System.out.println("==========================================================");
        System.out.println(">>> EMPLOYEE LEAVE MANAGEMENT SYSTEM");
        System.out.println(">>> ACTIVE SERVER PORT: " + activePort);
        System.out.println(">>> LAUNCHING LOGIN PAGE: http://localhost:" + activePort + "/login.html");
        System.out.println("==========================================================");
        
        SpringApplication.run(LeaveManagementApplication.class, args);
    }
    
    @EventListener(ApplicationReadyEvent.class)
    public void onApplicationReady() {
        String url = "http://localhost:" + activePort + "/login.html";
        System.out.println("\n>>> OPENING LOGIN PAGE DIRECTLY VIA NETBEANS: " + url + "\n");
        try {
            if (System.getProperty("os.name", "").toLowerCase().contains("win")) {
                new ProcessBuilder("cmd", "/c", "start", url).start();
            } else if (java.awt.Desktop.isDesktopSupported() && java.awt.Desktop.getDesktop().isSupported(java.awt.Desktop.Action.BROWSE)) {
                java.awt.Desktop.getDesktop().browse(new java.net.URI(url));
            }
        } catch (Exception e) {
            System.err.println("Could not auto-open browser: " + e.getMessage());
        }
    }
    
    private static int findAvailablePort(int... candidatePorts) {
        for (int port : candidatePorts) {
            try (ServerSocket socket = new ServerSocket()) {
                socket.setReuseAddress(false);
                socket.bind(new InetSocketAddress(port));
                return port;
            } catch (Exception ignored) {
                // Port occupied or restricted, fallback to next available candidate
            }
        }
        return 8082;
    }
}

