package chess;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InputController {
  private static final Scanner STDIN_SCANNER = new Scanner(System.in);

  public static Input getInput(String prompt) {
    System.out.print(prompt);
    return new Input(STDIN_SCANNER.nextLine());
  }

  static class Input {
    public String line;
    public Command command;
    public Map<String, String> params = new HashMap<>();

    Input(String line) {
      this.line = line.toLowerCase();
      command = Command.parse(this.line);

      Pattern pattern = null;
      String[] names = null;
      switch (command) {
        case SQUARE_POSSIBLE_MOVES:
          pattern = Board.REGEX_PATTERN_LIST_MOVES;
          names = Board.REGEX_PATTERN_LIST_MOVES_GROUP_NAMES;
          break;
        case GO_MOVE:
          pattern = Board.REGEX_PATTERN_MOVE;
          names = Board.REGEX_PATTERN_MOVE_GROUP_NAMES;
          break;
      }
      if (pattern == null) {
        return;
      }
      Matcher matcher = pattern.matcher(this.line);
      if (!matcher.matches()) {
        return;
      }

      for (String name : names) {
        params.put(name, matcher.group(name));
      }
    }

    /**
     * This method is just for debugging purpose. Shouldn't be called in production code.
     *
     * @return the detail of Input instance
     */
    @Override
    public String toString() {
      return String.format(
          "Input{\n\tline: %s,\n\tcommand: %s,\n\tparams: %s\n}", line, command, params);
    }
  }
}
