package chess.piece;

import java.util.HashMap;
import java.util.Map;

public class Rook extends Piece {
  private static final Map<Color, Character> symbols = new HashMap<Color, Character>(){{
    put(Color.WHITE, '♖');
    put(Color.BLACK, '♜');
  }};

  public Rook(Color color) {
    super(color, symbols);
  }

  @Override
  public boolean isValidMove(int row, int col) {
    if (!super.isValidMove(row, col)) {
      return false;
    }

    return true;
  }
}
