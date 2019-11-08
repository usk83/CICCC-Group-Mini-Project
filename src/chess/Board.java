package chess;

import java.lang.IllegalArgumentException;
import chess.piece.*;

public class Board {
  private static final Piece[] INITIAL_PIECES = {
    new Rook(Color.BLACK, 0, 0),
    new Knight(Color.BLACK, 0, 1),
    new Bishop(Color.BLACK, 0, 2),
    new Queen(Color.BLACK, 0, 3),
    new King(Color.BLACK, 0, 4),
    new Bishop(Color.BLACK, 0, 5),
    new Knight(Color.BLACK, 0, 6),
    new Rook(Color.BLACK, 0, 7),
    new Pawn(Color.BLACK, 1, 0),
    new Pawn(Color.BLACK, 1, 1),
    new Pawn(Color.BLACK, 1, 2),
    new Pawn(Color.BLACK, 1, 3),
    new Pawn(Color.BLACK, 1, 4),
    new Pawn(Color.BLACK, 1, 5),
    new Pawn(Color.BLACK, 1, 6),
    new Pawn(Color.BLACK, 1, 7),
    new Pawn(Color.WHITE, 6, 0),
    new Pawn(Color.WHITE, 6, 1),
    new Pawn(Color.WHITE, 6, 2),
    new Pawn(Color.WHITE, 6, 3),
    new Pawn(Color.WHITE, 6, 4),
    new Pawn(Color.WHITE, 6, 5),
    new Pawn(Color.WHITE, 6, 6),
    new Pawn(Color.WHITE, 6, 7),
    new Rook(Color.WHITE, 7, 0),
    new Knight(Color.WHITE, 7, 1),
    new Bishop(Color.WHITE, 7, 2),
    new Queen(Color.WHITE, 7, 3),
    new King(Color.WHITE, 7, 4),
    new Bishop(Color.WHITE, 7, 5),
    new Knight(Color.WHITE, 7, 6),
    new Rook(Color.WHITE, 7, 7)
  };
  private static final Piece[][] INITIAL_BOARD;

  private Piece[][] metrix;
  private BoardString stringRepresentation;

  static {
    INITIAL_BOARD = new Piece[8][8];
    for (Piece piece: INITIAL_PIECES) {
      Position pos = piece.getPosition();
      INITIAL_BOARD[pos.getRow()][pos.getCol()] = piece;
    }
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

  @Override
  public String toString() {
    return stringRepresentation.toString();
  }
}
