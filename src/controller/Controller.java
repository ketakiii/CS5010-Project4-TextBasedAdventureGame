package controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import model.Direction;
import model.IgameState;
import model.LocationType;
import model.SmellType;
import model.TreasureType;


/**
 * This is the Controller class that implements Icontroller interface and its methods.
 */
public class Controller implements Icontroller {

  private final Scanner scanner;
  private final Appendable output;
  private final IgameState gameState;

  /**
   * This is the constructor of the Controller class that initializes all the parameters.
   * @param scanner scanner Readable
   * @param output output Appendable
   * @param gameState game State
   */
  public Controller(Readable scanner, Appendable output, IgameState gameState) {
    if (scanner == null || output == null) {
      throw new IllegalArgumentException("The input output can not be null");
    }
    if (gameState == null) {
      throw new IllegalArgumentException("The model can not be null");
    }

    this.scanner = new Scanner(scanner);
    this.output = output;
    this.gameState = gameState;
  }

  @Override public void playGame() throws IOException {
    try {
      output.append("Game starts! Press Q for quitting\n");
      while (!this.gameState.isGameOver()) {
        try {
          if (this.gameState.playerWins()) {
            output.append("The player has won!");
            return;
          }
          if (this.gameState.getPlayerCurrentPosition().getLocationType() == LocationType.CAVE) {
            output.append("You are in a cave\n");
          } else if (this.gameState.getPlayerCurrentPosition().getLocationType()
              == LocationType.TUNNEL) {
            output.append("You are in a tunnel\nthat continues to").append(this.directionHelper())
                .append("\n");
          }
          if (this.gameState.getPlayerCurrentPosition().getTreasure() != null) {
            output.append("You find 1 ").append(helperTreasure(
                this.gameState.getPlayerCurrentPosition().getTreasure().getTreasureType()))
                .append(" here\n");
          }
          if (this.gameState.getSmellType() == SmellType.MOREPUNGENT) {
            output.append("You smell something terrible nearby\n");
          } else if (this.gameState.getSmellType() == SmellType.LESSPUNGENT) {
            output.append("You smell something nearby\n");
          }
          if (this.gameState.getPlayerCurrentPosition().getArrows() != 0) {
            output.append("You find ")
                .append(String.valueOf(this.gameState.getPlayerCurrentPosition().getArrows()))
                .append(" arrows\n");
          }
          output.append("\nMove, Pickup, or Shoot (M-P-S)? ");
          String input1 = scanner.next().toUpperCase(Locale.ROOT);
          if ("Q".equals(input1.toUpperCase(Locale.ROOT))) {
            output.append("Quitting game\n");
            return;
          }
          Direction direction = null;
          if ("M".equals(input1)) {
            output.append("Where to? ");
            String input2 = scanner.next().toUpperCase(Locale.ROOT);
            if ("Q".equals(input2.toUpperCase(Locale.ROOT))) {
              output.append("Quitting game\n");
              return;
            }
            if (!("N".equals(input2) || "S".equals(input2) || "W".equals(input2) || "E".equals(
                input2))) {
              throw new IllegalArgumentException("Check the direction!");
            } else {
              direction = this.gameState.helperDirection(input2);
            }
            Icommand move = new Move(direction);
            move.playGame(this.gameState);
            if (this.gameState.updatePlayerHealth()) {
              output.append("Chomp, chomp, chomp, you are eaten by an Otyugh!\n"
                  + "Better luck next time");
            }
          } else if ("P".equals(input1)) {
            output.append("What? ");
            String input3 = scanner.next().toLowerCase(Locale.ROOT);
            if ("Q".equals(input3.toUpperCase(Locale.ROOT))) {
              output.append("Quitting game\n");
              return;
            }
            if ("arrow".equals(input3) || "arrows".equals(input3)) {
              output.append("You pick ")
                  .append(String.valueOf(this.gameState.getPlayerCurrentPosition().getArrows()))
                  .append("arrows\n");
              this.gameState.pickArrows();
            } else {
              output.append("You pick up a ").append(input3).append("\n");
              Icommand pickup = new Pick();
              pickup.playGame(this.gameState);
            }
          } else if ("S".equals(input1)) {
            output.append("No. of caves (1-5)? ");
            String input4 = scanner.next();
            output.append("Where to? ");
            String input5 = scanner.next().toUpperCase(Locale.ROOT);
            if ("Q".equals(input5.toUpperCase(Locale.ROOT))) {
              output.append("Quitting game\n");
              return;
            }
            Icommand shoot = new Shoot(this.gameState.helperDirection(input5),
                Integer.parseInt(input4));
            shoot.playGame(this.gameState);
            if (this.gameState.updateMonsterHealth()) {
              output.append("You hear a great howl in the distance\n");
            } else {
              output.append("You shoot an arrow into the darkness\n");
            }
          } else {
            throw new IllegalArgumentException("Check your action input!");
          }
        } catch (IOException | IllegalArgumentException e) {
          output.append("You have entered illegal input");
          e.printStackTrace();
        }
      }
    } catch (IOException ioe) {
      throw new IOException("Failed", ioe);
    }
  }

  private String helperTreasure(TreasureType input) {
    Map<TreasureType, String> mapTreasures = new HashMap<>();
    mapTreasures.put(TreasureType.DIAMONDS, "diamond");
    mapTreasures.put(TreasureType.RUBIES, "ruby");
    mapTreasures.put(TreasureType.SAPPHIRES, "sapphire");
    return mapTreasures.get(input);
  }

  private String directionHelper() {
    StringBuilder sb = new StringBuilder();
    Map<Direction, String> mapDirections = new HashMap<>();
    mapDirections.put(Direction.NORTH, "N");
    mapDirections.put(Direction.SOUTH, "S");
    mapDirections.put(Direction.EAST, "E");
    mapDirections.put(Direction.WEST, "W");
    for (Direction d : this.gameState.getAvailableStartPosition()) {
      sb.append(" ").append(mapDirections.get(d));
    }
    return sb.toString();
  }

}
