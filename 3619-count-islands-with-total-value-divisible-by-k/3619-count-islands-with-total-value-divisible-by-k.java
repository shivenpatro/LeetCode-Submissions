class Solution {
    int m;
    int n;
    
    // 4 possible directions to explore neighbors: up, down, left, right
    int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

    public int countIslands(int[][] grid, int k) {
        m = grid.length;    // total rows
        n = grid[0].length; // total columns
        
        /*
         * =========================================================================
         * BIG PICTURE / INTUITION:
         * =========================================================================
         * so see... what does the problem say?
         * 0 represents water/empty space, and ANY positive number (> 0) represents land!
         * an island is a 4-directionally connected cluster of positive integers.
         * 
         * the "value" of an island is simply the sum of all cell values in that cluster!
         * our goal: count how many islands have a total sum that is divisible by k (sum % k == 0)!
         * 
         * this is literally the classic "Number of Islands" problem... 
         * with just ONE simple tweak:
         * instead of just counting 1 for each island, we traverse the entire island,
         * accumulate the sum of all its cells, and check if (sum % k == 0)!
         * 
         * IMPORTANT OVERFLOW NOTE:
         * grid cells can be up to 10^6, and there can be up to 10^5 cells...
         * so the total sum of an island can reach 10^11, which OVERFLOWS standard 32-bit int!
         * that's why we MUST use 'long' for accumulating the island sum, simple!
         * =========================================================================
         */
        
        int validIslandCount = 0; // tracks islands whose sum % k == 0
        
        // iterate through every cell in the matrix
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                
                // if grid[i][j] > 0, we just stepped onto a fresh, unvisited island!
                if (grid[i][j] > 0) {
                    // explore the entire island and get the sum of all its cells
                    long islandSum = dfs(grid, i, j);
                    
                    // check if this island's total sum is cleanly divisible by k
                    if (islandSum % k == 0) {
                        validIslandCount++;
                    }
                }
            }
        }
        
        return validIslandCount;
    }

    private long dfs(int[][] grid, int r, int c) {
        // grab current cell's value
        long sum = grid[r][c];
        
        // now see... how do we mark this cell as visited?
        // we can simply set grid[r][c] = 0 in-place!
        // why? because 0 means water/empty, so future loops and recursive calls
        // will automatically ignore it and never process it again!
        // this saves us from creating a separate visited[][] array, 0 extra heap memory!
        grid[r][c] = 0;
        
        // explore all 4 adjacent neighbors (up, down, left, right)
        for (int[] dir : directions) {
            int newR = r + dir[0];
            int newC = c + dir[1];
            
            // boundary checks: must stay inside the grid dimensions
            // AND the neighbor must be unvisited land (grid[newR][newC] > 0)!
            if (newR >= 0 && newR < m && newC >= 0 && newC < n && grid[newR][newC] > 0) {
                // recursively collect the sum from the neighbor and add it to our running island sum
                sum += dfs(grid, newR, newC);
            }
        }
        
        return sum; // returns total sum of this entire connected island component
    }
}