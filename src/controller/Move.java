package controller;

import model.Direction;
import model.IgameState;

/**
 * This class implements the move command.
 */
public class Move implements Icommand {

  private final Direction direction;

  /**
   * Constructor of the move class.
   * @param direction direction to move in
   */
  public Move(Direction direction) {
    if (direction == null) {
      throw new IllegalArgumentException("Direction is null!");
    }
    this.direction = direction;
  }

  @Override public void playGame(IgameState model) {
    if (model == null) {
      throw new IllegalArgumentException("Model is null!");
    }
    model.movePlayer(direction);
  }
}
