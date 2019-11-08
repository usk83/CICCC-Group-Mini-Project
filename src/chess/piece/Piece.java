package chess.piece;

import java.lang.IllegalArgumentException;
import java.util.Map;

public abstract class Piece {
  protected Color color;
  protected Position position;
  protected char symbol;

  public Piece(Color color, Map<Color, Character> symbols, int row, int col) {
    if (color == null) {
      throw new IllegalArgumentException("`color` must be specified.");
    }
    Character s = symbols.get(color);
    if (s == null) {
      throw new IllegalArgumentException("Corresponding symbol is not specified in `symbols`.");
    }

    this.color = color;
    position = new Position(row, col);
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

  public Position getPosition() {
    return position;
  }

  public void setPosition(Position position) {
    this.position = position;
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
