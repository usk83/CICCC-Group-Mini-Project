package chess.piece;

import java.util.HashMap;
import java.util.Map;

public class Queen extends Piece {
  private static final Map<Color, Character> symbols =
      new HashMap<Color, Character>() {
        {
          put(Color.WHITE, '♕');
          put(Color.BLACK, '♛');
        }
      };

  public Queen(Color color) {
    super(color, symbols);
  }

  @Override
  public boolean isValidMove(int x, int y, boolean isEnemyExisted) {
    return x == 0 || y == 0 || Math.abs(x) == Math.abs(y);
  }
}
