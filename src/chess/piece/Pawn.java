package chess.piece;

import chess.Position;

import java.util.*;

public class Pawn extends Piece {
  private static final Map<Color, Character> symbols = new HashMap<Color, Character>(){{
    put(Color.WHITE, '♙');
    put(Color.BLACK, '♟');
  }};


  public Pawn(Color color) {
    super(color, symbols);
  }

  @Override
  public boolean isValidMove(int x, int y, boolean isEnemyExisted) {
    if (!super.isValidMove(x, y, isEnemyExisted)) {
      return false;
    }

    if (this.color == Color.BLACK) {
      x = x * -1;
      y = y * -1;
    }

    // x = -1, 0, 1
    // y = 1, 2
    if (x < -1 || 1 < x) return false;
    if (y < 0 || 2 < y) return false;

    // Diagonally forward
    if (isEnemyExisted && y == 1 && (x == 1 || x == -1)) return true;

    // forward two
    if (!isEnemyExisted && lastMovedTurn == 0 && x == 0 && y == 2) return true;

    // forward one
    if (!isEnemyExisted && x == 0 && y == 1) return true;

    return false;
  }
}
