package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Random;
import org.junit.jupiter.api.Test;

/**
 * This class tests the Monster class and its methods.
 */
public class MonsterTest {

  Imonster monster = new Monster();
  Random random = new Random(12);

  @Test public void testGetName() {
    assertEquals("Otyugh", monster.getName());
  }

  @Test public void testGetCurrentLocation() {
    Ilocation location = new Location(2, 1, random);
    monster.setCurrentLocation(location);
    assertEquals(location, monster.getCurrentLocation());
  }

  @Test public void testGetHealth() {
    assertEquals(100, monster.getHealth());
  }

  @Test public void testReduceHealth() {
    assertEquals(100, monster.getHealth());
    monster.reduceHealth();
    assertEquals(50, monster.getHealth());
    monster.reduceHealth();
    assertEquals(0, monster.getHealth());
  }

  @Test public void testIsAlive() {
    assertTrue(monster.isAlive());
    monster.reduceHealth();
    assertTrue(monster.isAlive());
    monster.reduceHealth();
    assertFalse(monster.isAlive());

  }

}