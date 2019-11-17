package chess;

import chess.piece.*;

class BoardInitializer {
  public static final Piece[][] initializeBoard(Color[] tunes) {
    Piece[][] initialBoard = new Piece[8][8];
    initialBoard[7] = initializeKingsLine(tunes[0]);
    initialBoard[6] = initializePawnsLine(tunes[0]);
    initialBoard[0] = initializeKingsLine(tunes[1]);
    initialBoard[1] = initializePawnsLine(tunes[1]);
    return initialBoard;
  }

  private static final Piece[] initializeKingsLine(Color color) {
    return new Piece[] {
      new Rook(color),
      new Knight(color),
      new Bishop(color),
      new Queen(color),
      new King(color),
      new Bishop(color),
      new Knight(color),
      new Rook(color),
    };
  }

  private static final Piece[] initializePawnsLine(Color color) {
    return new Piece[] {
      new Pawn(color),
      new Pawn(color),
      new Pawn(color),
      new Pawn(color),
      new Pawn(color),
      new Pawn(color),
      new Pawn(color),
      new Pawn(color),
    };
  }
}
