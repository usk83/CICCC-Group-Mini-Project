package chess;

import chess.piece.*;

public class Board implements SquareManageable {
  private static final Piece[][] INITIAL_BOARD;

  private Piece[][] metrix;
  private BoardString stringRepresentation;

  static {
    INITIAL_BOARD = new Piece[8][8];
    INITIAL_BOARD[0][0] = new Rook(Color.BLACK);
    INITIAL_BOARD[0][1] = new Knight(Color.BLACK);
    INITIAL_BOARD[0][2] = new Bishop(Color.BLACK);
    INITIAL_BOARD[0][3] = new Queen(Color.BLACK);
    INITIAL_BOARD[0][4] = new King(Color.BLACK);
    INITIAL_BOARD[0][5] = new Bishop(Color.BLACK);
    INITIAL_BOARD[0][6] = new Knight(Color.BLACK);
    INITIAL_BOARD[0][7] = new Rook(Color.BLACK);
    INITIAL_BOARD[1][0] = new Pawn(Color.BLACK);
    INITIAL_BOARD[1][1] = new Pawn(Color.BLACK);
    INITIAL_BOARD[1][2] = new Pawn(Color.BLACK);
    INITIAL_BOARD[1][3] = new Pawn(Color.BLACK);
    INITIAL_BOARD[1][4] = new Pawn(Color.BLACK);
    INITIAL_BOARD[1][5] = new Pawn(Color.BLACK);
    INITIAL_BOARD[1][6] = new Pawn(Color.BLACK);
    INITIAL_BOARD[1][7] = new Pawn(Color.BLACK);
    INITIAL_BOARD[6][0] = new Pawn(Color.WHITE);
    INITIAL_BOARD[6][1] = new Pawn(Color.WHITE);
    INITIAL_BOARD[6][2] = new Pawn(Color.WHITE);
    INITIAL_BOARD[6][3] = new Pawn(Color.WHITE);
    INITIAL_BOARD[6][4] = new Pawn(Color.WHITE);
    INITIAL_BOARD[6][5] = new Pawn(Color.WHITE);
    INITIAL_BOARD[6][6] = new Pawn(Color.WHITE);
    INITIAL_BOARD[6][7] = new Pawn(Color.WHITE);
    INITIAL_BOARD[7][0] = new Rook(Color.WHITE);
    INITIAL_BOARD[7][1] = new Knight(Color.WHITE);
    INITIAL_BOARD[7][2] = new Bishop(Color.WHITE);
    INITIAL_BOARD[7][3] = new Queen(Color.WHITE);
    INITIAL_BOARD[7][4] = new King(Color.WHITE);
    INITIAL_BOARD[7][5] = new Bishop(Color.WHITE);
    INITIAL_BOARD[7][6] = new Knight(Color.WHITE);
    INITIAL_BOARD[7][7] = new Rook(Color.WHITE);
  }

  public Board() {
    initialize();
  }

  public Board(Piece[][] metrix) {
    if (metrix == null) {
      throw new IllegalArgumentException("`metrix` cannot be null.");
    }
    this.metrix = metrix;
    stringRepresentation = new BoardString(metrix);
  }

  private final void initialize() {
    this.metrix = INITIAL_BOARD;
    stringRepresentation = new BoardString(INITIAL_BOARD);
  }

  public void clear() {
    initialize();
  }

  public void update(Position pos, Position newPos) {
    Piece p = metrix[pos.getCol()][pos.getRow()];
    metrix[pos.getCol()][pos.getRow()] = null;
    metrix[newPos.getCol()][newPos.getRow()] = p;
    stringRepresentation = new BoardString(metrix);
  }

  public boolean isPiece(Position pos) {
    return metrix[pos.getCol()][pos.getRow()] != null;
  }

  public boolean isOwnPiece(Position pos, Color c) {
    if (!this.isPiece(pos)) {
      return false;
    }

    Piece that = metrix[pos.getCol()][pos.getRow()];
    return that.getColor() == c;
  }

  public boolean isEnemyPiece(Position pos, Color c) {
    if (!this.isPiece(pos)) {
      return false;
    }

    Piece that = metrix[pos.getCol()][pos.getRow()];
    return that.getColor() != c;
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
        if (this.isPiece(p)) return false;
      }
    }

    return true;
  }

  public boolean ableBasicMove(Position from, Position to, Color c) {
    int x = from.getRow() - to.getRow();
    int y = from.getCol() - to.getCol();
    Piece p = metrix[from.getCol()][from.getRow()];
    return p.isValidMove(x, y, isEnemyPiece(to, c));
  }

  @Override
  public String toString() {
    return stringRepresentation.toString();
  }

  @Override
  public Piece get(int row, int col) {
    return null;
  }

  @Override
  public void update(int fromRow, int fromCol, int toRow, int toCol) {

  }

  @Override
  public void remove(int row, int col) {

  }

  @Override
  public Piece[] getAttackedList(int row, int col) {
    return new Piece[0];
  }
}
