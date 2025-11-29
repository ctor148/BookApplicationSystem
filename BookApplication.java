import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BookApplication {

    static List<Book> books = new ArrayList<>();
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        boolean running = true;

        while (running) {
            System.out.print(
                    "\n--------------------------\n" +
                            "Book Application Menu\n" +
                            "--------------------------\n" +
                            "1. Add book (Printed or Audio)\n" +
                            "2. Show last 6 books\n" +
                            "3. Show last 3 printed books\n" +
                            "4. Show last 3 audio books\n" +
                            "5. Calculate total cost of all books\n" +
                            "6. Calculate total cost of printed books\n" +
                            "7. Calculate total cost of audio books\n" +
                            "8. Calculate average pages of all printed \n" +
                            "9. Calculate average length of all audiobooks\n" +
                            "10. Show number of books in each genre\n" +
                            "11. Import book data\n" +
                            "12. Export book data\n" +
                            "13. Exit\n" +
                            "Enter a number: ");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1: addBook(); break;
                case 2: showLastSixBooks(); break;
                case 3: System.out.println(PrintedBook.displayLastThreePrinted()); break;
                case 4: System.out.println(AudioBook.displayLastThreeAudio()); break;
                case 5: showTotalCostAll(); break;
                case 6: showTotalCostPrinted(); break;
                case 7: showTotalCostAudio(); break;
                case 8: showAveragePages(); break;
                case 9: showAverageLength(); break;
                case 10: showBooksPerGenre(); break;
                case 11: importBooks(); break;
                case 12: exportBooks(); break;
                case 13: running = false; break;
                default: System.out.println("Invalid number."); break;
            }
        }

        System.out.println("Program exited.");
        scanner.close();
    }

    private static void addBook() {
        scanner.nextLine();
        System.out.print("Enter type (printed/audio): ");
        String type = scanner.nextLine().trim().toLowerCase();

        System.out.print("Title: "); String title = scanner.nextLine();
        System.out.print("Author: "); String author = scanner.nextLine();
        System.out.print("Genre: "); String genre = scanner.nextLine();

        if (type.equals("printed")) {
            System.out.print("Pages: ");
            int pages = scanner.nextInt();
            PrintedBook pb = new PrintedBook(title, author, genre, pages);
            books.add(pb);
            System.out.println("Printed book added.");
        } else if (type.equals("audio")) {
            System.out.print("Length (minutes): ");
            double length = scanner.nextDouble();
            AudioBook ab = new AudioBook(title, author, genre, length);
            books.add(ab);
            System.out.println("Audio book added.");
        } else {
            System.out.println("Invalid type! Book not added.");
        }
    }

    private static void showLastSixBooks() {
        if (books.isEmpty()) {
            System.out.println("No books stored.");
            return;
        }

        books.get(0).lastSixBooks(books);
    }

    private static void showTotalCostAll() {
        if (books.isEmpty()) {
            System.out.println("Total cost: $0");
            return;
        }
        double total = 0;
        for (Book b : books) total += b.getCost();
        System.out.println("Total cost of all books: $" + total);
    }

    private static void showTotalCostPrinted() {
        double total = 0;
        for (Book b : books) {
            if (b instanceof PrintedBook) total += b.getCost();
        }
        System.out.println("Total cost of printed books: $" + total);
    }

    private static void showTotalCostAudio() {
        double total = 0;
        for (Book b : books) {
            if (b instanceof AudioBook) total += b.getCost();
        }
        System.out.println("Total cost of audio books: $" + total);
    }

    private static void showAveragePages() {
        System.out.println("Average pages of all printed books: " + PrintedBook.getAvePages(books));
    }

    private static void showAverageLength() {
        System.out.println("Average length of all audiobooks: " + AudioBook.getAveLength(books));
    }

    private static void showBooksPerGenre() {
        if (books.isEmpty()) {
            System.out.println("No books stored.");
            return;
        }
        System.out.println("\n--------------------------\n" +
                "Number of Books per Genre\n" +
                "--------------------------\n");
        List<String> countedGenres = new ArrayList<>();
        for (Book b : books) {
            if (!countedGenres.contains(b.getGenre())) {
                int count = b.numBookPerGenre(books, b.getGenre());
                System.out.println(b.getGenre() + ": " + count);
                countedGenres.add(b.getGenre());
            }
        }
    }

    private static void importBooks() {
        scanner.nextLine(); // consume newline
        System.out.print("Enter file name to import: ");
        String fileName = scanner.nextLine();

        try (Scanner file = new Scanner(new File(fileName))) {
            while (file.hasNextLine()) {
                String line = file.nextLine().trim();
                if (line.startsWith("Printed Book")) {

                    String[] parts = line.split(", ");
                    String title = parts[1].split(": ")[1].trim();
                    String author = parts[2].split(": ")[1].trim();
                    String genre = parts[3].split(": ")[1].trim();

                    double cost = Double.parseDouble(parts[4].split(": ")[1].trim().replace("$", ""));
                    int pages = Integer.parseInt(parts[5].split(": ")[1].trim());

                    PrintedBook pb = new PrintedBook(title, author, genre, pages);
                    books.add(pb);
                } else if (line.startsWith("Audiobook")) {

                    String[] parts = line.split(", ");
                    String title = parts[1].split(": ")[1].trim();
                    String author = parts[2].split(": ")[1].trim();
                    String genre = parts[3].split(": ")[1].trim();

                    double cost = Double.parseDouble(parts[4].split(": ")[1].trim().replace("$", ""));
                    double length = Double.parseDouble(parts[5].split(": ")[1].trim());

                    AudioBook ab = new AudioBook(title, author, genre, length);
                    books.add(ab);
                }
            }
            System.out.println("Books imported from " + fileName);
        } catch (Exception e) {
            System.out.println("Error importing file: " + e.getMessage());
        }
    }

    private static void exportBooks() {
        scanner.nextLine();
        System.out.print("Enter filename to export to: ");
        String filename = scanner.nextLine();

        try (PrintWriter pw = new PrintWriter(filename)) {
            for (Book b : books) {
                pw.println(b);
            }
            System.out.println("Exported to " + filename);
        } catch (Exception e) {
            System.out.println("Error exporting file: " + e.getMessage());
        }
    }
}