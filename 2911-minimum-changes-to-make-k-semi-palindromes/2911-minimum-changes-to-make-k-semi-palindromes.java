class Solution {
    int n;
    char[] arr;
    int[][] costMemo;
    int[][] dpMemo;
    int INF = 1_000_000_000;

    public int minimumChanges(String s, int k) {
        /* top down recursion with dual memoization: instead of precomputing the entire semiCost table upfront, we compute getCost(i, j) lazily on demand and cache it inside costMemo[i][j] so no substring is ever evaluated twice! similarly, solve(start, remPieces) explores all valid split points 'end' from start + 1 up to n - 1, recursively solves for (end + 1, remPieces - 1), and caches the optimal result in dpMemo[start][remPieces]! */

        this.n = s.length();
        this.arr = s.toCharArray();

        costMemo = new int[n][n];
        for (int[] row : costMemo) Arrays.fill(row, -1);

        dpMemo = new int[n + 1][k + 1];
        for (int[] row : dpMemo) Arrays.fill(row, -1);

        return solve(0, k);
    }

    private int solve(int start, int remPieces) {
        /* base case: if we have used up all k pieces, check if we exacty reached the end of the string */
        if (remPieces == 0) {
            return start == n ? 0 : INF;
        }

        /* early return: if remaining characters arent enough to form remPieces substrings of length >= 2, abort */
        if (start + 2 * remPieces > n) {
            return INF;
        }

        if (dpMemo[start][remPieces] != -1) {
            return dpMemo[start][remPieces];
        }

        int minCost = INF;

        /* try ending the current semi-palindrome piece at every valid index 'end' >= start + 1 */
        for (int end = start + 1; end < n; end++) {
            int remCost = solve(end + 1, remPieces - 1);
            if (remCost != INF) {
                minCost = Math.min(minCost, getCost(start, end) + remCost);
            }
        }

        return dpMemo[start][remPieces] = minCost;
    }

    private int getCost(int i, int j) {
        if (costMemo[i][j] != -1) return costMemo[i][j];

        int len = j - i + 1;
        int minChanges = Integer.MAX_VALUE;

        for (int d = 1; d < len; d++) {
            if (len % d != 0) continue;

            int changes = 0;
            int groupLen = len / d;

            for (int g = 0; g < d; g++) {
                int l = i + g;
                int r = l + (groupLen - 1) * d;

                while (l < r) {
                    if (arr[l] != arr[r]) {
                        changes++;
                    }
                    l += d;
                    r -= d;
                }
            }

            minChanges = Math.min(minChanges, changes);
        }

        return costMemo[i][j] = minChanges;
    }
}