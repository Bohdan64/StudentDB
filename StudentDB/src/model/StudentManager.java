package model;

import java.util.HashMap;
import java.util.Map;
import java.util.List;

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

    
    public void showSkill(int id) {
    	Student student = studentsById.get(id);
    	if (student != null) {
			student.showSkill();
		} else {
			System.out.println("Student s id " + id + " nebyl nalezen.");
		}
    }
    
    
    
    public List<Student> getStudentsByProgramSorted(StudyProgram program) {
        return studentsById.values().stream()
            .filter(student -> student.getProgram() == program)
            .sorted(StudentComparators.BY_SURNAME_THEN_NAME)
            .toList();
    }
    
    public void printStudents(List<Student> students) {
        if (students.isEmpty()) {
            System.out.println("Žádní studenti v této skupině.");
            return;
        }

        System.out.printf("%-5s %-15s %-15s %-15s %-10s%n", "ID", "Jméno", "Příjmení", "Rok narození", "Průměr");
        System.out.println("---------------------------------------------------------------------");

        for (Student s : students) {
            System.out.printf("%-5d %-15s %-15s %-15d %-10.2f%n",
                    s.getId(), s.getJmeno(), s.getPrijmeni(), s.getYear(), s.getAverageGrade());
        }
    }

    public void printAllStudentsGroupedAndSorted() {
        for (StudyProgram program : StudyProgram.values()) {
            System.out.println("=== " + program.name() + " ===");
            List<Student> sorted = getStudentsByProgramSorted(program);
            printStudents(sorted);
            System.out.println();
        }
    }

    
    public double getAverageGradeProgram(StudyProgram program) {
    	return studentsById.values().stream()
    			.filter(student -> student.getProgram() == program)
    			.mapToDouble(Student::getAverageGrade)
    			.average()
    			.orElse(0.0);
    }
    
    public long getCountByProgram(StudyProgram program) {
        return studentsById.values().stream()
                .filter(student -> student.getProgram() == program)
                .count();
    }


     
}
