package chess;

import chess.piece.*;

public class Game {
  private static final Color PLAY_FIRST = Color.WHITE;

  private Board board;
  private Color turn;

  public Game() {
    board = new Board();
    turn = PLAY_FIRST;
  }

  public void play() {
    /*
     * ToDo: implement below
     */
    System.out.println(""
        + "  ___  _  _  ____  ____  ____     ___   __   _  _  ____  _\n"
        + " / __)/ )( \\(  __)/ ___)/ ___)   / __) / _\\ ( \\/ )(  __)/ \\\n"
        + "( (__ ) __ ( ) _) \\___ \\\\___ \\  ( (_ \\/    \\/ \\/ \\ ) _) \\_/\n"
        + " \\___)\\_)(_/(____)(____/(____/   \\___/\\_/\\_/\\_)(_/(____)(_)\n"
        + "\n");
    System.out.println(board);
    boolean isGameOnGoing = true;
    while (isGameOnGoing) {
      String userInput = InputController.getUserInput("Enter UCI (type 'help' for help): ");
      System.out.println(Command.parse(userInput));
      switch (Command.parse(userInput)) {
        case RESIGN:
          isGameOnGoing = false;
          break;
        case GO_MOVE:
          int[] moveFromToInt = new int[4];
          char promotionPiece;
          int index = 0;
          for (int i = 0; i < userInput.length(); i++) {
            char c = userInput.charAt(i);
            moveFromToInt[index] = Character.isDigit(c) ? Character.getNumericValue(c) : convertLetterToNumber(c);
            index++;
          }

          for (int i: moveFromToInt) {
            System.out.println(i);
          }
      }
    }
  }

  //  https://stackoverflow.com/questions/15027231/java-how-to-convert-letters-in-a-string-to-a-number
  public int convertLetterToNumber (char c) {
    return c - 'a';
  }
}
