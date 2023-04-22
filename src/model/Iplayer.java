package model;

import java.util.Map;

/**
 * This interface represents the player and its attributes.
 */
public interface Iplayer {

  /**
   * Returns the name of the player.
   * @return string name
   */
  public String getName();

  /**
   * Adds the treasure in the treasure bag of the player.
   * @param treasure treasure
   */
  public void addTreasures(Itreasure treasure);

  /**
   * Gets the treasure bag of the player.
   * @return treasure bag
   */
  public Map<TreasureType, Integer> getTreasureBag();

  /**
   * Gets the current location of the player.
   * @return current location
   */
  public Ilocation getCurrentLocation();

  /**
   * Sets the current location of the player in the location we require.
   * @param location location
   */
  public void setCurrentLocation(Ilocation location);

  /**
   * Gives us the model.Player's details of the location coordinates and the treasure bag.
   * @return description of player
   */
  public String toStringStatus();

  /**
   * Gets the health of the player at the current moment.
   * @return player health
   */
  public int getHealth();

  /**
   * Reduces the health of the player when encountered with monster.
   */
  public void reduceHealth();

  /**
   * Tells if the player is alive or not at the current moment.
   * @return if the player is alive
   */
  public boolean isAlive();

  /**
   * Count of arrows with the player at the current moment.
   * @param n number of arrows to be added
   */
  public void addArrows(int n);

  /**
   * Adds the treasure to the player's treasure bag.
   */
  public void addTreasure();

  /**
   * Checks the player's current arrow count.
   * @return arrow count
   */
  public int arrowCount();

  /**
   * Sets the smell the player is getting.
   * @param smell smell
   */
  public void setSmell(SmellType smell);

  /**
   * Returns the smelltype the player is set to currently.
   * @return SmellType
   */
  public SmellType getSmell();
}
