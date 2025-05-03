package model;

import java.util.List;

public class BioStudent extends Student {
	
    private static final long serialVersionUID = 5L;
	
    public BioStudent(String jmeno, String prijmeni, int year) {
        super(jmeno, prijmeni, year, StudyProgram.BIOINFORMATICS);
    }

    public BioStudent(int id, String jmeno, String prijmeni, int year, List<Integer> grades) {
        super(id, jmeno, prijmeni, year, grades, StudyProgram.BIOINFORMATICS);
    }

    @Override
    public void showSkill() {
        System.out.println("Bioinformatická dovednost: Analyzuji genetická data a používám Python pro zpracování.");
    }
}
