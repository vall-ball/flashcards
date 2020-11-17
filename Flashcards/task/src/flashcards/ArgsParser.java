package flashcards;

public class ArgsParser {

    String[] args;

    ArgsParser(String[] args) {
        this.args = args;
    }

    public String fileImportName() {
        if (args != null && args.length > 0) {
            for (int i = 0; i < args.length; i++) {
                if (args[i].equals("-import")) {
                    return args[i + 1];
                }
            }
        }
        return null;
    }

    public String fileExportName() {
        if (args != null && args.length > 0) {
            for (int i = 0; i < args.length; i++) {
                if (args[i].equals("-export")) {
                    return args[i + 1];
                }
            }
        }
        return null;
    }

}
