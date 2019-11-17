package chess.piece;

import chess.Position;

import java.util.*;

public class Pawn extends Piece {
  private static final Map<Color, Character> symbols = new HashMap<Color, Character>(){{
    put(Color.WHITE, '♙');
    put(Color.BLACK, '♟');
  }};

  // if this pawn is moved, isFirstMoved turn out true but at next your own turn, it turn out false
  // this variable is related to "en passant"
  // ex. n:false -> n + 1:true -> n + 2:false
  private boolean isFirstMoved;

  // if this pawn is moved, isMoved turn out true and it never change again
  // when the value is true, this Pawn can move forward two squares.
  // ex. n:false -> n + 1:true
  private boolean isMoved;

  public Pawn(Color color) {
    super(color, symbols);
    this.isFirstMoved = false;
    this.isMoved = false;
  }

  @Override
  public boolean isValidMove(int row, int col, boolean isEnemyPiece) {
    if (!super.isValidMove(row, col, isEnemyPiece)) {
      return false;
    }

    // if arguments are impossible int
    // row = 1 or 2, col = 0 or 1
    if (row == 0 || 3 <= row || 2 <= col) return false;

    // move to capture the diagonally forward opponent piece
    if (isEnemyPiece && row == 1 && col == 1) return true;

    // move forward
    if (col == 0) {
      if (!isMoved && row == 2) return true;
      if (row == 1) return true;
    }

    return false;
  }
}
