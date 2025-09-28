package com.krishan.school.timetable.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HtmlController {

	 @GetMapping("/")
	    public String home() {
	        return "login";  // looks for src/main/resources/templates/login.html
	    }
    // Login page
    @GetMapping("/login-page")
    public String loginPage() {
        return "login"; // src/main/resources/templates/login.html
    }

    // Registration page
    @GetMapping("/register-page")
    public String registerPage() {
        return "register"; // src/main/resources/templates/register.html
    }

    // Dashboard page (after login)
    @GetMapping("/dashboard-page")
    public String dashboardPage() {
        return "dashboard"; // src/main/resources/templates/dashboard.html
    }
}
