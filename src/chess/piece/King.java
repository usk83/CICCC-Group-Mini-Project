package chess.piece;

import java.util.HashMap;
import java.util.Map;

public class King extends Piece {
  private static final Map<Color, Character> symbols =
      new HashMap<Color, Character>() {
        {
          put(Color.WHITE, '♔');
          put(Color.BLACK, '♚');
        }
      };

  public King(Color color) {
    super(color, symbols, true);
  }

  public Piece tryCastling(SquareManageable square, int xDiff, Color turn, int turnCount)
      throws InvalidSpecialMoveException {
    if (lastMovedTurn != 0) {
      throw new InvalidSpecialMoveException();
    }

    int direction = xDiff > 0 ? 1 : -1;
    Rook rook = null;
    int rookX = 0;
    try {
      int curX = 0;
      while (rook == null) {
        curX += direction;
        Piece piece = square.get(curX, 0);
        if (piece == null) {
          continue;
        }
        if (piece.getColor() != turn || !(piece instanceof Rook) || piece.lastMovedTurn != 0) {
          throw new InvalidSpecialMoveException();
        }
        rook = (Rook) piece;
        rookX = curX;
      }
    } catch (InvalidSpecialMoveException | IndexOutOfBoundsException e) {
      throw new InvalidSpecialMoveException();
    }

    square.move(0, 0, xDiff, 0);
    lastMovedTurn = turnCount;
    square.move(rookX, 0, xDiff + (direction * -1), 0);
    rook.lastMovedTurn = turnCount;
    return null;
  }

  @Override
  public Piece moveSpecially(
      SquareManageable square,
      int xDiff,
      int yDiff,
      Map<String, String> options,
      Color turn,
      int turnCount)
      throws InvalidOptionsException, InvalidSpecialMoveException, NotEnoughOptionsException {
    if (Math.abs(xDiff) != 2 || yDiff != 0) {
      throw new InvalidSpecialMoveException();
    }

    for (String option : options.values()) {
      if (option != null) throw new InvalidOptionsException("");
    }

    return tryCastling(square, xDiff, turn, turnCount);
  }

  @Override
  public boolean isValidMove(int x, int y, boolean isEnemyExisted) {
    return (Math.abs(x) < 2) && (Math.abs(y) < 2);
  }
}
