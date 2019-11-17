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

  public Pawn(Color color) {
    super(color, symbols);
  }

  @Override
  public boolean isValidMove(Position newPosition) {
    if (!super.isValidMove(newPosition)) {
      return false;
    }
    // ToDo: implement
    return true;
  }
}
