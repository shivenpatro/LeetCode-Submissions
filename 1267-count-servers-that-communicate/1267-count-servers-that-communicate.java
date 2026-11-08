class Solution{
    public int countServers(int[][] grid) {
        /* brute force simulation: for every single server at grid[i][j] == 1, manually scan all other columns in row i.. if another 1 is found, mark it valid and stop.. otherwise scan all other rows in column j.. if another 1 is found, mark valid.. if neither has another server, it is isolated and cannot comunicate */

        int m = grid.length;
        int n = grid[0].length;
        int count = 0;

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 1) {
                    boolean canTalk = false;

                    /* scan all other cells in the same row i */
                    for (int c = 0; c < n; c++) {
                        if (c != j && grid[i][c] == 1) {
                            canTalk = true;
                            break;
                        }
                    }

                    /* if not found in row, scan all other cells in the same column j */
                    if (!canTalk) {
                        for (int r = 0; r < m; r++) {
                            if (r != i && grid[r][j] == 1) {
                                canTalk = true;
                                break;
                            }
                        }
                    }

                    if (canTalk) {
                        count++;
                    }
                }
            }
        }

        return count;
    }
}