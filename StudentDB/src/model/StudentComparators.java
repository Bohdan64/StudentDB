package model;

import java.util.Comparator;

public class StudentComparators {
    
    // Прізвище → Ім'я
    public static final Comparator<Student> BY_SURNAME_THEN_NAME =
        Comparator.comparing(Student::getPrijmeni)
                  .thenComparing(Student::getJmeno);

    // Ім'я → Прізвище
    public static final Comparator<Student> BY_NAME_THEN_SURNAME =
        Comparator.comparing(Student::getJmeno)
                  .thenComparing(Student::getPrijmeni);

    // За роком народження (молодші спочатку)
    public static final Comparator<Student> BY_YEAR_ASC =
        Comparator.comparingInt(Student::getYear);

    // За роком народження (старші спочатку)
    public static final Comparator<Student> BY_YEAR_DESC =
        Comparator.comparingInt(Student::getYear).reversed();

    // За середнім балом (вищі бали перші)
    public static final Comparator<Student> BY_AVERAGE_GRADE_DESC =
        Comparator.comparingDouble(Student::getAverageGrade).reversed();

    // За середнім балом (нижчі бали перші)
    public static final Comparator<Student> BY_AVERAGE_GRADE_ASC =
        Comparator.comparingDouble(Student::getAverageGrade);
}
