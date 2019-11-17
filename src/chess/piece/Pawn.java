package chess.piece;

import chess.Position;

import java.util.*;

public class Pawn extends Piece {
  private static final Map<Color, Character> symbols = new HashMap<Color, Character>(){{
    put(Color.WHITE, '♙');
    put(Color.BLACK, '♟');
  }};


  public Pawn(Color color) {
    super(color, symbols);
  }

  @Override
  public boolean isValidMove(int row, int col, boolean isEnemyPiece) {
    if (!super.isValidMove(row, col, isEnemyPiece)) {
      return false;
    }

    return false;
  }
}
