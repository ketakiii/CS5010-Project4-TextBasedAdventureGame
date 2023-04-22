package controller;

import model.IgameState;

/**
 * This class implements the pick command.
 */
public class Pick implements Icommand {

  /**
   * Constructor of the pick class.
   */
  public Pick() {
  }

  @Override public void playGame(IgameState model) {
    if (model == null) {
      throw new IllegalArgumentException("Model is null!");
    }
    model.pickTreasure();

  }
}
