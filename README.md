# Minesweeper Simulator
Minesweeper is a single-player puzzle video game. The objective of the game is to clear a rectangular board containing hidden "mines" or bombs without detonating any of them, with help from clues about the number of neighboring mines in each field. The game originates from the 1960s, and it has been written for many computing platforms in use today. It has many variations and offshoots.

## Special thanks to these contributors
<a href="https://github.com/nmtrang"><img src="https://avatars.githubusercontent.com/u/39549148?v=4" alt="Trang Nguyen" width = 60px></a>
<a href="https://github.com/MrCat-2510"><img src="https://avatars.githubusercontent.com/u/58814046?v=4" alt="Tung Phan" width = 60px></a>
<a href="https://github.com/TraLe-ITDSIU19058"><img src="https://avatars.githubusercontent.com/u/71242240?v=4" alt="Tra Le" width = 60px></a>
<a href="https://github.com/phuctran2912"><img src="https://avatars.githubusercontent.com/u/71021762?v=4" alt="Phuc Tran" width = 60px></a>

## Tools used

Language: Java 15

Code editor: IntelliJ IDEA


## How to play?

The game can be run in 3 ways

1. Clone the project and open it in your code editor in order to run the Minesweeper file which is the main file
2. Double-click Minesweeper.jar file (make sure your Java is of version 15)
3. Navigate to the folder contains Minesweeper.jar file and run this command line

```bash
java -jar Minesweeper.jar
```

## Classes design

So, how did we design and organize classes?
Depending on every objects in the game, we extracted the most essential elements in the game and packaged them into these classes:

### Game.java
This is the entry point and UI design of the game
It is built with the layout of the Java Swing
It handles basic user input, specifically, clicking
It contains some action events involved in the game (mostly click events)

Note: Given that the grid consists of individual cells, all of which can be thought of as objects, with each cell sharing certain basic properties/functionalities so it will make sense to do some type of class inheritance in the program.

### Cell.java (parent class)
This is where we store properties of the most basic cell like whether it’s covered, flagged or what type of that cell: is it BOMB cell? Is it BOMB NEIGHBOR cell? Or is it EMPTY cell?

BombCell (extends Cell)

NeighborOfBombCell (extends Cell)

EmptyCell (extends Cell)

How is this EmptyCell initiated? It has 2 ways of creation:

- Whenever the game starts to begin, we have all the empty cells, of course

- When the player starts to play the game, some cells will be covered by some sort of numbers or flagged, some other cells will be left as remaining empty cells.


### Board.java

- Combines every different small classes into one big view and is used as a main controller.

- Handles user actions (either left or right click) and performs or displays desired moves/information accordingly using paint and event listeners

- Stores all images of these different types of cells in a map and displays images according to cells clicked

- Contains recursive function to clear a bomb-free region of empty cells and is called when user clicks on an empty cell

- Stores user moves whenever user clicks the board, and handles the “undo” functionality whenever the user desires to remove a move


### CellType.java and ImageName.java (enums)

Enums used to represent the different types of cells and images in a cleaner, more readable way

- CellType has Bomb, BombNeighbor, Empty
- ImageName has Empty, Covered, Marked, Wrongmarked, Bomb

### Input.java and CustomMap.java
Has two variable: size of map and number of mines
- If the user choose 8x8 map: return a map with width and height is equal 8 and the number of mines is 10

- If the user choose 16x16 map: return a map with width and height is equal 16 and the number of mines is 10
- If the user choose Custom (new feature):

    -  Open the JFrame CustomMap: let the user input the value of size and input the number of mines (0 is invalid)
    -  Cannot set the size lower than 6
    -  Cannot input Invalid value
    

## Algorithms brief walk-through
### 2D Array

Since Minesweeper takes place on a 16x16 grid (default), we used a 2-D array of “Cell” objects to considered as the grid to store the state of each cell in that grid in terms of which cells contain bombs, if they are empty, or if they are neighbor of bomb cells (i.e “number’ cells). “Cell” was a parent class we created that stores the properties of each cell and handles basic cell behavior depending on its type. The 2-D array was used whenever a new game was started, in which the 2-D array would be randomly filled with bomb cells, number cells (cells that are neighbors to bomb cells indicating how many bombs are in the vicinity), or empty cells. We would traverse through a double for loop that represents the rows and columns of the 2-D array or call individual elements of the 2-D array to initialize Cell objects, handle different user actions on a specific cell. My use of a 2-D array was extremely fundamental in organizing my overall structure of the game board and locating user actions/specifying desired locations to handle game logic.

### Collection

Players can "undo" their moves when they hit the "undo" button, unless hitting a bomb, which then the game ends. We fully take advantage of the LIFO property of Stack so it would be best for us to implement that principle to store the players’ move in order to undo the most recent step by popping it out of the Stack. In other words, removing the player’s move from Stack and returning the state of the cell depending on the player’s last click. The other function of Stack - push() is also used to add steps to the Stack whenever the player left-click a number of empty cells, and right-click to flag/unflag a cell, which shows the player’s most recent move. The player can undo any number of moves for any type of move, which includes clicking on flagged cells, empty cells, and neighbor cells, but for bomb cells, the undo() function is disabled (because that is plainly cheating, of course!) by “clearing” the Stack.

### Recursion

We utilize recursion to reveal and clear a mine-free region of cells. In other words, when a player clicks on a cell that has no bombs with adjacent cells also bomb-free so all adjacent cells are automatically cleared and the region with bomb-free cells is revealed up until the cell that is connected to a bomb.


## Enjoy!!!



   
