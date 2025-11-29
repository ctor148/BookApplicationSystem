import java.util.ArrayList;
import java.util.List;

public class AudioBook extends Book {

    private double length;
    private static final List<AudioBook> lastThreeAudio = new ArrayList<>(); //tracks last 3 audio books
    //inheritance
    public AudioBook(String title, String author, String genre, double length) {
        super(title, author, genre, 0); //cost starts at 0
        this.length = length;
        trackLastThree(this);
    }

    public void setLength(double length) { this.length = length; }
    public double getLength() { return length; }

    @Override //$5 per minute
    public double getCost() { return length * 5; }

    //enhanced for loop to calculate average of only audiobooks
    public static double getAveLength(List<Book> books) {
        double total = 0;
        int count = 0;
        for (Book b : books) {
            if (b instanceof AudioBook) {
                total += ((AudioBook)b).getLength();
                count++;
            }
        }
        return count == 0 ? 0 : total / count;
    }

    //tracks last 3 audiobooks and replaces the last if another is added
    private static void trackLastThree(AudioBook book) {
        lastThreeAudio.add(book);
        if (lastThreeAudio.size() > 3) lastThreeAudio.remove(0);
    }

    //enhanced for loop prints last 3 books
    public static String displayLastThreeAudio() {
        String output = "\n--- Last 3 Audio Books ---";
        for (AudioBook ab : lastThreeAudio) {
            output += "\n" + ab.toString();
        }
        return output;
    }

    @Override
    public String toString() {
        return "Audiobook, Title: " + title + ", Author: " + author + ", Genre: " + genre + ", Cost: $" + getCost() + ", Minutes: " + length;
    }
}