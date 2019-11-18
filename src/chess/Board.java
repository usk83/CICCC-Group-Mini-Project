package chess;

import chess.piece.*;

public class Board implements SquareManageable {
  private Color[] tunes;
  private Piece[][] metrix;
  private BoardString stringRepresentation;

  public Board(Color[] tunes) {
    this.tunes = tunes;
    initialize();
  }

  private final void initialize() {
    metrix = BoardInitializer.initializeBoard(tunes);
    stringRepresentation = new BoardString(metrix);
  }

  public void clear() {
    initialize();
  }

  public void clear(Color[] tunes) {
    this.tunes = tunes;
    initialize();
  }

  public boolean update(Position pos, Position newPos) {
    Piece p = getPiece(pos);
    metrix[pos.getCol()][pos.getRow()] = null;
    metrix[newPos.getCol()][newPos.getRow()] = p;
    stringRepresentation = new BoardString(metrix);
    // TODO: only when board successfully updated, return true
    return true;
  }

  private Piece getPiece(Position pos) {
    return metrix[pos.getCol()][pos.getRow()];
  }

  public boolean isOwnPiece(Position pos, Color c) {
    if (this.getPiece(pos) == null) {
      return false;
    }

    Piece that = metrix[pos.getCol()][pos.getRow()];
    return that.getColor() == c;
  }

  public boolean isEnemyPiece(Position pos, Color c) {
    if (this.getPiece(pos) == null) {
      return false;
    }

    Piece p = getPiece(pos);
    return p.getColor() != c;
  }

  public boolean isNotPiecesOnHalfway(Position from, Position to) {
    int x = from.getRow() - to.getRow();
    int y = from.getCol() - to.getCol();
    int x_abs = Math.abs(x);
    int y_abs = Math.abs(y);

    // if less than two spaces between the piece and destination
    if (Math.max(x_abs, y_abs) < 2) return true;
    if ((x_abs == y_abs) || (x_abs == 0) || (y_abs == 0)) {

      int startRow;
      int endRow;
      int startCol;
      int endCol;

      if (0 < x) {
        startRow = to.getRow();
        endRow = from.getRow();
      } else {
        startRow = from.getRow();
        endRow = to.getRow();
      }

      if (0 < y) {
        startCol = to.getCol();
        endCol = from.getCol();
      } else {
        startCol = from.getCol();
        endCol = to.getCol();
      }

      int countRow = 0;
      int countCol = 0;
      while ((startRow + countRow + 1 < endRow) || (startCol + countCol + 1 < endCol)) {
        if (startRow + countRow < endRow) countRow++;
        if (startCol + countCol < endCol) countCol++;
        Position p = new Position(startRow + countRow, startCol + countCol );
        if (this.getPiece(p) != null) return false;
      }
    }

    return true;
  }

  public boolean ableBasicMove(Position from, Position to, Color c) {
    int x = from.getRow() - to.getRow();
    int y = from.getCol() - to.getCol();
    Piece p = getPiece(from);
    return p.isValidMove(x, y, isEnemyPiece(to, c));
  }

  @Override
  public String toString() {
    return stringRepresentation.toString();
  }

  @Override
  public Piece get(int row, int col) {
    // TODO: get a piece(row, col)
    return null;
  }

  @Override
  public void update(int fromRow, int fromCol, int toRow, int toCol) {
    // TODO: move a piece from A(fromRow, fromCol) to B(fromRow, fromCol)
  }

  @Override
  public void remove(int row, int col) {
    // TODO: remove a piece(row, col)
  }

  @Override
  public Piece[] getAttackablePieces(int row, int col) {
    // TODO: get a list of attackable Pieces
    return new Piece[0];
  }
}
