package chess.piece;

import java.util.HashMap;
import java.util.Map;

public class King extends Piece {
  private static final Map<Color, Character> symbols = new HashMap<Color, Character>(){{
    put(Color.WHITE, '♔');
    put(Color.BLACK, '♚');
  }};

  public King(Color color, int row, int col) {
    super(color, symbols, row, col);
  }

  @Override
  public boolean isValidMove(Position newPosition) {
    if (!super.isValidMove(position)) {
      return false;
    }
    // ToDo: implement
    return false;
  }
}
