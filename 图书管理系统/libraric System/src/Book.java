import java.io.Serializable;
import java.util.Objects;

/**
 * 图书实体类
 */
public class Book implements Serializable {
    private String title;      // 书名
    private String isbn;       // ISBN号
    private String author;     // 作者
    private boolean available; // 是否可借

    // 构造方法
    public Book(String title, String isbn, String author, boolean available) {
        this.title = title;
        this.isbn = isbn;
        this.author = author;
        this.available = available;
    }

    // Getter和Setter方法
    public String getTitle() { return title; }
    public String getIsbn() { return isbn; }
    public String getAuthor() { return author; }
    public boolean isAvailable() { return available; }
    public void setAvailable(boolean available) { this.available = available; }

    @Override
    public String toString() {
        return title + "," + isbn + "," + author + "," + (available ? "在库" : "借出");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return isbn.equals(book.isbn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(isbn);
    }
}