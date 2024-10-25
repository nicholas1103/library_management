package org.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@SpringBootApplication
public class Main extends SpringBootServletInitializer {
//    private static MultipartFile createMultipartFileFromPath(String path) {
//        File file = new File(path);
//        try (FileInputStream input = new FileInputStream(file)) {
//            String fileName = file.getName();
//            String contentType = "image/jpeg"; // Hoặc "image/png" tùy vào định dạng hình ảnh
//
//            // Đọc nội dung của file vào mảng byte
//            byte[] content = new byte[(int) file.length()];
//            input.read(content);
//
//            // Tạo và trả về MultipartFile
//            return new MockMultipartFile(fileName, fileName, contentType, content);
//        } catch (IOException e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
//        Database_SQL databaseSql = new Database_SQL();
//        Controller_Client controller = new Controller_Client();
//        Controller_Administrator controllerAdministrator = new Controller_Administrator();
//        databaseSql.createDatabase();

//        System.out.println(databaseSql.generateCode("Library_Management"));
//        databaseSql.insertSampleLibraryBooks();
//        databaseSql.insertSampleUsers();
//        databaseSql.insertSampleAdministrators();
//        databaseSql.insertSampleLibraryManagement();

//        controller.registerUserFromJson("{\n" +
//                "    \"username\": \"phongnt\",\n" +
//                "    \"password\": \"aA@123\",\n" +
//                "    \"fullname\": \"Nguyen Tan Phong\",\n" +
//                "    \"birth\": \"2003-03-11\",\n" +
//                "    \"email\": \"0968710438p@gmail.com\",\n" +
//                "    \"phoneNumber\": \"0394486027\"\n" +
//                "}\n");

//        System.out.println(controller.loginUser("{\n" +
//                "    \"username\": \"phongnt\",\n" +
//                "    \"password\": \"aA@123\"\n" +
//                "}\n"));

//        System.out.println(controller.getAllBooksClient());

//        System.out.println(controllerAdministrator.loginAdmin("{ \"adminname\": \"admin01\", \"password\": \"adminpass1234\" }"));\

//        System.out.println(controllerAdministrator.getBooksForAdmin());
//        controller.borrowBook("{\n" +
//                "    \"username\": \"user01\",\n" +
//                "    \"ID_Book\": \"Library_Books-0001\",\n" +
//                "    \"Borrowed_Day\": \"2024-10-23\",\n" +
//                "    \"Return_Day\": \"2024-11-23\"\n" +
//                "}");

//        System.out.println(controller.getBorrowedBooksByUsername("phongnt"));

//        System.out.println(controller.returnBook("Library_Books-0002"));


        // Đường dẫn tới hình ảnh
        String imagePath = "C:\\Users\\Tan Phong\\IdeaProjects\\Library_Management_Project\\358013215_1350009645596871_7119339437647925535_n.jpg";
//
        // Tạo MultipartFile từ đường dẫn
//        MultipartFile bookImage = createMultipartFileFromPath(imagePath);

        // Thông tin sách
//        String bookName = "Sách Kỹ Năng";
//        String author = "Nguyễn Văn A";
//        String publicationDate = "2024-01-01";
//        String bookContent = "\"Đắc Nhân Tâm\" (How to Win Friends and Influence People) là cuốn sách kinh điển của Dale Carnegie, xuất bản lần đầu vào năm 1936, tập trung vào các nguyên tắc để xây dựng mối quan hệ tốt đẹp với mọi người và thuyết phục người khác. Nội dung sách xoay quanh các cách làm việc hiệu quả với mọi người, thu phục lòng người, và đạt được thành công trong công việc và cuộc sống. Sách chia làm bốn phần chính, mỗi phần đều chứa các nguyên tắc cụ thể giúp cải thiện kỹ năng giao tiếp và tương tác xã hội.\n" +
//                "\n" +
//                "Dưới đây là nội dung cơ bản của từng phần:\n" +
//                "\n" +
//                "Phần 1: Những kỹ thuật cơ bản trong việc đối nhân xử thế\n" +
//                "Không chỉ trích, oán trách hay than phiền: Để hiểu người khác, hãy đặt mình vào vị trí của họ và tránh làm tổn thương cảm xúc của họ.\n" +
//                "Thành thật khen ngợi và cảm kích người khác: Mọi người đều thích được công nhận. Khen ngợi chân thành sẽ giúp bạn xây dựng mối quan hệ tích cực.\n" +
//                "Gợi ý mong muốn của người khác: Để thuyết phục ai đó, hãy tập trung vào những điều họ muốn thay vì chỉ chú ý đến nhu cầu của bản thân.\n" +
//                "Phần 2: Sáu cách tạo thiện cảm\n" +
//                "Thành thật quan tâm đến người khác: Quan tâm thực sự đến nhu cầu và mong muốn của người khác giúp xây dựng sự gắn kết.\n" +
//                "Mỉm cười: Nụ cười tạo nên sự thân thiện và dễ gần, giúp người đối diện cảm thấy thoải mái.\n" +
//                "Nhớ tên người khác: Tên gọi là âm thanh ngọt ngào nhất đối với mỗi người, việc nhớ tên ai đó thể hiện sự tôn trọng.\n" +
//                "Lắng nghe người khác và khuyến khích họ nói về bản thân: Hãy trở thành người lắng nghe tích cực, điều này giúp bạn hiểu rõ hơn về người khác.\n" +
//                "Nói về điều mà người khác quan tâm: Hãy quan tâm đến những điều mà đối phương hứng thú để tạo ra cuộc trò chuyện có ý nghĩa.\n" +
//                "Làm cho người khác cảm thấy họ quan trọng: Hãy luôn tôn trọng và đánh giá cao giá trị của người khác.\n" +
//                "Phần 3: Làm thế nào để hướng người khác theo suy nghĩ của bạn\n" +
//                "Tránh tranh cãi: Tranh cãi hiếm khi mang lại kết quả tích cực. Thay vào đó, hãy học cách đối thoại hợp lý.\n" +
//                "Tôn trọng ý kiến người khác và không nói họ sai: Để tránh gây xung đột, hãy tìm cách hiểu ý kiến của họ trước khi đưa ra ý kiến phản bác.\n" +
//                "Thẳng thắn thừa nhận khi bạn sai: Thừa nhận lỗi lầm một cách chân thành sẽ giúp bạn nhận được sự tôn trọng từ người khác.\n" +
//                "Bắt đầu bằng thái độ thân thiện: Luôn bắt đầu cuộc trò chuyện với sự thân thiện và cởi mở.\n" +
//                "Đặt câu hỏi để khuyến khích sự đồng thuận: Hãy đặt những câu hỏi để khiến đối phương tự đưa ra những kết luận hợp lý.\n" +
//                "Để người khác cảm thấy ý tưởng của họ là của chính họ: Khi người khác cảm thấy ý tưởng là của họ, họ sẽ sẵn lòng theo đuổi nó.\n" +
//                "Cố gắng nhìn mọi việc từ góc độ của người khác: Hiểu cảm nhận và quan điểm của người khác giúp bạn giải quyết vấn đề một cách khôn ngoan.\n" +
//                "Thông cảm với mong muốn và ý kiến của người khác: Đồng cảm giúp bạn tạo dựng mối quan hệ tốt đẹp hơn.\n" +
//                "Khơi gợi động lực cao quý: Hãy khuyến khích người khác bằng cách khơi dậy những lý tưởng cao đẹp của họ.\n" +
//                "Dramatize ideas: Làm cho những ý tưởng của bạn trở nên sinh động và dễ nhớ sẽ giúp bạn thuyết phục người khác.\n" +
//                "Tạo ra sự thử thách: Đôi khi đặt ra thử thách có thể khơi dậy động lực và sự cạnh tranh trong người khác.\n" +
//                "Phần 4: Chuyển hóa con người mà không gây ra sự chống đối hay oán giận\n" +
//                "Bắt đầu bằng lời khen ngợi và cảm kích: Khi muốn sửa lỗi ai đó, hãy mở đầu bằng lời khen để tránh làm tổn thương lòng tự trọng.\n" +
//                "Góp ý sai lầm một cách gián tiếp: Đưa ra lời chỉ trích một cách khéo léo để người khác không cảm thấy bị tấn công.\n" +
//                "Thừa nhận lỗi của mình trước khi phê bình người khác: Khi bạn sẵn lòng thừa nhận lỗi lầm của mình, người khác sẽ dễ dàng chấp nhận phê bình.\n" +
//                "Gợi ý thay vì ra lệnh: Đưa ra lời khuyên hoặc gợi ý thay vì mệnh lệnh để khuyến khích sự hợp tác.\n" +
//                "Giữ thể diện cho người khác: Đừng làm ai đó mất mặt trước người khác. Hãy giúp họ bảo vệ danh dự.\n" +
//                "Khen ngợi những tiến bộ nhỏ và bất cứ tiến bộ nào: Hãy khen ngợi mọi cố gắng, dù là nhỏ nhất, để khích lệ tinh thần.\n" +
//                "Mang đến cho người khác một danh tiếng để họ phấn đấu: Hãy khuyến khích họ bằng cách cho họ thấy khả năng và tiềm năng của mình.\n" +
//                "Khuyến khích sự cải thiện: Luôn động viên người khác và thể hiện niềm tin rằng họ có thể cải thiện.\n" +
//                "Làm cho người khác hào hứng thực hiện điều bạn đề nghị: Hãy làm cho mục tiêu của bạn trở nên hấp dẫn để người khác muốn theo đuổi.\n" +
//                "Cuốn sách \"Đắc Nhân Tâm\" nhấn mạnh tầm quan trọng của việc xây dựng mối quan hệ và tương tác xã hội. Các nguyên tắc này không chỉ áp dụng trong công việc mà còn trong đời sống cá nhân, giúp bạn có được sự tôn trọng và yêu mến từ những người xung quanh."; // Nội dung sách
//
//        // Gọi phương thức addBook
//        boolean result = controllerAdministrator.addBook(bookImage, bookName, author, publicationDate, bookContent);
//
//        // Kiểm tra kết quả
//        if (result) {
//            System.out.println("Book added successfully.");
//        } else {
//            System.out.println("Failed to add book.");
//        }



        // Test dữ liệu giả lập với nội dung được cắt thành 3 phần
        String bookId = "Dac nhan tam";
        String contentPart1 = "\"Đắc Nhân Tâm\" (How to Win Friends and Influence People) là cuốn sách kinh điển của Dale Carnegie, xuất bản lần đầu vào năm 1936, tập trung vào các nguyên tắc để xây dựng mối quan hệ tốt đẹp với mọi người và thuyết phục người khác. Nội dung sách xoay quanh các cách làm việc hiệu quả với mọi người, thu phục lòng người, và đạt được thành công trong công việc và cuộc sống. Sách chia làm bốn phần chính, mỗi phần đều chứa các nguyên tắc cụ thể giúp cải thiện kỹ năng giao tiếp và tương tác xã hội.\n" +
                "\n" +
                "Dưới đây là nội dung cơ bản của từng phần:\n" +
                "\n" +
                "Phần 1: Những kỹ thuật cơ bản trong việc đối nhân xử thế\n" +
                "Không chỉ trích, oán trách hay than phiền: Để hiểu người khác, hãy đặt mình vào vị trí của họ và tránh làm tổn thương cảm xúc của họ.\n" +
                "Thành thật khen ngợi và cảm kích người khác: Mọi người đều thích được công nhận. Khen ngợi chân thành sẽ giúp bạn xây dựng mối quan hệ tích cực.\n" +
                "Gợi ý mong muốn của người khác: Để thuyết phục ai đó, hãy tập trung vào những điều họ muốn thay vì chỉ chú ý đến nhu cầu của bản thân.\n" +
                "Phần 2: Sáu cách tạo thiện cảm\n" +
                "Thành thật quan tâm đến người khác: Quan tâm thực sự đến nhu cầu và mong muốn của người khác giúp xây dựng sự gắn kết.\n" +
                "Mỉm cười: Nụ cười tạo nên sự thân thiện và dễ gần, giúp người đối diện cảm thấy thoải mái.\n" +
                "Nhớ tên người khác: Tên gọi là âm thanh ngọt ngào nhất đối với mỗi người, việc nhớ tên ai đó thể hiện sự tôn trọng.\n" +
                "Lắng nghe người khác và khuyến khích họ nói về bản thân: Hãy trở thành người lắng nghe tích cực, điều này giúp bạn hiểu rõ hơn về người khác.\n" +
                "Nói về điều mà người khác quan tâm: Hãy quan tâm đến những điều mà đối phương hứng thú để tạo ra cuộc trò chuyện có ý nghĩa.\n" +
                "Làm cho người khác cảm thấy họ quan trọng: Hãy luôn tôn trọng và đánh giá cao giá trị của người khác.\n";
        String contentPart2 = "Phần 3: Làm thế nào để hướng người khác theo suy nghĩ của bạn\n" +
                "Tránh tranh cãi: Tranh cãi hiếm khi mang lại kết quả tích cực. Thay vào đó, hãy học cách đối thoại hợp lý.\n" +
                "Tôn trọng ý kiến người khác và không nói họ sai: Để tránh gây xung đột, hãy tìm cách hiểu ý kiến của họ trước khi đưa ra ý kiến phản bác.\n" +
                "Thẳng thắn thừa nhận khi bạn sai: Thừa nhận lỗi lầm một cách chân thành sẽ giúp bạn nhận được sự tôn trọng từ người khác.\n" +
                "Bắt đầu bằng thái độ thân thiện: Luôn bắt đầu cuộc trò chuyện với sự thân thiện và cởi mở.\n" +
                "Đặt câu hỏi để khuyến khích sự đồng thuận: Hãy đặt những câu hỏi để khiến đối phương tự đưa ra những kết luận hợp lý.\n" +
                "Để người khác cảm thấy ý tưởng của họ là của chính họ: Khi người khác cảm thấy ý tưởng là của họ, họ sẽ sẵn lòng theo đuổi nó.\n" +
                "Cố gắng nhìn mọi việc từ góc độ của người khác: Hiểu cảm nhận và quan điểm của người khác giúp bạn giải quyết vấn đề một cách khôn ngoan.\n" +
                "Thông cảm với mong muốn và ý kiến của người khác: Đồng cảm giúp bạn tạo dựng mối quan hệ tốt đẹp hơn.\n" +
                "Khơi gợi động lực cao quý: Hãy khuyến khích người khác bằng cách khơi dậy những lý tưởng cao đẹp của họ.\n" +
                "Dramatize ideas: Làm cho những ý tưởng của bạn trở nên sinh động và dễ nhớ sẽ giúp bạn thuyết phục người khác.\n" +
                "Tạo ra sự thử thách: Đôi khi đặt ra thử thách có thể khơi dậy động lực và sự cạnh tranh trong người khác.\n";
        String contentPart3 = "Phần 4: Chuyển hóa con người mà không gây ra sự chống đối hay oán giận\n" +
                "Bắt đầu bằng lời khen ngợi và cảm kích: Khi muốn sửa lỗi ai đó, hãy mở đầu bằng lời khen để tránh làm tổn thương lòng tự trọng.\n" +
                "Góp ý sai lầm một cách gián tiếp: Đưa ra lời chỉ trích một cách khéo léo để người khác không cảm thấy bị tấn công.\n" +
                "Thừa nhận lỗi của mình trước khi phê bình người khác: Khi bạn sẵn lòng thừa nhận lỗi lầm của mình, người khác sẽ dễ dàng chấp nhận phê bình.\n" +
                "Gợi ý thay vì ra lệnh: Đưa ra lời khuyên hoặc gợi ý thay vì mệnh lệnh để khuyến khích sự hợp tác.\n" +
                "Giữ thể diện cho người khác: Đừng làm ai đó mất mặt trước người khác. Hãy giúp họ bảo vệ danh dự.\n" +
                "Khen ngợi những tiến bộ nhỏ và bất cứ tiến bộ nào: Hãy khen ngợi mọi cố gắng, dù là nhỏ nhất, để khích lệ tinh thần.\n" +
                "Mang đến cho người khác một danh tiếng để họ phấn đấu: Hãy khuyến khích họ bằng cách cho họ thấy khả năng và tiềm năng của mình.\n" +
                "Khuyến khích sự cải thiện: Luôn động viên người khác và thể hiện niềm tin rằng họ có thể cải thiện.\n" +
                "Làm cho người khác hào hứng thực hiện điều bạn đề nghị: Hãy làm cho mục tiêu của bạn trở nên hấp dẫn để người khác muốn theo đuổi.\n" +
                "Cuốn sách \"Đắc Nhân Tâm\" nhấn mạnh tầm quan trọng của việc xây dựng mối quan hệ và tương tác xã hội. Các nguyên tắc này không chỉ áp dụng trong công việc mà còn trong đời sống cá nhân, giúp bạn có được sự tôn trọng và yêu mến từ những người xung quanh.";

        // Thêm từng phần của nội dung
//        controllerAdministrator.processBookContent(bookImage, "Dac nhan tam", "Nguyen Tan Phong", "2003-03-11", contentPart1, 1, 3);
//        controllerAdministrator.processBookContent(bookImage, "Dac nhan tam", "Nguyen Tan Phong", "2003-03-11", contentPart2, 2, 3);
//        controllerAdministrator.processBookContent(bookImage, "Dac nhan tam", "Nguyen Tan Phong", "2003-03-11", contentPart3, 3, 3);


//        System.out.println(controllerAdministrator.deleteBook("Library_Books-0004"));
////        System.out.println(controller.readBook("Library_Books-0005"));
//        System.out.println(controllerAdministrator.editBook("Library_Books-0005", "Doi ngan dung ngu dai", "Nguyen Hoang Phuc", "2003-02-14"));
    }
}