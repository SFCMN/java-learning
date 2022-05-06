package org.sfcmn.basics;

public class TextBlock {
  public static void main(String[] args) {
    String html = """
              <html>
                <body>
                  <p>这是一个段落</p>
                  <p>这是第二个段落</p>
                  <p>这是第'$num'个段落</p>
                  <p>这是第"%d"个段落</p>
                </body>
              </html>
              """;
    System.out.println(html.replace("$num", "3").formatted(4));
  }
}
