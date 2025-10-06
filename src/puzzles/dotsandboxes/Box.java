package puzzles.dotsandboxes;

import puzzles.core.Piece;
import puzzles.core.Position;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents a box formed by four edges on the board.
 * Can be claimed by a player when all four edges are claimed.
 */
public final class Box extends Piece {
    private final Position topLeft;
    private final Position bottomRight;
    private final List<Edge> edges;
    private int claimedEdges;

    public Box(int id, Position topLeft, Position bottomRight) {
        super(id);
        if (topLeft == null || bottomRight == null) {
            throw new IllegalArgumentException("Positions cannot be null");
        }
        if (topLeft.row >= bottomRight.row || topLeft.col >= bottomRight.col) {
            throw new IllegalArgumentException("Invalid box dimensions");
        }
        this.topLeft = topLeft;
        this.bottomRight = bottomRight;
        this.edges = new ArrayList<>();
        this.claimedEdges = 0;
    }

    public Position getTopLeft() {
        return topLeft;
    }

    public Position getBottomRight() {
        return bottomRight;
    }

    public List<Edge> getEdges() {
        return Collections.unmodifiableList(edges);
    }

    public void addEdge(Edge edge) {
        if (edge == null) {
            throw new IllegalArgumentException("Edge cannot be null");
        }
        edges.add(edge);
    }

    public int getClaimedEdges() {
        return claimedEdges;
    }

    public boolean isComplete() {
        return claimedEdges == 4;
    }

    /**
     * Check if this box contains a given position.
     */
    public boolean contains(Position pos) {
        return pos.row >= topLeft.row && pos.row <= bottomRight.row &&
               pos.col >= topLeft.col && pos.col <= bottomRight.col;
    }

    /**
     * Update the claimed edges count and claim the box if complete.
     * Returns true if the box was just completed.
     */
    public boolean updateClaimedEdges() {
        claimedEdges = 0;
        for (Edge edge : edges) {
            if (edge.isClaimed()) {
                claimedEdges++;
            }
        }
        
        if (isComplete() && !claimed) {
            // Find the player who claimed the last edge
            for (Edge edge : edges) {
                if (edge.isClaimed()) {
                    claim(edge.getOwner());
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public String getDisplayChar() {
        if (!claimed) {
            return " ";
        }
        return owner.getInitials();
    }

    @Override
    public String toString() {
        return "Box" + id + "(" + topLeft + "-" + bottomRight + ")" + 
               (claimed ? "[" + owner.getName() + "]" : "[unclaimed]");
    }
}
