package com.krishan.school.timetable.controller;

import com.krishan.school.timetable.entity.SchoolClass;
import com.krishan.school.timetable.entity.Subject;
import com.krishan.school.timetable.entity.Teacher;
import com.krishan.school.timetable.repository.SchoolClassRepository;
import com.krishan.school.timetable.repository.SubjectRepository;
import com.krishan.school.timetable.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/subjects")
public class SubjectController {

    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private SchoolClassRepository schoolClassRepository;

    @Autowired
    private TeacherRepository teacherRepository;

    // ------------------ ADMIN METHODS ------------------

    // Create a new subject and assign it to a teacher and class
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/teacher/{teacherId}/class/{classId}")
    public Subject addSubject(@PathVariable Long teacherId,
                              @PathVariable Long classId,
                              @RequestBody Subject subject) {
        Teacher teacher = teacherRepository.findById(teacherId)
                .orElseThrow(() -> new RuntimeException("Teacher not found"));
        SchoolClass schoolClass = schoolClassRepository.findById(classId)
                .orElseThrow(() -> new RuntimeException("Class not found"));

        subject.setTeacher(teacher);
        subject.setSchoolClass(schoolClass);

        return subjectRepository.save(subject);
    }

    // Update a subject (Admin only)
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{subjectId}/teacher/{teacherId}/class/{classId}")
    public Subject updateSubject(@PathVariable Long subjectId,
                                 @PathVariable Long teacherId,
                                 @PathVariable Long classId,
                                 @RequestBody Subject subjectDetails) {
        Subject subject = subjectRepository.findById(subjectId)
                .orElseThrow(() -> new RuntimeException("Subject not found"));

        Teacher teacher = teacherRepository.findById(teacherId)
                .orElseThrow(() -> new RuntimeException("Teacher not found"));

        SchoolClass schoolClass = schoolClassRepository.findById(classId)
                .orElseThrow(() -> new RuntimeException("Class not found"));

        subject.setName(subjectDetails.getName());
        subject.setTeacher(teacher);
        subject.setSchoolClass(schoolClass);

        return subjectRepository.save(subject);
    }

    // Delete a subject (Admin only)
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{subjectId}")
    public String deleteSubject(@PathVariable Long subjectId) {
        Subject subject = subjectRepository.findById(subjectId)
                .orElseThrow(() -> new RuntimeException("Subject not found"));

        subjectRepository.delete(subject);
        return "Subject with ID " + subjectId + " deleted successfully.";
    }

    // ------------------ ADMIN & TEACHER METHODS ------------------

    // Get all subjects (Admin & Teacher)
    @PreAuthorize("hasAnyRole('ADMIN','TEACHER')")
    @GetMapping
    public List<Subject> getAllSubjects() {
        return subjectRepository.findAll();
    }

    // Get all subjects of a specific teacher (Admin & Teacher)
    @PreAuthorize("hasAnyRole('ADMIN','TEACHER')")
    @GetMapping("/teacher/{teacherId}")
    public List<Subject> getSubjectsByTeacher(@PathVariable Long teacherId) {
        return subjectRepository.findByTeacherId(teacherId);
    }
}
