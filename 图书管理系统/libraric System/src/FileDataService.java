import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * 文件数据服务 - 负责数据的持久化存储
 */
public class FileDataService {
    // 文件路径常量
    private static final String BOOKS_FILE = "图书信息.txt";
    private static final String RECORDS_FILE = "借阅记录.txt";

    // 日期格式化器
    private static final DateTimeFormatter DATE_FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd");

    /**
     * 保存图书信息到文件
     */
    public void saveBooks(List<Book> books) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(BOOKS_FILE))) {
            for (Book book : books) {
                writer.println(book.toString());
            }
        }
    }

    /**
     * 加载图书信息
     */
    public List<Book> loadBooks() throws IOException {
        List<Book> books = new ArrayList<>();
        File file = new File(BOOKS_FILE);

        if (!file.exists()) {
            return books;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    books.add(new Book(
                            parts[0],
                            parts[1],
                            parts[2],
                            parts[3].equals("在库")
                    ));
                }
            }
        }
        return books;
    }

    /**
     * 保存借阅记录
     */
    public void saveRecords(List<BorrowRecord> records) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(RECORDS_FILE))) {
            for (BorrowRecord record : records) {
                writer.println(record.toString());
            }
        }
    }

    /**
     * 加载借阅记录
     */
    public List<BorrowRecord> loadRecords() throws IOException {
        List<BorrowRecord> records = new ArrayList<>();
        File file = new File(RECORDS_FILE);

        if (!file.exists()) {
            return records;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 3) {
                    LocalDate borrowDate = LocalDate.parse(parts[2], DATE_FORMATTER);
                    BorrowRecord record = new BorrowRecord(parts[0], parts[1], borrowDate);

                    if (parts.length == 4 && !parts[3].equals("未归还")) {
                        record.setReturnDate(LocalDate.parse(parts[3], DATE_FORMATTER));
                    }

                    records.add(record);
                }
            }
        }
        return records;
    }
}