package chess;

import java.util.Scanner;

public class InputController {
  public static String getUserInput(String prompt) {
    System.out.print(prompt);
    Scanner in = new Scanner(System.in);
    return in.nextLine();
  }
}