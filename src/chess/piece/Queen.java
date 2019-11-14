package chess.piece;

import java.util.HashMap;
import java.util.Map;

public class Queen extends Piece {
  private static final Map<Color, Character> symbols = new HashMap<Color, Character>(){{
    put(Color.WHITE, '♕');
    put(Color.BLACK, '♛');
  }};

  public Queen(Color color) {
    super(color, symbols);
  }

  @Override
  public boolean isValidMove(int row, int col) {
    if (!super.isValidMove(row, col)) {
      return false;
    }

    if (row == 0 && col == 0) return false;
    if ((row == 0 && 1 <= col) || (col == 0 && 1 <= row) || (row - col == 0)) return true;

    return false;
  }
}
