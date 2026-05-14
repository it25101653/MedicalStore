package com.example.medicalstore.admin;

public class AdminUser {

    // Admin attributes
    private String adminId;
    private String name;
    private String email;
    private String password;
    private String level;

    // Constructor
    public AdminUser(String adminId, String name, String email, String password, String level) {
        this.adminId = adminId;
        this.name = name;
        this.email = email;
        this.password = password;
        this.level = level;
    }

    // Get admin ID
    public String getAdminId() {
        return adminId;
    }

    // Get admin name
    public String getName() {
        return name;
    }

    // Get admin email
    public String getEmail() {
        return email;
    }

    // Get admin password
    public String getPassword() {
        return password;
    }

    // Get admin level
    public String getLevel() {
        return level;
    }

    // Update admin name
    public void setName(String name) {
        this.name = name;
    }

    // Update admin level
    public void setLevel(String level) {
        this.level = level;
    }

    // Simple email validation
    public boolean isValidEmail() {
        return email != null && email.contains("@");
    }

    // Permission method
    public boolean canDeleteUsers() {
        return true;
    }

    // Convert object data into file format
    public String toFileString() {
        return adminId + "," + name + "," + email + "," + password + "," + level;
    }

    // Convert file data into object
    public static AdminUser fromFileString(String line) {

        if (line == null || line.trim().isEmpty()) {
            return null;
        }

        String[] p = line.split(",");

        if (p[4].equals("SUPER")) {
            return new SuperAdmin(p[0], p[1], p[2], p[3]);
        }

        return new AdminUser(p[0], p[1], p[2], p[3], "ADMIN");
    }
}
