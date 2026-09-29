class Solution {
    int m,n;//global level so row nad column number can be used in both functions
    Boolean memo[][][];//memo array since 3 parameters are changing, we hold a 3d array
    public boolean hasValidPath(char[][] grid) {
        m = grid.length;//this is the length of number of rows in the grid
        n = grid[0].length;//this is the length of number of columns in the grid
        if((m+n-1) % 2 != 0 || grid[0][0] == ')' || grid[m-1][n-1] == '('){
            return false;//so see we do 3 checks here, if the number of elements which any path can take is odd, then false because odd so it can never be balanaed only, and why  m+n-1...because see ... imgaine the grid u will udnerstand or see cwm... if  you have 3*3.. u start from 0,0,... to go to 2,2 max possible ways ois m+n-1 only... because see.. like imagine u take 2 right.. go to 0,2.. and come 2 down, its 4 + 1(bracket at 0,0)...so total 5 which is same as mm+n-1..3+3-1..same any path u take.. count it.. it willl be 4 and then the +1 for first element, 
            //2nd condition because if the 1st elemeent itself is close brakcet... there cant eb any  close bracket before it to defend or balance it  simple
            //3rd and same simple.. if last is oepn bracket... there cant be any close brakcet  to balance it
        }
        memo = new Boolean[m][n][m+n+1];//we ddeclare wrapper class and not primitive.. because primitive will create with false by default in all cells... but wrapper class creates with null in each cell, m+n also will work here because max number of elements in a path can be m+n but for safety we choose m+n+1.. just one extra
        return solve(0,0,0,grid);//m,n,count and grid
    }
    public boolean solve(int i, int j, int count, char grid[][]){
        count = count + (grid[i][j] == '(' ? 1 : -1);//simple... if open ++ or else --
         if(count<0) return false;
         //this means we have reached a point where we found more closing than opening.. so there is no way to balance that closing one which we found rn.. so no pt... just this path is false.. go back and explore something else, also  we have to do this check after that count++ or count-- whatever is done.. because... if we do the check before the operation, its like just when the recursion call has happened.. we are checking this first... so imagine the count comes with 0... even though the cell has ')'.. so false should be returned.. but this check was done after this count<0 statment.. so it doenst return false nad loop runs.. count becaomes -1.. and later na...when memo[i][j][-1] since count is -1 now... it will caouse INDEX OUT  OF BOUND!!
            //BELOW IS THE CASE IF WE ARE AT 0,1 AND CHAR IS ')'..
         /*

    // LINE 1: Check if count < 0
    if (count < 0) return false; 
    // What is count right now? It is 0! 
    // Is 0 < 0? NO! 
    // So Java skips this line and DOES NOT return!

    // LINE 2: Update count
    count = count + (grid[i][j] == '(' ? 1 : -1);
    // Since grid[0][1] is ')', we do: 0 + (-1) = -1.
    // Now count is -1!

    // LINE 3: Destination check
    if (i == m - 1 && j == n - 1) return count == 0; 
    // Not at end, skips this.

    // LINE 4: Memoization check
    if (memo[i][j][count] != null) return memo[i][j][count];
    // Look closely at what Java evaluates:
    // memo[0][1][-1]  <--- CRASH! 
    // java.lang.ArrayIndexOutOfBoundsException: Index -1 out of bounds!
    */
        if(i == m-1 && j == n-1){
            //we reached the end cell
            return count == 0;//see... this is boolean check.. if count == 0 that menas both brackets are balanced.. so we return true.. if not false is returned for that path
        }
        if(memo[i][j][count] != null) return memo[i][j][count];//classic memoization na.. if the subproblem is stored.. return it no.. saves time
        //move down
        if(i+1<m){
            //basic boundary check to not move out of gird
            if(solve(i+1,j,count,grid)) {
                return memo[i][j][count] = true;//basic memo thing, basically since there is no recursion here  we do the call and if the call returns true... we store that true value in the 3d array.. which will later help us to solve a sub problem if that same exact cell is called again and hence save time...
            }
        }
        //move right
        if (j + 1 < n) {//again the boundary check for column... to not move out of grid
            if (solve(i, j + 1, count, grid)) {
                return memo[i][j][count] = true;///same memo thing 
            }
        }
        return memo[i][j][count] = false;//now see... if the control reached here... that means... that path returned false na... so we havfe to memoize that as well.. thats the thing... like control reched here means... basically imagine this like this//
        /*
         * =========================================================================
         * WHY DO WE REACH THIS LINE & WHY DO WE MEMOIZE 'false'?
         * =========================================================================
         *
         * 1. RECURSION CALL TREE & CONTROL FLOW VISUALIZATION:
         *
         *                  State: solve(i, j, count)
         *                         /           \
         *                        /             \
         *               Move DOWN:            Move RIGHT:
         *             solve(i+1, j, count)   solve(i, j+1, count)
         *                   |                       |
         *                   v                       v
         *             [Subtree 1]             [Subtree 2]
         *                   |                       |
         *                   v                       v
         *               returns false           returns false
         *             (all paths died)        (all paths died)
         *                   \                       /
         *                    \                     /
         *             Both options exhausted with NO solution found!
         *                                 |
         *                                 v
         *           >>> EXECUTION DROPS HERE: Line reached! <<<
         *
         * 2. HOW THE CONTROL GOT HERE:
         *    - We first tried to move DOWN:
         *      If ANY valid path existed through the down branch, `solve(i+1, j, ...)`
         *      would have evaluated to `true`, hit `return memo[i][j][count] = true;`,
         *      and terminated early without ever reaching this line.
         *    - Because DOWN failed, the CPU continued to move RIGHT:
         *      Similarly, if any valid path existed to the right, `solve(i, j+1, ...)`
         *      would have triggered `return memo[i][j][count] = true;` and exited.
         *    - Therefore, reaching this point mathematically PROVES that:
         *      Starting from cell (i, j) with a balance of `count`, it is IMPOSSIBLE
         *      to reach the destination (m-1, n-1) with a balanced count of 0.
         *
         * 3. WHY WE MUST CACHE 'false':
         *    - Grid paths have massive overlaps (DAG structure). Different paths can
         *      converge on the same coordinate with the exact same openCount:
         *
         *             Path A: (0,0) -> (0,1) -> (1,1) [Balance = 1]
         *                                        \
         *                                         ---> solve(1, 1, count=1)
         *                                        /
         *             Path B: (0,0) -> (1,0) -> (1,1) [Balance = 1]
         *
         *    - Path A already fully explored (1,1) with balance=1 and failed.
         *    - If we do NOT memoize `false`, when Path B reaches (1, 1) with balance=1,
         *      it will re-run the entire recursion tree (DOWN and RIGHT again),
         *      causing the time complexity to blow up exponentially to O(2^(m+n)) (TLE).
         *    - By storing `memo[i][j][count] = false`, Path B hits the cache check
         *      `if (memo[i][j][count] != null)` in O(1) time and aborts immediately.
         * =========================================================================
         */
    }
}