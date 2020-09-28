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
    TextFormatter formatter = new TextFormatter(30);
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

  public void printLine(List<String> tokens) {
    List<String> lineTokens = tokensForNextLine(tokens);
    StringBuffer sb = new StringBuffer();
    for (String s : lineTokens) {
      sb.append(s);
      sb.append(' ');
    } 
    System.out.println(sb.toString());
  }

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

  public int trimSpaces(List<String> tokens) {
    return maxLen - minLineLength(tokens);
  }

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

  public String[] splitAt(String aText, int pos) {
    String tail = aText.substring(pos+1);
    String head = aText.substring(0, pos+1);
    return new String[] {head, tail};
  }


}