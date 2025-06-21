import java.io.IOException;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 图书馆核心服务类
 */
public class LibraryService {
    private List<Book> books;
    private List<BorrowRecord> records;
    private FileDataService fileDataService;

    public LibraryService() {
        this.fileDataService = new FileDataService();
        try {
            this.books = fileDataService.loadBooks();
            this.records = fileDataService.loadRecords();
        } catch (IOException e) {
            System.err.println("初始化数据失败: " + e.getMessage());
            this.books = new ArrayList<>();
            this.records = new ArrayList<>();
        }
    }

    /**
     * 添加新书
     */
    public void addBook(String title, String isbn, String author) throws Exception {
        if (findBookByIsbn(isbn).isPresent()) {
            throw new Exception("ISBN已存在: " + isbn);
        }
        books.add(new Book(title, isbn, author, true));
        saveAllData();
    }

    /**
     * 借书
     */
    public void borrowBook(String studentId, String isbn) throws Exception {
        Book book = findBookByIsbn(isbn)
                .orElseThrow(() -> new Exception("图书不存在"));

        if (!book.isAvailable()) {
            throw new Exception("图书已被借出");
        }

        book.setAvailable(false);
        records.add(new BorrowRecord(studentId, isbn, LocalDate.now()));
        saveAllData();
    }

    /**
     * 还书
     */
    public void returnBook(String studentId, String isbn) throws Exception {
        BorrowRecord record = findBorrowRecord(studentId, isbn)
                .orElseThrow(() -> new Exception("未找到借阅记录"));

        if (record.getReturnDate() != null) {
            throw new Exception("该书已归还");
        }

        record.setReturnDate(LocalDate.now());
        findBookByIsbn(isbn).ifPresent(book -> book.setAvailable(true));
        saveAllData();
    }

    /**
     * 搜索图书
     */
    public List<Book> searchBooks(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return new ArrayList<>(books);
        }

        String lowerKeyword = keyword.toLowerCase();
        return books.stream()
                .filter(book ->
                        book.getTitle().toLowerCase().contains(lowerKeyword) ||
                                book.getAuthor().toLowerCase().contains(lowerKeyword))
                .collect(Collectors.toList());
    }

    /**
     * 获取逾期记录
     */
    public List<BorrowRecord> getOverdueRecords() {
        return records.stream()
                .filter(BorrowRecord::isOverdue)
                .collect(Collectors.toList());
    }

    // 私有辅助方法
    private Optional<Book> findBookByIsbn(String isbn) {
        return books.stream()
                .filter(book -> book.getIsbn().equals(isbn))
                .findFirst();
    }

    private Optional<BorrowRecord> findBorrowRecord(String studentId, String isbn) {
        return records.stream()
                .filter(r -> r.getStudentId().equals(studentId) && r.getIsbn().equals(isbn))
                .filter(r -> r.getReturnDate() == null)
                .findFirst();
    }

    private void saveAllData() throws IOException {
        fileDataService.saveBooks(books);
        fileDataService.saveRecords(records);
    }
}