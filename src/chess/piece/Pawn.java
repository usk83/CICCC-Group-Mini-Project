package chess.piece;

import java.util.HashMap;
import java.util.Map;

public class Pawn extends Piece {
  private static final Map<Color, Character> symbols =
      new HashMap<Color, Character>() {
        {
          put(Color.WHITE, '♙');
          put(Color.BLACK, '♟');
        }
      };

  private int didTwoStepMove;

  public Pawn(Color color) {
    super(color, symbols);
    didTwoStepMove = 0;
  }

  @Override
  public boolean isValidMove(int x, int y, boolean isEnemyExisted) {
    // All possible move
    // x: -1 or 0 or 1
    // y: 1 or 2
    if (x < -1 || 1 < x || y < 1 || 2 < y) return false;

    if (isEnemyExisted) {
      // Diagonally forward
      if (Math.abs(x) == 1 && y == 1) return true;
    } else {
      // forward one
      // or
      // forward two when first move
      if (x == 0 && (y == 1 || (y == 2 && lastMovedTurn == 0))) return true;
    }

    return false;
  }

  @Override
  public void recordMoved(int x, int y, int turn) {
    super.recordMoved(x, y, turn);
    if (y == 2) {
      didTwoStepMove = turn;
    }
  }
}
