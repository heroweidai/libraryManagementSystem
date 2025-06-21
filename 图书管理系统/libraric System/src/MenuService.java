import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

/**
 * 用户界面服务
 */
public class MenuService {
    private Scanner scanner;
    private LibraryService libraryService;

    public MenuService() {
        this.scanner = new Scanner(System.in);
        this.libraryService = new LibraryService();
    }

    public void showMainMenu() {
        while (true) {
            System.out.println("\n==== 图书管理系统 ====");
            System.out.println("1. 图书管理");
            System.out.println("2. 借阅管理");
            System.out.println("3. 查询图书");
            System.out.println("4. 查看逾期记录");
            System.out.println("0. 退出");
            System.out.print("请选择: ");

            try {
                int choice = Integer.parseInt(scanner.nextLine());

                switch (choice) {
                    case 1: showBookManagementMenu(); break;
                    case 2: showBorrowManagementMenu(); break;
                    case 3: searchBooks(); break;
                    case 4: showOverdueRecords(); break;
                    case 0:
                        System.out.println("谢谢使用！");
                        return;
                    default:
                        System.out.println("无效选择，请重新输入");
                }
            } catch (NumberFormatException e) {
                System.out.println("请输入有效数字");
            } catch (Exception e) {
                System.out.println("操作失败: " + e.getMessage());
            }
        }
    }

    private void showBookManagementMenu() throws Exception {
        System.out.println("\n=== 图书管理 ===");
        System.out.println("1. 添加图书");
        System.out.println("2. 查看所有图书");
        System.out.println("0. 返回");
        System.out.print("请选择: ");

        int choice = Integer.parseInt(scanner.nextLine());

        switch (choice) {
            case 1: addBook(); break;
            case 2: showAllBooks(); break;
            case 0: return;
            default: System.out.println("无效选择");
        }
    }

    private void showBorrowManagementMenu() throws Exception {
        System.out.println("\n=== 借阅管理 ===");
        System.out.println("1. 借书");
        System.out.println("2. 还书");
        System.out.println("0. 返回");
        System.out.print("请选择: ");

        int choice = Integer.parseInt(scanner.nextLine());

        switch (choice) {
            case 1: borrowBook(); break;
            case 2: returnBook(); break;
            case 0: return;
            default: System.out.println("无效选择");
        }
    }

    private void addBook() throws Exception {
        do {
            System.out.println("\n--- 添加图书 ---");
            System.out.print("书名: ");
            String title = scanner.nextLine();
            System.out.print("ISBN: ");
            String isbn = scanner.nextLine();
            System.out.print("作者: ");
            String author = scanner.nextLine();

            libraryService.addBook(title, isbn, author);
            System.out.println("添加成功！");

            System.out.print("是否继续添加图书？(Y/N): ");
        } while (scanner.nextLine().equalsIgnoreCase("Y"));
    }

    private void showAllBooks() {
        List<Book> books = libraryService.searchBooks("");
        if (books.isEmpty()) {
            System.out.println("没有图书记录");
            return;
        }

        System.out.println("\n--- 所有图书 ---");
        System.out.println("书名\tISBN\t作者\t状态");
        books.forEach(book ->
                System.out.printf("%s\t%s\t%s\t%s%n",
                        book.getTitle(),
                        book.getIsbn(),
                        book.getAuthor(),
                        book.isAvailable() ? "在库" : "借出")
        );
    }

    private void borrowBook() throws Exception {
        do {
            System.out.println("\n--- 借书 ---");
            System.out.print("学生卡号: ");
            String studentId = scanner.nextLine();
            System.out.print("图书ISBN: ");
            String isbn = scanner.nextLine();

            libraryService.borrowBook(studentId, isbn);
            System.out.println("借书成功！");

            System.out.print("是否继续借阅其他图书？(Y/N): ");
        } while (scanner.nextLine().equalsIgnoreCase("Y"));
    }

    private void returnBook() throws Exception {
        System.out.println("\n--- 还书 ---");
        System.out.print("学生卡号: ");
        String studentId = scanner.nextLine();
        System.out.print("图书ISBN: ");
        String isbn = scanner.nextLine();

        libraryService.returnBook(studentId, isbn);
        System.out.println("还书成功！");
    }

    private void searchBooks() {
        do {
            System.out.println("\n--- 搜索图书 ---");
            System.out.print("输入关键词(书名/作者): ");
            String keyword = scanner.nextLine();

            List<Book> results = libraryService.searchBooks(keyword);
            if (results.isEmpty()) {
                System.out.println("没有找到匹配的图书");
                return;
            }

            System.out.println("\n搜索结果:");
            results.forEach(book ->
                    System.out.printf("%s - %s (%s)%n",
                            book.getTitle(),
                            book.getAuthor(),
                            book.isAvailable() ? "可借" : "已借出")
            );
            System.out.print("是否继续搜索其他图书？(Y/N): ");
        }while(scanner.nextLine().equalsIgnoreCase("Y"));

    }

    private void showOverdueRecords() {
        List<BorrowRecord> overdueRecords = libraryService.getOverdueRecords();
        if (overdueRecords.isEmpty()) {
            System.out.println("没有逾期记录");
            return;
        }

        System.out.println("\n--- 逾期记录 ---");
        System.out.println("学生卡号\tISBN\t借阅日期\t逾期天数");
        overdueRecords.forEach(record ->
                System.out.printf("%s\t%s\t%s\t%d天%n",
                        record.getStudentId(),
                        record.getIsbn(),
                        record.getBorrowDate(),
                        record.getBorrowDays())
        );
    }
}