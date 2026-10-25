class Solution {
    int m;
    int n;

    public int closedIsland(int[][] grid) {
        m = grid.length;//total number of rows
        n = grid[0].length;//total number of columns
        
        /*
         * =========================================================================
         * BIG PICTURE / INTUITION:
         * =========================================================================
         * so see... what is given in the problem?
         * 0 represents LAND.
         * 1 represents WATER.
         * 
         * and what defines a "closed island"?
         * an island is a connected component of 0s (land) that is 100% surrounded 
         * by 1s (water) in all 4 directions (up, down, left, right)!
         * 
         * now think... when is an island NOT closed?
         * if ANY cell of that island touches the boundary or goes out of bounds!
         * because if it touches the boundary, it is exposed to the edge of the grid,
         * NOT surrounded by water!
         * 
         * so the idea is super clean:
         * we iterate through every cell in the grid.
         * whenever we spot unvisited land (grid[i][j] == 0):
         * we launch a DFS from that cell to explore the entire island component!
         * - if the DFS confirms that EVERY branch hit water (1) and NONE went out of bounds,
         *   then this whole island is completely closed! count++!
         * - if even ONE single cell of this component hits out of bounds,
         *   the island is open/invalid!
         * =========================================================================
         */

        int count = 0;//tracks the number of valid closed islands

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                // found unvisited land!
                if (grid[i][j] == 0) {
                    // run DFS to explore the entire connected island
                    // if it is completely surrounded by water, dfs returns true!
                    if (dfs(grid, i, j)) {
                        count++;
                    }
                }
            }
        }

        return count;
    }

    private boolean dfs(int[][] grid, int r, int c) {
        // BASE CASE 1: OUT OF BOUNDS!
        // if row or col leaves the grid boundary...
        // that means this land touched the edge of the map!
        // so it cannot be enclosed by water from this side! return false!
        if (r < 0 || r >= m || c < 0 || c >= n) {
            return false;
        }

        // BASE CASE 2: WATER OR ALREADY VISITED!
        // if grid[r][c] == 1, it's either natural water or a land cell we already marked as visited...
        // either way, this direction is safely blocked by water! return true!
        if (grid[r][c] == 1) {
            return true;
        }

        // mark current land cell as visited by turning it into water (1)!
        // why? because we don't want to get stuck in an infinite cycle,
        // and we don't want future loops to count this same island again!
        grid[r][c] = 1;

        /*
         * =========================================================================
         * CRUCIAL DETAIL (DO NOT SHORT-CIRCUIT!):
         * =========================================================================
         * notice how we store all 4 recursive calls into separate boolean variables:
         * up, down, left, right!
         * 
         * why can't we just write:
         * return dfs(r-1, c) && dfs(r+1, c) && dfs(r, c-1) && dfs(r, c+1); ?
         * 
         * because in Java, '&&' is SHORT-CIRCUITING!
         * if the first call returns false (touches boundary), Java will STOP and skip
         * the remaining dfs calls!
         * and if it skips them, the rest of this connected island stays as 0 (unvisited)!
         * then later on, our outer nested loops will stumble upon those leftover 0s
         * and mistakenly treat them as brand new islands!
         * 
         * so evaluating all 4 directions explicitly guarantees the ENTIRE island component
         * gets sunken/visited into 1s, regardless of whether it's closed or not!
         * =========================================================================
         */
        boolean up    = dfs(grid, r - 1, c);
        boolean down  = dfs(grid, r + 1, c);
        boolean left  = dfs(grid, r, c - 1);
        boolean right = dfs(grid, r, c + 1);

        // the island is closed ONLY IF all 4 directions are safely closed by water!
        return up && down && left && right;
    }
}