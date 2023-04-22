package model;

/**
 * This interface represents the monster and its attributes.
 */
public interface Imonster {

  /**
   * Returns the name of the monster.
   * @return string name
   */
  public String getName();

  /**
   * Sets the current location of the monster in the location we require.
   * @param location location
   */
  public void setCurrentLocation(Ilocation location);

  /**
   * Gets the current location of the monster.
   * @return current location
   */
  public Ilocation getCurrentLocation();

  /**
   * Gets the health of the monster at the current moment.
   * @return monster health
   */
  public int getHealth();

  /**
   * Reduces the health of the monster when shot.
   */
  public void reduceHealth();

  /**
   * Tells if the monster is alive or not at the current moment.
   * @return if the monster is alive
   */
  public boolean isAlive();
}
