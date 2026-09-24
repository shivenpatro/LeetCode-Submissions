// so basically this question is the classic MULTI-SOURCE BFS problem!!
// why Multi-Source BFS and NOT DFS?
// because all rotten oranges (cells with value 2) rot their 4-directional fresh neighbors SIMULTANEOUSLY at the exact same minute!
// and we need to find the MINIMUM time for all oranges to rot.. and whenever minimum time/shortest path is asked, BFS is the king!!
// DFS goes deep in one path first, which will give wrong elapsed time for cells that had a closer rotten orange nearby!
// SO HERE IS THE GAME PLAN:
// 1. First scan the entire grid to do two things:
//    - find ALL rotten oranges (val == 2) and push their coordinates into our Queue upfront! (these are all our starting sources!)
//    - count total number of fresh oranges (val == 1).. we will decrement this as oranges rot!
// 2. Corner Case: if freshCount == 0 from the start, 0 minutes needed, return 0 right away!
// 3. Then standard Level-Order BFS (level by level using queue size!):
//    - every level represents exactly ONE MINUTE passing!
//    - for all rotten oranges in current level, pop them and check their 4 neighbors (up, down, left, right)..
//    - if neighbor is within bounds and is a fresh orange (val == 1):
//        * rot it immediately (grid[new_r][new_c] = 2)
//        * decrement freshCount--
//        * push new rotten orange into queue for the next minute!
//    - after finishing the current level, increment minutes++!
// 4. At the very end:
//    - if freshCount == 0 (all fresh oranges are rotten), return minutes - 1 (because that last level pops the last oranges, finds no new fresh neighbors, but still increments minutes one extra time before queue becomes empty!)
//    - if freshCount > 0, it means some oranges were isolated and could NEVER be reached! return -1!

class Solution {
    public int orangesRotting(int[][] grid) {
        int m = grid.length;//this is number of rows
        int n = grid[0].length;//this is number of columns
        Queue<int[]> q = new LinkedList<>();//so this is the queue to hold row, column for each orange, its not like we have a particular single value for a orange or something, so we need to store row,column both so that each orange can be found from queue, its a queue of arrays, where each element is an array holding [row,col], 
        int freshCount = 0;//this will keep the track of number of fresh oranges, which is represented by 1... so we are keeping track of that

        //Step 1. our first step is to find all the rotten oranges in the 2d grid, we find all the rotten oranges and add each of the oranges row, col  to the queue so that it can be popped nad all 4 directions can be processed later
        for(int i = 0; i<m ;i++){
            for(int j = 0; j<n; j++){
                if(grid[i][j] == 2){
                    //2 means this orange is rotten, add this to the queue, so that we can find the initial numebr of rotten oranges
                    q.add(new int[]{i,j});//simply create a new array and store coordinates for that orange in that queue
                }else if(grid[i][j] == 1){
                    //that means we found a fresh orange, so we need to maintain the count and do count++
                    freshCount++;//so we keep the count, and this is needed, because when we mark any orange rotten later while doing bfs.. we will mark + do -- here as well.. so the count is maintained
                }
            }
        }
        if(freshCount == 0) return 0;//no fresh oranges.. so obv 0 time because no rotting only... 0 minutes needed
        int[][] directions = {{-1,0},{1,0},{0,-1},{0,1}};
        int minutes = 0;//it tracks the amount of minutes passed, the final answer which we need to return, the shortest and smallest amount of time required to make all fresh oranges rotten, if not all rotten thne -1 returned obv

        //Step 2: process now level by level, multisource bfs it is thats why we need to find the size of the queue in the current stage and then we need to pop all the oranges in that current queue size,... at that level.. because it will be happening all in single minute.. so its like run a loop for whole queue and then for  each level there would be another loop, and then for each loop, pop the orange, and marking and reducing the number of fresh orange is done.. and once  one level is over.. we do minutes++.. because in that minute.. using multi source bfs.. all the oranges which needed to be rotten got rotten... its not a classic single single pop.. and check neighbors nope... we need to concurrently go level wise... because all rotten oranges will be rotting the neighbors together concurrently level by level
        while(!q.isEmpty()){
            int o = q.size();//so we find the size of queue at that level... and then we run a loop till n.. its typically the set of rotten oranges for that level which are gonna rot the next set of oranges and get added to queue for next level
            while(o-->0){//simple shit.. for each level we are traversing the whole level and popping all the oranges in that level, chekcin the 4 directions and doing the marking, so this makes sure we traverse and check all 4 directions of each rotten orange thats all
                int[] curr = q.poll();//we get the array holding 2 coordinates, row and column
                int r = curr[0];
                int c = curr[1];//row and column.. the positino for that orange
                for(int[] dir : directions){//we initialized the directions array, so we are gonna check all the 4 directions here thats all.. up down left right.. running loop for that for each direction simple
                    int new_r = r + dir[0];
                    int new_c = c + dir[1];
                    if(new_r < m && new_r >= 0 && new_c < n && new_c >= 0 && grid[new_r][new_c] == 1){
                        //we are doing the boundary check so the position it goes is a position in the grid, and also the orange has to be fresh obv... i mean then only we rot it change it and do all the works
                        grid[new_r][new_c] = 2;//since its rotten, mark it rotten
                        freshCount--;//reduce the count of number of fresh oranges and now since its rotten, pass it to queue so we check all directions for this new rotten as well
                        q.offer(new int[]{new_r,new_c});//passing into queue the positions for this orange as well
                    }
                }
            }
            if(!q.isEmpty()){
                minutes++;
            }//so see see... lets understand this... the control is here means one whole level was done in the queue.. so that level mutisource bfs has been donne and all posibble oranges to be rotten has been rotten for that particular level, so we do minutes++.. because at the end we find the minutes it took.. and bfs for fastest... now now.. main thing is.. understand.. when the last rotten orange coordinates are added to the queue... that wont be rotting anyone because if that is added and that is the last one.. its possible that if nothing surrounds it... if the final rotten orange added doesnt have any fresh orange surrounding it...so it got added but it wont convert anything.. so extra minutes++ will be done.. which we need to prevent... so simple check.. before doing minutes++, check if queue is empty, that means that minutes++ shjouldnt be done because that is extra obv
        }
        if(freshCount == 0) return minutes;//because all got converted so we need to return time
        else return -1;
    }
}