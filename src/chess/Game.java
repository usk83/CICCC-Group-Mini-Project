package chess;

import chess.piece.*;

public class Game {
  private static final Color[] TUNES = {Color.WHITE, Color.BLACK};

  private int turnIndex;
  private int turnCount;
  private Board board;

  public Game() {
    turnIndex = 0;
    turnCount = 1;
    board = new Board(TUNES);
  }

  public void play() {
    displayTitle();
    System.out.printf("\n%s", board);

    boolean isGameOnGoing = true;
    while (isGameOnGoing) {
      System.out.printf("\n%s to move\n", TUNES[turnIndex]);
      String userInput = InputController.getUserInput("Enter UCI (type 'help' for help): ");
      switch (Command.parse(userInput)) {
        case HELP:
          System.out.println(
              ""
                  + "* type 'help' for help \n "
                  + "* type 'board' to see the board again \n "
                  + "* type 'resign' to resign \n "
                  + "* type 'moves' to list all possible moves \n "
                  + "* type a square (e.g. b1, e2) to list possible moves for that square \n "
                  + "* type a UCI (e.g. b1c3, e7e8q) to make a move");
          break;
        case BOARD:
          System.out.printf("\n%s", board);
          break;
        case RESIGN:
          String[] score = new String[TUNES.length];
          for (int i = 0; i < TUNES.length; i++) {
            score[i] = i == getNextTurnIndex() ? "1" : "0";
          }
          System.out.printf("\n%s\n", board);
          System.out.printf(
              "Game over - %s - %s won by resignation\n",
              String.join("-", score), TUNES[getNextTurnIndex()]);
          isGameOnGoing = false;
          break;
        case ALL_POSSIBLE_MOVES:
          System.err.println("This command haven't yet implemented.");
          break;
        case SQUARE_POSSIBLE_MOVES:
          System.err.println("This command haven't yet implemented.");
          break;
        case GO_MOVE:
          int[] moveTo = new int[4];
          char promotionPiece;
          int index = 0;
          for (int i = 0; i < userInput.length(); i++) {
            char c = userInput.charAt(i);
            moveTo[index] = Character.isDigit(c) ? convertBoardNumber(c) : convertLetterToNumber(c);
            index++;
          }

          Position fromPosition = new Position(moveTo[0], moveTo[1]);
          Position toPosition = new Position(moveTo[2], moveTo[3]);

          // Check FromPosition
          if (!board.isOwnPiece(fromPosition, TUNES[turnIndex])) {
            System.out.println("Can not find your own piece you want to move.");
            break;
          }

          // Check ToPosition
          if (board.isOwnPiece(toPosition, TUNES[turnIndex])) {
            System.out.println("Your piece already exists on the destination your piece try to move.");
            break;
          }

          // Check Halfway
          if (!board.isNotPiecesOnHalfway(fromPosition, toPosition)) {
            System.out.println("Can not move the selected piece because other piece is on halfway.");
            break;
          }

          // Check Basic move of a Piece
          if(!board.ableBasicMove(fromPosition, toPosition, TUNES[turnIndex])) {
            System.out.println("The piece you selected doesn't allow to move to the destination");
            break;
          }

          board.update(fromPosition, toPosition);
          System.out.println("OK");
          System.out.printf("\n%s", board);
          switchTurn();
          break;
        case INVALID:
          System.out.println("Invalid input, please try again");
      }
    }
  }

  private static final void displayTitle() {
    System.out.println(
        ""
            + "  ___  _  _  ____  ____  ____     ___   __   _  _  ____  _\n"
            + " / __)/ )( \\(  __)/ ___)/ ___)   / __) / _\\ ( \\/ )(  __)/ \\\n"
            + "( (__ ) __ ( ) _) \\___ \\\\___ \\  ( (_ \\/    \\/ \\/ \\ ) _) \\_/\n"
            + " \\___)\\_)(_/(____)(____/(____/   \\___/\\_/\\_/\\_)(_/(____)(_)");
  }

  private int getNextTurnIndex() {
    return (turnIndex + 1) % TUNES.length;
  }

  private void switchTurn() {
    turnIndex = getNextTurnIndex();
    turnCount++;
  }

  // https://stackoverflow.com/questions/15027231/java-how-to-convert-letters-in-a-string-to-a-number
  private int convertLetterToNumber(char c) {
    return c - 'a';
  }

  private int convertBoardNumber(char c) {
    return (Character.getNumericValue(c) - 8) * -1;
  }
}
