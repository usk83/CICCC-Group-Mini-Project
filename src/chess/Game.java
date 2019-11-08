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
  }
}
