package com.krishan.school.timetable.controller;

import com.krishan.school.timetable.entity.SchoolClass;
import com.krishan.school.timetable.entity.Subject;
import com.krishan.school.timetable.repository.SchoolClassRepository;
import com.krishan.school.timetable.repository.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/classes")
public class SchoolClassController {

    @Autowired
    private SchoolClassRepository classRepository;

    @Autowired
    private SubjectRepository subjectRepository;

    // ------------------ ADMIN METHODS ------------------

    // Create a new class (Admin only)
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public SchoolClass createClass(@RequestBody SchoolClass schoolClass) {
        return classRepository.save(schoolClass);
    }

    // Assign a subject to a class (Admin only)
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/{classId}/subjects/{subjectId}")
    public Subject assignSubjectToClass(@PathVariable Long classId, @PathVariable Long subjectId) {
        SchoolClass schoolClass = classRepository.findById(classId)
                .orElseThrow(() -> new RuntimeException("Class not found"));
        Subject subject = subjectRepository.findById(subjectId)
                .orElseThrow(() -> new RuntimeException("Subject not found"));

        subject.setSchoolClass(schoolClass);
        return subjectRepository.save(subject);
    }

    // ------------------ ADMIN & TEACHER METHODS ------------------

    // Get all classes (Admin & Teacher)
    @PreAuthorize("hasAnyRole('ADMIN','TEACHER')")
    @GetMapping
    public List<SchoolClass> getAllClasses() {
        return classRepository.findAll();
    }

    // Get all subjects for a class (Admin & Teacher)
    @PreAuthorize("hasAnyRole('ADMIN','TEACHER')")
    @GetMapping("/{classId}/subjects")
    public List<Subject> getSubjectsForClass(@PathVariable Long classId) {
        SchoolClass schoolClass = classRepository.findById(classId)
                .orElseThrow(() -> new RuntimeException("Class not found"));
        return schoolClass.getSubjects();
    }
}
