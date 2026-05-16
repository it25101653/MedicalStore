package com.example.medicalstore.admin;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AdminController {

    @Autowired
    private AdminService adminService;

    // Admin login page
    @GetMapping("/admin/login")
    public String adminLoginPage() {
        return "admin-login";
    }

    // Login admin
    @PostMapping("/admin/login")
    public String adminLogin(@RequestParam String email,
                             @RequestParam String password,
                             HttpSession session) {

        AdminUser admin = adminService.login(email, password);

        if (admin != null) {

            session.setAttribute("adminUser", admin);

            return "redirect:/admin";
        }

        return "redirect:/admin/login";
    }

    // Load admin dashboard
    @GetMapping("/admin")
    public String dashboard(Model model,
                            HttpSession session) {

        if (session.getAttribute("adminUser") == null) {
            return "redirect:/admin/login";
        }

        model.addAttribute("admins", adminService.getAllAdmins());

        return "admin";
    }

    // Register new admin
    @PostMapping("/admin/register")
    public String registerAdmin(@RequestParam String name,
                                @RequestParam String email,
                                @RequestParam String password,
                                @RequestParam String level,
                                HttpSession session) {

        if (session.getAttribute("adminUser") == null) {
            return "redirect:/admin/login";
        }

        String id = adminService.generateId();

        AdminUser a = level.equals("SUPER")
                ? new SuperAdmin(id, name, email, password)
                : new AdminUser(id, name, email, password, "ADMIN");

        adminService.register(a);

        return "redirect:/admin";
    }

    // Update admin level
    @PostMapping("/admin/update")
    public String updateLevel(@RequestParam String adminId,
                              @RequestParam String level,
                              HttpSession session) {

        if (session.getAttribute("adminUser") == null) {
            return "redirect:/admin/login";
        }

        adminService.updateLevel(adminId, level);

        return "redirect:/admin";
    }

    // Delete admin
    @GetMapping("/admin/delete")
    public String deleteAdmin(@RequestParam String id,
                              HttpSession session) {

        if (session.getAttribute("adminUser") == null) {
            return "redirect:/admin/login";
        }

        adminService.delete(id);

        return "redirect:/admin";
    }

    // Logout admin
    @GetMapping("/admin/logout")
    public String logout(HttpSession session) {

        session.invalidate();

        return "redirect:/admin/login";
    }
}
