package chess.piece;

import java.util.HashMap;
import java.util.Map;

public class Rook extends Piece {
  private static final Map<Color, Character> symbols = new HashMap<Color, Character>(){{
    put(Color.WHITE, '♖');
    put(Color.BLACK, '♜');
  }};

  public Rook(Color color, int row, int col) {
    super(color, symbols, row, col);
  }

  @Override
  public boolean isValidMove(Position newPosition) {
    if (!super.isValidMove(position)) {
      return false;
    }
    if (newPosition.getCol() == position.getCol()
        && newPosition.getRow() == position.getRow()) {
      return true;
    } else {
      return false;
    }
  }
}
