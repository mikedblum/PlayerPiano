package cogitolearning;

public class Test
{

  /**
   * @param args
   */
  public static void main(String[] args)
  {
    Tokenizer tokenizer = new Tokenizer();
    String s;
//    s = "sin(x) * (1 - var_12)";
//    tokenizer.add("sin|cos|exp|ln|sqrt", 1);
//    tokenizer.add("\\(", 2);
//    tokenizer.add("\\)", 3);
//    tokenizer.add("\\+|-", 4);
//    tokenizer.add("\\*|/", 5);
//    tokenizer.add("[0-9]+",6);
//    tokenizer.add("[a-zA-Z][a-zA-Z0-9_]*", 7);

    s = "a#[b3  cb5  |  dn6/8 |e]";
    tokenizer.add("[a-z](#|b|n)?\\d?(/\\d)?", 1);
    tokenizer.add("\\|", 2);
    tokenizer.add("\\[", 3);
    tokenizer.add("]", 4);

    try
    {
      tokenizer.tokenize(s);

      for (Tokenizer.Token tok : tokenizer.getTokens())
      {
        System.out.println("" + tok.token + " " + tok.sequence);
      }
    }
    catch (ParserException e)
    {
      System.out.println(e.getMessage());
    }

  }
}
