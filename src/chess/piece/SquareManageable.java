package chess.piece;

public interface SquareManageable {
  public Piece get(int x, int y) throws IndexOutOfBoundsException;

  public Piece update(Piece piece, int toX, int toY) throws IndexOutOfBoundsException;

  public Piece move(int fromX, int fromY, int toX, int toY) throws IndexOutOfBoundsException;

  public Piece remove(int x, int y) throws IndexOutOfBoundsException;

  public Piece[] getAttackablePieces(int x, int y) throws IndexOutOfBoundsException;
}
