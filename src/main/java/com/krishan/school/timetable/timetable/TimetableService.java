package com.krishan.school.timetable.timetable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.krishan.school.timetable.entity.Subject;
import com.krishan.school.timetable.entity.Teacher;
import com.krishan.school.timetable.entity.SchoolClass;
import com.krishan.school.timetable.repository.SubjectRepository;
import com.krishan.school.timetable.repository.SchoolClassRepository;
//import com.krishan.school.timetable.repository.TeacherRepository;

import java.util.*;

@Service
public class TimetableService {

    @Autowired private TimetableRepository timetableRepository;
    @Autowired private SubjectRepository subjectRepository;
//    @Autowired private TeacherRepository teacherRepository;
    @Autowired private SchoolClassRepository classRepository;

    private static final String[] DAYS = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};
    private static final String[] SLOTS = {
            "09:00 - 10:00",
            "10:00 - 11:00",
            "11:15 - 12:15",
            "12:15 - 01:15",
            "02:00 - 03:00"
    };

    public List<Timetable> generateTimetable() {
//    	Let us first delete all the previous timetables
        timetableRepository.deleteAll(); 

//        Getting all the classes of the school
        List<SchoolClass> classes = classRepository.findAll();
        List<Timetable> savedTimetables = new ArrayList<>();

        Random random = new Random();

        for (SchoolClass schoolClass : classes) {
            List<Subject> subjects = subjectRepository.findAll();
            for (String day : DAYS) {
                for (String slot : SLOTS) {
                	
                    // Pick a random subject for this class
                    Subject subject = subjects.get(random.nextInt(subjects.size()));
                    Teacher teacher = subject.getTeacher();

                    Timetable timetable = new Timetable();
                    timetable.setSchoolClass(schoolClass);
                    timetable.setSubject(subject);
                    timetable.setTeacher(teacher);
                    timetable.setDay(day);
                    timetable.setTimeSlot(slot);

                    savedTimetables.add(timetable);
                }
            }
        }

        return timetableRepository.saveAll(savedTimetables);
    }

    public List<Timetable> getTimetableByClass(Long classId) {
        List<Timetable> all = timetableRepository.findAll();
        List<Timetable> filtered = new ArrayList<>();
        for (Timetable t : all) {
            if (t.getSchoolClass().getId().equals(classId)) {
                filtered.add(t);
            }
        }
        return filtered;
    }
}
