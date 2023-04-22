# Project Description

In this part of our project, we will be implementing a simple text-based controller that can be used to play the game that we developed during Project 3. In addition, we will be adding enhancements to the dungeon to make the game more interactive and fun.

#UML

Updated UML
![UML](../uml/project4/updatedProject4uml.png)

Old UML
![UML](../uml/project4/Project4uml.png)

# Changes Made to the UML
* A new enum - SmellType has been added to set the smell type of the player at the current location based on the neighbouring monsters and their proximity. 
* Added a new interface - Imonster which represents the Otyughs and their features and characteristics.
* A few other changes have been made in the GameState class like adding more methods to support functionality. 

# How to Run the Jar 
java -jar out/artifacts/Project4_jar/Project4.jar

# How to run a program

```
Example Run 1 : This has been shown in the file named 
ExampleRun1 under the res folder which shows the player 
traverse through the dungeon, pick up treasure they get 
along the way, testing that the player has been assigned 
3 arrows at the beginning of the game. The player also 
starts smelling the Otyugh when it reaches 2 positions 
away from it and finally shoot the Otyugh twice to kill 
it and enter the end location to win the game. 
```

```
Example Run 2 : This has been shown in the file named
ExampleRun2 under the res folder which shows the player
traverse through the dungeon, pick treasures, shoot an 
arrow to miss the Otyugh, and moves into the end location
where it gets killed by the Otyugh. 
```

```
Example Run 3 : This has been shown in the file named 
ExampleRun3 under the res folder which shows the player 
traverse through the dungeon, move towards the end 
location. Then it shoots the Otyugh once before entering 
the end cave - given a 50% chance of surving an injured 
Otyugh the Player is rescued and wins the game since
it is at the end location alive.
```

# Assumptions
* The grid length should be more than 5 since the minimum distance between the start and the end location is 5.
* The grid width should be more than 5 since the minimum distance between the start and the end location is 5.
* The interconnectivity can be equal to or greater than 0.
* The percentage of treasure to be added to the caves randomly should be a number greater than 0.
* Dungeon are of two types : wrapped and non-wrapped.
* If the player reaches the end location alive it wins. 

# Limitations
* Need Java version 11 or greater.
* Can not test for a grid smaller than 7x7 since a minimum distance of 5 needs to be maintained between the start and the end locations.

#Citations
1. [Canvas Question](https://northeastern.instructure.com/courses/136753/assignments/1707745)
2. [MST Algorithm](https://www.geeksforgeeks.org/prims-minimum-spanning-tree-mst-greedy-algo-5/)
3. [GeeksForGeeks](https://www.geeksforgeeks.org/)
4. [Google](https://www.google.com/)
5. [StackOverflow](https://stackoverflow.com/)
6. [Java Documentation](https://docs.oracle.com/en/java/)