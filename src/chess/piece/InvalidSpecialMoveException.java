package chess.piece;

public class InvalidSpecialMoveException extends Exception {
  public InvalidSpecialMoveException() {
    super("It's not valid as special move of the piece.");
  }

  public InvalidSpecialMoveException(String errorMessage) {
    super(errorMessage);
  }
}
