package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import org.junit.jupiter.api.Test;

/**
 * This class test the model.Location class and its methods.
 */
class LocationTest {
  Random random = new Random(12);
  Ilocation location = new Location(2, 4, random);

  @Test public void testLocation() {
    assertThrows(IllegalArgumentException.class, () -> new Location(-3, 3, random));
    assertThrows(IllegalArgumentException.class, () -> new Location(2, 4, null));
  }

  @Test public void testGetRowNumber() {
    assertEquals(2, location.getRowNumber());
  }

  @Test public void testGetColumnNumber() {
    assertEquals(4, location.getColumnNumber());
  }

  @Test public void testAttachLocation() {
    location.attachLocation(Direction.NORTH, location);
    Map<Direction, Ilocation> m = new HashMap<>();
    m.put(Direction.NORTH, location);
    assertEquals(m, location.getNeighbours());
    Ilocation location1 = new Location(2, 4, random);
    location.attachLocation(Direction.SOUTH, location);
    m.put(Direction.SOUTH, location1);
    assertEquals(m, location.getNeighbours());
  }

  @Test public void testGetLocationType() {
    location.attachLocation(Direction.NORTH, location);
    LocationType locationType = LocationType.CAVE;
    assertEquals(locationType, location.getLocationType());
  }

  @Test public void setTreasure() {
    location.attachLocation(Direction.NORTH, location);
    TreasureType treasureType = TreasureType.DIAMONDS;
    Itreasure treasure = new Treasure(treasureType, random);
    location.setTreasure(treasure);
    assertEquals(1, location.getTreasure().getTreasure().size());
  }

  @Test public void getTreasure() {
    location.attachLocation(Direction.NORTH, location);
    TreasureType treasureType = TreasureType.DIAMONDS;
    Itreasure treasure = new Treasure(treasureType, random);
    location.setTreasure(treasure);
    assertEquals("model.Treasure{treasury={DIAMONDS=2}, MAX_TREASURE_QUANTITY=1, "
        + "MAX_TREASURE_QUANTITY=5}", location.getTreasure().toString());
  }

  @Test public void testGetNeighbours() {
    location.attachLocation(Direction.NORTH, location);
    Map<Direction, Ilocation> m = new HashMap<>();
    m.put(Direction.NORTH, location);
    assertEquals(m, location.getNeighbours());

    Ilocation location1 = new Location(2, 0, random);
    location.attachLocation(Direction.WEST, location1);
    Ilocation location2 = new Location(2, 2, random);
    location.attachLocation(Direction.EAST, location2);
    Map<Direction, Ilocation> m1 = new HashMap<>();
    m1.put(Direction.SOUTH, location);
    m1.put(Direction.WEST, location1);
    m1.put(Direction.EAST, location2);
    assertEquals(m1, location.getNeighbours());

    Map<Direction, Ilocation> m2 = new HashMap<>();
    m2.put(Direction.EAST, location2);
    Ilocation location3 = new Location(2, 3, random);
    location.attachLocation(Direction.SOUTH, location3);
    assertEquals(m2, location.getNeighbours());
  }

  @Test public void testToString() {
    assertEquals("Row: 2 Column: 4", location.toString());
  }

  @Test public void testgetArrows() {
    location.setArrows(2);
    assertEquals(2, location.getArrows());
  }

  @Test public void testGetMonster() {
    Imonster monster = new Monster();
    location.setMonster(monster);
    assertEquals(monster, location.getMonster());
  }

  @Test public void testHasMonster() {
    assertFalse(location.hasMonster());
    Imonster monster = new Monster();
    location.setMonster(monster);
    assertTrue(location.hasMonster());    // tunnel?!
  }

  @Test public void testRemoveTreasure() {
    location.attachLocation(Direction.NORTH, location);
    TreasureType treasureType = TreasureType.DIAMONDS;
    Itreasure treasure = new Treasure(treasureType, random);
    location.setTreasure(treasure);
    location.removeTreasure();
    assertNull(location.getTreasure());
  }

  @Test public void testRemoveArrow() {
    location.setArrows(2);
    assertEquals(2, location.getArrows());
    location.removeArrows();
    assertEquals(0, location.getArrows());
  }
}