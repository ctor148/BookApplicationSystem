import java.util.List;
//abstraction
public abstract class Book implements BookInterface {
    //encapsulation
    protected String title;
    protected String author;
    protected String genre;
    protected double cost;
    // constructor
    public Book (String title, String author, String genre, double cost) {
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.cost = cost;
    }
    //getter methods
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public String getGenre() { return genre; }
    public double getCost() { return cost; } //getCost is used by both AudioBook and PrintedBook (polymorphism)

    @Override //counts the amount of books in a genre
    public int numBookPerGenre(List<Book> books, String genre) {
        int count = 0;
        for (Book b : books) {
            if (b.getGenre().equalsIgnoreCase(genre)) count++;
        }
        return count;
    }

    @Override //adds prices of all books
    public double getTotalCost(List<Book> books) {
        double total = 0;
        for (Book b : books) {
            total += b.getCost();
        }
        return total;
    }
}