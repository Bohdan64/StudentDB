package app;

import model.TelecomStudent;

public class Main {
    public static void main(String[] args) {
        
        TelecomStudent student1 = new TelecomStudent("Ivan", "Novak", 2000);

        System.out.println("ID: " + student1.getId());
        System.out.println("Jméno: " + student1.getJmeno());
        System.out.println("Příjmení: " + student1.getPrijmeni());
        System.out.println("Rok narození: " + student1.getYear());
        System.out.println("Průměr známek: " + student1.getAverageGrade());

        student1.addGrade(1);
        student1.addGrade(2);
        student1.addGrade(3);

        System.out.println("Průměr známek po přidání 1,2,3: " + student1.getAverageGrade());

        System.out.println("Morse kód:");
        student1.showSkill();
    }
}
