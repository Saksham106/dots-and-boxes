package puzzles.core;

/**
 * Minimal game engine interface separating model and I/O.
 * The CLI will orchestrate prompts and call these methods.
 */
public interface Game {
    /** Reset or start a new game using current config and generate a solvable board. */
    void newGame(int rows, int cols);

    /** Render the current board as a string for terminal output. */
    String render();

    /**
     * Attempt to apply a player action given a tile value.
     * Returns true if a legal move was performed; false otherwise (no move consumed).
     */
    boolean applyMove(int tileValue);

    /** @return true if the game is in a terminal (win) state. */
    boolean isWin();
}
