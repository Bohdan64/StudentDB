package model;

import java.util.HashMap;
import java.util.Map;


public class StudentManager{
	
	private Map<Integer, Student> studentsById = new HashMap<>();

	public Student addStudent(StudyProgram program, String jmeno, String prijmeni, int year) {
	    Student student = program.createStudent(jmeno, prijmeni, year);
	    studentsById.put(student.getId(), student);
	    return student;
	}

	public Student getStudentById(int id) {
	    return studentsById.get(id);
	}

    
    public boolean addGrade(int studentId, int grade) {
        Student student = getStudentById(studentId);
        if (student != null) {
            if (grade >= 1 && grade <= 5) {
                student.addGrade(grade);
                return true;
            } else {
                System.out.println("Chyba: Známka musí být v rozsahu 1 až 5.");
            }
        } else {
            System.out.println("Chyba: Student s ID " + studentId + " nebyl nalezen.");
        }
        return false;
    }
    
    public boolean removeStudentById(int id) {
        Student student = studentsById.remove(id);
        return student != null;
    }
    
    public void ShowSkill(int id) {
    	Student student = studentsById.get(id);
    	if (student != null) {
			student.showSkill();
		} else {
			System.out.println("Student s id " + id + " nebyl nalezen.");
		}
    }
    
    


     
}
