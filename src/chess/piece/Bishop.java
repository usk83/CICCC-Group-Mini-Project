package chess.piece;

import java.util.HashMap;
import java.util.Map;

public class Bishop extends Piece {
  private static final Map<Color, Character> symbols =
      new HashMap<Color, Character>() {
        {
          put(Color.WHITE, '♗');
          put(Color.BLACK, '♝');
        }
      };

  public Bishop(Color color) {
    super(color, symbols);
  }

  Bishop(Color color, int lastMovedTurn) {
    super(color, symbols, lastMovedTurn);
  }

  @Override
  public boolean isValidMove(int x, int y, boolean isEnemyExisted) {
    return Math.abs(x) == Math.abs(y);
  }
}
