class Solution {
    public int numOfSubarrays(int[] arr) {
        /* ok so see.. what is this problem actually asking? we are given an integer array arr and we want to count the total number of contiguous subarrays whose sum is ODD! and since the number of subarrays can get huge (up to N*(N+1)/2 which is ~5 * 10^9 for N = 10^5), we must return the answer modulo 10^9 + 7! */
        /* now think about basic math parity rules.. when is the difference between two prefix sums (currSum - prevPrefixSum) an ODD number? 
        1. if currSum is EVEN: (EVEN - ODD) = ODD! so any previous prefix sum that was ODD can be subtracted from currSum to form a valid odd-sum subarray ending right here at index i! that means we add 'oddCount' to our answer!
        2. if currSum is ODD: (ODD - EVEN) = ODD! so any previous prefix sum that was EVEN can be subtracted from currSum to form a valid odd-sum subarray ending at index i! that means we add 'evenCount' to our answer!
        and super important corner case: why do we initialize evenCount = 1 and oddCount = 0? because BEFORE index 0, our empty prefix sum is 0, and 0 is an EVEN number! if we encounter an odd currSum at index i, subtracting that initial 0 prefix sum represents the entire subarray arr[0..i] itself! if we forget evenCount = 1, we would completely miss every odd subarray starting from index 0, simple na! */
        int MOD = 1_000_000_007;
        long totalOddSubarrays = 0;
        int currSum = 0;
        int evenCount = 1; // initial empty prefix sum is 0, which is even
        int oddCount = 0;  // no odd prefix sums seen yet
        for (int num : arr) {
            currSum += num;
            if (currSum % 2 == 0) {
                /* currSum is EVEN: pairing it with any past ODD prefix sum gives (EVEN - ODD) = ODD subarray */
                totalOddSubarrays = (totalOddSubarrays + oddCount) % MOD;
                evenCount++; // record this current even prefix sum for future elements
            } else {
                /* currSum is ODD: pairing it with any past EVEN prefix sum (including initial 0) gives (ODD - EVEN) = ODD subarray */
                totalOddSubarrays = (totalOddSubarrays + evenCount) % MOD;
                oddCount++; // record this current odd prefix sum for future elements
            }
        }
        return (int) totalOddSubarrays;
    }
}