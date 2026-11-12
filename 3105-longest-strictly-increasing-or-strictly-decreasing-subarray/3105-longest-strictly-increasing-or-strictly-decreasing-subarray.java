//first we go for simple nested brute approach
/*class Solution {
    public int longestMonotonicSubarray(int[] nums) {
        // brute force simulation: fix every index i as the starting point of our subarray.. from index i, first walk pointer j forward as far as possible while nums[j] > nums[j - 1] to measure the longest strictly increasing subarray starting at i.. then reset j back to i + 1 and walk forward again while nums[j] < nums[j - 1] to measure the longest strictly decreasing subarray starting at i.. take the maximum length found across all starting positions i in O(N^2) time! 
        int n = nums.length;
        int maxLen = 1;
        for (int i = 0; i < n; i++) {
            // check strictly increasing subarray starting from index i 
            int incLen = 1;
            int j = i + 1;
            while (j < n && nums[j] > nums[j - 1]) {
                incLen++;
                j++;
            }
            // check strictly decreasing subarray starting from index i 
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
}*/

//optimal approach
class Solution {
    public int longestMonotonicSubarray(int[] nums) {
        /* ok so see.. what is this problem actually asking? we are given an integer array nums and we want to find the length of the longest contiguous subarray that is EITHER strictly increasing (nums[i] > nums[i-1]) OR strictly decreasing (nums[i] < nums[i-1]). notice why brute force does redundant work: if we find a strictly increasing streak like [3, 5, 6] starting at index 2, starting again from index 3 ([5, 6]) will obviously only give a shorter subarray! so we never need nested loops at all! */
        /* instead, we can solve this in a single clean O(N) pass by tracking two running streak counters: 'inc' (length of current strictly increasing subarray ending at i) and 'dec' (length of current strictly decreasing subarray ending at i). initially at index 0, a single element by itself is both an increasing subarray of length 1 and a decreasing subarray of length 1, so inc = 1, dec = 1, nad maxLen = 1! as we scan from i = 1 to n - 1, there are three cases: 
        1. nums[i] > nums[i - 1]: our increasing streak continues! so inc++.. and since it just went up, any previous decreasing streak is broken, so dec resets back to 1 (starting fresh at nums[i]). 
        2. nums[i] < nums[i - 1]: our decreasing streak continues! so dec++.. and any increasing streak is broken, so inc resets to 1. 
        3. nums[i] == nums[i - 1]: strictly increasing/decreasing requires strict inequality (< or >), so equal neighbors break BOTH streaks! both inc and dec reset back to 1. 
        at every step we just update maxLen = Math.max(maxLen, Math.max(inc, dec)), simple na! */
        int n = nums.length;
        if (n == 0) return 0;
        int inc = 1;    // tracks current strictly increasing subarray length
        int dec = 1;    // tracks current strictly decreasing subarray length
        int maxLen = 1; // at least 1 element is always valid
        for (int i = 1; i < n; i++) {
            if (nums[i] > nums[i - 1]) {
                inc++;   // extend increasing subarray
                dec = 1; // decreasing streak breaks, reset to current element alone
            } else if (nums[i] < nums[i - 1]) {
                dec++;   // extend decreasing subarray
                inc = 1; // increasing streak breaks, reset to current element alone
            } else {
                /* equal elements (nums[i] == nums[i - 1]) break both strict streaks */
                inc = 1;
                dec = 1;
            }
            maxLen = Math.max(maxLen, Math.max(inc, dec));
        }
        return maxLen;
    }
}