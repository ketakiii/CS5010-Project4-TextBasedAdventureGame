package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

/**
 * This class defines the model.GameState class and its methods.
 */
public class GameState implements IgameState {

  private final Grid dungeon;
  private Iplayer player;
  private final int row;
  private final int column;
  private final int newConnections;
  private final int numberOfMonsters;
  private final double cavesWithMoreTreasure;
  private final Random random;
  private int numberOfArrows;
  private final Map<Direction, Direction> complementMap;
  private boolean playerHealthFlag = false;
  private boolean monsterHealthFlag = false;

  /**
   * Constructor of the class model.GameState.
   * @param row row
   * @param column column
   * @param newConnections new Connections to be made in the grid
   * @param cavesWithMoreTreasure Percentage of caves to add treasure in the dungeon
   * @param numberOfMonsters number of monsters to be added  in the dungeon
   * @param numberOfArrows number of arrows to be added in the dungeon
   * @param isWrapped is the dungeon wrapped or not
   * @param random random
   */
  public GameState(int row, int column, int newConnections, double cavesWithMoreTreasure,
      int numberOfMonsters, int numberOfArrows, boolean isWrapped, Random random) {
    if (row <= 6 || column <= 6) {
      throw new IllegalArgumentException("Check the coordinates!");
    }
    if (newConnections < 0) {
      throw new IllegalArgumentException("New connections can not be less than 0");
    }
    if (cavesWithMoreTreasure < 0) {
      throw new IllegalArgumentException("% of caves that should have treasures "
          + "added should be more than 0!");
    }
    if (numberOfMonsters < 1) {
      throw new IllegalArgumentException("There should be at least 1 monster");
    }
    if (random == null) {
      throw new IllegalArgumentException("Random is null!");
    }
    this.row = row;
    this.column = column;
    this.random = random;
    this.newConnections = newConnections;
    this.numberOfMonsters = numberOfMonsters;
    this.cavesWithMoreTreasure = cavesWithMoreTreasure;
    this.numberOfArrows = numberOfArrows;
    this.complementMap = new HashMap<>();
    complementMap.put(Direction.EAST, Direction.WEST);
    complementMap.put(Direction.WEST, Direction.EAST);
    complementMap.put(Direction.NORTH, Direction.SOUTH);
    complementMap.put(Direction.SOUTH, Direction.NORTH);
    this.dungeon = new Grid(row, column, newConnections, cavesWithMoreTreasure,
        numberOfMonsters, numberOfArrows, false,  random);
    this.player = new Player("model.Player");
    player.setCurrentLocation(this.dungeon.getPlayerStartLocation());
  }

  @Override public Iplayer getPlayer() {
    return this.player;
  }

  @Override public boolean isGameOver() {
    return !this.player.isAlive() || this.playerWins();
  }

  @Override public List<Direction> getAvailableStartPosition() {
    Map<Direction, Ilocation> neighbours =
        this.dungeon.getNeighbours(this.player.getCurrentLocation());
    List<Direction> listOfDirections = new ArrayList<>();
    for (Direction d : neighbours.keySet()) {
      listOfDirections.add(d);
    }
    return listOfDirections;
  }

  @Override public Ilocation getPlayerStartPosition() {
    return this.dungeon.getPlayerStartLocation();
  }

  @Override public Ilocation getPlayerEndPosition() {
    return this.dungeon.getPlayerEndLocation();
  }

  @Override public Ilocation getPlayerCurrentPosition() {
    return this.player.getCurrentLocation();
  }

  @Override public Ilocation movePlayer(Direction d) {
    if (this.player.isAlive()) {
      movePlayerHelper(d);
      this.player.setSmell(this.getTypeOfSmell());
      this.updatePlayerHealthStatus();
      this.playerWins();
      return this.getPlayerCurrentPosition();
    } else {
      throw new IllegalStateException("The player can not move since it has been killed by an "
          + "Otyugh!");
    }
  }

  @Override public Ilocation shootArrows(Direction direction, int distance,
      Ilocation currentLocation) {
    if (this.player.arrowCount() < 1) {
      throw new IllegalArgumentException("Arrow count is less than 1.");
    }
    this.player.addArrows(-1);
    Ilocation shootArrowsHelper = this.shootArrowsHelper(direction, distance, currentLocation);
    if (shootArrowsHelper == null) {
      return null;
    } else {
      if (shootArrowsHelper.hasMonster()) {
        this.monsterHealthFlag = true;
        shootArrowsHelper.getMonster().reduceHealth();
      }
    }
    return shootArrowsHelper;
  }


  @Override public Ilocation[][] getDungeon() {
    return this.dungeon.getGrid();
  }

  @Override public String toString() {
    return "model.GameState{" + "dungeon=" + dungeon + ", player=" + player + ", row=" + row
        + ", column=" + column + ", newConnections=" + newConnections + ", cavesWithMoreTreaure="
        + cavesWithMoreTreasure + ", random=" + random + '}';
  }

  @Override public String toStringStatus() {
    return this.player.toStringStatus();
  }

  @Override public Direction helperDirection(String input) {
    Map<String, Direction> mapDirections = new HashMap<>();
    mapDirections.put("N", Direction.NORTH);
    mapDirections.put("S", Direction.SOUTH);
    mapDirections.put("E", Direction.EAST);
    mapDirections.put("W", Direction.WEST);
    return mapDirections.get(input);
  }

  @Override public void pickTreasure() {
    if (this.getPlayerCurrentPosition().getTreasure() != null) {
      this.player.addTreasure();
      this.getPlayerCurrentPosition().removeTreasure();
    }
  }

  @Override public void pickArrows() {
    if (this.getPlayerCurrentPosition().getArrows() != 0) {
      this.player.addArrows(this.getPlayerCurrentPosition().getArrows());
      this.getPlayerCurrentPosition().removeArrows();
    }
  }

  @Override public boolean playerWins() {
    if (this.getPlayerCurrentPosition() == this.getPlayerEndPosition() && this.player.isAlive()) {
      return true;
    } else {
      return false;
    }
  }

  @Override public SmellType getSmellType() {
    return this.getTypeOfSmell();
  }

  @Override public boolean updatePlayerHealth() {
    return this.playerHealthFlag;
  }

  @Override public boolean updateMonsterHealth() {
    return this.monsterHealthFlag;
  }

  // private methods
  private Ilocation shootArrowsHelper(Direction direction, int distance,
      Ilocation currentLocation) {
    Map<Direction, Ilocation> neighbours =
        this.dungeon.getNeighbours(currentLocation);
    Ilocation location = neighbours.get(direction);
    if (location == null) {
      return null;
    }
    if (location.getLocationType() == LocationType.CAVE) {
      distance -= 1;
      if (distance > 0) {
        return shootArrowsHelper(direction, distance, location);
      } else {
        return location;
      }
    } else if (location.getLocationType() == LocationType.TUNNEL) {
      Direction complement = complementMap.get(direction);
      for (Direction d : location.getNeighbours().keySet()) {
        if (complement != d) {
          return shootArrowsHelper(d, distance, location);
        }
      }
    }
    return location;
  }

  private Ilocation movePlayerHelper(Direction d) {
    Map<Direction, Ilocation> neighbours =
        this.dungeon.getNeighbours(this.player.getCurrentLocation());
    Ilocation location = neighbours.get(d);
    if (location == null) {
      return null;
    }
    this.player.setCurrentLocation(location);
    //    if (location.getLocationType() == LocationType.TUNNEL) {
    //      Direction complement = complementMap.get(d);
    //      for (Direction direction : location.getNeighbours().keySet()) {
    //        if (complement != direction) {
    //          return movePlayerHelper(direction);
    //        }
    //      }
    //    }
    return location;
  }


  private int countsNeighbouringMonsters() {
    Set<Ilocation> neighbourList = this.dungeon.getNeighbourList();
    neighbourList.clear();
    this.dungeon.getNnearestNeighbours(this.getPlayerCurrentPosition(), 1);
    Set<Ilocation> dist1 = this.dungeon.getNeighbourList();
    int monsterProximity = 0;
    for (Ilocation location : dist1) {
      if (location.hasMonster()) {
        monsterProximity += 2;
      }
    }
    neighbourList.clear();
    this.dungeon.getNnearestNeighbours(this.getPlayerCurrentPosition(), 2);
    Set<Ilocation> dist2 = this.dungeon.getNeighbourList();
    for (Ilocation location2 : dist2) {
      if (location2.hasMonster()) {
        monsterProximity += 1;
      }
    }
    return monsterProximity;
  }

  private SmellType getTypeOfSmell() {
    int neighbouringMonsters = this.countsNeighbouringMonsters();
    if (neighbouringMonsters >= 2) {
      return SmellType.MOREPUNGENT;
    } else if (neighbouringMonsters > 0) {
      return SmellType.LESSPUNGENT;
    } else {
      return SmellType.NOPUNGENT;
    }
  }

  private void updatePlayerHealthStatus() {
    Random rand = new Random(12);
    if (this.getPlayerCurrentPosition().hasMonster()) {
      if (this.getPlayerCurrentPosition().getMonster().getHealth() == 50) {
        double doubleRandom = rand.nextDouble();
        if (doubleRandom < 0.5) {
          this.playerHealthFlag = true;
          this.player.reduceHealth();
        }
      } else {
        this.playerHealthFlag = true;
        this.player.reduceHealth();
      }
    }
  }
}
