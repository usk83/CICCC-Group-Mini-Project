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

  Knight(Color color, int lastMovedTurn) {
    super(color, symbols, lastMovedTurn);
  }

  @Override
  public boolean isValidMove(int x, int y, boolean isEnemyExisted) {
    int xDiff = Math.abs(x);
    int yDiff = Math.abs(y);
    return (xDiff == 1 && yDiff == 2) || (xDiff == 2 && yDiff == 1);
  }
}
