package model;

import java.util.List;

/**
 * This is an interface of the model.GameState interface and its defined methods.
 */
public interface IgameState {

  /**
   * Gives us the player created in the game state.
   * @return player
   */
  public Iplayer getPlayer();

  /**
   * Checks if the game is over.
   * @return if game is over
   */
  public boolean isGameOver();

  /**
   * Checks all possible directions the player can move in.
   * @return list of directions
   */
  public List<Direction> getAvailableStartPosition();

  /**
   * Returns the start position of the player.
   * @return start location
   */
  public Ilocation getPlayerStartPosition();

  /**
   * Returns the end location of the player.
   * @return end location
   */
  public Ilocation getPlayerEndPosition();

  /**
   * Returns the current position of the player.
   * @return current location
   */
  public Ilocation getPlayerCurrentPosition();

  /**
   * Moves the player.
   * @param direction direction
   * @return location where the player moved
   */
  public Ilocation movePlayer(Direction direction);

  /**
   * Returns the dungeon of the game state.
   * @return the grid
   */
  public Ilocation[][] getDungeon();

  /**
   * Returns the player's details.
   * @return string of player details
   */
  public String toStringStatus();

  /**
   * This method helps us convert the string input to the Direction object to be given to the model
   * to make a move.
   * @param input String input
   * @return Direction object
   */
  public Direction helperDirection(String input);

  /**
   * This method helps the user shoot arrows in the direction and distance given.
   * @param direction direction in which to shoot an arrow
   * @param n distance to shoot the arrow
   * @param currentLocation the current location of the player
   * @return the location where the arrow will land
   */
  public Ilocation shootArrows(Direction direction, int n, Ilocation currentLocation);

  /**
   * This method helps us pick the treasure from a location.
   */
  public void pickTreasure();

  /**
   * This method helps us pick the arrow from a location.
   */
  public void pickArrows();

  /**
   * Tells us if the player wins or not.
   * @return player win or not
   */
  public boolean playerWins();

  /**
   * Returns the type of smell type.
   * @return smell type
   */
  public SmellType getSmellType();

  /**
   * Updated player health status returned.
   * @return true if player health reduced
   */
  public boolean updatePlayerHealth();

  /**
   * Updated monster health status returned.
   * @return true if monster health reduced
   */
  public boolean updateMonsterHealth();

}
