package com.krishan.school.timetable.entity;

import jakarta.persistence.*;
import java.util.List;
import com.fasterxml.jackson.annotation.*;

@Entity
@Table(name = "classes")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class SchoolClass {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;  // Example: "10-A", "12-B"
    
    @Column(nullable=false)
    private String section;

    @OneToMany(mappedBy = "schoolClass", cascade = CascadeType.ALL, orphanRemoval = true)
    @com.fasterxml.jackson.annotation.JsonManagedReference("class-subjects")
    private List<Subject> subjects;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Subject> getSubjects() {
		return subjects;
	}

	public void setSubjects(List<Subject> subjects) {
		this.subjects = subjects;
	}

	public SchoolClass(Long id, String name, List<Subject> subjects) {
		super();
		this.id = id;
		this.name = name;
		this.subjects = subjects;
	}

	public SchoolClass() {
		super();
	}

	public String getSection() {
		return section;
	}

	public void setSection(String section) {
		this.section = section;
	}

	public SchoolClass(Long id, String name, String section, List<Subject> subjects) {
		super();
		this.id = id;
		this.name = name;
		this.section = section;
		this.subjects = subjects;
	}
}
