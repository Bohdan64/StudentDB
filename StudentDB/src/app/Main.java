package app;

import model.*;
import repository.*;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("1 - SQL režim");
        System.out.println("2 - Souborový režim");
        System.out.print("Volba: ");
        int mode = readInt(scanner);

        boolean isFileMode = mode == 2;

        StudentRepository sourceRepository;
        InMemoryStudentRepository inMemoryRepository = new InMemoryStudentRepository();

        if (mode == 1) {
            SQLStudentRepository sqlRepository = new SQLStudentRepository();
            sqlRepository.initializeSchema(); 
            sourceRepository = sqlRepository;
        } else {
            sourceRepository = new FileStudentRepository();
        }

        for (Student s : sourceRepository.findAll()) {
            inMemoryRepository.save(s);
        }
        inMemoryRepository.updateNextId();

        StudentManager manager = new StudentManager(inMemoryRepository);
        FileStudentRepository fileRepo = isFileMode ? (FileStudentRepository) sourceRepository : null;

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
            int choice = readInt(scanner);

            switch (choice) {
                case 1 -> {
                    System.out.print("Jméno: ");
                    String jmeno = scanner.nextLine();
                    System.out.print("Příjmení: ");
                    String prijmeni = scanner.nextLine();
                    System.out.print("Rok narození: ");
                    int rok = readInt(scanner);
                    System.out.print("Obor (1 - Kyberbezpečnost, 2 - Telekomunikace, 3 - IT, 4 - Bioinformatika, 5 - Mikroelektronika): ");
                    int oborVolba = readInt(scanner);

                    StudyProgram program = switch (oborVolba) {
                    case 1 -> StudyProgram.CYBERSECURITY;
                    case 2 -> StudyProgram.TELECOMMUNICATIONS;
                    case 3 -> StudyProgram.INFORMATION_TECHNOLOGY;
                    case 4 -> StudyProgram.BIOINFORMATICS;
                    case 5 -> StudyProgram.MICROELECTRONICS;
                        default -> {
                            System.out.println("Neplatný výběr oboru.");
                            yield null;
                        }
                    };

                    if (program != null) {
                        Student student = program.createStudent(jmeno, prijmeni, rok);
                        manager.addStudent(student);
                        System.out.println("Student přidán. ID studenta: " + student.getId());
                        if (isFileMode) {
                            fileRepo.save(student);
                        }
                    }
                }

                case 2 -> {
                    System.out.print("ID studenta: ");
                    int id = readInt(scanner);
                    System.out.print("Známka (1-5): ");
                    int grade = readInt(scanner);

                    boolean success = manager.addGrade(id, grade);
                    if (success) {
                        System.out.println("✅ Známka přidána.");
                        if (isFileMode) {
                            fileRepo.save(manager.getStudentById(id));
                        }
                    }
                }

                case 3 -> {
                    System.out.print("ID studenta: ");
                    int id = readInt(scanner);

                    Student student = manager.getStudentById(id);
                    if (student != null) {
                        System.out.println("ID: " + student.getId());
                        System.out.println("Jméno: " + student.getJmeno());
                        System.out.println("Příjmení: " + student.getPrijmeni());
                        System.out.println("Rok narození: " + student.getYear());
                        System.out.println("Program: " + student.getProgram().name());
                        System.out.println("Průměr známek: " + student.getAverageGrade());
                        System.out.println("Dovednost:");
                        student.showSkill();
                    } else {
                        System.out.println("Student s tímto ID nebyl nalezen.");
                    }
                }

                case 4 -> {
                    System.out.print("Zadejte ID studenta k odstranění: ");
                    int id = readInt(scanner);

                    boolean removed = manager.removeStudentById(id);
                    if (removed) {
                        System.out.println("✅ Student byl úspěšně odstraněn.");
                        if (isFileMode) {
                            fileRepo.delete(id);
                        }
                    } else {
                        System.out.println("Student s tímto ID nebyl nalezen.");
                    }
                }

                case 5 -> {
                    System.out.println("Zvolte režim třídění:");
                    System.out.println("1 - Příjmení + Jméno");
                    System.out.println("2 - Jméno + Příjmení");
                    System.out.println("3 - Rok narození vzestupně");
                    System.out.println("4 - Rok narození sestupně");
                    System.out.println("5 - Průměrná známka vzestupně");
                    System.out.println("6 - Průměrná známka sestupně");
                    System.out.print("Volba: ");
                    int sortChoice = readInt(scanner);

                    SortMode sortMode = switch (sortChoice) {
                        case 2 -> SortMode.BY_NAME;
                        case 3 -> SortMode.BY_YEAR_ASC;
                        case 4 -> SortMode.BY_YEAR_DESC;
                        case 5 -> SortMode.BY_GRADE_ASC;
                        case 6 -> SortMode.BY_GRADE_DESC;
                        default -> SortMode.BY_SURNAME;
                    };

                    System.out.println("Zvolte obor pro výpis:");
                    int i = 1;
                    for (StudyProgram program : StudyProgram.values()) {
                        System.out.println(i + " - " + program.name());
                        i++;
                    }
                    System.out.print("Volba: ");
                    int oborVolba = readInt(scanner);

                    if (oborVolba < 1 || oborVolba > StudyProgram.values().length) {
                        System.out.println("Neplatná volba oboru.");
                        break;
                    }

                    StudyProgram selectedProgram = StudyProgram.values()[oborVolba - 1];
                    System.out.println("=== " + selectedProgram.name() + " ===");
                    var sorted = manager.getStudentsByProgramSorted(selectedProgram, sortMode);
                    manager.printStudents(sorted);
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
                    if (!isFileMode) {
                        SQLStudentRepository sqlRepository = (SQLStudentRepository) sourceRepository;
                        System.out.println("Ukládám změny do SQL databáze...");
                        sqlRepository.clearDatabase();
                        sqlRepository.saveAll(inMemoryRepository.findAll());
                        System.out.println("Změny byly uloženy.");
                    } else {
                        System.out.print("Vložit změny do SQL databáze? (ano/ne): ");
                        String answer = scanner.nextLine().trim().toLowerCase();
                        if (answer.equals("ano")) {
                            SQLStudentRepository sqlRepository = new SQLStudentRepository();
                            sqlRepository.clearDatabase();
                            sqlRepository.saveAll(inMemoryRepository.findAll());
                            System.out.println("Změny byly vloženy do SQL databáze.");
                        } else {
                            System.out.println("Program ukončen.");
                        }
                    }
                    running = false;
                }
                default -> System.out.println("Neplatná volba.");
            }
        }
        scanner.close();
    }

    private static int readInt(Scanner scanner) {
        while (true) {
            try {
                int value = scanner.nextInt();
                scanner.nextLine();
                return value;
            } catch (InputMismatchException e) {
                System.out.print("⚠️ Zadejte platné číslo: ");
                scanner.nextLine();
            }
        }
    }
}
