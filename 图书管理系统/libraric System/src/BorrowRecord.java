import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * 借阅记录实体类
 */
public class BorrowRecord {
    private String studentId;   // 学生卡号
    private String isbn;        // 图书ISBN
    private LocalDate borrowDate; // 借阅日期
    private LocalDate returnDate; // 归还日期

    // 日期格式化器
    private static final DateTimeFormatter DATE_FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public BorrowRecord(String studentId, String isbn, LocalDate borrowDate) {
        this.studentId = studentId;
        this.isbn = isbn;
        this.borrowDate = borrowDate;
        this.returnDate = null; // 初始时未归还
    }

    // Getter和Setter
    public String getStudentId() { return studentId; }
    public String getIsbn() { return isbn; }
    public LocalDate getBorrowDate() { return borrowDate; }
    public LocalDate getReturnDate() { return returnDate; }
    public void setReturnDate(LocalDate returnDate) { this.returnDate = returnDate; }

    // 计算借阅天数
    public long getBorrowDays() {
        LocalDate endDate = returnDate != null ? returnDate : LocalDate.now();
        return java.time.temporal.ChronoUnit.DAYS.between(borrowDate, endDate);
    }

    // 是否逾期
    public boolean isOverdue() {
        return returnDate == null && getBorrowDays() > 30;
    }

    @Override
    public String toString() {
        return studentId + "," + isbn + "," +
                borrowDate.format(DATE_FORMATTER) + "," +
                (returnDate != null ? returnDate.format(DATE_FORMATTER) : "未归还");
    }
}