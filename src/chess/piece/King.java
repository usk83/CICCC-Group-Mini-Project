package chess.piece;

import java.util.HashMap;
import java.util.Map;

public class King extends Piece {
  private static final Map<Color, Character> symbols = new HashMap<Color, Character>(){{
    put(Color.WHITE, '♔');
    put(Color.BLACK, '♚');
  }};

  public King(Color color) {
    super(color, symbols);
  }

  @Override
  public boolean isValidMove(Position newPosition) {
    if (!super.isValidMove(newPosition)) {
      return false;
    }
    // if difference of Col & Row is 1, it can move
    if (Math.abs(this.position.getCol()) - (newPosition.getCol()) <= 1 &&
        Math.abs(this.position.getRow()) - (newPosition.getRow()) <= 1) {
      return true;
    }
    return false;
  }
}
