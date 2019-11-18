package chess.piece;

import java.util.HashMap;
import java.util.Map;

public class King extends Piece {
  private static final Map<Color, Character> symbols =
      new HashMap<Color, Character>() {
        {
          put(Color.WHITE, '♔');
          put(Color.BLACK, '♚');
        }
      };

  public King(Color color) {
    super(color, symbols);
  }

  @Override
  public boolean isValidMove(int x, int y, boolean isEnemyExisted) {
    if ((Math.abs(x) < 2) && (Math.abs(y) < 2)) return true;
    return false;
  }
}
