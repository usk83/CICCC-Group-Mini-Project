package chess.piece;

import chess.Position;

public interface Square {
  public boolean isValidUniqueMoveByOwnPosition(Position pos, int row, int col);
}
