package repository;

import model.Student;
import model.StudyProgram;

import java.util.*;

public class InMemoryStudentRepository implements StudentRepository {
    private final Map<Integer, Student> students = new HashMap<>();

    @Override
    public void save(Student student) {
        students.put(student.getId(), student);
    }

    @Override
    public boolean delete(int id) {
        return students.remove(id) != null;
    }

    @Override
    public Student findById(int id) {
        return students.get(id);
    }

    @Override
    public List<Student> findAll() {
        return new ArrayList<>(students.values());
    }

    @Override
    public List<Student> findByProgram(StudyProgram program) {
        return students.values().stream()
                .filter(s -> s.getProgram() == program)
                .toList();
    }
}
