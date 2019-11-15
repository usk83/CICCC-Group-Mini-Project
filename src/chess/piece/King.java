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
    // ToDo: implement
    // if difference of Col & Row is 1, it can move
    Position currentPosition = null;
    int diffOfCol = Math.abs(currentPosition.getCol() - newPosition.getCol());
    int diffOfRow = Math.abs(currentPosition.getRow() - newPosition.getRow());
    if (diffOfCol <= 1 && diffOfRow <= 1) {
      return true;
    }
    return false;
  }
}




