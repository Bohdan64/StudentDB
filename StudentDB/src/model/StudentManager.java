package model;

import java.util.List;
import repository.StudentRepository;

public class StudentManager {
	
    private final StudentRepository repository;

    public StudentManager(StudentRepository repository) {
        this.repository = repository;
    }
	
    public Student addStudent(Student student) {
        repository.save(student);
        return student;
    }

    public Student getStudentById(int id) {
        return repository.findById(id);
    }
    
    public boolean addGrade(int studentId, int grade) {
        Student student = getStudentById(studentId);
        if (student != null) {
            if (grade >= 1 && grade <= 5) {
                student.addGrade(grade);
                repository.save(student);
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
        return repository.delete(id);
    }
    
    public void showSkill(int id) {
        Student student = getStudentById(id);
        if (student != null) {
            student.showSkill();
        } else {
            System.out.println("Student s id " + id + " nebyl nalezen.");
        }
    }
    
    public List<Student> getStudentsByProgramSorted(StudyProgram program) {
        return repository.findByProgram(program).stream()
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
        return repository.findByProgram(program).stream()
                .mapToDouble(Student::getAverageGrade)
                .average()
                .orElse(0.0);
    }
    
    public long getCountByProgram(StudyProgram program) {
        return repository.findByProgram(program).size();
    }
}
