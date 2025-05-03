package model;

import java.util.List;

public class ITStudent extends Student {
	
    private static final long serialVersionUID = 4L;
    
    public ITStudent(String jmeno, String prijmeni, int year) {
        super(jmeno, prijmeni, year, StudyProgram.INFORMATION_TECHNOLOGY);
    }

    public ITStudent(int id, String jmeno, String prijmeni, int year, List<Integer> grades) {
        super(id, jmeno, prijmeni, year, grades, StudyProgram.INFORMATION_TECHNOLOGY);
    }

    @Override
    public void showSkill() {
        System.out.println("IT dovednost: Pracuji s databázemi a backendem.");
    }
}
