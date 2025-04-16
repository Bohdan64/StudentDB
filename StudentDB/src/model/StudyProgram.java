package model;

public enum StudyProgram {
	CYBERSECURITY {
		@Override
        public Student createStudent(String jmeno, String prijmeni, int year) {
            return new CyberStudent(jmeno, prijmeni, year);
            }
		},

    TELECOMMUNICATIONS {
	        @Override
	        public Student createStudent(String jmeno, String prijmeni, int year) {
	            return new TelecomStudent(jmeno, prijmeni, year);
	        }
	    };
	
	public abstract Student createStudent(String jmeno, String prijmeni, int year);
	
}

