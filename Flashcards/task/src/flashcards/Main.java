package flashcards;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        ArgsParser parser = new ArgsParser(args);
        Menu menu = new Menu();
        menu.fileImportName = parser.fileImportName();
        menu.fileExportName = parser.fileExportName();
        menu.menu();
    }
}

