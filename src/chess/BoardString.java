package chess;

import chess.piece.*;

public class BoardString {
  private static final int WIDTH = 8 + 25 + 4 + 1;
  private static final int HEIGHT = 8 + 9 + 2;
  private static final int LENGTH = HEIGHT * WIDTH;
  private static final String ABC_LINE = "    A   B   C   D   E   F   G   H    \n";
  private static final String BORDER_LINE = "  +---+---+---+---+---+---+---+---+  \n";
  private static final String NUM_LINE_TEMPLATE = "%d |   |   |   |   |   |   |   |   | %d\n";
  private static final String TEMPLATE =
      ABC_LINE
          + BORDER_LINE
          + String.format(NUM_LINE_TEMPLATE, 8, 8)
          + BORDER_LINE
          + String.format(NUM_LINE_TEMPLATE, 7, 7)
          + BORDER_LINE
          + String.format(NUM_LINE_TEMPLATE, 6, 6)
          + BORDER_LINE
          + String.format(NUM_LINE_TEMPLATE, 5, 5)
          + BORDER_LINE
          + String.format(NUM_LINE_TEMPLATE, 4, 4)
          + BORDER_LINE
          + String.format(NUM_LINE_TEMPLATE, 3, 3)
          + BORDER_LINE
          + String.format(NUM_LINE_TEMPLATE, 2, 2)
          + BORDER_LINE
          + String.format(NUM_LINE_TEMPLATE, 1, 1)
          + BORDER_LINE
          + ABC_LINE;

  // Smaller scall example
  //     A   B   C
  //   +---+---+---+_
  // 3 | ♜ | ♚ | ♜ | 3
  //   +---+---+---+
  // 2 |   |   |   | 2
  //   +---+---+---+
  // 1 | ♖ | ♔ | ♖ | 1
  //   +---+---+---+
  //     A   B   C
  private StringBuilder body = new StringBuilder(LENGTH);

  public BoardString() {
    initialize();
  }

  public BoardString(Piece[][] metrix) {
    this();
    if (metrix != null) {
      for (int y = 0; y < 8; y++) {
        for (int x = 0; x < 8; x++) {
          update(metrix[y][x], x, y);
        }
      }
    }
  }

  private void initialize() {
    body.replace(0, LENGTH, TEMPLATE);
  }

  public void clear() {
    initialize();
  }

  public void update(Piece p, int x, int y) {
    int position = (y + 1) * 2 * WIDTH + (x + 1) * 4;
    if (p == null) {
      body.replace(position, position + 1, " ");
    } else {
      body.replace(position, position + 1, String.valueOf(p));
    }
  }

  @Override
  public String toString() {
    return body.toString();
  }
}
