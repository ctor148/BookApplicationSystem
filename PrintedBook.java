import java.util.ArrayList;
import java.util.List;

public class PrintedBook extends Book {

    private int pages;
    private static final List<PrintedBook> lastThreePrinted = new ArrayList<>(); //tracks last 3 printed books
    //inheritance
    public PrintedBook(String title, String author, String genre, int pages) {
        super(title, author, genre, 0); // cost starts at 0
        this.pages = pages;
        trackLastThree(this);
    }

    public void setPages(int pages) { this.pages = pages; }
    public int getPages() { return pages; }

    @Override //$10 per page
    public double getCost() { return pages * 10; }

    //enhanced for loop to calculate average of only printed books
    public static double getAvePages(List<Book> books) {
        int total = 0, count = 0;
        for (Book b : books) {
            if (b instanceof PrintedBook) {
                total += ((PrintedBook)b).getPages();
                count++;
            }
        }
        return count == 0 ? 0 : (double) total / count;
    }

    //tracks last 3 printed books and replaces the last if another is added
    private static void trackLastThree(PrintedBook book) {
        lastThreePrinted.add(book);
        if (lastThreePrinted.size() > 3) lastThreePrinted.remove(0);
    }

    //enhanced for loop prints last 3 books
    public static String displayLastThreePrinted() {
        String output = "\n--- Last 3 Printed Books ---";
        for (PrintedBook pb : lastThreePrinted) {
            output += "\n" + pb.toString();
        }
        return output;
    }

    @Override
    public String toString() {
        return "Printed Book, Title: " + title + ", Author: " + author + ", Genre: " + genre + ", Cost: $" + getCost() + ", Pages: " + pages;
    }
}