package chess.piece;

public enum Color {
  WHITE,
  BLACK;

  @Override
  public final String toString() {
    switch (this) {
      case WHITE:
        return "White";
      case BLACK:
        return "Black";
    }
    return ""; // Just for compiler
  }
}
