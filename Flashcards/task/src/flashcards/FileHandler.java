package flashcards;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileHandler {
    FileWriter writer = null;
    String fileName;

    FileHandler(String fileName) {
        this.fileName = fileName;
    }

    public void saveFlashcards(List<Flashcard> flashcards) throws IOException {
        writer = new FileWriter(fileName);
        StringBuilder builder = new StringBuilder();
        for (Flashcard flashcard : flashcards) {
            builder.append(flashcard.term + "\r\n" + flashcard.definition + "\r\n" + flashcard.mistakes + "\r\n");
        }
        writer.write(builder.toString());
        writer.close();
    }

    public List<Flashcard> loadFlashcards() throws FileNotFoundException {
        Scanner scanner = new Scanner(new File(fileName));
        StringBuilder builder = new StringBuilder();
        while (scanner.hasNext()) {
            builder.append(scanner.nextLine());
            builder.append("\r\n");
        }
        scanner.close();

        String[] str = builder.toString().split("\r\n");
        List<Flashcard> flashcards = new ArrayList<>();
        for (int i = 0; i < str.length - 2; i += 3) {
            //System.out.println(str[i] + " " +str[i + 1] + " " + Integer.parseInt(str[2]));
            Flashcard flashcard = new Flashcard(str[i], str[i + 1], Integer.parseInt(str[2]));
            flashcards.add(flashcard);
        }
        return flashcards;
    }
}
