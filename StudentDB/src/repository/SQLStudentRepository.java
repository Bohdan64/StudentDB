package repository;

import model.Student;
import model.StudyProgram;

import java.sql.*;
import java.util.*;
import java.util.logging.Logger;

public class SQLStudentRepository implements StudentRepository {
	private static final Logger logger = Logger.getLogger(SQLStudentRepository.class.getName());
	private final String url = "jdbc:mysql://localhost:3306/studentdb";
    private final String user = "root";
    private final String password = "root";
    
    public void initializeSchema() {
        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            Statement stmt = conn.createStatement();
            
            stmt.executeUpdate("CREATE DATABASE IF NOT EXISTS studentdb");
            conn.setCatalog("studentdb");

            stmt.executeUpdate("""
                CREATE TABLE IF NOT EXISTS students (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    jmeno VARCHAR(100),
                    prijmeni VARCHAR(100),
                    year INT,
                    program VARCHAR(50)
                )
            """);

            stmt.executeUpdate("""
                CREATE TABLE IF NOT EXISTS grades (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    student_id INT,
                    grade INT,
                    FOREIGN KEY (student_id) REFERENCES students(id)
                )
            """);

            System.out.println("Tabulky byly úspěšně vytvořeny nebo již existují.");
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Chyba při inicializaci tabulek", e);
        }
    }

    private void saveWithConnection(Connection conn, Student student) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement(
            "INSERT INTO students (id, jmeno, prijmeni, year, program) VALUES (?, ?, ?, ?, ?)");
        stmt.setInt(1, student.getId());
        stmt.setString(2, student.getJmeno());
        stmt.setString(3, student.getPrijmeni());
        stmt.setInt(4, student.getYear());
        stmt.setString(5, student.getProgram().name());
        stmt.executeUpdate();

        for (int grade : student.getGrades()) {
            PreparedStatement gStmt = conn.prepareStatement(
                "INSERT INTO grades (student_id, grade) VALUES (?, ?)");
            gStmt.setInt(1, student.getId());
            gStmt.setInt(2, grade);
            gStmt.executeUpdate();
        }
    }
    
    @Override
    public void saveAll(List<Student> students) {
        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            conn.setAutoCommit(false);

            for (Student student : students) {
                saveWithConnection(conn, student);
            }

            conn.commit();
            logger.info("Všichni studenti byli uloženi do databáze.");
        } catch (SQLException e) {
            throw new RuntimeException("Chyba při ukládání všech studentů", e);
        }
    }

    public void clearDatabase() {
        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            Statement stmt = conn.createStatement();
            stmt.executeUpdate("DELETE FROM grades");
            stmt.executeUpdate("DELETE FROM students");
            System.out.println("Databáze byla úspěšně vyprázdněna.");
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Chyba při vyprázdnění databáze", e);
        }
    }

    @Override
    public List<Student> findAll() {
        List<Student> result = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM students");

            while (rs.next()) {
                int id = rs.getInt("id");
                String jmeno = rs.getString("jmeno");
                String prijmeni = rs.getString("prijmeni");
                int year = rs.getInt("year");
                StudyProgram program = StudyProgram.valueOf(rs.getString("program"));

                List<Integer> grades = loadGrades(conn, id);

                result.add(program.createStudent(id, jmeno, prijmeni, year, grades));
            }
        } catch (SQLException e) {
            logger.severe("Chyba při načítání studentů z databáze: " + e.getMessage());
            throw new RuntimeException("Chyba při načítání studentů z databáze", e);
        }
        return result;
    }

    private List<Integer> loadGrades(Connection conn, int studentId) throws SQLException {
        List<Integer> grades = new ArrayList<>();
        PreparedStatement stmt = conn.prepareStatement("SELECT grade FROM grades WHERE student_id = ?");
        stmt.setInt(1, studentId);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            grades.add(rs.getInt("grade"));
        }
        return grades;
    }
    
    @Override
    public void save(Student student) {
    	throw new UnsupportedOperationException("Metoda není v této implementaci implementována.");
    }
    
    @Override
    public boolean delete(int id) {
    	throw new UnsupportedOperationException("Metoda není v této implementaci implementována.");
    }
    
    @Override
    public Student findById(int id) {
    	throw new UnsupportedOperationException("Metoda není v této implementaci implementována.");
    }
    
    @Override
    public List<Student> findByProgram(StudyProgram program) {
    	throw new UnsupportedOperationException("Metoda není v této implementaci implementována.");
    }
}