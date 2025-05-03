package model;

import java.util.Comparator;

public class StudentComparators {
    
    public static final Comparator<Student> BY_SURNAME_THEN_NAME =
        Comparator.comparing(Student::getPrijmeni)
                  .thenComparing(Student::getJmeno);

    public static final Comparator<Student> BY_NAME_THEN_SURNAME =
        Comparator.comparing(Student::getJmeno)
                  .thenComparing(Student::getPrijmeni);

    public static final Comparator<Student> BY_YEAR_ASC =
        Comparator.comparingInt(Student::getYear);

    public static final Comparator<Student> BY_YEAR_DESC =
        Comparator.comparingInt(Student::getYear).reversed();

    public static final Comparator<Student> BY_AVERAGE_GRADE_DESC =
        Comparator.comparingDouble(Student::getAverageGrade).reversed();

    public static final Comparator<Student> BY_AVERAGE_GRADE_ASC =
        Comparator.comparingDouble(Student::getAverageGrade);
}
