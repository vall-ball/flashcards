package flashcards;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Menu {

    final Scanner scanner = new Scanner(System.in);
    Set<String> cards = new HashSet<>();
    Set<String> definitions = new HashSet<>();
    List<Flashcard> flashcards = new ArrayList<>();
    List<Flashcard> flashcardsFromFile = new ArrayList<>();
    List<String> log = new ArrayList<>();
    String fileImportName = null;
    String fileExportName = null;

    public void menu() throws IOException {
        if (fileImportName != null) {
            FileHandler fileHandler = new FileHandler(fileImportName);
            flashcards = fileHandler.loadFlashcards();
            for (Flashcard f : flashcards) {
                cards.add(f.term);
                definitions.add(f.definition);
            }
            System.out.println(flashcards.size() + " cards have been loaded.");
            log.add(flashcards.size() + " cards have been loaded.");
        }
        while (true) {
            System.out.println("Input the action (add, remove, import, export, ask, exit, log, hardest card, reset stats):");
            log.add("Input the action (add, remove, import, export, ask, exit, log, hardest card, reset stats):");
            String answer = scanner.nextLine();
            log.add(answer);

            switch (answer) {
                case "add":
                    add();
                    break;
                case "remove":
                    remove();
                    break;
                case "import":
                    importFile();
                    break;
                case "export":
                    exportFile();
                    break;
                case "ask":
                    ask();
                    break;
                case "log":
                    writeLog();
                    break;
                case "hardest card":
                    hardestCards();
                    break;
                case "reset stats":
                    resetStats();
                    break;
                case "exit":
                    System.out.println("Bye bye!");
                    log.add("Bye bye!");
                    if (fileExportName != null) {
                        FileHandler fileHandler = new FileHandler(fileExportName);
                        fileHandler.saveFlashcards(flashcards);
                        System.out.println(flashcards.size() + " cards have been saved.");
                        log.add(flashcards.size() + " cards have been saved.");
                    }
                    return;
            }
        }
    }

    public void add() {
        System.out.println("The card:");
        log.add("The card:");
        String card = scanner.nextLine();
        log.add(card);
        if (cards.contains(card)) {
            System.out.println("The card \"" + card + "\" already exists.");
            log.add("The card \"" + card + "\" already exists.");
            return;
        }
        System.out.println("The definition of the card:");
        log.add("The definition of the card:");
        String definition = scanner.nextLine();
        log.add(definition);
        if (definitions.contains(definition)) {
            System.out.println("The definition \"" + definition + "\" already exists.");
            log.add("The definition \"" + definition + "\" already exists.");
            return;
        }
            Flashcard flashcard = new Flashcard(card, definition);
            flashcards.add(flashcard);
            cards.add(card);
            definitions.add(definition);
            System.out.println("The pair (\"" + card + "\":\"" + definition + "\") has been added.");
            log.add("The pair (\"" + card + "\":\"" + definition + "\") has been added.");
    }

    public void remove() {
        System.out.println("The card:");
        log.add("The card:");
        String card = scanner.nextLine();
        log.add(card);
        if (cards.contains(card)) {
            Flashcard flashcard = findFlashkardByCard(card);
            cards.remove(card);
            definitions.remove(flashcard.definition);
            flashcards.remove(flashcard);
            if (isFlashkardFromFileByCard(card)) {
                flashcardsFromFile.remove(findFlashkardFromFileByCard(card));
            }
            System.out.println("The card has been removed.");
            log.add("The card has been removed.");
        } else {
            System.out.println("Can't remove \"" + card + "\": there is no such card.");
            log.add("Can't remove \"" + card + "\": there is no such card.");
        }
    }

    private Flashcard findFlashkardByCard(String card) {
        for (Flashcard flashcard : flashcards) {
            if (flashcard.term.equals(card)) {
                return flashcard;
            }
        }
        return null;
    }

    private boolean isFlashkardFromFileByCard(String card) {
        for (Flashcard flashcard : flashcardsFromFile) {
            if (flashcard.term.equals(card)) {
                return true;
            }
        }
        return false;
    }
    private Flashcard findFlashkardFromFileByCard(String card) {
        for (Flashcard flashcard : flashcardsFromFile) {
            if (flashcard.term.equals(card)) {
                return flashcard;
            }
        }
        return null;
    }

    private Flashcard findFlashkardByDefinition(String definition) {
        for (Flashcard flashcard : flashcards) {
            if (flashcard.definition.equals(definition)) {
                return flashcard;
            }
        }
        return null;
    }

    public void importFile() {
        System.out.println("File name:");
        log.add("File name:");
        String fileName = scanner.nextLine();
        log.add(fileName);
        try {
            FileHandler handler = new FileHandler(fileName);
            flashcardsFromFile = handler.loadFlashcards();
            System.out.println(flashcardsFromFile.size() + " cards have been loaded.");
            log.add(flashcardsFromFile.size() + " cards have been loaded.");
            addFromFile();
        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
            log.add("File not found.");
        }
    }

    private void addFromFile() {
        ListIterator<Flashcard> iterator = flashcardsFromFile.listIterator();
        while (iterator.hasNext()){
            Flashcard flashcard = iterator.next();
            if (cards.contains(flashcard.term)) {
                Flashcard findcard = findFlashkardByCard(flashcard.term);
                flashcards.remove(findcard);
                cards.remove(findcard.term);
                definitions.remove(findcard.definition);
                flashcards.add(flashcard);
                cards.add(flashcard.term);
                definitions.add(flashcard.definition);
                //System.out.println("+++++++++" + flashcard.term + " " + flashcard.definition + " " + flashcard.mistakes);
                iterator.remove();
            } else if (definitions.contains(flashcard.definition)) {
                Flashcard findcard = findFlashkardByDefinition(flashcard.definition);
                flashcards.remove(findcard);
                cards.remove(findcard.term);
                definitions.remove(findcard.definition);
                flashcards.add(flashcard);
                cards.add(flashcard.term);
                definitions.add(flashcard.definition);
                //System.out.println("--------------------" + flashcard.term + " " + flashcard.definition + " " + flashcard.mistakes);
                iterator.remove();
            } else {
                flashcards.add(flashcard);
                cards.add(flashcard.term);
                definitions.add(flashcard.definition);
            }
        }
    }

    public void exportFile() {
        System.out.println("File name:");
        log.add("File name:");
        String fileName = scanner.nextLine();
        log.add(fileName);
        FileHandler handler = new FileHandler(fileName);
        try {
            handler.saveFlashcards(flashcards);
            System.out.println((flashcards.size() - flashcardsFromFile.size()) + " cards have been saved.");
            log.add((flashcards.size() - flashcardsFromFile.size()) + " cards have been saved.");
        } catch (IOException e) {

        }
    }

    public void ask() {
        System.out.println("How many times to ask?");
        log.add("How many times to ask?");
        String a = scanner.nextLine();
        int attempts = Integer.parseInt(a);
        log.add(a);
        Random random = new Random();
        while (attempts != 0){
            int number = random.nextInt(flashcards.size());
            Flashcard randomCard = flashcards.get(number);
            System.out.println("Print the definition of \"" + randomCard.term + "\":");
            log.add("Print the definition of \"" + randomCard.term + "\":");
            String answer = scanner.nextLine();
            log.add(answer);
            if (randomCard.definition.equals(answer)) {
                System.out.println("Correct!");
                log.add("Correct!");
            } else if (definitions.contains(answer)) {
                System.out.println("Wrong. The right answer is \"" + randomCard.definition + "\", but your definition is correct for \"" + findFlashkardByDefinition(answer).term + "\".");
                log.add("Wrong. The right answer is \"" + randomCard.definition + "\", but your definition is correct for \"" + findFlashkardByDefinition(answer).term + "\".");
                randomCard.mistakes++;
            } else {
                System.out.println("Wrong. The right answer is \"" + flashcards.get(number).definition + "\".");
                log.add("Wrong. The right answer is \"" + flashcards.get(number).definition + "\".");
                randomCard.mistakes++;
            }
            attempts--;
            if (attempts == 0) {
                break;
            }
        }
    }

    public void writeLog() throws IOException {
        System.out.println("File name:");
        log.add("File name:");
        String fileName = scanner.nextLine();
        log.add(fileName);
        FileWriter writer = new FileWriter(fileName);
        for (String entry : log) {
            writer.write(entry + "\n");
        }
        writer.close();
        System.out.println("The log has been saved.");
        log.add("The log has been saved.");
    }

    public void hardestCards() {
        int max = 0;
        for (Flashcard flashcard : flashcards) {
            if (flashcard.mistakes > max) {
                max = flashcard.mistakes;
            }
        }
        if (max == 0) {
            System.out.println("There are no cards with errors.");
            log.add("There are no cards with errors.");
            return;
        } else {
            List<Flashcard> hardestCards = new ArrayList<>();
            for (Flashcard flashcard : flashcards) {
                if (flashcard.mistakes == max) {
                    hardestCards.add(flashcard);
                }
            }
            if (hardestCards.size() == 1) {
                System.out.println("The hardest card is \"" + hardestCards.get(0).term + "\". You have " +hardestCards.get(0).mistakes + " errors answering it.");
                log.add("The hardest card is \"" + hardestCards.get(0).term + "\". You have " + max + " errors answering it.");
            } else {
                StringBuilder builder = new StringBuilder();
                builder.append("The hardest cards are ");
                for (int i = 0; i < hardestCards.size() - 1; i++) {
                    builder.append("\"" + hardestCards.get(i).term + "\", ");
                }
                builder.append("\"" + hardestCards.get(hardestCards.size() - 1).term + "\". You have " + max + " errors answering it.");
                System.out.println(builder.toString());
                log.add(builder.toString());
            }
        }
    }

    public void resetStats() {
        for (Flashcard flashcard : flashcards) {
            flashcard.mistakes = 0;
        }
        System.out.println("Card statistics have been reset.");
        log.add("Card statistics have been reset.");
    }
}