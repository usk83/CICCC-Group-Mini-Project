package chess;

enum Command {
  HELP,
  BOARD,
  RESIGN,
  GO_MOVE,
  INVALID;

  static Command parse(String str) {
    switch (str) {
      case "help":
        return HELP;
      case "board":
        return BOARD;
      case "resign":
        return RESIGN;
      default:
        if (Board.REGEX_PATTERN_MOVE.matcher(str).matches()) {
          return GO_MOVE;
        }
        break;
    }
    return INVALID;
  }
}
