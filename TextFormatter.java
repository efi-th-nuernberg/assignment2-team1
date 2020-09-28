import java.util.*;

class TextFormatter {

    private static final String text = "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy " +
            "eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et " +
            "accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem " +
            "ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod " +
            "tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et " +
            "justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est " +
            "Lorem ipsum dolor sit amet.";

    public static void main(String[] args) {
        TextFormatter formatter = new TextFormatter(30, Alignment.BLOCK);
        formatter.print(text);
    }

    enum Alignment {
        LEFT,
        BLOCK,
        RIGHT
    }

    private int maxLen;
    private Alignment alignment = Alignment.LEFT;

    // Konstruktor
    public TextFormatter(int maxLineLength) {
        this(maxLineLength, Alignment.LEFT);
    }

    // Konstruktor
    public TextFormatter(int maxLineLength, Alignment alignment) {
        maxLen = maxLineLength;
        this.alignment = alignment;
    }

    // Ausgabe
    public void print(String aText) {
        List<String> tokens = tokenize(aText);
        while (tokens.size()>0)
            printLine(tokens);
    }

    // Zerlegung in einzelne Worte
    public List<String> tokenize(String aText) {
        return new ArrayList<String>(Arrays.asList(aText.split("\\s+")));
    }

    // Ausgabe der nächsten Zeile
    public void printLine(List<String> tokens) {
        List<String> lineTokens = tokensForNextLine(tokens);
        StringBuffer sb = new StringBuffer();
        indent(sb, calcGap(lineTokens));
        for (int i=0; i< lineTokens.size(); i++) {
            sb.append(lineTokens.get(i));
            List<String> token_left = lineTokens.subList(i+1, lineTokens.size());
            stretch_spaces(sb, token_left.size(), calcGap(token_left)-sb.length());
        }
        System.out.println(sb.toString());
    }

    // Einrücken bei rechtsbündig
    public void indent(StringBuffer sb, int gap) {
        if (alignment == Alignment.RIGHT)
            insert_spaces(sb, gap);
    }

    // Spreizen bei Blocksatz
    public void stretch_spaces(StringBuffer sb, int token_left, int gap_left) {
        if (token_left == 0) return;
        if (alignment != Alignment.BLOCK || token_left > 1)
            sb.append(' ');
        if (alignment == Alignment.BLOCK)
            insert_spaces(sb, gap_left / token_left);
    }

    // Einfügen einer bestimmten Anzahl von Leerzeichen
    public void insert_spaces(StringBuffer sb, int count) {
        for (int i = 0; i < count; i++)
            sb.append(' ');
    }

    // Minimale Länge einer Liste von Token
    public int minLineLength(List<String> tokens) {
        int result = 0;
        if (!tokens.isEmpty())
        {
            for (String s: tokens)
                result += s.length();
            result += tokens.size()-1;
        }
        return result;
    }

    // Differenz zwischen ungespreizter Länge und Maximallänge
    public int calcGap(List<String> tokens) {
        return maxLen - minLineLength(tokens);
    }

    // Auswahl der Token, die in die nächste Zeile passen
    public List<String> tokensForNextLine(List<String> tokens) {
        int currentLength = 0;
        List<String> result = new ArrayList<String>();
        while (!tokens.isEmpty() && (currentLength + tokens.get(0).length() <= maxLen)) {
            result.add(tokens.get(0));
            currentLength += tokens.get(0).length() + 1;
            tokens.remove(0);
        }
        if ((!tokens.isEmpty()) && (result.isEmpty())) {
            int pos = Integer.min(maxLen, tokens.get(0).length());
            String[] splitted = splitAt(tokens.get(0), pos);
            result.add(splitted[0]);
            tokens.set(0, splitted[1]);
        }
        return result;
    }

    // Aufteilen eines Strings in zwei Teilstrings
    public String[] splitAt(String aText, int pos) {
        String tail = aText.substring(pos);
        String head = aText.substring(0, pos);
        return new String[] {head, tail};
    }
}
