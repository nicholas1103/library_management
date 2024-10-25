package org.example;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.*;

@RestController
@RequestMapping("/admin")
public class Controller_Administrator {
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

    @GetMapping("/login")
    public Object loginAdmin(@RequestBody String jsonString) {
        JsonObject jsonObject = JsonParser.parseString(jsonString).getAsJsonObject();

        String adminname = jsonObject.get("adminname").getAsString();
        String password = jsonObject.get("password").getAsString();

        String loginQuery = "SELECT * FROM Administrators WHERE Adminname = ? AND Password = ?";
        JsonObject responseObject = new JsonObject();

        try (Connection conn = DriverManager.getConnection(SQL.jdbcURL, SQL.USERNAME, SQL.PASSWORD);
             PreparedStatement loginStmt = conn.prepareStatement(loginQuery)) {

            loginStmt.setString(1, adminname);
            loginStmt.setString(2, password);
            ResultSet rs = loginStmt.executeQuery();

            if (rs.next()) {
                responseObject.addProperty("adminname", rs.getString("Adminname"));
                responseObject.addProperty("fullname", rs.getString("Fullname"));
                responseObject.addProperty("birth", rs.getString("Birth"));
                responseObject.addProperty("email", rs.getString("Email"));
                responseObject.addProperty("phone_number", rs.getString("Phone_Number"));

                return responseObject.toString();
            } else {
                return false;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }


    @GetMapping("/getBooks")
    public String getBooksForAdmin() {
        String query = "SELECT lb.ID_Book, lb.Name_Book, lb.Author, lb.Publication_Date, lb.Status, lb.Image_Endpoint, " +
                "lm.Username, lm.Borrowed_Day, lm.Return_Day " +
                "FROM Library_Books lb " +
                "LEFT JOIN Library_Management lm ON lb.ID_Book = lm.ID_Book";

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
                bookObject.addProperty("Image_Endpoint", rs.getString("Image_Endpoint"));
                String status = rs.getString("Status");
                if (status != null) {
                    bookObject.addProperty("Status", status);

                    if ("Borrowed".equals(status)) {
                        bookObject.addProperty("Username", rs.getString("Username"));
                        bookObject.addProperty("Fullname", getFullnameByUsername(rs.getString("Username")));
                        bookObject.addProperty("Borrowed_Day", rs.getString("Borrowed_Day"));
                        bookObject.addProperty("Return_Day", rs.getString("Return_Day"));
                    }
                } else {
                    bookObject.addProperty("Status", "Available");
                }

                booksArray.add(bookObject);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return booksArray.toString();
    }

    @GetMapping("/getFullName")
    private String getFullnameByUsername(@RequestParam("username") String username) {
        String fullname = "";
        String query = "SELECT Fullname FROM Users WHERE Username = ?";

        try (Connection conn = DriverManager.getConnection(SQL.jdbcURL, SQL.USERNAME, SQL.PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                fullname = rs.getString("Fullname");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return fullname;
    }


    private void addBook(MultipartFile bookImage, String idBook, String bookName, String author, String publicationDate, String baseDirectory) {

        String imagePath = Endpoint.path + idBook + ".jpg";

        String contentPath = baseDirectory;

        File bookDir = new File(baseDirectory);
        if (!bookDir.exists()) {
            bookDir.mkdirs();
        }

        try {
            File imageFile = new File(imagePath);
            bookImage.transferTo(imageFile);

            String insertQuery = "INSERT INTO Library_Books (Id_Book, Name_Book, Author, Publication_Date, Status, Content_Endpoint, Image_Endpoint) " +
                    "VALUES (?, ?, ?, ?, 'Available', ?, ?)";

            try (Connection conn = DriverManager.getConnection(SQL.jdbcURL, SQL.USERNAME, SQL.PASSWORD);
                 PreparedStatement stmt = conn.prepareStatement(insertQuery)) {

                stmt.setString(1, idBook);
                stmt.setString(2, bookName);
                stmt.setString(3, author);
                stmt.setString(4, publicationDate);
                stmt.setString(5, contentPath);
                stmt.setString(6, imagePath);

                int rowsAffected = stmt.executeUpdate();

                if (rowsAffected > 0) {
                    System.out.println("Book added successfully!");
//                    return true;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

//        return false;
    }

    @PostMapping("/addBook")
    public void processBookContent(@RequestPart MultipartFile bookImage,
                                   @RequestParam("bookName") String bookName,
                                   @RequestParam("author") String author,
                                   @RequestParam("pubDate") String publicationDate,
                                   @RequestParam("contentPart") String contentPart,
                                   @RequestParam("partNumber") int partNumber,
                                   @RequestParam("totalParts") int totalParts) {
        String bookId = generateCode("Library_Books");
        String directoryPath = Endpoint.path;

        String fileName = directoryPath + bookId + "_" + partNumber + ".txt";
        File file = new File(fileName);

        if (partNumber < totalParts) {
            try {
                if (!file.exists()) {
                    file.getParentFile().mkdirs();
                    file.createNewFile();
                }

                try (FileOutputStream fos = new FileOutputStream(file, false)) {
                    byte[] contentBytes = contentPart.getBytes(StandardCharsets.UTF_8);
                    fos.write(contentBytes);
                }

                System.out.println("Part " + partNumber + " of content saved to file: " + fileName);

            } catch (IOException e) {
                System.err.println("Error saving part " + partNumber + " of content to file: " + e.getMessage());
            }
        } else {
            String finalFileName = directoryPath + bookId + ".txt";
            File finalFile = new File(finalFileName);

            try {
                if (!finalFile.exists()) {
                    finalFile.createNewFile();
                }

                try (FileOutputStream fos = new FileOutputStream(finalFile, true)) {
                    for (int i = 1; i < totalParts; i++) {
                        String partFileName = directoryPath + bookId + "_" + i + ".txt";
                        File partFile = new File(partFileName);

                        if (partFile.exists()) {
                            byte[] partContent = readFileContent(partFile);
                            fos.write(partContent);

                            partFile.delete();
                        }
                    }

                    byte[] finalContent = contentPart.getBytes(StandardCharsets.UTF_8);
                    fos.write(finalContent);

                    addBook(bookImage, bookId, bookName, author, publicationDate, finalFileName);

                    System.out.println("Final content saved to file: " + finalFileName);
                }

            } catch (IOException e) {
                System.err.println("Error merging content to final file: " + e.getMessage());
            }
        }
    }

    private byte[] readFileContent(File file) {
        try (FileInputStream fis = new FileInputStream(file)) {
            return fis.readAllBytes();
        } catch (IOException e) {
            System.err.println("Error reading content from file: " + e.getMessage());
            return new byte[0];
        }
    }

    @PostMapping("/deleteBook")
    public boolean deleteBook(@RequestParam("idBook") String idBook) {
        String queryCheckStatus = "SELECT Status, Image_Endpoint, Content_Endpoint FROM Library_Books WHERE Id_Book = ?";
        String queryDeleteBook = "DELETE FROM Library_Books WHERE Id_Book = ?";

        try (Connection conn = DriverManager.getConnection(SQL.jdbcURL, SQL.USERNAME, SQL.PASSWORD);
             PreparedStatement stmtCheckStatus = conn.prepareStatement(queryCheckStatus);
             PreparedStatement stmtDeleteBook = conn.prepareStatement(queryDeleteBook)) {

            stmtCheckStatus.setString(1, idBook);
            ResultSet rs = stmtCheckStatus.executeQuery();

            if (rs.next()) {
                String status = rs.getString("Status");
                String imagePath = rs.getString("Image_Endpoint");
                String contentPath = rs.getString("Content_Endpoint");

                if ("Borrowed".equalsIgnoreCase(status)) {
                    System.out.println("The book is currently borrowed and cannot be deleted.");
                    return false;
                } else if ("Available".equalsIgnoreCase(status)) {
                    stmtDeleteBook.setString(1, idBook);
                    int rowsAffected = stmtDeleteBook.executeUpdate();

                    if (rowsAffected > 0) {
                        System.out.println("Book deleted successfully from the database.");

                        deleteFile(imagePath);
                        deleteFile(contentPath);

                        return true;
                    }
                }
            } else {
                System.out.println("Book with ID " + idBook + " does not exist.");
                return false;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    private void deleteFile(String filePath) {
        if (filePath != null) {
            File file = new File(filePath);
            if (file.exists()) {
                if (file.delete()) {
                    System.out.println("File " + filePath + " deleted successfully.");
                } else {
                    System.out.println("Failed to delete file: " + filePath);
                }
            } else {
                System.out.println("File " + filePath + " does not exist.");
            }
        }
    }

    @PostMapping("/editBook")
    public boolean editBook(@RequestParam("idBook") String idBook,
                            @RequestParam("newBookName") String newBookName,
                            @RequestParam("newAuthor") String newAuthor,
                            @RequestParam("newPubDate") String newPublicationDate) {
        String updateQuery = "UPDATE Library_Books SET Name_Book = ?, Author = ?, Publication_Date = ? WHERE ID_Book = ?";

        try (Connection conn = DriverManager.getConnection(SQL.jdbcURL, SQL.USERNAME, SQL.PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(updateQuery)) {

            stmt.setString(1, newBookName);
            stmt.setString(2, newAuthor);
            stmt.setString(3, newPublicationDate);
            stmt.setString(4, idBook);

            int rowsUpdated = stmt.executeUpdate();

            if (rowsUpdated > 0) {
                System.out.println("Book information updated successfully!");
                return true;
            } else {
                System.out.println("No book found with ID: " + idBook);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }


}
