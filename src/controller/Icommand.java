package controller;

import model.IgameState;

/**
 * This class defines the commands required to play the game.
 */
public interface Icommand {

  /**
   * This method defines the command required for playing the dungeon game.
   * @param model model of the game
   */
  public void playGame(IgameState model);
}
