package chess;

import chess.piece.*;
import java.util.Map;
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

  private Piece getPiece(Position pos) {
    return metrix[pos.getCol()][pos.getRow()];
  }

  private void setPiece(Piece piece, Position pos) {
    metrix[pos.getCol()][pos.getRow()] = piece;
  }

  private Piece movePiece(Piece piece, Position from, Position to) {
    final Piece dest = getPiece(to);
    setPiece(piece, to);
    setPiece(null, from);
    return dest;
  }

  public Piece update(
      Position from, Position to, Map<String, String> options, Color turn, int turnCount)
      throws InvalidMoveException {
    final Piece targetPiece = getPiece(from);

    // check if there is turn's piece at `from` position
    if (targetPiece == null || targetPiece.getColor() != turn) {
      throw new InvalidMoveException("Can not find your own piece you want to move.");
    }

    // calculate move from the piece perspective
    int x = to.getRow() - from.getRow();
    int y = to.getCol() - from.getCol();
    if (turn == turns[0]) {
      y *= -1;
    } else if (turn == turns[0]) {
      x *= -1;
    }

    /*
     * TODO: check if special move is available
     */

    /*
     * try normal move
     */
    // check the piece at `to` position
    // if there is turn's piece, invalid
    Piece destPiece = getPiece(to);
    boolean isEnemyPieceOnDest = false;
    if (destPiece != null) {
      if (destPiece.getColor() == turn) {
        throw new InvalidMoveException(
            "Your piece already exists on the destination your piece try to move.");
      } else {
        isEnemyPieceOnDest = true;
      }
    }

    // move is invalid if there is a piece between `from` and `to`
    if (isPieceExistedBetween(from, to)) {
      throw new InvalidMoveException(
          "Can not move the selected piece because other piece is on halfway.");
    }

    // check if move is valid from the piece's perspective
    if (!targetPiece.isValidMove(x, y, isEnemyPieceOnDest)) {
      throw new InvalidMoveException(
          "The piece you selected doesn't allow to move to the destination");
    }

    targetPiece.setLastMovedTurn(turnCount);
    movePiece(targetPiece, from, to);
    stringRepresentation.update(targetPiece, from, to);

    // TODO: recalculate all possible moves

    return destPiece;
  }

  private static final int calcGcd(int x, int y) {
    if (y != 0) return calcGcd(y, x % y);
    return x;
  }

  private boolean isPieceExistedBetween(Position from, Position to) {
    int baseX = from.getRow();
    int baseY = from.getCol();
    int destX = to.getRow();
    int destY = to.getCol();

    int xDiff = destX - baseX;
    int yDiff = destY - baseY;
    if (xDiff == 0 && yDiff == 0) {
      return false;
    }

    int unitX = 0;
    int unitY = 0;
    if (xDiff == 0) {
      unitY = yDiff > 0 ? 1 : -1;
    } else if (yDiff == 0) {
      unitX = xDiff > 0 ? 1 : -1;
    } else {
      // if both xDiff and yDiff is not 0, calculate the greatest common divisor,
      // and divide each difference by that.
      // So we can obtain each change to the position between `from` and `to`
      int gcd = calcGcd(Math.abs(xDiff), Math.abs(yDiff));
      unitX = xDiff / gcd;
      unitY = yDiff / gcd;
    }

    baseX += unitX;
    baseY += unitY;
    while (baseX != destX || baseY != destY) {
      // return false if any piece in on the position bettween `from` and `to`
      if (getPiece(new Position(baseX, baseY)) != null) return true;
      baseX += unitX;
      baseY += unitY;
    }

    return false;
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
      if (turns.length != 2) {
        throw new IllegalArgumentException("Only 2 players(turns) game is supported currently.");
      }
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
        if (metrix.length != 8 || metrix[0].length != 8) {
          throw new IllegalArgumentException("Only 8x8 metrix is supported currently.");
        }
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

    public void update(Piece p, Position pos) {
      update(p, pos.getRow(), pos.getCol());
    }

    public void update(Piece p, Position from, Position to) {
      update(null, from.getRow(), from.getCol());
      update(p, to.getRow(), to.getCol());
    }

    @Override
    public String toString() {
      return body.toString();
    }
  }
}
