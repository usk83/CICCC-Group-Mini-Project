package chess.piece;

public interface SquareManageable {
  public Piece get(int row, int col);

  public boolean update(int fromRow, int fromCol, int toRow, int toCol);

  public boolean remove(int row, int col);

  public Piece[] getAttackablePieces(int row, int col);
}
