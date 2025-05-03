package repository;

import model.Student;
import model.StudyProgram;

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class FileStudentRepository implements StudentRepository {

    private static final String DIRECTORY = "students/";

    public FileStudentRepository() {
        try {
            Files.createDirectories(Paths.get(DIRECTORY));
        } catch (IOException e) {
            throw new RuntimeException("Chyba při inicializaci složky students/", e);
        }
    }

    @Override
    public void save(Student student) {
        String filename = DIRECTORY + student.getId() + ".ser";
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename))) {
            out.writeObject(student);
        } catch (IOException e) {
            throw new RuntimeException("Chyba při ukládání studenta ID: " + student.getId(), e);
        }
    }

    @Override
    public boolean delete(int id) {
        String filename = DIRECTORY + id + ".ser";
        try {
            return Files.deleteIfExists(Paths.get(filename));
        } catch (IOException e) {
            throw new RuntimeException("Chyba při mazání studenta ID: " + id, e);
        }
    }

    @Override
    public Student findById(int id) {
        String filename = DIRECTORY + id + ".ser";
        if (!Files.exists(Paths.get(filename))) {
            return null;
        }
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename))) {
            return (Student) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException("Chyba při načítání studenta ID: " + id, e);
        }
    }

    @Override
    public List<Student> findAll() {
        List<Student> students = new ArrayList<>();
        try {
            Files.list(Paths.get(DIRECTORY))
                .filter(path -> path.getFileName().toString().endsWith(".ser"))
                .forEach(path -> {
                    try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(path.toFile()))) {
                        students.add((Student) in.readObject());
                    } catch (IOException | ClassNotFoundException e) {
                        System.err.println("Chyba při načítání souboru: " + path.getFileName());
                    }
                });
        } catch (IOException e) {
            throw new RuntimeException("Chyba při načítání všech studentů", e);
        }
        return students;
    }

    @Override
    public List<Student> findByProgram(StudyProgram program) {
        List<Student> students = findAll();
        return students.stream()
                .filter(s -> s.getProgram() == program)
                .toList();
    }
    
    @Override
    public void saveAll(List<Student> students) {
        for (Student student : students) {
            save(student);
        }
    }
}