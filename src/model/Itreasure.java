package model;

import java.util.Map;

/**
 * This interface represents the treasure and its attributes.
 */
public interface Itreasure {

  /**
   * Gets the treasure type of a treasure object.
   * @return treasure type
   */
  public TreasureType getTreasureType();

  /**
   * Gets the treasure we require to be fetched.
   * @return map of treasure type and quantity
   */
  public Map<TreasureType, Integer> getTreasure();

}
