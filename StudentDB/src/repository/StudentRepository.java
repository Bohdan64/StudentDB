package repository;

import model.Student;
import model.StudyProgram;
import java.util.List;

public interface StudentRepository {
    void saveAll(List<Student> students);
    void save(Student student);
    boolean delete(int id);
    Student findById(int id);
    List<Student> findAll();
    List<Student> findByProgram(StudyProgram program);
}
