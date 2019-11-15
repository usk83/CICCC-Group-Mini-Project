package chess.piece;

import java.lang.IllegalArgumentException;
import java.util.Map;

public abstract class Piece {
  protected Color color;
  protected char symbol;
  protected Position position;

  public Piece(Color color, Map<Color, Character> symbols) {
    if (color == null) {
      throw new IllegalArgumentException("`color` must be specified.");
    }
    Character s = symbols.get(color);
    if (s == null) {
      throw new IllegalArgumentException("Corresponding symbol is not specified in `symbols`.");
    }

    this.color = color;
    symbol = s;
  }

  public boolean isValidMove(Position newPosition) {
    if(newPosition.getRow() > 0 && newPosition.getCol() > 0
        && newPosition.getRow() < 8 && newPosition.getCol() < 8) {
      return true;
    } else {
      return false;
    }
  }

  public void printClassName() {
    final String className = new Object(){}.getClass().getEnclosingClass().getName();
    System.out.println(className);
  }

  @Override
  public String toString() {
    return String.valueOf(symbol);
  }
}
