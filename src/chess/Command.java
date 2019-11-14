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

  private static final Pattern REGEX_PATTER_UCI
      = Pattern.compile("([a-zA-Z]+[\\d]+)(?<secondPos>[a-zA-Z]+[\\d]+([q|Q|b|B|k|K|r|R])?)?");

  static Command parse(String str) {
    switch (str.toLowerCase()) {
      case "help":
        System.out.println("* type 'help' for help \n " +
            "* type 'board' to see the board again \n " +
            "* type 'resign' to resign \n " +
            "* type 'moves' to list all possible moves \n " +
            "* type a square (e.g. b1, e2) to list possible moves for that square \n " +
            "* type a UCI (e.g. b1c3, e7e8q) to make a move");

        return HELP;
      case "board":
        return BOARD;
      case "resign":
        return RESIGN;
      case "moves":
        return ALL_POSSIBLE_MOVES;
      default:
        Matcher matcher = REGEX_PATTER_UCI.matcher(str);
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
