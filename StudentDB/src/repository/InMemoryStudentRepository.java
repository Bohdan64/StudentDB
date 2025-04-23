package repository;

import model.Student;
import model.StudyProgram;

import java.util.*;

public class InMemoryStudentRepository implements StudentRepository {
    private final Map<Integer, Student> students = new HashMap<>();
    private int nextId = 1;

    public void updateNextId() {
        nextId = students.keySet().stream().mapToInt(Integer::intValue).max().orElse(0) + 1;
    }

    @Override
    public void save(Student student) {
        if (student.getId() == null) {
            student.setId(nextId++);
        } else if (student.getId() >= nextId) {
            nextId = student.getId() + 1;
        }
        students.put(student.getId(), student);
    }

    @Override
    public void saveAll(List<Student> students) {
        for (Student s : students) {
            save(s);
        }
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
