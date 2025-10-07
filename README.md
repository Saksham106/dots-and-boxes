# CS611-Assignment 2
## Dots and Boxes
---------------------------------------------------------------------------
- Name: Edaad Azman
- Email: edaad@bu.edu
- Student ID: U38459100

- Name: Saksham Goel
- Email: sakshamg@bu.edu
- Student ID: U45400025

## Files
---------------------------------------------------------------------------

### CLI Package
App.java: Main application entry point that delegates all game logic to the Game class. 

### Core Package
Game.java: Abstract base class implementing the template method pattern for all board games. Handles the main game flow including setup, game loop, input processing, and replay functionality. Contains static method for game collection menu.

Board.java: Generic interface defining common board operations like dimensions, grid representation, and solved state checking. Designed for scalability across different grid-based games.

Player.java: Manages player information including name, scoring, and input handling. Serves as an input handler using Scanner for user interaction while maintaining player state.

Piece.java: Abstract base class for game pieces like edges and boxes, that can be claimed by players. Provides common functionality for ownership, claiming mechanics, and display representation.

Position.java: Coordinate class representing row/column positions. 

Tile.java: Generic tile representation class (currently unused in this implementation). Designed for grid-based games that need unique tile identifiers and position tracking.

### Dots and Boxes Package
DotsAndBoxesGame.java: Main game implementation extending the Game abstract class. Manages two player gameplay, turn mechanics, scoring, and game specific logic.

DotsAndBoxesBoard.java: Board implementation using efficient 2D arrays for edge states and box ownership. 

Box.java: Represents a box formed by four edges. Tracks edge completion status and handles automatic claiming when all four edges are completed by players.

Edge.java: Represents edges between dots that can be claimed by players. Supports both horizontal and vertical orientations with position tracking and adjacency checking.

## Notes
---------------------------------------------------------------------------

### Design Choices:

- The abstract Game class provides a template for all board games, allowing easy extension for new game types while maintaining consistent game flow and user experience.

- Separated concerns with CLI handling only program entry, core classes handling game framework, and specific game logic isolated in game-specific packages.

- Board interface allows different board implementations, making the system extensible for various grid-based games beyond dots and boxes.

- **Encapsulation**: Each class has clear responsibilities with controlled access through public methods. DotsAndBoxesBoard encapsulates game state, Player handles input, and Game manages flow.

- **Polymorphism**: The Game abstract class works with any Board implementation, and the framework can handle different game types through method overriding.

### Cool Features / Creative Choices:

- Implemented a smart UI conversion system for 1-based UI coordinates which are user friendly and 0-based internal coordinates for more intuitive gameplay.

- Smart algorithm that automatically detects and claims boxes when the fourth edge is completed, with proper scoring system and turn management.

- Shows player initials in completed boxes for clear ownership visualization on the game board.

## How to compile and run
---------------------------------------------------------------------------

### Compilation
1. Navigate to the project directory:
   ```bash
   $ cd dots-and-boxes
   ```

2. Compile all Java files into ./out:
   ```bash
   $ mkdir -p out
   $ javac -d out $(find src -name "*.java")
   ```

   # Compile all sources into ./out (Java 8)


### Running the Application
1. Run the main application:
   ```bash
   $ java -cp out puzzles.cli.App
   ```

2. Follow the on-screen prompts to:
   - Choose between available games
   - Set up player names
   - Play the game
   - Choose to play again or exit


## Input/Output Example
---------------------------------------------------------------------------

```
