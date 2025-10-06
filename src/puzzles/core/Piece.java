package puzzles.core;

/**
 * Abstract base class for game pieces (edges, boxes, etc.).
 * Provides common functionality for pieces that can be claimed by players.
 */
public abstract class Piece {
    protected final int id;
    protected Player owner;
    protected boolean claimed;

    protected Piece(int id) {
        if (id < 0) {
            throw new IllegalArgumentException("Piece ID cannot be negative");
        }
        this.id = id;
        this.owner = null;
        this.claimed = false;
    }

    public int getId() {
        return id;
    }

    public Player getOwner() {
        return owner;
    }

    public boolean isClaimed() {
        return claimed;
    }

    public void claim(Player player) {
        if (player == null) {
            throw new IllegalArgumentException("Player cannot be null");
        }
        if (claimed) {
            throw new IllegalStateException("Piece is already claimed");
        }
        this.owner = player;
        this.claimed = true;
    }

    public void unclaim() {
        this.owner = null;
        this.claimed = false;
    }

    public abstract String getDisplayChar();

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Piece piece = (Piece) obj;
        return id == piece.id;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + id + (claimed ? "(" + owner.getName() + ")" : "(unclaimed)");
    }
}
