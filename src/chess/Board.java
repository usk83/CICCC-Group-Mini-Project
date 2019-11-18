package chess;

import chess.piece.*;
import java.util.regex.Pattern;

public class Board implements SquareManageable {
  public static final Pattern REGEX_PATTERN_LIST_MOVES =
      Pattern.compile("^(?<ofX>[a-h])(?<ofY>[1-8])$");
  public static final Pattern REGEX_PATTERN_MOVE =
      Pattern.compile(
          "^(?<fromX>[a-h])(?<fromY>[1-8])(?<toX>[a-h])(?<toY>[1-8])(?<promotion>[q|b|k|r])?$");
  public static final String[] REGEX_PATTERN_LIST_MOVES_GROUP_NAMES = {"ofX", "ofY"};
  public static final String[] REGEX_PATTERN_MOVE_GROUP_NAMES = {
    "fromX", "fromY", "toX", "toY", "promotion"
  };

  private Color[] turns;
  private Piece[][] metrix;
  private BoardString stringRepresentation;

  public Board(Color[] turns) {
    this.turns = turns;
    initialize();
  }

  private final void initialize() {
    metrix = BoardInitializer.initializeBoard(turns);
    stringRepresentation = new BoardString(metrix);
  }

  public void clear() {
    initialize();
  }

  public void clear(Color[] turns) {
    this.turns = turns;
    initialize();
  }

  public static final Position parsePosition(String x, String y) {
    return new Position(convertX(x), convertY(y));
  }

  private static final int convertX(String x) {
    int posX = 0;
    for (int i = 0; i < x.length(); i++) {
      posX += ((x.charAt(i) - 'a') + 1) * (int) Math.pow(26, x.length() - 1 - i);
    }
    return posX - 1;
  }

  private static final int convertY(String y) {
    return (Integer.valueOf(y) - 8) * -1;
  }

  public boolean update(Position pos, Position newPos, int turn) {
    Piece p = getPiece(pos);
    setPiece(pos, null);
    setPiece(newPos, p);
    p.setLastMovedTurn(turn);
    // TODO: implement update method to BoardString
    stringRepresentation = new BoardString(metrix);
    // TODO: only when board successfully updated, return true
    return true;
  }

  private Piece getPiece(Position pos) {
    return metrix[pos.getCol()][pos.getRow()];
  }

  private void setPiece(Position pos, Piece piece) {
    metrix[pos.getCol()][pos.getRow()] = piece;
  }

  public boolean isOwnPiece(Position pos, Color c) {
    Piece p = getPiece(pos);
    if (p == null) {
      return false;
    }
    return p.getColor() == c;
  }

  public boolean isEnemyPiece(Position pos, Color c) {
    Piece p = getPiece(pos);
    if (p == null) {
      return false;
    }
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
        Position p = new Position(startRow + countRow, startCol + countCol);
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
  public Piece update(int fromRow, int fromCol, int toRow, int toCol) {
    // TODO: move a piece from A(fromRow, fromCol) to B(fromRow, fromCol)
    return null;
  }

  @Override
  public Piece remove(int row, int col) {
    // TODO: remove a piece(row, col)
    return null;
  }

  @Override
  public Piece[] getAttackablePieces(int row, int col) {
    // TODO: get a list of attackable Pieces
    return new Piece[0];
  }

  static class BoardInitializer {
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

  static class BoardString {
    private static final int WIDTH = 8 + 25 + 4 + 1;
    private static final int HEIGHT = 8 + 9 + 2;
    private static final int LENGTH = HEIGHT * WIDTH;
    private static final String ABC_LINE = "    A   B   C   D   E   F   G   H    \n";
    private static final String BORDER_LINE = "  +---+---+---+---+---+---+---+---+  \n";
    private static final String NUM_LINE_TEMPLATE = "%d |   |   |   |   |   |   |   |   | %d\n";
    private static final String TEMPLATE =
        ABC_LINE
            + BORDER_LINE
            + String.format(NUM_LINE_TEMPLATE, 8, 8)
            + BORDER_LINE
            + String.format(NUM_LINE_TEMPLATE, 7, 7)
            + BORDER_LINE
            + String.format(NUM_LINE_TEMPLATE, 6, 6)
            + BORDER_LINE
            + String.format(NUM_LINE_TEMPLATE, 5, 5)
            + BORDER_LINE
            + String.format(NUM_LINE_TEMPLATE, 4, 4)
            + BORDER_LINE
            + String.format(NUM_LINE_TEMPLATE, 3, 3)
            + BORDER_LINE
            + String.format(NUM_LINE_TEMPLATE, 2, 2)
            + BORDER_LINE
            + String.format(NUM_LINE_TEMPLATE, 1, 1)
            + BORDER_LINE
            + ABC_LINE;

    // Smaller scall example
    //     A   B   C
    //   +---+---+---+_
    // 3 | ♜ | ♚ | ♜ | 3
    //   +---+---+---+
    // 2 |   |   |   | 2
    //   +---+---+---+
    // 1 | ♖ | ♔ | ♖ | 1
    //   +---+---+---+
    //     A   B   C
    private StringBuilder body = new StringBuilder(LENGTH);

    public BoardString() {
      initialize();
    }

    public BoardString(Piece[][] metrix) {
      this();
      if (metrix != null) {
        for (int y = 0; y < 8; y++) {
          for (int x = 0; x < 8; x++) {
            update(metrix[y][x], x, y);
          }
        }
      }
    }

    private void initialize() {
      body.replace(0, LENGTH, TEMPLATE);
    }

    public void clear() {
      initialize();
    }

    public void update(Piece p, int x, int y) {
      int position = (y + 1) * 2 * WIDTH + (x + 1) * 4;
      if (p == null) {
        body.replace(position, position + 1, " ");
      } else {
        body.replace(position, position + 1, String.valueOf(p));
      }
    }

    @Override
    public String toString() {
      return body.toString();
    }
  }
}
