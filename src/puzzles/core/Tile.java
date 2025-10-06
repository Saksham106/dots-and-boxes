package puzzles.core;

/**
 * Represents a dot/tile on the game board.
 * Immutable position with unique identifier.
 */
public final class Tile {
    private final int id;
    private final Position position;

    public Tile(int id, Position position) {
        if (id < 0) {
            throw new IllegalArgumentException("Tile ID cannot be negative");
        }
        if (position == null) {
            throw new IllegalArgumentException("Position cannot be null");
        }
        this.id = id;
        this.position = position;
    }

    public int getId() {
        return id;
    }

    public Position getPosition() {
        return position;
    }

    public int getRow() {
        return position.row;
    }

    public int getCol() {
        return position.col;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Tile tile = (Tile) obj;
        return id == tile.id;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }

    @Override
    public String toString() {
        return "Tile" + id + position;
    }
}
