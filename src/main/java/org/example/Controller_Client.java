package org.example;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;

@RestController
@RequestMapping("/client")
public class Controller_Client {
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

    @GetMapping("/register")
    public void registerUserFromJson(@RequestBody String jsonString) {
//        String jsonString = "{\n" +
//                "    \"username\": \"new_user\",\n" +
//                "    \"password\": \"password123\",\n" +
//                "    \"fullname\": \"John Doe\",\n" +
//                "    \"birth\": \"1990-01-01\",\n" +
//                "    \"email\": \"john.doe@example.com\",\n" +
//                "    \"phoneNumber\": \"0123456789\"\n" +
//                "}\n";
        JsonObject jsonObject = JsonParser.parseString(jsonString).getAsJsonObject();

        String username = jsonObject.get("username").getAsString();
        String password = jsonObject.get("password").getAsString();
        String fullname = jsonObject.get("fullname").getAsString();
        String birth = jsonObject.get("birth").getAsString();
        String email = jsonObject.get("email").getAsString();
        String phoneNumber = jsonObject.get("phoneNumber").getAsString();

        String checkUserExistsQuery = "SELECT COUNT(*) FROM Users WHERE Username = ?";
        String insertUserQuery = """
            INSERT INTO Users (Username, Password, Fullname, Birth, Email, Phone_Number)
            VALUES (?, ?, ?, ?, ?, ?);
        """;

        try (Connection conn = DriverManager.getConnection(SQL.jdbcURL, SQL.USERNAME, SQL.PASSWORD);
             PreparedStatement checkUserStmt = conn.prepareStatement(checkUserExistsQuery);
             PreparedStatement insertUserStmt = conn.prepareStatement(insertUserQuery)) {

            checkUserStmt.setString(1, username);
            ResultSet rs = checkUserStmt.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                System.out.println("Username already exists. Please choose another username.");
                return;
            }

            insertUserStmt.setString(1, username);
            insertUserStmt.setString(2, password);
            insertUserStmt.setString(3, fullname);
            insertUserStmt.setString(4, birth);
            insertUserStmt.setString(5, email);
            insertUserStmt.setString(6, phoneNumber);
            int rowsInserted = insertUserStmt.executeUpdate();

            if (rowsInserted > 0) {
                System.out.println("User registered successfully!");
            } else {
                System.out.println("Error occurred during user registration.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @GetMapping("/login")
    public Object loginUser(@RequestBody String jsonString) {
        //        String example = "{\n" +
//                "    \"username\": \"new_user\",\n" +
//                "    \"fullname\": \"John Doe\"\n" +
//                "}";
        JsonObject jsonObject = JsonParser.parseString(jsonString).getAsJsonObject();

        String username = jsonObject.get("username").getAsString();
        String password = jsonObject.get("password").getAsString();

        String loginQuery = "SELECT * FROM Users WHERE Username = ?";

        try (Connection conn = DriverManager.getConnection(SQL.jdbcURL, SQL.USERNAME, SQL.PASSWORD);
             PreparedStatement loginStmt = conn.prepareStatement(loginQuery)) {

            loginStmt.setString(1, username);
            ResultSet rs = loginStmt.executeQuery();

            if (rs.next()) {
                String storedPassword = rs.getString("Password");

                if (storedPassword.equals(password)) {
                    JsonObject response = new JsonObject();
                    response.addProperty("username", rs.getString("Username"));
                    response.addProperty("fullname", rs.getString("Fullname"));
                    response.addProperty("birth", rs.getString("Birth"));
                    response.addProperty("email", rs.getString("Email"));
                    response.addProperty("phone_number", rs.getString("Phone_Number"));

                    System.out.println("Login successful!");
                    return response.toString();
                } else {
                    System.out.println("Incorrect password.");
                }
            } else {
                System.out.println("Username does not exist.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }


    @GetMapping("/getBooks")
    public String getAllBooksClient() {
        String query = "SELECT * FROM Library_Books";
        JsonArray booksArray = new JsonArray();

        try (Connection conn = DriverManager.getConnection(SQL.jdbcURL, SQL.USERNAME, SQL.PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                JsonObject bookObject = new JsonObject();
                bookObject.addProperty("ID_Book", rs.getString("ID_Book"));
                bookObject.addProperty("Name_Book", rs.getString("Name_Book"));
                bookObject.addProperty("Author", rs.getString("Author"));
                bookObject.addProperty("Publication_Date", rs.getString("Publication_Date"));
                bookObject.addProperty("Status", rs.getString("Status"));
                bookObject.addProperty("Content_Endpoint", rs.getString("Content_Endpoint"));
                bookObject.addProperty("Image_Endpoint", rs.getString("Image_Endpoint"));

                booksArray.add(bookObject);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return booksArray.toString();
    }

    @GetMapping("/borrowBook")
    public boolean borrowBook(@RequestBody String jsonString) {
//        String example = "{\n" +
//                "    \"username\": \"phongnt\",\n" +
//                "    \"ID_Book\": \"Library_Books-0003\",\n" +
//                "    \"Borrowed_Day\": \"2024-10-23\",\n" +
//                "    \"Return_Day\": \"2024-11-23\"\n" +
//                "}";

        JsonObject jsonObject = JsonParser.parseString(jsonString).getAsJsonObject();
        String username = jsonObject.get("username").getAsString();
        String idBook = jsonObject.get("ID_Book").getAsString();
        String borrowedDay = jsonObject.get("Borrowed_Day").getAsString();
        String returnDay = jsonObject.get("Return_Day").getAsString();

        String checkStatusQuery = "SELECT Status FROM Library_Books WHERE ID_Book = ?";
        String updateStatusQuery = "UPDATE Library_Books SET Status = 'Borrowed' WHERE ID_Book = ?";
        String insertManagementQuery = "INSERT INTO Library_Management (ID, Username, ID_Book, Borrowed_Day, Return_Day) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(SQL.jdbcURL, SQL.USERNAME, SQL.PASSWORD);
             PreparedStatement checkStatusStmt = conn.prepareStatement(checkStatusQuery);
             PreparedStatement updateStmt = conn.prepareStatement(updateStatusQuery);
             PreparedStatement insertStmt = conn.prepareStatement(insertManagementQuery)) {

            checkStatusStmt.setString(1, idBook);
            ResultSet rs = checkStatusStmt.executeQuery();

            if (rs.next()) {
                String status = rs.getString("Status");

                if ("Borrowed".equals(status)) {
                    System.out.println("This book is already borrowed by another user.");
                    return false;
                }
            } else {
                System.out.println("Book not found.");
                return false;
            }

            updateStmt.setString(1, idBook);
            int updatedRows = updateStmt.executeUpdate();

            if (updatedRows > 0) {
                insertStmt.setString(1, generateCode("Library_Management"));
                insertStmt.setString(2, username);
                insertStmt.setString(3, idBook);
                insertStmt.setString(4, borrowedDay);
                insertStmt.setString(5, returnDay);
                insertStmt.executeUpdate();
                System.out.println("Book borrowed successfully!");
                return true;
            } else {
                System.out.println("Failed to update the book status.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    @GetMapping("/listBorrowed")
    public JsonArray getBorrowedBooksByUsername(@RequestParam("username") String username) {
        String query = "SELECT lb.Id_Book, lb.Name_Book, lb.Author, lb.Publication_Date, lm.Borrowed_Day, lm.Return_Day " +
                "FROM Library_Management lm " +
                "JOIN Library_Books lb ON lm.ID_Book = lb.Id_Book " +
                "WHERE lm.Username = ? AND lb.Status = 'Borrowed'";

        JsonArray borrowedBooks = new JsonArray();

        try (Connection conn = DriverManager.getConnection(SQL.jdbcURL, SQL.USERNAME, SQL.PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                JsonObject bookInfo = new JsonObject();
                bookInfo.addProperty("ID_Book", rs.getString("Id_Book"));
                bookInfo.addProperty("Name_Book", rs.getString("Name_Book"));
                bookInfo.addProperty("Author", rs.getString("Author"));
                bookInfo.addProperty("Publication_Date", rs.getString("Publication_Date"));
                bookInfo.addProperty("Borrowed_Day", rs.getString("Borrowed_Day"));
                bookInfo.addProperty("Return_Day", rs.getString("Return_Day"));

                borrowedBooks.add(bookInfo);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return borrowedBooks;
    }

    @GetMapping("/returnBook")
    public boolean returnBook(@RequestParam("idBook") String idBook) {
        String deleteManagementQuery = "DELETE FROM Library_Management WHERE ID_Book = ?";
        String updateStatusQuery = "UPDATE Library_Books SET Status = 'Available' WHERE ID_Book = ?";

        try (Connection conn = DriverManager.getConnection(SQL.jdbcURL, SQL.USERNAME, SQL.PASSWORD);
             PreparedStatement deleteStmt = conn.prepareStatement(deleteManagementQuery);
             PreparedStatement updateStmt = conn.prepareStatement(updateStatusQuery)) {

            deleteStmt.setString(1, idBook);
            int deletedRows = deleteStmt.executeUpdate();

            if (deletedRows > 0) {
                updateStmt.setString(1, idBook);
                int updatedRows = updateStmt.executeUpdate();

                if (updatedRows > 0) {
                    System.out.println("Book returned successfully!");
                    return true;
                } else {
                    System.out.println("Failed to update book status to 'Available'.");
                }
            } else {
                System.out.println("No matching record found in Library_Management for the given book ID.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    @GetMapping("/readBook")
    public String readBook(@RequestParam("idBook") String idBook) {
        String query = "SELECT Content_Endpoint FROM Library_Books WHERE Id_Book = ?";

        try (Connection conn = DriverManager.getConnection(SQL.jdbcURL, SQL.USERNAME, SQL.PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, idBook);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String contentPath = rs.getString("Content_Endpoint");

                // Đọc nội dung file sách từ đường dẫn
                return readFileContent(contentPath);
            } else {
                System.out.println("Book with ID " + idBook + " does not exist.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;  // Trả về null nếu không tìm thấy sách hoặc có lỗi
    }

    private String readFileContent(String filePath) {
        StringBuilder content = new StringBuilder();

        if (filePath != null) {
            File file = new File(filePath);

            if (file.exists()) {
                try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                    String line;
                    while ((line = br.readLine()) != null) {
                        content.append(line).append(System.lineSeparator());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("File " + filePath + " does not exist.");
            }
        }

        return content.toString();  // Trả về nội dung sách dưới dạng chuỗi
    }


}
