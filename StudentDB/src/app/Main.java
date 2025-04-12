package app;

import model.CyberStudent;
import model.TelecomStudent;

public class Main {
    public static void main(String[] args) {
        
        TelecomStudent telecomStudent = new TelecomStudent("Ivan", "Novak", 2000);
        
        System.out.println("Telecom Student:");
        System.out.println("ID: " + telecomStudent.getId());
        System.out.println("Jméno: " + telecomStudent.getJmeno());
        System.out.println("Příjmení: " + telecomStudent.getPrijmeni());
        System.out.println("Rok narození: " + telecomStudent.getYear());
        System.out.println("Průměr známek: " + telecomStudent.getAverageGrade());

        telecomStudent.addGrade(1);
        telecomStudent.addGrade(2);
        telecomStudent.addGrade(3);

        System.out.println("Průměr známek po přidání 1,2,3: " + telecomStudent.getAverageGrade());

        System.out.println("Morse kód:");
        telecomStudent.showSkill();

        System.out.println();
        
        CyberStudent cyberStudent = new CyberStudent("Ivan", "Novak", 2000);

        System.out.println("Cyber Student:");
        System.out.println("ID: " + cyberStudent.getId());
        System.out.println("Jméno: " + cyberStudent.getJmeno());
        System.out.println("Příjmení: " + cyberStudent.getPrijmeni());
        System.out.println("Rok narození: " + cyberStudent.getYear());

        cyberStudent.addGrade(5);
        cyberStudent.addGrade(4);
        cyberStudent.addGrade(3);

        System.out.println("Průměr známek po přidání 5,4,3: " + cyberStudent.getAverageGrade());

        cyberStudent.showSkill();
    }
}
