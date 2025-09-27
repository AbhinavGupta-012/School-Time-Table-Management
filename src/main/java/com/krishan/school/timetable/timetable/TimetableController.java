package com.krishan.school.timetable.timetable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/timetable")
public class TimetableController {

    @Autowired
    private TimetableService timetableService;

    // ------------------ ADMIN METHODS ------------------

    // Generate the timetable for all classes (Admin only)
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/generate")
    public List<Timetable> generateTimetable() {
        return timetableService.generateTimetable();
    }

    // ------------------ ADMIN & TEACHER METHODS ------------------

    // Get timetable for a specific class (Admin & Teacher)
    @PreAuthorize("hasAnyRole('ADMIN','TEACHER')")
    @GetMapping("/class/{classId}")
    public List<Timetable> getClassTimetable(@PathVariable Long classId) {
        return timetableService.getTimetableByClass(classId);
    }
}
