package chess;

import java.lang.IllegalArgumentException;
import chess.piece.*;

public class Board {
  private static final Piece[][] INITIAL_BOARD;

  private Piece[][] metrix;
  private BoardString stringRepresentation;

  static {
    INITIAL_BOARD = new Piece[8][8];
    INITIAL_BOARD[0][0] = new Rook(Color.BLACK);
    INITIAL_BOARD[0][1] = new Knight(Color.BLACK);
    INITIAL_BOARD[0][2] = new Bishop(Color.BLACK);
    INITIAL_BOARD[0][3] = new Queen(Color.BLACK);
    INITIAL_BOARD[0][4] = new King(Color.BLACK);
    INITIAL_BOARD[0][5] = new Bishop(Color.BLACK);
    INITIAL_BOARD[0][6] = new Knight(Color.BLACK);
    INITIAL_BOARD[0][7] = new Rook(Color.BLACK);
    INITIAL_BOARD[1][0] = new Pawn(Color.BLACK);
    INITIAL_BOARD[1][1] = new Pawn(Color.BLACK);
    INITIAL_BOARD[1][2] = new Pawn(Color.BLACK);
    INITIAL_BOARD[1][3] = new Pawn(Color.BLACK);
    INITIAL_BOARD[1][4] = new Pawn(Color.BLACK);
    INITIAL_BOARD[1][5] = new Pawn(Color.BLACK);
    INITIAL_BOARD[1][6] = new Pawn(Color.BLACK);
    INITIAL_BOARD[1][7] = new Pawn(Color.BLACK);
    INITIAL_BOARD[6][0] = new Pawn(Color.WHITE);
    INITIAL_BOARD[6][1] = new Pawn(Color.WHITE);
    INITIAL_BOARD[6][2] = new Pawn(Color.WHITE);
    INITIAL_BOARD[6][3] = new Pawn(Color.WHITE);
    INITIAL_BOARD[6][4] = new Pawn(Color.WHITE);
    INITIAL_BOARD[6][5] = new Pawn(Color.WHITE);
    INITIAL_BOARD[6][6] = new Pawn(Color.WHITE);
    INITIAL_BOARD[6][7] = new Pawn(Color.WHITE);
    INITIAL_BOARD[7][0] = new Rook(Color.WHITE);
    INITIAL_BOARD[7][1] = new Knight(Color.WHITE);
    INITIAL_BOARD[7][2] = new Bishop(Color.WHITE);
    INITIAL_BOARD[7][3] = new Queen(Color.WHITE);
    INITIAL_BOARD[7][4] = new King(Color.WHITE);
    INITIAL_BOARD[7][5] = new Bishop(Color.WHITE);
    INITIAL_BOARD[7][6] = new Knight(Color.WHITE);
    INITIAL_BOARD[7][7] = new Rook(Color.WHITE);
  }

  public Board() {
    initialize();
  }

  public Board(Piece[][] metrix) {
    if (metrix == null) {
      throw new IllegalArgumentException("`metrix` cannot be null.");
    }
    this.metrix = metrix;
    stringRepresentation = new BoardString(metrix);
  }

  private final void initialize() {
    this.metrix = INITIAL_BOARD;
    stringRepresentation = new BoardString(INITIAL_BOARD);
  }

  public void clear() {
    initialize();
  }

  public void update(Position pos, Position newPos) {
    Piece p = metrix[pos.getCol()][pos.getRow()];
    metrix[pos.getCol()][pos.getRow()] = null;
    metrix[newPos.getCol()][newPos.getRow()] = p;
    stringRepresentation = new BoardString(metrix);
  }

  @Override
  public String toString() {
    return stringRepresentation.toString();
  }
}
