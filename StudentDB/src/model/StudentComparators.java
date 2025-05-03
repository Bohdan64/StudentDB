package model;

import java.util.Comparator;

public class StudentComparators {
    public static final Comparator<Student> BY_SURNAME_THEN_NAME =
        Comparator.comparing(Student::getPrijmeni)
                  .thenComparing(Student::getJmeno);
}