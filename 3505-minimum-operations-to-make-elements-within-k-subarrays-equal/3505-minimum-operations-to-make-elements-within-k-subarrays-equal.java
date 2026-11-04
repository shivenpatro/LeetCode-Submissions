class Solution {
    int n, x, k;
    long[] minOps;
    Long[][] memo;
    long INF = (long) 1e16;

    public long minOperations(int[] nums, int x, int k) {
        /* ok so see.. what is this problem actually asking us to do? we are given an array nums and two integers x nad k. we need to pick AT LEAST k non-overlapping subarrays, each of length EXACTLY x, such that within every chosen subarray, all elements are made equal using the minimum possible operations (where one operation is incrementing or decrementing any element by 1). and we need the overall minimum operations across all chosen subarrays! */

        /* now break this beast down into two separate problems: 
        1. if we pick a specific subarray of size x starting at index i, how many operations does it take to make all its elements equal? from leetcode 462 we already proved that to minimize absolute linear deviations, the mathematically optimal target element is ALWAYS the MEDIAN of that subarray! if we sort the subarray, median is the middle element. any element smaller than median needs (median - val) operations, nad any element larger needs (val - median) operations. but sorting every subarray of size x would take O(N * x log x) which is way too slow! instead, we can slide a window of size x across nums, maintaining the median dynamically using two TreeMaps (acting like multisets) representing the lower half (left) and upper half (right). on the fly we also track leftSum and rightSum so the cost is computed in O(1) without iterating: leftOps = (leftCount * median - leftSum) and rightOps = (rightSum - rightCount * median)! 
        2. once we precompute minOps[i] for every possible subarray of length x starting at i, the problem simply becomes: pick at least k disjoint intervals of length x to minimize total cost. and since extra subarrays only add non-negative cost, picking exactly k subarrays is always optimal! this is a textbook pick-or-skip dynamic programming recurrence: at index i, we can either take the subarray starting at i (cost is minOps[i] + solve(i + x, count + 1)) or skip index i (cost is solve(i + 1, count)). with memoization on (index, count), this runs in O(N * K) time, simple na! */

        this.n = nums.length;
        this.x = x;
        this.k = k;

        minOps = new long[n - x + 1];
        precomputeMinOps(nums);

        memo = new Long[n + 1][k + 1];
        return solve(0, 0);
    }

    /* dp function returning minimum operations starting from index i having already picked 'count' subarrays */
    private long solve(int i, int count) {
        /* base case: if we have successfully selected all k subarrays, no more opertions are needed so return 0 */
        if (count == k) return 0;

        /* if the remaining elements in the array (n - i) are strictly less than what we need to form the remaining (k - count) subarrays of size x, then this branch is impossible so return a huge sentinel value INF */
        if ((long) (k - count) * x > n - i) return INF;

        /* return already computed state from memo table */
        if (memo[i][count] != null) return memo[i][count];

        /* option 1: take the subarray starting at index i, which costs minOps[i], and jump forward by x to prevent overlap */
        long take = minOps[i] + solve(i + x, count + 1);

        /* option 2: skip index i and try to start our next subaray at index i + 1 */
        long skip = solve(i + 1, count);

        return memo[i][count] = Math.min(take, skip);
    }

    /* sliding window median computation using two TreeMaps acting as balanced multisets */
    private void precomputeMinOps(int[] nums) {
        TreeMap<Integer, Integer> left = new TreeMap<>();
        TreeMap<Integer, Integer> right = new TreeMap<>();
        long leftSum = 0, rightSum = 0;
        int leftCount = 0, rightCount = 0;

        int i = 0;
        for (int j = 0; j < n; j++) {
            /* add current element nums[j] to the lower multiset (left) if it has fewer or equal elements than right, otherwise add to right */
            int val = nums[j];
            if (leftCount <= rightCount) {
                add(left, val);
                leftSum += val;
                leftCount++;
            } else {
                add(right, val);
                rightSum += val;
                rightCount++;
            }

            /* balance the contents: if the maximum element in left is strictly greater than the minimum element in right, swap them so all elements in left stay <= all elements in right */
            if (!left.isEmpty() && !right.isEmpty() && left.lastKey() > right.firstKey()) {
                int maxL = left.lastKey();
                int minR = right.firstKey();

                remove(left, maxL);
                remove(right, minR);
                add(left, minR);
                add(right, maxL);

                leftSum += (long) minR - maxL;
                rightSum += (long) maxL - minR;
            }

            /* when window reaches size x, the median of the current window is simply left.lastKey() */
            if (j - i + 1 == x) {
                long median = left.lastKey();

                /* math formula: total operations for left half is (leftCount * median - leftSum) nad for right half is (rightSum - rightCount * median) */
                long cost = ((long) leftCount * median - leftSum) + (rightSum - (long) rightCount * median);
                minOps[i] = cost;

                /* now remove outgoing element nums[i] from our multisets to slide window forward */
                int outVal = nums[i];
                if (left.containsKey(outVal)) {
                    remove(left, outVal);
                    leftSum -= outVal;
                    leftCount--;
                } else {
                    remove(right, outVal);
                    rightSum -= outVal;
                    rightCount--;
                }

                /* restore balance of sizes so leftCount is either equal to rightCount or rightCount + 1 */
                if (leftCount < rightCount) {
                    int moveVal = right.firstKey();
                    remove(right, moveVal);
                    rightSum -= moveVal;
                    rightCount--;

                    add(left, moveVal);
                    leftSum += moveVal;
                    leftCount++;
                } else if (leftCount > rightCount + 1) {
                    int moveVal = left.lastKey();
                    remove(left, moveVal);
                    leftSum -= moveVal;
                    leftCount--;

                    add(right, moveVal);
                    rightSum += moveVal;
                    rightCount++;
                }

                i++;
            }
        }
    }

    private void add(TreeMap<Integer, Integer> map, int val) {
        map.put(val, map.getOrDefault(val, 0) + 1);
    }

    private void remove(TreeMap<Integer, Integer> map, int val) {
        int count = map.get(val);
        if (count == 1) {
            map.remove(val);
        } else {
            map.put(val, count - 1);
        }
    }
}