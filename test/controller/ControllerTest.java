package controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.io.StringReader;
import java.util.Random;
import model.GameState;
import model.IgameState;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * This is the class that tests if the controller works correctly.
 */
public class ControllerTest {

  Random random = new Random(12);
  IgameState gameState = new GameState(8, 10, 2, 20.5, 8, 5, false, random);

  @Test public void testController() {
    StringReader reader = new StringReader("Q");
    Appendable output = new StringBuffer();
    assertThrows(IllegalArgumentException.class, () -> new Controller(null, output, gameState));
    assertThrows(IllegalArgumentException.class, () -> new Controller(reader, null, gameState));
  }

  @Test public void testMove() throws IOException {
    StringReader reader = new StringReader("M\nn\nQ");
    Appendable output = new StringBuffer();
    Icontroller controller = new Controller(reader, output, gameState);
    controller.playGame();
    assertTrue(output.toString().contains("Where to?"));
  }

  @Test public void testPickTreasure() throws IOException {
    StringReader reader = new StringReader("M\ns\nM\ns\nM\ns\nM\ns\nP\ndiamond\nQ");
    Appendable output = new StringBuffer();
    Icontroller controller = new Controller(reader, output, gameState);
    controller.playGame();
    assertTrue(output.toString().contains("You pick up a diamond"));
  }

  @Test public void testPickArrows() throws IOException {
    StringReader reader = new StringReader("M\ne\nM\ne\nM\ne\nP\narrows\nQ");
    Appendable output = new StringBuffer();
    Icontroller controller = new Controller(reader, output, gameState);
    controller.playGame();
    assertTrue(output.toString().contains("You pick 1arrows"));
  }

  @Test public void testShoot() throws IOException {
    StringReader reader = new StringReader("S\n1\ne\nS\n1e\nQ");
    Appendable output = new StringBuffer();
    Icontroller controller = new Controller(reader, output, gameState);
    controller.playGame();
    assertTrue(output.toString().contains("You shoot an arrow into the darkness"));
  }

  @Test public void testQuitGame() throws IOException {
    StringReader reader = new StringReader("S\n1\ne\nS\n1\ne\nQ");
    Appendable output = new StringBuffer();
    Icontroller controller = new Controller(reader, output, gameState);
    controller.playGame();
    assertTrue(output.toString().contains("Quitting game"));
  }

  @Test public void testIoExceptionHandled() throws IOException {
    StringReader reader = new StringReader("Q\n");
    Appendable output = new FailingAppendable();
    Icontroller controller = new Controller(reader, output, gameState);
    assertThrows(IOException.class, controller::playGame);
  }

  @Test public void testWrongModel() throws IOException {
    StringReader reader = new StringReader("Q");
    Appendable output = new StringBuffer();
    assertThrows(IllegalArgumentException.class,
        () -> new Controller(reader, output, new GameState(5, 5, 20, 40, 4, 4, false, random)));
    assertThrows(IllegalArgumentException.class,
        () -> new Controller(reader, output, new GameState(8, 8, -20, 40, 4, 4, false, random)));
  }
}
