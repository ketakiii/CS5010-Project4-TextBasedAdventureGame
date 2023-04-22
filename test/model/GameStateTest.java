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
 * This class tests the model.GameState class and its methods.
 */
public class GameStateTest {

  Random random;
  IgameState gameState;

  private void initialize() {
    random = new Random(12);
    gameState = new GameState(9, 10, 20, 30, 4, 4, false, random);
  }

  @Test public void testGameState() {
    initialize();
    assertThrows(IllegalArgumentException.class,
        () -> new GameState(-2, 2, 3, 20.0, 5, 5, false,  random));
    assertThrows(IllegalArgumentException.class,
        () -> new GameState(2, -3, 3, 30.0, 8, 5, false, random));
    assertThrows(IllegalArgumentException.class,
        () -> new GameState(3, 3, -3, 20.5, 5, 5, false, random));
    assertThrows(IllegalArgumentException.class,
        () -> new GameState(3, 3, 3, -30.0, 7, 5, false, random));
    assertThrows(IllegalArgumentException.class,
        () -> new GameState(3, 3, 4, 12.5, 5, 5, false, null));
  }

  @Test public void testGetPlayer() {
    initialize();
    Ilocation location = new Location(1, 8, random);
    Iplayer player = new Player("model.Player");
    player.setCurrentLocation(location);
    assertEquals(player.toString(), this.gameState.getPlayer().toString());
  }

  @Test public void testIsGameOver() {
    initialize();
    assertFalse(this.gameState.isGameOver());
    this.gameState.movePlayer(Direction.NORTH);
    this.gameState.movePlayer(Direction.WEST);
    this.gameState.movePlayer(Direction.WEST);
    assertFalse(this.gameState.isGameOver());
    this.gameState.shootArrows(Direction.WEST, 1, this.gameState.getPlayerCurrentPosition());
    this.gameState.shootArrows(Direction.WEST, 1, this.gameState.getPlayerCurrentPosition());
    this.gameState.movePlayer(Direction.WEST);
    assertTrue(this.gameState.isGameOver());
  }

  @Test public void testGetAvailableStartPosition() {
    initialize();
    assertTrue(gameState.getAvailableStartPosition().contains(Direction.SOUTH));
    assertTrue(gameState.getAvailableStartPosition().contains(Direction.NORTH));
    assertTrue(gameState.getAvailableStartPosition().contains(Direction.WEST));
  }

  @Test public void testGetPlayerStartPosition() {
    initialize();
    assertEquals("Row: 1 Column: 8", this.gameState.getPlayerStartPosition().toString());
  }

  @Test public void testGetPlayerEndPosition() {
    initialize();
    assertEquals("Row: 0 Column: 5", this.gameState.getPlayerEndPosition().toString());
  }

  @Test public void testGetPlayerCurrentPosition() {
    initialize();
    assertEquals(this.gameState.getPlayerCurrentPosition().toString(),
        this.gameState.getPlayerStartPosition().toString());
    assertEquals("Row: 1 Column: 8", this.gameState.getPlayerCurrentPosition().toString());
  }

  @Test public void testMovePlayer() {
    initialize();
    assertEquals("Row: 1 Column: 8", this.gameState.getPlayerCurrentPosition().toString());
    this.gameState.movePlayer(Direction.NORTH);
    assertEquals("Row: 0 Column: 8", this.gameState.getPlayerCurrentPosition().toString());
    this.gameState.movePlayer(Direction.WEST);
    assertEquals("Row: 0 Column: 7", this.gameState.getPlayerCurrentPosition().toString());
    this.gameState.movePlayer(Direction.WEST);
    assertEquals("Row: 0 Column: 6", this.gameState.getPlayerCurrentPosition().toString());
    this.gameState.movePlayer(Direction.SOUTH);
    assertEquals("Row: 1 Column: 6", this.gameState.getPlayerCurrentPosition().toString());
  }

  @Test public void testInterconnectivity() {
    initialize();
    GameState gameStateNoConnectivity = new GameState(10, 10, 0, 20.5, 5, 5, false, random);
    GameState gameStateConnectivity = new GameState(10, 10, 2, 20.5, 5, 5, false, random);
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
    int percentWithTreasure = (int) round(caves * 0.3);
    assertEquals(percentWithTreasure, cavesWithTreasure, 0.0);

  }

  @Test public void testPickingTreasure() {
    initialize();
    Ilocation[][] grid = this.gameState.getDungeon();
    grid[0][8].setTreasure(new Treasure(TreasureType.DIAMONDS, random));
    assertEquals("Current model.Location: Row: 1 Column: 8 Treasures: {}",
        this.gameState.toStringStatus());
    this.gameState.movePlayer(Direction.NORTH);
    this.gameState.pickTreasure();
    assertEquals("Current model.Location: Row: 0 Column: 8 Treasures: {DIAMONDS=1}",
        this.gameState.toStringStatus());
  }
  
  @Test public void testGrid() {
    initialize();
    int interconnectivity = 2;
    Ilocation[][] grid = gameState.getDungeon();
    int validPaths = 0;
    for (int i = 0; i < grid.length; i++) {
      for (int j = 0; j < grid[i].length; j++) {
        validPaths += grid[i][j].getNeighbours().size();
      }
    }
    assertEquals(91, (long) grid.length * grid[1].length - 1
        + interconnectivity);
  }

  @Test public void testPathLength() {
    initialize();
    assertTrue(distance(gameState.getPlayerStartPosition(),
        gameState.getPlayerEndPosition()) >= 5);
  }

  @Test public void testToStringStatus() {
    initialize();
    assertEquals("Current model.Location: Row: 1 Column: 8 Treasures: {}",
        gameState.toStringStatus());
  }

  @Test public void testMonsterAtEndCave() {
    initialize();
    assertTrue(this.gameState.getPlayerEndPosition().hasMonster());
  }

  @Test public void testMultipleMonstersInDungeon() {
    initialize();
    int monsterCount = 0;
    Ilocation[][] grid = this.gameState.getDungeon();
    for (int i = 0; i < grid.length; i++) {
      for (int j = 0; j < grid[i].length; j++) {
        if (grid[i][j].hasMonster()) {
          monsterCount += 1;
        }
      }
    }
    assertEquals(4, monsterCount);
  }


  @Test public void testSmellOneMonster() {
    initialize();
    Ilocation[][] grid = this.gameState.getDungeon();
    Imonster monster = new Monster();
    grid[0][7].setMonster(monster);
    assertEquals(SmellType.LESSPUNGENT, this.gameState.getSmellType());

    initialize();
    Ilocation[][] grid1 = this.gameState.getDungeon();
    Imonster monster1 = new Monster();
    grid1[1][7].setMonster(monster1);
    assertEquals(SmellType.MOREPUNGENT, this.gameState.getSmellType());
  }

  @Test public void testSmellMultipleMonsters() {
    initialize();
    Ilocation[][] grid = this.gameState.getDungeon();
    Imonster monster = new Monster();
    grid[0][7].setMonster(monster);
    Imonster monster1 = new Monster();
    grid[3][8].setMonster(monster1);
    assertEquals(SmellType.MOREPUNGENT, this.gameState.getSmellType());
  }

  @Test public void testArrows() {
    initialize();
    int arrowCount = 0;
    Ilocation[][] grid = this.gameState.getDungeon();
    for (int i = 0; i < grid.length; i++) {
      for (int j = 0; j < grid[i].length; j++) {
        if (grid[i][j].getArrows() != 0) {
          System.out.println(i + " " + j + " " + grid[i][j].getLocationType());
          arrowCount += grid[i][j].getArrows();
        }
      }
    }
    assertEquals(4, arrowCount);
  }

  @Test public void testPickUpArrows() {
    initialize();
    this.gameState.getPlayerCurrentPosition().setArrows(2);
    this.gameState.pickArrows();
    assertEquals(5, this.gameState.getPlayer().arrowCount());
  }

  @Test public void testShootArrows() {
    initialize();
    assertEquals(3, this.gameState.getPlayer().arrowCount());
    this.gameState.shootArrows(Direction.SOUTH, 2, this.gameState.getPlayerCurrentPosition());
    assertEquals(2, this.gameState.getPlayer().arrowCount());
  }

  @Test public void testArrowTravellingTest() {
    initialize();
    Ilocation[][] grid = this.gameState.getDungeon();
    Imonster monster = new Monster();
    grid[0][8].setMonster(monster);
    assertEquals("Row: 0 Column: 8", this.gameState.shootArrows(Direction.NORTH, 1,
        this.gameState.getPlayerCurrentPosition()).toString());
    this.gameState.movePlayer(Direction.NORTH);
    Imonster monster1 = new Monster();
    grid[1][4].setMonster(monster1);
    assertEquals("Row: 0 Column: 6", this.gameState.shootArrows(Direction.WEST, 2,
        this.gameState.getPlayerCurrentPosition()).toString());
  }

  @Test public void testShootingAndHitting() {
    initialize();
    Ilocation[][] grid = this.gameState.getDungeon();
    grid[0][8].setMonster(new Monster());
    for (int i = 0; i < grid.length; i++) {
      for (int j = 0; j < grid[i].length; j++) {
        if (grid[i][j].hasMonster()) {
          assertEquals(100, grid[i][j].getMonster().getHealth());
        }
      }
    }
    assertEquals(50, this.gameState.shootArrows(Direction.NORTH, 1,
        this.gameState.getPlayerCurrentPosition()).getMonster().getHealth());
  }

  @Test public void testShootButMissMonster() {
    initialize();
    Ilocation[][] grid = this.gameState.getDungeon();
    Imonster monster = new Monster();
    grid[1][4].setMonster(monster);
    this.gameState.shootArrows(Direction.NORTH, 1,
        this.gameState.getPlayerCurrentPosition());
    for (int i = 0; i < grid.length; i++) {
      for (int j = 0; j < grid[i].length; j++) {
        if (grid[i][j].hasMonster()) {
          assertEquals(100, grid[i][j].getMonster().getHealth());
        }
      }
    }
  }

  @Test public void testMonsterKillsPlayer() {
    initialize();
    Ilocation[][] grid = this.gameState.getDungeon();
    Imonster monster = new Monster();
    grid[0][8].setMonster(monster);
    assertTrue(this.gameState.getPlayer().isAlive());
    this.gameState.movePlayer(Direction.NORTH);
    assertFalse(this.gameState.getPlayer().isAlive());
  }

  @Test public void testSurvivingInjuredMonster() {
    random = new Random(12);
    gameState = new GameState(8, 10, 2, 20.5, 3, 5, false, random);
    Ilocation[][] grid = gameState.getDungeon();
    for (int i = 0; i < grid.length; i++) {
      for (int j = 0; j < grid[i].length; j++) {
        if (grid[i][j].hasMonster()) {
          assertEquals(100, grid[i][j].getMonster().getHealth());
        }
      }
    }
    gameState.movePlayer(Direction.EAST);
    gameState.movePlayer(Direction.EAST);
    gameState.movePlayer(Direction.EAST);
    gameState.movePlayer(Direction.EAST);
    gameState.movePlayer(Direction.EAST);
    gameState.movePlayer(Direction.EAST);
    assertEquals(50, gameState.shootArrows(Direction.EAST, 1,
        gameState.getPlayerCurrentPosition()).getMonster().getHealth());
    gameState.movePlayer(Direction.EAST);
    assertTrue(gameState.getPlayer().isAlive());
  }

  @Test public void testMonsterDiesAfterTwoArrowsShot() {
    initialize();
    Ilocation[][] grid = this.gameState.getDungeon();
    for (int i = 0; i < grid.length; i++) {
      for (int j = 0; j < grid[i].length; j++) {
        if (grid[i][j].hasMonster()) {
          assertEquals(100, grid[i][j].getMonster().getHealth());
        }
      }
    }
    Imonster monster = new Monster();
    grid[0][8].setMonster(monster);
    this.gameState.shootArrows(Direction.NORTH, 1, this.gameState.getPlayerCurrentPosition());
    this.gameState.shootArrows(Direction.NORTH, 1, this.gameState.getPlayerCurrentPosition());
    this.gameState.movePlayer(Direction.NORTH);
    assertFalse(this.gameState.getPlayerCurrentPosition().getMonster().isAlive());
  }

  @Test public void testPlayerEnteringCaveAfterMonsterKilled() {
    initialize();
    Ilocation[][] grid = this.gameState.getDungeon();
    for (int i = 0; i < grid.length; i++) {
      for (int j = 0; j < grid[i].length; j++) {
        if (grid[i][j].hasMonster()) {
          assertEquals(100, grid[i][j].getMonster().getHealth());
        }
      }
    }
    Imonster monster = new Monster();
    grid[0][8].setMonster(monster);
    this.gameState.shootArrows(Direction.NORTH, 1, this.gameState.getPlayerCurrentPosition());
    this.gameState.shootArrows(Direction.NORTH, 1, this.gameState.getPlayerCurrentPosition());
    this.gameState.movePlayer(Direction.NORTH);
    assertTrue(this.gameState.getPlayer().isAlive());
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