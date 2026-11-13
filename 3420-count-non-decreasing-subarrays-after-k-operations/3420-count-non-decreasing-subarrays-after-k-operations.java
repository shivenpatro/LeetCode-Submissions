class Solution {
    public long countNonDecreasingSubarrays(int[] nums, int k) {
        /* ok so see.. what is this problem actually asking? we are given an integer array nums and an integer k. for any subarray, we can increment elements by 1 up to k times total (independently for each subarray) to make it non-decreasing (every element >= previous element). our goal is to count how many subarrays can be made non-decreasing within at most k operations! */

        /* why does brute force TLE? because N goes up to 10^5, so checking all O(N^2) subarrays is impossible. now notice a crucial monotonic property: if a subarray nums[i..j] can be made non-decreasing in <= k operations, then ANY smaller suffix subarray ending at j (like nums[i+1..j], nums[i+2..j], ..., nums[j..j]) can ALSO be made non-decreasing in <= k operations! why? because dropping elements from the left only removes elements or reduces the prefix maximums that later elements had to climb up to! so once we find a valid window [i..j], ALL (j - i + 1) subarrays ending at j are immediately valid! this screams two-pointer Sliding Window! */

        /* now heres the tricky part that makes this a Hard problem: when our window [i..j] is valid, how do we calculate the exact operations needed in O(1) time as we expand j or shrink i? 
        suppose the maximum element in our current window [i..j] sits at index 'm' (which we can track in O(1) using a Monotonic Decreasing Deque just like Sliding Window Maximum!). notice that index 'm' splits our window into two halves:
        1. the right half [m..j]: since nums[m] is the maximum element in the ENTIRE window [i..j], every single element from m to j is <= nums[m]! so to make [m..j] non-decreasing, EVERY element in [m..j] must be raised up to match nums[m]! the cost for this right half is simply: nums[m] * (j - m + 1) - rangeSum(m, j)!
        2. the left half [i..m-1]: here nums[m] is NOT at the start of the subarray, so elements before m dont climb to nums[m], they only climb up to their own local prefix maximums! how do we get the cost of [i..m-1] in O(1)? we precompute suffixOps[i] = total operations needed to make the entire suffix nums[i..n-1] non-decreasing! because nums[m] >= every element in [i..m-1], when making the full suffix [i..n-1] non-decreasing, nothing from [i..m-1] ever spills over past nums[m]! that means the operations for [i..m-1] is literally just suffixOps[i] - suffixOps[m]!
        3. and how do we precompute suffixOps[i] from right to left in O(N)? find 'nextGreater' index 'nxt' to the right of i using a monotonic stack! between i and nxt - 1, every element is <= nums[i], so they all get raised to nums[i] at cost: nums[i] * (nxt - i) - rangeSum(i, nxt - 1), plus whatever suffixOps[nxt] already costs! simple na! */

        int n = nums.length;

        /* step 1: prefix sum array so we can query sum of any subarray [l..r] in O(1) time */
        long[] prefSum = new long[n + 1];
        for (int i = 0; i < n; i++) {
            prefSum[i + 1] = prefSum[i] + nums[i];
        }

        /* step 2: find Next Greater Element index to the right (ngr[i]) using a monotonic decreasing stack.. default is n if no greater element exists on teh right */
        int[] ngr = new int[n];
        Arrays.fill(ngr, n);
        Deque<Integer> stack = new ArrayDeque<>();
        for (int i = 0; i < n; i++) {
            while (!stack.isEmpty() && nums[stack.peek()] < nums[i]) {
                ngr[stack.pop()] = i;
            }
            stack.push(i);
        }

        /* step 3: precompute suffixOps[i] = operations to make nums[i..n-1] non-decreasing.. size is n + 1 so suffixOps[n] = 0 acts as clean boundary */
        long[] suffixOps = new long[n + 1];
        for (int i = n - 1; i >= 0; i--) {
            int nxt = ngr[i]; // first index to the right where nums[nxt] > nums[i]
            long segCount = nxt - i;
            long segSum = prefSum[nxt] - prefSum[i]; // sum of nums[i .. nxt - 1]
            long segCost = (long) nums[i] * segCount - segSum;
            suffixOps[i] = segCost + suffixOps[nxt];
        }

        /* step 4: sliding window [i..j] using monotonic decreasing deque to always know the index 'm' of the maximum element in current window */
        Deque<Integer> dq = new ArrayDeque<>();
        long validSubarrays = 0;
        int i = 0;

        for (int j = 0; j < n; j++) {
            /* maintain monotonic decreasing order in deque so dq.peekFirst() is always the max element index in [i..j] */
            while (!dq.isEmpty() && nums[dq.peekLast()] <= nums[j]) {
                dq.pollLast();
            }
            dq.offerLast(j);

            /* shrink window from left (i++) while total operations required for [i..j] strictly exceeds k */
            while (i <= j) {
                int m = dq.peekFirst(); // index of maximum element in [i..j]

                /* left segment [i .. m-1] cost is suffixOps[i] - suffixOps[m] */
                long leftCost = suffixOps[i] - suffixOps[m];

                /* right segment [m .. j] cost raises all elements in [m..j] up to nums[m] */
                long rightCount = j - m + 1;
                long rightSum = prefSum[j + 1] - prefSum[m];
                long rightCost = (long) nums[m] * rightCount - rightSum;

                long totalOps = leftCost + rightCost;

                if (totalOps <= k) {
                    break; // window [i..j] is valid! no need to shrink further
                }

                /* window exceeds k operations, evict left index i and pop from deque front if max element just left the window */
                if (dq.peekFirst() == i) {
                    dq.pollFirst();
                }
                i++;
            }

            /* every subarray ending at j and starting anywhere from i to j is guaranteed valid */
            validSubarrays += (j - i + 1);
        }

        return validSubarrays;
    }
}