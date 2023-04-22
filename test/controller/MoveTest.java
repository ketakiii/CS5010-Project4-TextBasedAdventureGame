package controller;

import static org.junit.Assert.assertThrows;

import model.Direction;
import org.junit.Test;

/**
 * This class tests the Move class and its methods.
 */
public class MoveTest {

  Icommand move = new Move(Direction.WEST);

  @Test public void testMove() {
    assertThrows(IllegalArgumentException.class, () -> new Move(null));
  }

  @Test public void testPlayGame() {
    assertThrows(IllegalArgumentException.class, () -> move.playGame(null));
  }

}