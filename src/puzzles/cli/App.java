package puzzles.cli;

import puzzles.core.Game;
import puzzles.core.Player;
import puzzles.core.Position;
import puzzles.dotsandboxes.DotsAndBoxesGame;
import puzzles.dotsandboxes.Edge;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

/**
 * Terminal UI supporting both sliding puzzle and dots and boxes games.
 * Welcome → game selection → game-specific setup → gameplay loop → end game options.
 */
public final class App {
    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        println("\nWelcome to the Game Collection!");
        println("Choose your game:");
        println("1. Sliding Puzzle");
        println("2. Dots and Boxes");
        println("");

        while (true) {
            String choice = prompt(in, "Enter your choice (1 or 2): ");
            
            if (choice.equals("1")) {
                playSlidingPuzzle(in);
            } else if (choice.equals("2")) {
                playDotsAndBoxes(in);
            } else {
                println("Invalid choice. Please enter 1 or 2.");
                continue;
            }
            
            String again = prompt(in, "Play another game? (y/n): ").trim().toLowerCase();
            if (!again.equals("y")) {
                println("Thanks for playing! Goodbye!");
                return;
            }
        }
    }

    private static void playSlidingPuzzle(BufferedReader in) throws IOException {
        println("\n=== SLIDING PUZZLE ===");
        String player = prompt(in, "Player name: ");
        int rows = readIntInRange(in, "Number of rows (2 - 8): ", 2, 8);
        int cols = readIntInRange(in, "Number of cols (2 - 8): ", 2, 8);

        // For now, we'll just show a message since we don't have the sliding puzzle implementation
        println("Sliding puzzle game would start here.");
        println("(This would integrate with your existing sliding puzzle code)");
    }

    private static void playDotsAndBoxes(BufferedReader in) throws IOException {
        println("\n=== DOTS AND BOXES ===");
        
        // Get player names
        String player1Name = prompt(in, "Player 1 name: ");
        String player2Name = prompt(in, "Player 2 name: ");
        
        // Ensure different names
        while (player1Name.equals(player2Name)) {
            println("Players must have different names.");
            player2Name = prompt(in, "Player 2 name: ");
        }
        
        // Get board size
        int rows = readIntInRange(in, "Number of rows (2 - 10): ", 2, 10);
        int cols = readIntInRange(in, "Number of cols (2 - 10): ", 2, 10);
        
        // Create players and game
        Player player1 = new Player(player1Name);
        Player player2 = new Player(player2Name);
        
        DotsAndBoxesGame game = new DotsAndBoxesGame();
        game.setPlayers(player1, player2);
        game.newGame(rows, cols);
        
        println("\nGame started! Players take turns claiming edges.");
        println("Enter edges as 'H r c' for horizontal or 'V r c' for vertical.");
        println("Coordinates are 1-based as shown in the board display.");
        println("");

        // Game loop
        while (true) {
            println(game.render());
            
            if (game.isWin()) {
                if (game.isTie()) {
                    println("It's a tie! Both players have " + player1.getScore() + " points.");
                } else {
                    Player winner = game.getWinner();
                    println("Congratulations " + winner.getName() + "! You won with " + 
                           winner.getScore() + " points!");
                }
                break;
            }
            
            // Get edge selection
            String input = prompt(in, game.getCurrentPlayer().getName() + 
                                ", select an edge (H r c or V r c): ");
            
            char type;
            int r, c;
            try {
                String[] parts = input.trim().split("\\s+");
                if (parts.length != 3) throw new IllegalArgumentException();
                
                type = parts[0].toUpperCase().charAt(0);
                if (type != 'H' && type != 'V') throw new IllegalArgumentException();
                
                r = Integer.parseInt(parts[1]);
                c = Integer.parseInt(parts[2]);
            } catch (Exception e) {
                println("Invalid format. Use 'H r c' for horizontal or 'V r c' for vertical edges.");
                continue;
            }
            
            // Apply the move
            boolean success = game.applyMove(type, r, c);
            if (!success) {
                println("Invalid move. That edge is already claimed or out of range.");
            }
        }
    }

    // ---- Helper methods ----
    private static String prompt(BufferedReader in, String msg) throws IOException {
        System.out.print(msg);
        String s = in.readLine();
        return (s == null) ? "" : s.trim();
    }

    private static void println(String s) { 
        System.out.println(s); 
    }

    private static int readIntInRange(BufferedReader in, String msg, int lo, int hi) throws IOException {
        while (true) {
            String s = prompt(in, msg);
            Integer v = parseIntOrNull(s);
            if (v != null && v >= lo && v <= hi) return v;
            println("Please enter an integer in [" + lo + ", " + hi + "].");
        }
    }

    private static Integer parseIntOrNull(String s) {
        try { 
            return Integer.parseInt(s.trim()); 
        } catch (Exception e) { 
            return null; 
        }
    }
}
