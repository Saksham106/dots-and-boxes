package puzzles.dotsandboxes;

import puzzles.core.Piece;
import puzzles.core.Position;

/**
 * Represents an edge between two dots on the board.
 * Can be horizontal or vertical and can be claimed by players.
 */
public final class Edge extends Piece {
    public enum Type {
        HORIZONTAL, VERTICAL
    }

    private final Type type;
    private final Position start;
    private final Position end;

    public Edge(int id, Type type, Position start, Position end) {
        super(id);
        if (type == null) {
            throw new IllegalArgumentException("Edge type cannot be null");
        }
        if (start == null || end == null) {
            throw new IllegalArgumentException("Start and end positions cannot be null");
        }
        this.type = type;
        this.start = start;
        this.end = end;
    }

    public Type getType() {
        return type;
    }

    public Position getStart() {
        return start;
    }

    public Position getEnd() {
        return end;
    }

    public boolean isHorizontal() {
        return type == Type.HORIZONTAL;
    }

    public boolean isVertical() {
        return type == Type.VERTICAL;
    }

    @Override
    public String getDisplayChar() {
        if (!claimed) {
            return isHorizontal() ? "-" : "|";
        }
        return isHorizontal() ? "=" : "‖";
    }

    /**
     * Check if this edge is adjacent to a given position.
     */
    public boolean isAdjacentTo(Position pos) {
        return start.equals(pos) || end.equals(pos);
    }

    /**
     * Get the other end of this edge given one end.
     */
    public Position getOtherEnd(Position oneEnd) {
        if (start.equals(oneEnd)) {
            return end;
        } else if (end.equals(oneEnd)) {
            return start;
        } else {
            throw new IllegalArgumentException("Position is not an end of this edge");
        }
    }

    @Override
    public String toString() {
        return "Edge" + id + "(" + type + "," + start + "-" + end + ")" + 
               (claimed ? "[" + owner.getName() + "]" : "[unclaimed]");
    }
}
