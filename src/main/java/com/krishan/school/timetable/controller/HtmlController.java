package com.krishan.school.timetable.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HtmlController {

    @GetMapping("/")
    public String home() {
        return "login";
    }

    @GetMapping("/login-page")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/register-page")
    public String registerPage() {
        return "register";
    }

    @GetMapping("/dashboard-page")
    public String dashboardPage(Model model) {
        // You can add any model attributes needed for the dashboard
        return "dashboard";
    }
}