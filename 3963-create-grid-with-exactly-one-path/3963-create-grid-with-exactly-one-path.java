import java.util.Arrays;

class Solution {
    public String[] createGrid(int m, int n) {
        // so see.. question is asking us to build ANY m x n grid using '.' (free) and '#' (wall)
        // condition is simple: moving only right or down from (0,0), there should be EXACTLY ONE path to (m-1, n-1)
        // now why overcomplicate and draw weird zig-zags or random mazes when we can just force an L-path?
        // . . .
        // # # .
        // # # .
        //the above is an reverse L... or you can do L also.. it will work for every case
        // think about it.. if we just go all the way right across the very first row..
        // and then drop straight down the last column to the end..
        // and put '#' everywhere else under row 0..
        // can the path branch anywhere? nope!
        // from row 0 you can't jump down early because everything below is '#'
        // once you hit the top right corner, you can't go right anymore, so you HAVE to go down!
        // and in the last column you can only move down.. so zero choices, exactly 1 path na!
        String[] grid = new String[m];
        for (int i = 0; i < m; i++) {
            char[] row = new char[n];
            if (i == 0) {
                // row 0 is our highway.. fill entire first row with '.' so we can walk all the way to the right
                Arrays.fill(row, '.');
            } else {
                // for all other rows.. block everything with '#' first so no shortcut or extra path can form
                Arrays.fill(row, '#');
                // then open ONLY the last spot (n - 1) with '.' so we can step down towards (m-1, n-1)
                row[n - 1] = '.';
            }
            // convert the char array to string for this row and we're done
            grid[i] = new String(row);
        }
        return grid;
    }
}