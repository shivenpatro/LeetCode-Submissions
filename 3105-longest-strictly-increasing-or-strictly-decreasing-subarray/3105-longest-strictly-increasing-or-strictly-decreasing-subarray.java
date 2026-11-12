//first we go for simple nested brute approach
class Solution {
    public int longestMonotonicSubarray(int[] nums) {
        /* brute force simulation: fix every index i as the starting point of our subarray.. from index i, first walk pointer j forward as far as possible while nums[j] > nums[j - 1] to measure the longest strictly increasing subarray starting at i.. then reset j back to i + 1 and walk forward again while nums[j] < nums[j - 1] to measure the longest strictly decreasing subarray starting at i.. take the maximum length found across all starting positions i in O(N^2) time! */
        int n = nums.length;
        int maxLen = 1;
        for (int i = 0; i < n; i++) {
            /* check strictly increasing subarray starting from index i */
            int incLen = 1;
            int j = i + 1;
            while (j < n && nums[j] > nums[j - 1]) {
                incLen++;
                j++;
            }
            /* check strictly decreasing subarray starting from index i */
            int decLen = 1;
            j = i + 1;
            while (j < n && nums[j] < nums[j - 1]) {
                decLen++;
                j++;
            }
            maxLen = Math.max(maxLen, Math.max(incLen, decLen));
        }
        return maxLen;
    }
}