package com.krishan.school.timetable.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class TestController {

    @GetMapping("/admin/test")
    public String adminTest() {
        return "Hello Admin!";
    }

    @GetMapping("/teacher/test")
    public String teacherTest() {
        return "Hello Teacher!";
    }
}
