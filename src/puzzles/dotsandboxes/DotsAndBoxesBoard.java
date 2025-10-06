package puzzles.dotsandboxes;

import puzzles.core.Board;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Board implementation for Dots and Boxes game.
 * Uses a clean 2D array approach for edges and box ownership.
 */
public final class DotsAndBoxesBoard implements Board {
    // m x n boxes => (m+1) x n horizontal edges, m x (n+1) vertical edges
    private final int m; // rows of boxes
    private final int n; // cols of boxes

    private final boolean[][] h; // h[r][c] => edge between (r,c) and (r,c+1), r in [0..m], c in [0..n-1]
    private final boolean[][] v; // v[r][c] => edge between (r,c) and (r+1,c), r in [0..m-1], c in [0..n]
    private final char[][] owner; // owner[r][c] => box owner initial or '\0'

    public DotsAndBoxesBoard(int rows, int cols) {
        if (rows < 2 || rows > 9 || cols < 2 || cols > 9) {
            throw new IllegalArgumentException("Size must be between 2x2 and 9x9");
        }
        this.m = rows;
        this.n = cols;
        this.h = new boolean[m + 1][n];
        this.v = new boolean[m][n + 1];
        this.owner = new char[m][n];
        for (char[] row : owner) {
            for (int i = 0; i < row.length; i++) {
                row[i] = '\0';
            }
        }
    }

    public int rows() { 
        return m; 
    }
    
    public int cols() { 
        return n; 
    }

    public boolean isFull() {
        // Full when all boxes have owners
        for (int r = 0; r < m; r++) {
            for (int c = 0; c < n; c++) {
                if (owner[r][c] == '\0') return false;
            }
        }
        return true;
    }

    // Claim an edge. type 'H' or 'V'. r,c are 1-based from the UI.
    // Returns the number of boxes completed by this claim (0,1, or 2).
    public int claim(char type, int r, int c, char playerInitial) {
        type = Character.toUpperCase(type);
        if (type == 'H') {
            // UI r in [1..m+1], c in [1..n]
            int rr = r - 1, cc = c - 1;
            if (rr < 0 || rr > m || cc < 0 || cc >= n) throw new IllegalArgumentException("Out of range");
            if (h[rr][cc]) throw new IllegalStateException("Edge already taken");
            h[rr][cc] = true;
            return fillBoxesAfterHorizontal(rr, cc, playerInitial);
        } else if (type == 'V') {
            // UI r in [1..m], c in [1..n+1]
            int rr = r - 1, cc = c - 1;
            if (rr < 0 || rr >= m || cc < 0 || cc > n) throw new IllegalArgumentException("Out of range");
            if (v[rr][cc]) throw new IllegalStateException("Edge already taken");
            v[rr][cc] = true;
            return fillBoxesAfterVertical(rr, cc, playerInitial);
        } else {
            throw new IllegalArgumentException("Type must be H or V");
        }
    }

    private int fillBoxesAfterHorizontal(int rr, int cc, char p) {
        int made = 0;
        // Box above: (rr-1, cc)
        if (rr - 1 >= 0 && isBoxComplete(rr - 1, cc) && owner[rr - 1][cc] == '\0') {
            owner[rr - 1][cc] = p; made++;
        }
        // Box below: (rr, cc)
        if (rr < m && isBoxComplete(rr, cc) && owner[rr][cc] == '\0') {
            owner[rr][cc] = p; made++;
        }
        return made;
    }

    private int fillBoxesAfterVertical(int rr, int cc, char p) {
        int made = 0;
        // Box left:  (rr, cc-1)
        if (cc - 1 >= 0 && isBoxComplete(rr, cc - 1) && owner[rr][cc - 1] == '\0') {
            owner[rr][cc - 1] = p; made++;
        }
        // Box right: (rr, cc)
        if (cc < n && isBoxComplete(rr, cc) && owner[rr][cc] == '\0') {
            owner[rr][cc] = p; made++;
        }
        return made;
    }

    private boolean isBoxComplete(int r, int c) {
        return h[r][c] && h[r + 1][c] && v[r][c] && v[r][c + 1];
    }

    public String render(char p1, char p2) {
        StringBuilder sb = new StringBuilder();
        sb.append("   Dots & Boxes ").append(m).append("x").append(n).append("\n");
        sb.append("   Input: H r c  (1<=r<=" + (m + 1) + ", 1<=c<=" + n + ")\n");
        sb.append("          V r c  (1<=r<=" + m + ", 1<=c<=" + (n + 1) + ")\n\n");

        // Print dots with horizontal edges and boxes rows
        for (int r = 0; r <= m; r++) {
            // Dots row with horizontal edges
            sb.append("   ");
            for (int c = 0; c < n; c++) {
                sb.append("+");
                sb.append(h[r][c] ? "---" : "   ");
            }
            sb.append("+\n");

            if (r < m) {
                // Boxes row with vertical edges and owners
                sb.append("   ");
                for (int c = 0; c <= n; c++) {
                    sb.append(v[r][c] ? "|" : " ");
                    if (c < n) {
                        char o = owner[r][c];
                        char display = (o == '\0') ? ' ' : o;
                        sb.append(" ").append(display).append(" ");
                    }
                }
                sb.append("\n");
            }
        }
        return sb.toString();
    }

    @Override
    public List<List<Integer>> asGrid() {
        // For dots and boxes, we'll return a simple representation
        List<List<Integer>> grid = new ArrayList<>();
        for (int r = 0; r < m; r++) {
            List<Integer> row = new ArrayList<>();
            for (int c = 0; c < n; c++) {
                row.add(r * n + c); // Box ID
            }
            grid.add(Collections.unmodifiableList(row));
        }
        return Collections.unmodifiableList(grid);
    }

    @Override
    public boolean isSolved() {
        return isFull();
    }
}
