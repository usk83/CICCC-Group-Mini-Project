package chess.piece;

import java.util.HashMap;
import java.util.Map;

public class Pawn extends Piece {
  private static final Map<Color, Character> symbols = new HashMap<>(){{
    put(Color.WHITE, '♙');
    put(Color.BLACK, '♟');
  }};

  public Pawn(Color color, int row, int col) {
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
