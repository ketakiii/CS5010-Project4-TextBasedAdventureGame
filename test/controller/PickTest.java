package controller;

import static org.junit.Assert.assertThrows;

import org.junit.Test;

/**
 * This class tests the Pick class and its methods.
 */
public class PickTest {

  Icommand pick = new Pick();

  @Test public void testPlay() {
    assertThrows(IllegalArgumentException.class, () -> pick.playGame(null));
  }

}