package flashcards;

public class Flashcard {
    public String term;
    public String definition;
    public int mistakes;

    public Flashcard() {

    }
    public Flashcard(String term, String definition) {
        this.term = term;
        this.definition = definition;
        mistakes = 0;
    }

    public Flashcard(String term, String definition, int mistakes) {
        this.term = term;
        this.definition = definition;
        this.mistakes = mistakes;
    }
}
