package chess;

import chess.piece.Piece;

public interface SquareManageable {
  public Piece get(int row, int col);
  public void update(int fromRow, int fromCol, int toRow, int toCol);
  public void remove(int row, int col);
  public Piece[] getAttackedList(int row, int col);
}
