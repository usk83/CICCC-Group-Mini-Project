package chess.piece;

import java.util.HashMap;
import java.util.Map;

public class Knight extends Piece {
  private static final Map<Color, Character> symbols =
      new HashMap<Color, Character>() {
        {
          put(Color.WHITE, '♘');
          put(Color.BLACK, '♞');
        }
      };

  public Knight(Color color) {
    super(color, symbols);
  }

  @Override
  public boolean isValidMove(int x, int y, boolean isEnemyExisted) {
    if (!super.isValidMove(x, y, isEnemyExisted)) {
      return false;
    }
    if ((Math.abs(x) == 1) && (Math.abs(y) == 2)) return true;
    if ((Math.abs(x) == 2) && (Math.abs(y) == 1)) return true;
    return false;
  }
}
