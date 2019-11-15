package chess.piece;

import java.lang.IllegalArgumentException;
import java.util.Map;

public abstract class Piece {
  protected Color color;
  protected char symbol;

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

  public boolean isValidMove(int row, int col, boolean isEnemyPiece) {
    return true;
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
