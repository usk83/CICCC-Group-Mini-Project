package chess;

import chess.piece.*;

class BoardInitializer {
  public static final Piece[][] initializeBoard(Color[] turns) {
    Piece[][] initialBoard = new Piece[8][8];
    initialBoard[7] = initializeKingsLine(turns[0]);
    initialBoard[6] = initializePawnsLine(turns[0]);
    initialBoard[0] = initializeKingsLine(turns[1]);
    initialBoard[1] = initializePawnsLine(turns[1]);
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
