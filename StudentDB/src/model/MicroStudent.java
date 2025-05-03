package model;

import java.util.List;

public class MicroStudent extends Student {

    private static final long serialVersionUID = 6L;
	
    public MicroStudent(String jmeno, String prijmeni, int year) {
        super(jmeno, prijmeni, year, StudyProgram.MICROELECTRONICS);
    }

    public MicroStudent(int id, String jmeno, String prijmeni, int year, List<Integer> grades) {
        super(id, jmeno, prijmeni, year, grades, StudyProgram.MICROELECTRONICS);
    }

    @Override
    public void showSkill() {
        System.out.println("Mikroelektronická dovednost: Navrhuji integrované obvody a pracuji s FPGA.");
    }
}
