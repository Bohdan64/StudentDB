package model;

import java.util.ArrayList;
import java.util.List;

public abstract class Student {

    protected static int nextId = 1;

    protected int id;
    protected String jmeno;
    protected String prijmeni;
    protected int year;
    private final StudyProgram program;
    protected List<Integer> grades = new ArrayList<>();

    public Student(String jmeno, String prijmeni, int year, StudyProgram program) {
        this.id = nextId++;
        this.jmeno = jmeno;
        this.prijmeni = prijmeni;
        this.year = year;
        this.program = program;
    }

    public Student(int id, String jmeno, String prijmeni, int year, List<Integer> grades, StudyProgram program) {
        this.id = id;
        this.jmeno = jmeno;
        this.prijmeni = prijmeni;
        this.year = year;
        this.grades = new ArrayList<>(grades); 
        this.program = program;

        if (id >= nextId) {
            nextId = id + 1;
        }
    }

    public void addGrade(int grade) {
        grades.add(grade);
    }

    public double getAverageGrade() {
        return grades.stream()
                     .mapToInt(Integer::intValue)
                     .average()
                     .orElse(0.0);
    }
    
    public int getId() {
        return id;
    }

    public String getJmeno() {
        return jmeno;
    }

    public String getPrijmeni() {
        return prijmeni;
    }

    public int getYear() {
        return year;
    }

    public List<Integer> getGrades() {
        return grades;
    }

    public StudyProgram getProgram() {
        return program;
    }
    
    public abstract void showSkill();
}
