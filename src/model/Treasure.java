package model;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * This class represents the class that implements the interface ITreasure and has all its
 * methods.
 */
public class Treasure implements Itreasure {

  private HashMap<TreasureType, Integer> treasureBag;
  private final Random random;
  private final int minTreasure = 1;
  private final int maxTreasure = 5;
  private TreasureType treasureType;

  /**
   * Constructor for the class model.Treasure.
   * @param random random
   */
  public Treasure(Random random) {
    this.random = random;
  }

  /**
   * Another constructor for the model.Treasure class.
   * @param treasureType model.Treasure Type
   * @param random random
   */
  public Treasure(TreasureType treasureType, Random random) {
    if (random == null) {
      throw new IllegalArgumentException("Random initiaized wrong!");
    } else {
      this.treasureType = treasureType;
      this.random = random;
      this.treasureBag = new HashMap<>();
      treasureBag.put(treasureType, helperInit(minTreasure, maxTreasure, random));
    }
  }

  @Override public TreasureType getTreasureType() {
    return this.treasureType;
  }

  @Override public Map<TreasureType, Integer> getTreasure() {
    return this.treasureBag;
  }

  @Override public String toString() {
    return "model.Treasure{" + "treasury=" + treasureBag
        + ", MAX_TREASURE_QUANTITY=" + minTreasure
        + ", MAX_TREASURE_QUANTITY=" + maxTreasure + '}';
  }

  private int helperInit(int constant, int upperbound, Random random) {
    if (random == null) {
      throw new IllegalArgumentException("Initialized wrong Random!");
    } else {
      int intRandom = random.nextInt(upperbound);
      return constant + intRandom;
    }
  }
}
