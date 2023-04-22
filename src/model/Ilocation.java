package model;

import java.util.Map;

/**
 * This interface represents the location and its attributes.
 */
public interface Ilocation {

  /**
   * Returns the name of the locations : coordinates.
   * @return string of coordinates
   */
  public String getName();

  /**
   * Returns the row coordinate.
   * @return row coordinate
   */
  public int getRowNumber();

  /**
   * Returns the column coordinate.
   * @return column coordinate
   */
  public int getColumnNumber();

  /**
   * Attaches the two locations in the direction mentioned.
   * @param d direction
   * @param location location
   */
  public void attachLocation(Direction d, Ilocation location);

  /**
   * Sets the treasure as mentioned in the parameter.
   * @param treasure treasure to be set
   */
  public void setTreasure(Itreasure treasure);

  /**
   * Gets the treasure in the location we need.
   * @return treasure
   */
  public Itreasure getTreasure();

  /**
   * Gets the neighbours of a location and their direction.
   * @return map of direction and location
   */
  public Map<Direction, Ilocation> getNeighbours();

  /**
   * Returns the location type of the current location.
   * @return location type
   */
  public LocationType getLocationType();

  /**
   * This method sets the number of arrows given at the current location.
   * @param n number of arrows to be set
   */
  public void setArrows(int n);

  /**
   * The method returns the number of arrows at the position.
   * @return number of arrows
   */
  public int getArrows();

  /**
   * This method sets the monsters at the current location.
   * @param monster monster to be set
   */
  public void setMonster(Imonster monster);

  /**
   * Returns the monster at the current location.
   * @return monster
   */
  public Imonster getMonster();

  /**
   * Returns a true false status if monster is present at the current location or not.
   * @return boolean
   */
  public boolean hasMonster();

  /**
   * Removes the treasure from the current location - called in case the player picks it up.
   * @return treasure after removal
   */
  public Itreasure removeTreasure();

  /**
   * Removes the arrow from the current location - called in case the player picks it up.
   * @return arrow after removal
   */
  public int removeArrows();

  /**
   * The to string method.
   * @return string
   */
  public String toString();
}
