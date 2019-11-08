package chess.piece;

import java.lang.Math;
import java.util.HashMap;
import java.util.Map;

public class Bishop extends Piece {
  private static final Map<Color, Character> symbols = new HashMap<>(){{
    put(Color.WHITE, '♗');
    put(Color.BLACK, '♝');
  }};

  public Bishop(Color color, int row, int col) {
    super(color, symbols, row, col);
  }

  @Override
  public boolean isValidMove(Position newPosition) {
    if (!super.isValidMove(position)) {
      return false;
    }
    if (Math.abs(newPosition.getCol() - position.getCol())
        == Math.abs(newPosition.getRow() - position.getRow())) {
      return true;
    } else {
      return false;
    }
  }
}
