package chess;

import chess.piece.*;

public class Board {
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

  public void update(Position pos, Position newPos) {
    Piece p = metrix[pos.getCol()][pos.getRow()];
    metrix[pos.getCol()][pos.getRow()] = null;
    metrix[newPos.getCol()][newPos.getRow()] = p;
    stringRepresentation = new BoardString(metrix);
  }

  @Override
  public String toString() {
    return stringRepresentation.toString();
  }
}
