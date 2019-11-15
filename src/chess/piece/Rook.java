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
  public boolean isValidMove(Position newPosition) {
    if (!super.isValidMove(newPosition)) {
      return false;
    }
    // If we passed the first test then check for the specific rook movement
    if (newPosition.getCol() == this.position.getCol()
        && newPosition.getRow() == this.position.getRow()) {
      return true;
    } else {
      return false;
    }
  }
}
