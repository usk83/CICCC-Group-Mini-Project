package chess.piece;

import java.util.HashMap;
import java.util.Map;

public class Pawn extends Piece {
  private static final Map<Color, Character> symbols =
      new HashMap<Color, Character>() {
        {
          put(Color.WHITE, '♙');
          put(Color.BLACK, '♟');
        }
      };

  private int didTwoStepMove;

  public Pawn(Color color) {
    super(color, symbols);
    didTwoStepMove = 0;
  }

  @Override
  public boolean isValidMove(int x, int y, boolean isEnemyExisted) {
    // All possible move
    // x: -1 or 0 or 1
    // y: 1 or 2
    if (x < -1 || 1 < x || y < 1 || 2 < y) return false;

    if (isEnemyExisted) {
      // Diagonally forward
      if (Math.abs(x) == 1 && y == 1) return true;
    } else {
      // forward one
      // or
      // forward two when first move
      if (x == 0 && (y == 1 || (y == 2 && lastMovedTurn == 0))) return true;
    }

    return false;
  }

  public Piece tryPromotion(
      SquareManageable square,
      int xDiff,
      int yDiff,
      Map<String, String> options,
      Color turn,
      int turnCount)
      throws InvalidOptionsException, InvalidSpecialMoveException, NotEnoughOptionsException {
    Piece dest = square.get(xDiff, yDiff);
    if (dest == null
        ? !isValidMove(xDiff, yDiff, false)
        : dest.getColor() == turn || !isValidMove(xDiff, yDiff, true)) {
      throw new InvalidSpecialMoveException();
    }

    try {
      square.get(xDiff, yDiff + 1);
      throw new InvalidSpecialMoveException();
    } catch (IndexOutOfBoundsException e) {
    }

    if (options.size() != 1) {
      throw new NotEnoughOptionsException("Option parameter is not valid.");
    }
    if (!options.containsKey("promotion")) {
      throw new InvalidOptionsException("Specify the piece to promote.");
    }

    String promotion = options.remove("promotion");
    if (promotion == null) {
      promotion = "";
    }
    Piece promoted = promote(promotion, turn, turnCount);
    Piece removed = square.move(0, 0, xDiff, yDiff);
    promoted.lastMovedTurn = turnCount;
    square.update(promoted, xDiff, yDiff);
    return removed;
  }

  private Piece promote(String s, Color color, int turnCount) throws InvalidOptionsException {
    switch (s) {
      case "q":
        return new Queen(color, turnCount);
      case "b":
        return new Bishop(color, turnCount);
      case "k":
        return new Knight(color, turnCount);
      case "r":
        return new Rook(color, turnCount);
      default:
        throw new InvalidOptionsException("Invalid parameter to specify the piece to promote.");
    }
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
    if (Math.abs(xDiff) >= 2 || yDiff != 1) {
      throw new InvalidSpecialMoveException();
    }

    try {
      return tryPromotion(square, xDiff, yDiff, options, turn, turnCount);
    } catch (InvalidOptionsException | NotEnoughOptionsException e) {
      throw e;
    } catch (InvalidSpecialMoveException e) {
    }

    // En Passant

    // return null;

    throw new InvalidSpecialMoveException();
  }

  @Override
  public void recordMoved(int x, int y, int turn) {
    super.recordMoved(x, y, turn);
    if (y == 2) {
      didTwoStepMove = turn;
    }
  }
}
