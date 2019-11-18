package chess;

import chess.piece.*;

public class Game {
  private static final Color[] TURNS = {Color.WHITE, Color.BLACK};

  private int turnIndex;
  private int turnCount;
  private Board board;

  public Game() {
    turnIndex = 0;
    turnCount = 1;
    board = new Board(TURNS);
  }

  public void play() {
    displayTitle();
    System.out.printf("\n%s", board);

    boolean isGameOnGoing = true;
    while (isGameOnGoing) {
      System.out.printf("\n%s to move\n", TURNS[turnIndex]);
      InputController.Input input = InputController.getInput("Enter UCI (type 'help' for help): ");
      switch (input.command) {
        case HELP:
          System.out.println(
              ""
                  + "* type 'help' for help \n"
                  + "* type 'board' to see the board again \n"
                  + "* type 'resign' to resign \n"
                  + "* type a UCI (e.g. b1c3, e7e8q) to make a move");
          break;
        case BOARD:
          System.out.printf("\n%s", board);
          break;
        case RESIGN:
          printFinalMessage(getNextTurnIndex(), "won by resignation");
          isGameOnGoing = false;
          break;
        case GO_MOVE:
          Position from = Board.parsePosition(input.params.get("fromX"), input.params.get("fromY"));
          Position to = Board.parsePosition(input.params.get("toX"), input.params.get("toY"));
          input.params.remove("fromX");
          input.params.remove("fromY");
          input.params.remove("toX");
          input.params.remove("toY");

          try {
            board.update(from, to, input.params, TURNS[turnIndex], turnCount);
            System.out.println("OK");

            if (board.isCheckmated(TURNS[getNextTurnIndex()])) {
              printFinalMessage(turnIndex, "won by checkmate");
              isGameOnGoing = false;
            } else {
              System.out.printf("\n%s", board);
              switchTurn();
            }
          } catch (InvalidMoveException | InvalidParameterException e) {
            System.err.println(e.getMessage());
          }

          break;
        case INVALID:
          System.out.println("Invalid input, please try again");
          break;
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

  private final void printFinalMessage(int winnerIndex, String result) {
    // TODO: consider draw game
    String[] score = new String[TURNS.length];
    for (int i = 0; i < TURNS.length; i++) {
      score[i] = i == winnerIndex ? "1" : "0";
    }
    System.out.printf("\n%s\n", board);
    System.out.printf(
        "Game over - %s - %s %s\n", String.join("-", score), TURNS[winnerIndex], result);
  }

  private int getNextTurnIndex() {
    return (turnIndex + 1) % TURNS.length;
  }

  private void switchTurn() {
    turnIndex = getNextTurnIndex();
    turnCount++;
  }
}
