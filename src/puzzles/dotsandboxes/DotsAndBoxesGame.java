package puzzles.dotsandboxes;

import puzzles.core.Game;
import puzzles.core.Player;
import puzzles.core.Position;

import java.util.ArrayList;
import java.util.List;

/**
 * Game implementation for Dots and Boxes.
 * Manages two players, turns, and game logic.
 */
public final class DotsAndBoxesGame implements Game {
    private DotsAndBoxesBoard board;
    private Player player1;
    private Player player2;
    private Player currentPlayer;
    private boolean gameStarted;
    private int currentPlayerIndex;

    public DotsAndBoxesGame() {
        this.board = null;
        this.player1 = null;
        this.player2 = null;
        this.currentPlayer = null;
        this.gameStarted = false;
        this.currentPlayerIndex = 0;
    }

    public void setPlayers(Player player1, Player player2) {
        if (player1 == null || player2 == null) {
            throw new IllegalArgumentException("Both players must be non-null");
        }
        if (player1.equals(player2)) {
            throw new IllegalArgumentException("Players must be different");
        }
        this.player1 = player1;
        this.player2 = player2;
        this.currentPlayer = player1;
        this.currentPlayerIndex = 0;
    }

    @Override
    public void newGame(int rows, int cols) {
        if (player1 == null || player2 == null) {
            throw new IllegalStateException("Players must be set before starting a new game");
        }
        
        this.board = new DotsAndBoxesBoard(rows, cols);
        this.player1.resetScore();
        this.player2.resetScore();
        this.currentPlayer = player1;
        this.currentPlayerIndex = 0;
        this.gameStarted = true;
    }

    @Override
    public String render() {
        if (board == null) {
            return "(no board)";
        }

        StringBuilder sb = new StringBuilder();
        sb.append(board.render(player1.getInitials().charAt(0), player2.getInitials().charAt(0)));
        
        // Add score information
        sb.append("\nScores: ").append(player1.getName()).append(": ").append(player1.getScore())
          .append(", ").append(player2.getName()).append(": ").append(player2.getScore()).append("\n");
        
        if (gameStarted) {
            sb.append("Current player: ").append(currentPlayer.getName()).append("\n");
        }

        return sb.toString();
    }

    @Override
    public boolean applyMove(int tileValue) {
        // This method is not used for dots and boxes
        // We'll use a different method for edge selection
        return false;
    }

    /**
     * Apply a move by claiming an edge.
     * Returns true if the move was successful.
     */
    public boolean applyMove(char type, int r, int c) {
        if (!gameStarted || board == null) {
            return false;
        }

        try {
            char playerInitial = currentPlayer.getInitials().charAt(0);
            int boxesCompleted = board.claim(type, r, c, playerInitial);
            
            if (boxesCompleted > 0) {
                // Player gets points and another turn
                currentPlayer.addScore(boxesCompleted);
                return true;
            } else {
                // Switch to the other player
                switchPlayer();
                return true;
            }
        } catch (IllegalArgumentException | IllegalStateException e) {
            return false;
        }
    }

    private void switchPlayer() {
        currentPlayerIndex = (currentPlayerIndex + 1) % 2;
        currentPlayer = (currentPlayerIndex == 0) ? player1 : player2;
    }

    @Override
    public boolean isWin() {
        return gameStarted && board != null && board.isFull();
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public Player getWinner() {
        if (!isWin()) {
            return null;
        }
        
        if (player1.getScore() > player2.getScore()) {
            return player1;
        } else if (player2.getScore() > player1.getScore()) {
            return player2;
        } else {
            return null; // Tie
        }
    }

    public boolean isTie() {
        return isWin() && getWinner() == null;
    }

    public DotsAndBoxesBoard getBoard() {
        return board;
    }
}
