package model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TelecomStudent extends Student {

    private static final long serialVersionUID = 3L;
    private static final Map<Character, String> MORSE_MAP = new HashMap<>();

    static {
        MORSE_MAP.put('A', ".-");    MORSE_MAP.put('B', "-...");
        MORSE_MAP.put('C', "-.-.");  MORSE_MAP.put('D', "-..");
        MORSE_MAP.put('E', ".");     MORSE_MAP.put('F', "..-.");
        MORSE_MAP.put('G', "--.");   MORSE_MAP.put('H', "....");
        MORSE_MAP.put('I', "..");    MORSE_MAP.put('J', ".---");
        MORSE_MAP.put('K', "-.-");   MORSE_MAP.put('L', ".-..");
        MORSE_MAP.put('M', "--");    MORSE_MAP.put('N', "-.");
        MORSE_MAP.put('O', "---");   MORSE_MAP.put('P', ".--.");
        MORSE_MAP.put('Q', "--.-");  MORSE_MAP.put('R', ".-.");
        MORSE_MAP.put('S', "...");   MORSE_MAP.put('T', "-");
        MORSE_MAP.put('U', "..-");   MORSE_MAP.put('V', "...-");
        MORSE_MAP.put('W', ".--");   MORSE_MAP.put('X', "-..-");
        MORSE_MAP.put('Y', "-.--");  MORSE_MAP.put('Z', "--..");
    }

    public TelecomStudent(String jmeno, String prijmeni, int year) {
        super(jmeno, prijmeni, year, StudyProgram.TELECOMMUNICATIONS);
    }

    public TelecomStudent(int id, String jmeno, String prijmeni, int year, List<Integer> grades) {
        super(id, jmeno, prijmeni, year, grades, StudyProgram.TELECOMMUNICATIONS);
    }

    @Override
    public void showSkill() {
        String fullName = (jmeno + " " + prijmeni).toUpperCase();

        StringBuilder morseOutput = new StringBuilder();

        for (char ch : fullName.toCharArray()) {
            if (ch == ' ') {
                morseOutput.append("/ ");
            } else if (MORSE_MAP.containsKey(ch)) {
                morseOutput.append(MORSE_MAP.get(ch)).append(" ");
            } else {
                morseOutput.append("? ");
            }
        }

        System.out.println("Morse kód studenta: " + morseOutput);
    }
     
}
