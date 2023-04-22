package model;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * This class represents the class that implements the interface IPlayer and has all its
 * methods.
 */
public class Player implements Iplayer {

  private final String name;
  private final Map<TreasureType, Integer> treasureBag;
  private int arrowBag;
  private Ilocation currentLocation;
  private int health;
  private final int maxHealth = 100;
  private SmellType smellType = null;

  /**
   * This method represents the constructor of the model.Player class.
   * @param name name of the player
   */
  public Player(String name) {
    if (name == null || "".equals(name)) {
      throw new IllegalArgumentException("model.Player is null");
    }
    this.name = name;
    this.treasureBag = new HashMap<>();
    this.currentLocation = null;
    this.health = maxHealth;
    this.arrowBag = 3;
  }

  @Override public String getName() {
    return this.name;
  }

  @Override public void addTreasures(Itreasure treasure) {
    treasureBag.put(treasure.getTreasureType(),
        treasureBag.getOrDefault(treasure.getTreasureType(), 0) + 1);
  }

  @Override public void addArrows(int n) {
    this.arrowBag += n;
  }

  @Override public int arrowCount() {
    return this.arrowBag;
  }

  @Override public Map<TreasureType, Integer> getTreasureBag() {
    return this.treasureBag;
  }

  @Override public Ilocation getCurrentLocation() {
    return this.currentLocation;
  }

  @Override public void setCurrentLocation(Ilocation location) {
    if (location != null) {
      this.currentLocation = location;
      //this.setTypeOfSmell();
    } else {
      throw new IllegalArgumentException("Location is null!");
    }
  }

  @Override public void addTreasure() {
    if (this.getCurrentLocation().getTreasure() != null) {
      addTreasures(this.getCurrentLocation().getTreasure());
      this.getCurrentLocation().setTreasure(null);
    }
  }

  @Override public String toStringStatus() {
    return "Current model.Location: " + this.currentLocation
        + " Treasures: " + this.getTreasureBag();
  }

  @Override public String toString() {
    return "model.Player{" + "name='" + name + '\'' + ", treasure="
        + treasureBag + ", currentLocation=" + currentLocation + '}' + "smell type=" + smellType;
  }

  @Override public int getHealth() {
    return this.health;
  }

  @Override public void reduceHealth() {
    this.health = 0;
  }

  @Override public boolean isAlive() {
    boolean isAlive = this.health > 0;
    return isAlive;
  }

  @Override public void setSmell(SmellType smell) {
    this.smellType = smell;
  }

  @Override public SmellType getSmell() {
    return smellType;
  }

}
