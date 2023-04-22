package model;

import static java.lang.Math.round;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Random;
import java.util.Set;
import org.junit.Test;

/**
 * This class tests the model.GameState class for the wrapping case and its methods.
 */
public class GameStateWrappingTest {

  Random random;
  IgameState gameState;

  private void initialize() {
    random = new Random(12);
    gameState = new GameState(7, 10, 50, 20.5, 2, 5,  true, random);

  }


  @Test public void testGameState() {
    initialize();
    assertThrows(IllegalArgumentException.class,
        () -> new GameState(-2, 2, 3, 20.0, 2, 5, true,  random));
    assertThrows(IllegalArgumentException.class,
        () -> new GameState(5, 5, 3, 20.0, 2, 5, true,  random));
    assertThrows(IllegalArgumentException.class,
        () -> new GameState(4, 6, 3, 20.0, 3, 5, true,  random));
    assertThrows(IllegalArgumentException.class,
        () -> new GameState(2, -3, 3, 30.0, 10, 5, true, random));
    assertThrows(IllegalArgumentException.class,
        () -> new GameState(3, 3, -3, 20.5, 3, 5, true, random));
    assertThrows(IllegalArgumentException.class,
        () -> new GameState(3, 3, 3, -30.0, 5, 5, true, random));
    assertThrows(IllegalArgumentException.class,
        () -> new GameState(3, 3, 4, 12.5, 6, 5, true, null));
  }

  @Test public void testGetPlayer() {
    initialize();
    Iplayer player = new Player("model.Player");
    assertEquals(player.toString(), gameState.getPlayer().toString());
  }

  @Test public void testIsGameOver() {
    Random random = new Random(12);
    GameState gameState1 = new GameState(7, 10, 100, 20.5, 5, 5, true, random);
    assertFalse(gameState1.isGameOver());
    gameState1.movePlayer(Direction.EAST);
    gameState1.movePlayer(Direction.EAST);
    gameState1.movePlayer(Direction.EAST);
    gameState1.movePlayer(Direction.NORTH);
    gameState1.movePlayer(Direction.NORTH);
    gameState1.movePlayer(Direction.EAST);
    gameState1.movePlayer(Direction.EAST);
    gameState1.movePlayer(Direction.NORTH);
    assertTrue(gameState1.isGameOver());
  }

  @Test public void testGetAvailableStartPosition() {
    initialize();
    assertEquals("[SOUTH, EAST, NORTH]", gameState.getAvailableStartPosition().toString());
    gameState.movePlayer(Direction.EAST);
    assertEquals("[NORTH, WEST, SOUTH]", gameState.getAvailableStartPosition().toString());
    gameState.movePlayer(Direction.SOUTH);
    assertEquals("[NORTH, WEST, SOUTH]", gameState.getAvailableStartPosition().toString());

  }

  @Test public void testGetPlayerStartPosition() {
    initialize();
    assertEquals("Row: 4 Column: 4", gameState.getPlayerStartPosition().toString());
  }

  @Test public void testGetPlayerEndPosition() {
    initialize();
    assertEquals("Row: 0 Column: 7", gameState.getPlayerEndPosition().toString());
  }

  @Test public void testGetPlayerCurrentPosition() {
    initialize();
    assertEquals("Row: 4 Column: 4", gameState.getPlayerCurrentPosition().toString());
  }

  @Test public void testMovePlayer() {
    Random random = new Random(12);
    GameState gameState1 = new GameState(10, 10, 12, 20.5, 4, 5, true, random);
    assertEquals("Row: 9 Column: 4", gameState1.getPlayerCurrentPosition().toString());
    gameState1.movePlayer(Direction.NORTH);
    assertEquals("Row: 3 Column: 4", gameState1.getPlayerCurrentPosition().toString());
    gameState1.movePlayer(Direction.WEST);
    assertEquals("Row: 3 Column: 3", gameState1.getPlayerCurrentPosition().toString());
    gameState1.movePlayer(Direction.SOUTH);
    assertEquals("Row: 5 Column: 3", gameState1.getPlayerCurrentPosition().toString());
    gameState1.movePlayer(Direction.NORTH);
    assertEquals("Row: 3 Column: 3", gameState1.getPlayerCurrentPosition().toString());
    gameState1.movePlayer(Direction.NORTH);
    assertEquals("Row: 0 Column: 3", gameState1.getPlayerCurrentPosition().toString());
  }

  @Test public void testInterconnectivity() {
    initialize();
    GameState gameStateNoConnectivity = new GameState(10, 10, 0, 20.5, 7, 5, true, random);
    GameState gameStateConnectivity = new GameState(10, 10, 2, 20.5, 8, 6, true, random);
    int degreeNoConnectivity = 0;
    int degreeConnectivity = 0;
    Ilocation[][] gridNoConnectivity = gameStateNoConnectivity.getDungeon();
    Ilocation[][] gridConnectivity = gameStateConnectivity.getDungeon();
    for (int i = 0; i < gridNoConnectivity.length; i++) {
      for (int j = 0; j < gridNoConnectivity[i].length; j++) {
        degreeNoConnectivity += gridNoConnectivity[i][j].getNeighbours().size();
        degreeConnectivity += gridConnectivity[i][j].getNeighbours().size();
      }
    }
    degreeNoConnectivity = degreeNoConnectivity / 2;
    degreeConnectivity = degreeConnectivity / 2;
    assertEquals(degreeNoConnectivity, degreeConnectivity - 2);
  }

  @Test public void testTreasureInLocations() {
    initialize();
    Ilocation[][] grid = gameState.getDungeon();
    boolean isPresent = true;
    for (int i = 0; i < grid.length; i++) {
      for (int j = 0; j < grid[i].length; j++) {
        if (grid[i][j].getLocationType() != LocationType.CAVE
            && grid[i][j].getTreasure() != null) {  //doubt
          isPresent = false;
        }
      }
      assertTrue(isPresent);
    }
  }

  @Test public void testTreasurePercent() {
    initialize();
    double caves = 0;
    double cavesWithTreasure = 0;
    Ilocation[][] grid = gameState.getDungeon();
    for (int i = 0; i < grid.length; i++) {
      for (int j = 0; j < grid[i].length; j++) {
        if (grid[i][j].getLocationType() == LocationType.CAVE) {
          caves += 1;
          if (grid[i][j].getTreasure() != null) {
            cavesWithTreasure += 1;
          }
        }
      }
    }
    int percentWithTreasure = (int) round(caves * 0.2);
    assertEquals(percentWithTreasure, cavesWithTreasure, 0.0);

  }

  @Test public void testPickingTreasure() {
    initialize();
    Ilocation[][] grid = gameState.getDungeon();
    Ilocation playerLocation = gameState.getPlayerCurrentPosition();
    grid[playerLocation.getRowNumber()][playerLocation.getColumnNumber()].getNeighbours()
        .get(Direction.EAST).setTreasure(new Treasure(TreasureType.DIAMONDS, random));
    assertEquals("Current model.Location: Row: 4 Column: 4 Treasures: {}",
        gameState.toStringStatus());
    gameState.movePlayer(Direction.EAST);
    assertEquals("Current model.Location: Row: 4 Column: 5 Treasures: {DIAMONDS=1}",
        gameState.toStringStatus());
  }

  @Test public void testGrid() {
    initialize();
    int interconnectivity = 50;
    Ilocation[][] grid = gameState.getDungeon();
    int validPaths = 0;
    for (int i = 0; i < grid.length; i++) {
      for (int j = 0; j < grid[i].length; j++) {
        validPaths += grid[i][j].getNeighbours().size();
      }
    }
    assertEquals(70, (long) grid.length * grid[1].length);
  }

  @Test public void testPathLength() {
    initialize();
    assertTrue(distance(gameState.getPlayerStartPosition(),
        gameState.getPlayerEndPosition()) >= 5);
  }

  private int distance(Ilocation start, Ilocation end) {
    initialize();
    Set<Ilocation> visitedLocations = new HashSet<>();
    Integer level = 0;
    Queue<Ilocation> objects = new LinkedList<>();
    Map<Ilocation, Integer> map = new HashMap<>();
    map.put(start, level);
    objects.add(start);

    while (!objects.isEmpty()) {
      Ilocation current = objects.remove();
      int currentLevel = map.get(current);
      if (current.equals(end)) {
        return currentLevel;
      }
      visitedLocations.add(current);
      for (Ilocation v : current.getNeighbours().values()) {
        if (v != null) {
          if (!visitedLocations.contains(v)) {
            map.put(v, currentLevel + 1);
            objects.add(v);
          }
        }
      }
    }
    return -1;
  }
}