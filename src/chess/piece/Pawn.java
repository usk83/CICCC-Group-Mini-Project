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
  public boolean isValidMove(int x, int y, boolean isEnemyExisted) {
    if (!super.isValidMove(x, y, isEnemyExisted)) {
      return false;
    }

    return false;
  }
}
