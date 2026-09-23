//dfs approach
/*class Solution {
    int m,n;//global level declare
    int[][] directions ={{-1,0},{1,0},{0,-1},{0,1}};
    public int numIslands(char [][] grid) {
        if(grid == null || grid.length == 0) return 0;//edge case check since no grid or no length of grid
        m=grid.length;//number of rows
        n=grid[0].length;//number of columns this is
        int count = 0;//the final answre count which weill rertunr number of islands
        for(int i = 0;i<m;i++){
            for(int j = 0;j<n;j++){//nested loop to traverse the 2d matrix
                if(grid[i][j] == '1'){//if its 1 that means its not visited obv na bruh, because whtever we visit, we change the sign to dollr or smhing anything
                    dfs(i,j,grid);//so we will call dfs , but on what? that i,j pointed node.. so we pass i j obv, and then the grid as well because obv we need that na lmao
                    count++;
                }
            }
        }
        return count;
    }
    public void dfs(int i, int j, char[][] grid){
        if(i<0 || i>=m || j<0 || j>=n || grid[i][j] != '1') return;//backtrack because this is not correct node bruh, either out of bounds, or not the node with 1 so visited or not a island(0)
        grid[i][j] = '$';//if cointrol came here that means that  node is 1 and is unvisited so mark it visited by chaning it to dolloer, u can mark it anything whtever u want bruh
        for(int[] dir:directions){
            //basic simple shit... we have to go up down left right from each node na.. for that we pass thorugh array oof arrays direction.. and then use those vals... thats wht it is there for 
            int new_i = i + dir[0];//just the new i and new j formed which will be passed .. 
            int new_j = j+ dir[1];//just first down, then up then left then right... all calls will happen in the recursion.. it will go deepre deeper deeper.. because its dfs!!
            dfs( new_i,  new_j, grid);
        }
    }
} */

// now solving the exact same problem using BFS!!
// same outer nested loops.. whenever we spot grid[i][j] == '1', increment count++ and start BFS from (i, j)!!
// inside BFS, we push coordinates {i, j} into a Queue<int[]> and mark grid[i][j] as visited immediately!!
// then standard while(!q.isEmpty()), poll front coordinate, check its 4 adjacent neighbors (up, down, left, right)...
// if neighbor is within grid bounds and grid[new_i][new_j] == '1', mark it visited right away and push to queue!!
// once queue empties, the whole connected island is submerged/visited, and we resume scanning the grid!!
class Solution {
    int m, n;
    int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};//the 2d directions array, same as dfs

    public int numIslands(char[][] grid) {
        if (grid == null || grid.length == 0) return 0;//simple same edge case check
        m = grid.length;//same row count for tis and n is the col count
        n = grid[0].length;
        int count = 0;

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == '1') {
                    count++;
                    bfs(i, j, grid);// start BFS for this island, because 1 means unvisited
                }
            }
        }
        return count;
    }

    public void bfs(int start_i, int start_j, char[][] grid) {
        Queue<int[]> q = new LinkedList<>();// queue stores coordinate pairs [row, col], its not one thing na... i mean each node or point is noted by row and col, so we need to store both in queue, and use both, to move in directions obv.. in provinces we just stored the v node cuz that was represending single node, here node is represented by 2 coordinates
        q.offer(new int[]{start_i, start_j});
        grid[start_i][start_j] = '$';// mark visited immediately upon offering to prevent duplicate additions!!

        while (!q.isEmpty()) {
            int[] curr = q.poll();
            int r = curr[0];
            int c = curr[1];

            // check all 4 neighbors
            for (int[] dir : directions) {
                int new_r = r + dir[0];
                int new_c = c + dir[1];

                // check boundary and if the neighbor is connected land
                if (new_r >= 0 && new_r < m && new_c >= 0 && new_c < n && grid[new_r][new_c] == '1') {
                    grid[new_r][new_c] = '$';// mark visited immediately!!
                    q.offer(new int[]{new_r, new_c});// push to queue to process next
                }
            }
        }
    }
}