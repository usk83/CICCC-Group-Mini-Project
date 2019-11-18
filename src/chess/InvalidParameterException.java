package chess;

import java.util.Map;

public class InvalidParameterException extends Exception {
  public InvalidParameterException(String errorMessage) {
    super(errorMessage);
  }

  public static final InvalidParameterException newException(Map<String, String> options) {
    String optionsString = "Invalid parameter: ";
    for (String value : options.values()) {
      optionsString += value;
    }
    return new InvalidParameterException(optionsString);
  }
}
