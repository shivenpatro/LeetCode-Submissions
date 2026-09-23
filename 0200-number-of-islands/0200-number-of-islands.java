//dfs approach
class Solution {
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
}