package puzzles.core;

import java.util.List;

/**
 * Generic turn-based board interface.
 * Designed for scalability/extendability (other grid games can implement this).
 */
public interface Board {
    int rows();
    int cols();

    /** Return an immutable snapshot of the board as a row-major 2D list of integers. */
    List<List<Integer>> asGrid();

    /** Is the board in a game-defined goal state? */
    boolean isSolved();
}
