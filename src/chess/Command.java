package chess;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

enum Command {
  HELP,
  BOARD,
  RESIGN,
  ALL_POSSIBLE_MOVES,
  SQUARE_POSSIBLE_MOVES,
  GO_MOVE,
  INVALID;

  static Command parse(String str) {
    switch (str.toLowerCase()) {
      case "help":
        return HELP;
      case "board":
        return BOARD;
      case "resign":
        return RESIGN;
      case "moves":
        return ALL_POSSIBLE_MOVES;
      default:
        Pattern pattern = Pattern.compile("([a-z]+[0-9]+)(?<secondPos>[a-z]+[0-9]+([a-z]+)?)?");
        Matcher matcher = pattern.matcher(str);
        if (matcher.matches()) {
          if (matcher.group("secondPos") == null) {
            return SQUARE_POSSIBLE_MOVES;
          } else {
            return GO_MOVE;
          }
        }

        return INVALID;
    }
  }
}
