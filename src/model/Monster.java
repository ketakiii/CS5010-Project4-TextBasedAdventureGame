package model;

/**
 * The Monster class implement the Imonster interface and its methods.
 */
public class Monster implements Imonster {

  private String name;
  private int health;
  private Ilocation currentLocation;
  private final int maxHealth = 100;

  /**
   * This is the constructor of the Monster class.
   */
  public Monster() {
    this.name = "Otyugh";
    this.health = maxHealth;
  }

  @Override public String getName() {
    return name;
  }

  @Override public void setCurrentLocation(Ilocation location) {
    this.currentLocation = location;
  }

  @Override public Ilocation getCurrentLocation() {
    return this.currentLocation;
  }

  @Override public String toString() {
    return "Monster{" + "name='" + name + '\'' + ", health=" + health + '}';
  }

  @Override public int getHealth() {
    return this.health;
  }

  @Override public void reduceHealth() {
    this.health = this.health - 50;
  }

  @Override public boolean isAlive() {
    boolean isAlive = this.health > 0;
    return isAlive;
  }
}
