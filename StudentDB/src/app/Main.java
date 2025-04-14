package app;

import model.Student;
import model.StudentManager;
import model.StudyProgram;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        StudentManager manager = new StudentManager();
        
        System.out.println("Obor studenta (1 - Kyberbezpečnost, 2 - Telekomunikace):");
        int choice = sc.nextInt();
        sc.nextLine();

        StudyProgram program = switch (choice) {
        case 1 -> StudyProgram.CYBERSECURITY;
        case 2 -> StudyProgram.TELECOMMUNICATIONS;
		default ->
		throw new IllegalArgumentException("Neplatný výběr oboru");
		};

        System.out.println("Jméno studenta:");
        String jmeno = sc.nextLine();

        System.out.println("Příjmení studenta:");
        String prijmeni = sc.nextLine();

        System.out.println("Rok narození:");
        int year = sc.nextInt();
        
        Student student = manager.addStudent(program, jmeno, prijmeni, year);

        System.out.println("Student byl vytvořen:");
        System.out.println("ID: " + student.getId());
        System.out.println("Jméno: " + student.getJmeno());
        System.out.println("Příjmení: " + student.getPrijmeni());
        System.out.println("Rok narození: " + student.getYear());
        System.out.println("Průměr známek: " + student.getAverageGrade());

        System.out.println("Speciální dovednost:");
        student.showSkill();

        sc.close();
    }
}
