package chess.piece;

public class InvalidOptionsException extends Exception {
  public InvalidOptionsException(String errorMessage) {
    super(errorMessage);
  }
}
