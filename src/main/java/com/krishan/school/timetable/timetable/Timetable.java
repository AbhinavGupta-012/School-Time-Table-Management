package com.krishan.school.timetable.timetable;

import jakarta.persistence.*;
import com.krishan.school.timetable.entity.*;
import com.fasterxml.jackson.annotation.*;

@Entity
@Table(name = "timetables")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Timetable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Which class this timetable belongs to
    @ManyToOne
    @JoinColumn(name = "class_id", nullable = false)
    private SchoolClass schoolClass;

    // Which subject is assigned
    @ManyToOne
    @JoinColumn(name = "subject_id", nullable = false)
    private Subject subject;

    // Which teacher teaches this slot
    @ManyToOne
    @JoinColumn(name = "teacher_id", nullable = false)
    private Teacher teacher;

    @Column(nullable = false)
    private String day; // Example: "Monday"

    @Column(nullable = false)
    private String timeSlot; // Example: "09:00 - 10:00"

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public SchoolClass getSchoolClass() {
		return schoolClass;
	}

	public void setSchoolClass(SchoolClass schoolClass) {
		this.schoolClass = schoolClass;
	}

	public Subject getSubject() {
		return subject;
	}

	public void setSubject(Subject subject) {
		this.subject = subject;
	}

	public Teacher getTeacher() {
		return teacher;
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public String getTimeSlot() {
		return timeSlot;
	}

	public void setTimeSlot(String timeSlot) {
		this.timeSlot = timeSlot;
	}

	public Timetable(Long id, SchoolClass schoolClass, Subject subject, Teacher teacher, String day, String timeSlot) {
		super();
		this.id = id;
		this.schoolClass = schoolClass;
		this.subject = subject;
		this.teacher = teacher;
		this.day = day;
		this.timeSlot = timeSlot;
	}

	public Timetable() {
		super();
	}
}
