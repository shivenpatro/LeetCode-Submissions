//top down
/*class Solution {
    int[] memo;

    public int climbStairs(int n, int[] costs) {
        //top down recursion with memoization perspective: let solve(i) be the minimum cost needed to reach step n starting from current step i. base case: when i == n, we are already at destination, so 0 more cost is needed! from step i, we try jumping to j in {i + 1, i + 2, i + 3}. the cost of that transition is costs[j - 1] + (j - i)^2 + solve(j). we take the minimum across all 3 choices nad store it in memo[i] so we never recompute the same step twice! 

        memo = new int[n + 1];
        Arrays.fill(memo, -1);
        return solve(0, n, costs);
    }

    private int solve(int i, int n, int[] costs) {
        //reached step n: no more cost needed to complete teh journey 
        if (i == n) return 0;

        //return cached result if state i has already been solved 
        if (memo[i] != -1) return memo[i];

        int minCost = Integer.MAX_VALUE;

        //test jumping 1, 2, or 3 steps ahead 
        for (int jump = 1; jump <= 3; jump++) {
            int j = i + jump;
            if (j <= n) {
                int currentJumpCost = costs[j - 1] + jump * jump;
                minCost = Math.min(minCost, currentJumpCost + solve(j, n, costs));
            }
        }

        return memo[i] = minCost;
    }
}*/

//bottom up
class Solution {
    public int climbStairs(int n, int[] costs) {
        /* ok so see.. what is this problem actually asking? we have a staircase with n + 1 steps numbered from 0 to n. we start at step 0 with cost 0, nad we want to reach step n with the minimum total cost possible. from any step i, we can only take a jump of size 1, 2, or 3 (meaning we land on step j where j is i + 1, i + 2, or i + 3, as long as j <= n). the cost of making a jump from step i to step j has two parts: the cost of stepping on j itself which is costs[j - 1] (since costs array is 1-indexed, step 1 is costs[0], step 2 is costs[1], etc.), plus the jump penalty which is (j - i)^2! */

        /* now why is this a classic dp problem? because at any step i, our future optimal decisions only depend on which step we are currently standing on, not how we got there! let dp[i] represent the minimum cost to reach step i from the starting step 0. base case is super clear: dp[0] = 0 because we start at step 0 with zero cost. for every step i from 0 up to n - 1, we can try jumping to j = i + 1, i + 2, nad i + 3. if we jump to j, the new cost to reach j would be dp[i] + costs[j - 1] + (j - i)^2! we just take the minimum over all possible ways to land on step j, nad at teh end dp[n] will directly give us the answer to reach step n, simple na! */

        int[] dp = new int[n + 1];
        Arrays.fill(dp, Integer.MAX_VALUE);
        dp[0] = 0; // starting at step 0 costs 0

        for (int i = 0; i < n; i++) {
            /* if step i itself is unreachable, we cant jump from here */
            if (dp[i] == Integer.MAX_VALUE) continue;

            /* try all 3 jump options: 1 step, 2 steps, or 3 steps forward */
            for (int jump = 1; jump <= 3; jump++) {
                int j = i + jump;

                /* make sure we dont jump past step n */
                if (j <= n) {
                    int jumpCost = costs[j - 1] + jump * jump; // costs[j - 1] is node cost, jump * jump is (j - i)^2
                    dp[j] = Math.min(dp[j], dp[i] + jumpCost);
                }
            }
        }

        return dp[n]; // min cost to reach the destination step n
    }
}