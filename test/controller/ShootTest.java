package controller;

import static org.junit.Assert.assertThrows;

import model.Direction;
import org.junit.Test;

/**
 * This class tests the Shoot class and its methods.
 */
public class ShootTest {

  Icommand shoot = new Shoot(Direction.WEST, 1);

  @Test public void testShoot() {
    assertThrows(IllegalArgumentException.class, () -> new Shoot(null, 1));
    assertThrows(IllegalArgumentException.class, () -> new Shoot(Direction.WEST, -1));
    assertThrows(IllegalArgumentException.class, () -> new Shoot(Direction.WEST, 8));
  }

  @Test public void testPlay() {
    assertThrows(IllegalArgumentException.class, () -> shoot.playGame(null));
  }

}