package app;

import model.StudentManager;
import model.StudyProgram;
import model.Student;
import repository.InMemoryStudentRepository;
import repository.SQLStudentRepository;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        SQLStudentRepository sqlRepository = new SQLStudentRepository();
        sqlRepository.initializeSchema();

        InMemoryStudentRepository inMemoryRepository = new InMemoryStudentRepository();

        for (Student s : sqlRepository.findAll()) {
            inMemoryRepository.save(s);
        }
        inMemoryRepository.updateNextId();

        StudentManager manager = new StudentManager(inMemoryRepository);

        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("\n=== MENU ===");
            System.out.println("1 - Přidat studenta");
            System.out.println("2 - Přidat známku");
            System.out.println("3 - Zobrazit informace a spustit dovednost");
            System.out.println("4 - Odstranit studenta");
            System.out.println("5 - Seřazený výpis všech studentů dle oborů");
            System.out.println("6 - Zobrazit počet studentů dle oboru");
            System.out.println("7 - Zobrazit průměrnou známku dle oboru");
            System.out.println("0 - Konec");
            System.out.print("Volba: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> {
                    System.out.print("Jméno: ");
                    String jmeno = scanner.nextLine();
                    System.out.print("Příjmení: ");
                    String prijmeni = scanner.nextLine();
                    System.out.print("Rok narození: ");
                    int rok = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Obor (1 - Kyberbezpečnost, 2 - Telekomunikace): ");
                    int oborVolba = scanner.nextInt();
                    scanner.nextLine();

                    StudyProgram program = switch (oborVolba) {
                        case 1 -> StudyProgram.CYBERSECURITY;
                        case 2 -> StudyProgram.TELECOMMUNICATIONS;
                        default -> {
                            System.out.println("Neplatný výběr oboru.");
                            yield null;
                        }
                    };

                    if (program != null) {
                        Student student = program.createStudent(jmeno, prijmeni, rok);
                        manager.addStudent(student);
                        System.out.println("Student přidán.");
                    }
                }

                case 2 -> {
                    System.out.print("ID studenta: ");
                    int id = scanner.nextInt();
                    System.out.print("Známka (1-5): ");
                    int grade = scanner.nextInt();
                    scanner.nextLine();

                    boolean success = manager.addGrade(id, grade);
                    if (success) {
                        System.out.println("Známka přidána.");
                    }
                }

                case 3 -> {
                    System.out.print("ID studenta: ");
                    int id = scanner.nextInt();
                    scanner.nextLine();

                    Student student = manager.getStudentById(id);
                    if (student != null) {
                        System.out.println("ID: " + student.getId());
                        System.out.println("Jméno: " + student.getJmeno());
                        System.out.println("Příjmení: " + student.getPrijmeni());
                        System.out.println("Rok narození: " + student.getYear());
                        System.out.println("Průměr známek: " + student.getAverageGrade());
                        System.out.println("Dovednost:");
                        student.showSkill();
                    } else {
                        System.out.println("Student s tímto ID nebyl nalezen.");
                    }
                }

                case 4 -> {
                    System.out.print("Zadejte ID studenta k odstranění: ");
                    int id = scanner.nextInt();
                    scanner.nextLine();

                    boolean removed = manager.removeStudentById(id);
                    if (removed) {
                        System.out.println("Student byl úspěšně odstraněn.");
                    } else {
                        System.out.println("Student s tímto ID nebyl nalezen.");
                    }
                }

                case 5 -> {
                    manager.printAllStudentsGroupedAndSorted();
                }

                case 6 -> {
                    System.out.println("\n--- Počet studentů dle oboru ---");
                    for (StudyProgram program : StudyProgram.values()) {
                        long count = manager.getCountByProgram(program);
                        System.out.println(program.name() + ": " + count + " studentů");
                    }
                }

                case 7 -> {
                    System.out.println("\n--- Průměrné známky dle oboru ---");
                    for (StudyProgram program : StudyProgram.values()) {
                        double average = manager.getAverageGradeProgram(program);
                        System.out.printf("%s: %.2f%n", program.name(), average);
                    }
                }

                case 0 -> {
                    System.out.println("Ukládám změny do SQL databáze...");
                    sqlRepository.clearDatabase();
                    sqlRepository.saveAll(inMemoryRepository.findAll());
                    System.out.println("Změny byly uloženy. Program ukončen.");
                    running = false;
                }

                default -> System.out.println("Neplatná volba.");
            }
        }

        scanner.close();
    }
}
