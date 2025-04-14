package model;

import java.util.List;
import java.util.ArrayList;


public class StudentManager{
	
    private List<Student> students = new ArrayList<>();
    
    public Student addStudent(StudyProgram program, String jmeno, String prijmeni, int year) {
        Student student = program.createStudent(jmeno, prijmeni, year);
        students.add(student);
        return student;
    }
    
}
