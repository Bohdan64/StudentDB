package model;

import java.util.List;

public enum StudyProgram {
    CYBERSECURITY {
        @Override
        public Student createStudent(String jmeno, String prijmeni, int year) {
            return new CyberStudent(jmeno, prijmeni, year);
        }

        @Override
        public Student createStudent(int id, String jmeno, String prijmeni, int year, List<Integer> grades) {
            return new CyberStudent(id, jmeno, prijmeni, year, grades);
        }
    },

    TELECOMMUNICATIONS {
        @Override
        public Student createStudent(String jmeno, String prijmeni, int year) {
            return new TelecomStudent(jmeno, prijmeni, year);
        }

        @Override
        public Student createStudent(int id, String jmeno, String prijmeni, int year, List<Integer> grades) {
            return new TelecomStudent(id, jmeno, prijmeni, year, grades);
        }
    },

    INFORMATION_TECHNOLOGY {
        @Override
        public Student createStudent(String jmeno, String prijmeni, int year) {
            return new ITStudent(jmeno, prijmeni, year);
        }

        @Override
        public Student createStudent(int id, String jmeno, String prijmeni, int year, List<Integer> grades) {
            return new ITStudent(id, jmeno, prijmeni, year, grades);
        }
    },

    BIOINFORMATICS {
        @Override
        public Student createStudent(String jmeno, String prijmeni, int year) {
            return new BioStudent(jmeno, prijmeni, year);
        }

        @Override
        public Student createStudent(int id, String jmeno, String prijmeni, int year, List<Integer> grades) {
            return new BioStudent(id, jmeno, prijmeni, year, grades);
        }
    },

    MICROELECTRONICS {
        @Override
        public Student createStudent(String jmeno, String prijmeni, int year) {
            return new MicroStudent(jmeno, prijmeni, year);
        }

        @Override
        public Student createStudent(int id, String jmeno, String prijmeni, int year, List<Integer> grades) {
            return new MicroStudent(id, jmeno, prijmeni, year, grades);
        }
    };

    public abstract Student createStudent(String jmeno, String prijmeni, int year);
    public abstract Student createStudent(int id, String jmeno, String prijmeni, int year, List<Integer> grades);
}
