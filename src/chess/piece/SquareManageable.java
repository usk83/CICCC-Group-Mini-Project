package chess.piece;

public interface SquareManageable {
  public Piece get(int row, int col);
  public void update(int fromRow, int fromCol, int toRow, int toCol);
  public void remove(int row, int col);
  public Piece[] getAttackablePieces(int row, int col);
}
