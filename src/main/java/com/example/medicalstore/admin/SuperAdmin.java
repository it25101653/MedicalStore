package com.example.medicalstore.admin;

public class SuperAdmin extends AdminUser {

    // Constructor
    public SuperAdmin(String adminId, String name, String email, String password) {
        super(adminId, name, email, password, "SUPER");
    }

    // Override permission method
    @Override
    public boolean canDeleteUsers() {
        return true;
    }

    // Super admin can manage admins
    public boolean canManageAdmins() {
        return true;
    }

    // Super admin can manage reports
    public boolean canManageReports() {
        return true;
    }
}
