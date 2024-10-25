package org.example;

import java.sql.*;

public class Database_SQL {
    public String generateCode(String prefix) {
        int nextId = getNextId(prefix);
        return prefix + "-" + String.format("%04d", nextId);
    }

    public int getNextId(String prefix) {
        int nextId = 1;
        String sql = "";

        if (prefix.equals("Library_Books")){
            sql = "SELECT MAX(CAST(SUBSTRING_INDEX(Id_Book, '-', -1) AS UNSIGNED)) FROM " + prefix;
        } else if (prefix.equals("Library_Management")){
            sql = "SELECT MAX(CAST(SUBSTRING_INDEX(ID, '-', -1) AS UNSIGNED)) FROM " + prefix;
        }

        try (Connection connection = DriverManager.getConnection(SQL.jdbcURL, SQL.USERNAME, SQL.PASSWORD);
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int currentMaxId = rs.getInt(1);
                if (currentMaxId > 0) {
                    nextId = currentMaxId + 1;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return nextId;
    }
    public void createDatabase (){
        String createLibraryBooksTable = """
            CREATE TABLE IF NOT EXISTS Library_Books (
                ID_Book VARCHAR(255) PRIMARY KEY,
                Name_Book VARCHAR(255) NOT NULL,
                Author VARCHAR(255) NOT NULL,
                Publication_Date VARCHAR(100),
                Status VARCHAR(50),
                Content_Endpoint VARCHAR(255),
                Image_Endpoint VARCHAR(255)
            );
        """;

        String createUsersTable = """
            CREATE TABLE IF NOT EXISTS Users (
                Username VARCHAR(255) PRIMARY KEY,
                Password VARCHAR(255) NOT NULL,
                Fullname VARCHAR(255) NOT NULL,
                Birth VARCHAR(100),
                Email VARCHAR(255) NOT NULL,
                Phone_Number VARCHAR(20)
            );
        """;

        String createAdministratorsTable = """
            CREATE TABLE IF NOT EXISTS Administrators (
                Adminname VARCHAR(255) PRIMARY KEY,
                Password VARCHAR(255) NOT NULL,
                Fullname VARCHAR(255) NOT NULL,
                Birth VARCHAR(100),
                Email VARCHAR(255) NOT NULL,
                Phone_Number VARCHAR(20)
            );
        """;

        String createLibraryManagementTable = """
            CREATE TABLE IF NOT EXISTS Library_Management (
                ID VARCHAR(255) PRIMARY KEY,
                Username VARCHAR(255) NOT NULL,
                ID_Book VARCHAR(255) NOT NULL,
                Borrowed_Day VARCHAR(100),
                Return_Day VARCHAR(100),
                FOREIGN KEY (Username) REFERENCES Users(Username),
                FOREIGN KEY (ID_Book) REFERENCES Library_Books(ID_Book)
            );
        """;


        try (Connection conn = DriverManager.getConnection(SQL.jdbcURL, SQL.USERNAME, SQL.PASSWORD);
             Statement stmt = conn.createStatement()) {

            stmt.execute(createLibraryBooksTable);
//            stmt.execute(createUsersTable);
//            stmt.execute(createAdministratorsTable);
            stmt.execute(createLibraryManagementTable);

            System.out.println("Tables created successfully!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertSampleLibraryBooks() {
        String insertLibraryBooks = """
            INSERT INTO Library_Books (Id_Book, Name_Book, Author, Publication_Date, Status, Content_Endpoint, Image_Endpoint) 
            VALUES (?, ?, ?, ?, ?, ?, ?);
        """;

        try (Connection conn = DriverManager.getConnection(SQL.jdbcURL, SQL.USERNAME, SQL.PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(insertLibraryBooks)) {

            pstmt.setString(1, generateCode("Library_Books"));
            pstmt.setString(2, "Effective Java");
            pstmt.setString(3, "Joshua Bloch");
            pstmt.setString(4, "2008-05-28");
            pstmt.setString(5, "Available");
            pstmt.setString(6, "/content/effective_java.pdf");
            pstmt.setString(7, "/images/great_gatsby.jpg");
            pstmt.executeUpdate();

            pstmt.setString(1, generateCode("Library_Books"));
            pstmt.setString(2, "Clean Code");
            pstmt.setString(3, "Robert C. Martin");
            pstmt.setString(4, "2008-08-01");
            pstmt.setString(5, "Borrowed");
            pstmt.setString(6, "/content/clean_code.pdf");
            pstmt.setString(7, "/images/1984.jpg");
            pstmt.executeUpdate();

            pstmt.setString(1, generateCode("Library_Books"));
            pstmt.setString(2, "Design Patterns");
            pstmt.setString(3, "Erich Gamma");
            pstmt.setString(4, "1994-10-31");
            pstmt.setString(5, "Available");
            pstmt.setString(6, "/content/design_patterns.pdf");
            pstmt.setString(7, "/images/to_kill_a_mockingbird.jpg");
            pstmt.executeUpdate();

            System.out.println("Sample books inserted successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertSampleUsers() {
        String insertUsers = """
            INSERT INTO Users (Username, Password, Fullname, Birth, Email, Phone_Number)
            VALUES (?, ?, ?, ?, ?, ?);
        """;

        try (Connection conn = DriverManager.getConnection(SQL.jdbcURL, SQL.USERNAME, SQL.PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(insertUsers)) {

            pstmt.setString(1, "user01");
            pstmt.setString(2, "password123");
            pstmt.setString(3, "John Doe");
            pstmt.setString(4, "1990-01-01");
            pstmt.setString(5, "john.doe@example.com");
            pstmt.setString(6, "0123456789");
            pstmt.executeUpdate();

            pstmt.setString(1, "user02");
            pstmt.setString(2, "password456");
            pstmt.setString(3, "Jane Smith");
            pstmt.setString(4, "1992-05-12");
            pstmt.setString(5, "jane.smith@example.com");
            pstmt.setString(6, "0987654321");
            pstmt.executeUpdate();

            System.out.println("Sample users inserted successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertSampleAdministrators() {
        String insertAdministrators = """
            INSERT INTO Administrators (Adminname, Password, Fullname, Birth, Email, Phone_Number)
            VALUES (?, ?, ?, ?, ?, ?);
        """;

        try (Connection conn = DriverManager.getConnection(SQL.jdbcURL, SQL.USERNAME, SQL.PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(insertAdministrators)) {

            pstmt.setString(1, "admin01");
            pstmt.setString(2, "adminpass123");
            pstmt.setString(3, "Alice Admin");
            pstmt.setString(4, "1985-11-15");
            pstmt.setString(5, "alice.admin@example.com");
            pstmt.setString(6, "0123456789");
            pstmt.executeUpdate();

            pstmt.setString(1, "admin02");
            pstmt.setString(2, "adminpass456");
            pstmt.setString(3, "Bob Admin");
            pstmt.setString(4, "1983-03-22");
            pstmt.setString(5, "bob.admin@example.com");
            pstmt.setString(6, "0987654321");
            pstmt.executeUpdate();

            System.out.println("Sample administrators inserted successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertSampleLibraryManagement() {
        String insertLibraryManagement = """
            INSERT INTO Library_Management (ID, Username, ID_Book, Borrowed_Day, Return_Day)
            VALUES (?, ?, ?, ?, ?);
        """;

        try (Connection conn = DriverManager.getConnection(SQL.jdbcURL, SQL.USERNAME, SQL.PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(insertLibraryManagement)) {

            pstmt.setString(1, generateCode("Library_Management"));
            pstmt.setString(2, "user01");
            pstmt.setString(3, "Library_Books-0001");
            pstmt.setString(4, "2024-10-01");
            pstmt.setString(5, "2024-10-15");
            pstmt.executeUpdate();

            pstmt.setString(1, generateCode("Library_Management"));
            pstmt.setString(2, "user02");
            pstmt.setString(3, "Library_Books-0002");
            pstmt.setString(4, "2024-09-20");
            pstmt.setString(5, "2024-10-04");
            pstmt.executeUpdate();

            System.out.println("Sample library management records inserted successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
