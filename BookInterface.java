import java.util.List;

public interface BookInterface {

    //default method for last 6 books
    default void lastSixBooks(List<Book> books) {
        if (books.isEmpty()) {
            System.out.println("Book list is empty.");
            return;
        }
        //prints last 6 books
        int start = Math.max(0, books.size() - 6);
        System.out.println("\n--- Last 6 Books ---");
        for (int i = start; i < books.size(); i++) {
            System.out.println(books.get(i));
        }
    }

    int numBookPerGenre(List<Book> books, String genre);

    double getTotalCost(List<Book> books);
}