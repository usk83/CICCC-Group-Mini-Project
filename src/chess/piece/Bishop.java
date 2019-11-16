package chess.piece;

import java.lang.Math;
import java.util.HashMap;
import java.util.Map;

public class Bishop extends Piece {
  private static final Map<Color, Character> symbols = new HashMap<Color, Character>(){{
    put(Color.WHITE, '♗');
    put(Color.BLACK, '♝');
  }};

  public Bishop(Color color) {
    super(color, symbols);
  }

  @Override
  public boolean isValidMove(Position newPosition) {
    if (!super.isValidMove(newPosition)) {
      // ToDo: implement
      return true;
    }
    return false;
  }
}
