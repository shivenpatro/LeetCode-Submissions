class Solution {
    int[] memo;

    public int climbStairs(int n, int[] costs) {
        /* top down recursion with memoization perspective: let solve(i) be the minimum cost needed to reach step n starting from current step i. base case: when i == n, we are already at destination, so 0 more cost is needed! from step i, we try jumping to j in {i + 1, i + 2, i + 3}. the cost of that transition is costs[j - 1] + (j - i)^2 + solve(j). we take the minimum across all 3 choices nad store it in memo[i] so we never recompute the same step twice! */

        memo = new int[n + 1];
        Arrays.fill(memo, -1);
        return solve(0, n, costs);
    }

    private int solve(int i, int n, int[] costs) {
        /* reached step n: no more cost needed to complete teh journey */
        if (i == n) return 0;

        /* return cached result if state i has already been solved */
        if (memo[i] != -1) return memo[i];

        int minCost = Integer.MAX_VALUE;

        /* test jumping 1, 2, or 3 steps ahead */
        for (int jump = 1; jump <= 3; jump++) {
            int j = i + jump;
            if (j <= n) {
                int currentJumpCost = costs[j - 1] + jump * jump;
                minCost = Math.min(minCost, currentJumpCost + solve(j, n, costs));
            }
        }

        return memo[i] = minCost;
    }
}