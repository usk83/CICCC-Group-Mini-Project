package chess;

import chess.piece.*;
import java.util.Map;
import java.util.regex.Pattern;

public class Board {
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

  private final int[] convertDirection(int x, int y, Color turn) {
    if (turn == turns[0]) {
      y *= -1;
    } else if (turn == turns[0]) {
      x *= -1;
    }
    return new int[] {x, y};
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
      throw new InvalidMoveException("Can not find your own piece to move.");
    }

    // calculate move from the piece perspective
    int[] directions =
        convertDirection(to.getRow() - from.getRow(), to.getCol() - from.getCol(), turn);
    int x = directions[0];
    int y = directions[1];

    /*
     * TODO: check if special move is available
     */

    /*
     * try normal move
     */
    // check the piece at `to` position
    Piece destPiece = getPiece(to);
    boolean isEnemyPieceOnDest = false;
    boolean destError = false;
    if (destPiece != null) {
      if (destPiece.getColor() == turn) {
        destError = true;
      } else {
        isEnemyPieceOnDest = true;
      }
    }

    // check if move is valid from the piece's perspective
    if (!targetPiece.isValidMove(x, y, isEnemyPieceOnDest)) {
      throw new InvalidMoveException("Not a valid move for the piece.");
    }

    // move is invalid if there is a piece between `from` and `to`
    if (isPieceExistedBetween(from, to)) {
      throw new InvalidMoveException("Can not jump over other pieces.");
    }

    // if there is turn's piece at the destination, invalid
    if (destError) {
      throw new InvalidMoveException("Your other piece is at the destination.");
    }

    movePiece(targetPiece, from, to);
    stringRepresentation.update(targetPiece, from, to);
    targetPiece.recordMoved(x, y, turnCount);

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

  public boolean isCheckmated(Color turn) {
    // NOTE: it might be better to hold a pre-calculated list of essentials
    for (Piece[] row : metrix) {
      for (Piece piece : row) {
        if (piece == null) continue;
        if (piece.getColor() != turn) continue;
        if (piece.isEssential()) return false;
      }
    }
    return true;
  }

  @Override
  public String toString() {
    return stringRepresentation.toString();
  }

  class Square implements SquareManageable {
    private Piece[][] metrix;
    private Position currentPosition;
    private Color currentTurn;

    Square(Piece[][] metrix, Position currentPosition, Color currentTurn) {
      this.metrix = metrix;
      this.currentPosition = currentPosition;
      this.currentTurn = currentTurn;
    }

    @Override
    public Piece get(int x, int y) throws IndexOutOfBoundsException {
      int[] directions = convertDirection(x, y, currentTurn);
      int destX = currentPosition.getRow() + directions[0];
      int destY = currentPosition.getCol() + directions[1];

      return metrix[destY][destX];
    }

    @Override
    public Piece update(Piece piece, int toX, int toY) throws IndexOutOfBoundsException {
      int[] directions = convertDirection(toX, toY, currentTurn);
      int destX = currentPosition.getRow() + directions[0];
      int destY = currentPosition.getCol() + directions[1];

      Piece removed = metrix[destY][destX];
      metrix[destY][destX] = piece;
      return removed;
    }

    @Override
    public Piece move(int fromX, int fromY, int toX, int toY) throws IndexOutOfBoundsException {
      Piece removed = get(toX, toY);
      update(get(fromX, fromY), toX, toY);
      update(null, fromX, fromY);
      return removed;
    }

    @Override
    public Piece remove(int x, int y) throws IndexOutOfBoundsException {
      return update(null, x, y);
    }

    @Override
    public Piece[] getAttackablePieces(int x, int y) throws IndexOutOfBoundsException {
      // TODO: get a list of attackable Pieces
      return new Piece[] {};
    }
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
      recalculate(metrix);
    }

    private void initialize() {
      body.replace(0, LENGTH, TEMPLATE);
    }

    public void clear() {
      initialize();
    }

    public void recalculate(Piece[][] metrix) {
      if (metrix == null) {
        return;
      }
      if (metrix.length != 8 || metrix[0].length != 8) {
        throw new IllegalArgumentException("Only 8x8 metrix is supported currently.");
      }
      for (int y = 0; y < 8; y++) {
        for (int x = 0; x < 8; x++) {
          update(metrix[y][x], x, y);
        }
      }
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
