package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import java.util.Random;
import org.junit.jupiter.api.Test;

/**
 * This class test the model.Treasure class and its methods.
 */
class TreasureTest {

  Random random;

  @Test public void testTreasure() {
    assertThrows(IllegalArgumentException.class,
        () -> new Treasure(TreasureType.DIAMONDS, null));
  }

  @Test public void testTreasureType() {
    random = new Random(12);
    Itreasure treasure = new Treasure(TreasureType.DIAMONDS, random);
    assertEquals("DIAMONDS", treasure.getTreasureType().toString());
  }

  @Test public void testGetTreasure() {
    random = new Random(12);
    Itreasure treasure = new Treasure(TreasureType.DIAMONDS, random);
    assertEquals("{DIAMONDS=2}", treasure.getTreasure().toString());
  }

  @Test public void testToString() {
    random = new Random(12);
    Itreasure treasure = new Treasure(TreasureType.DIAMONDS, random);
    assertEquals("model.Treasure{treasury={DIAMONDS=2}, MAX_TREASURE_QUANTITY=1, "
        + "MAX_TREASURE_QUANTITY=5}", treasure.toString());
  }
}