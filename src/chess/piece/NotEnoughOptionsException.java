package chess.piece;

public class NotEnoughOptionsException extends Exception {
  public NotEnoughOptionsException(String errorMessage) {
    super(errorMessage);
  }
}
