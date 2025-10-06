package puzzles.core;

/**
 * Represents a player in a game with name and score tracking.
 * Immutable for thread safety and clean design.
 */
public final class Player {
    private final String name;
    private int score;

    public Player(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Player name cannot be null or empty");
        }
        this.name = name.trim();
        this.score = 0;
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    public void addScore(int points) {
        if (points < 0) {
            throw new IllegalArgumentException("Score cannot be negative");
        }
        this.score += points;
    }

    public void resetScore() {
        this.score = 0;
    }

    public String getInitials() {
        if (name.length() <= 2) {
            return name.toUpperCase();
        }
        return name.substring(0, 2).toUpperCase();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Player player = (Player) obj;
        return name.equals(player.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public String toString() {
        return name + " (" + score + " points)";
    }
}
