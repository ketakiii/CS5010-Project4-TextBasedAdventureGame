package controller;

import model.Direction;
import model.IgameState;

/**
 * This class implements the shoot command.
 */
public class Shoot implements Icommand {

  private final Direction startDirection;
  private final int distance;

  /**
   * Constructor of the shoot class.
   * @param direction direction in which we want to shoot
   * @param distance distance at which we want to shoot
   */
  public Shoot(Direction direction, int distance) {
    if (direction == null) {
      throw new IllegalArgumentException("Direction given is null!");
    }
    if (distance < 0 || distance > 5) {
      throw new IllegalArgumentException("Distance should be between 0 and 5.");
    }
    this.startDirection = direction;
    this.distance = distance;
  }

  @Override public void playGame(IgameState model) {
    if (model == null) {
      throw new IllegalArgumentException("Model given is null!");
    }
    model.shootArrows(startDirection, distance, model.getPlayerCurrentPosition());
  }
}
