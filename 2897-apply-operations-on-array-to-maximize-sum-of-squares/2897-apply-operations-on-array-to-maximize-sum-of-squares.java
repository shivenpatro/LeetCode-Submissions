class Solution {
    public int maxSum(List<Integer> nums, int k) {
        // modulo constant as asked in problem description
        long mod = 1_000_000_007L;
        // see... first understand the core trick/magic of this question:
        // we are given:
        // nums[i] = nums[i] AND nums[j]
        // nums[j] = nums[i] OR nums[j]
        //
        // trace what happens bit-by-bit:
        // - if both had 0 at some bit pos -> (0 & 0) = 0, (0 | 0) = 0 (bits remain 0)
        // - if both had 1 at some bit pos -> (1 & 1) = 1, (1 | 1) = 1 (bits remain 1)
        // - if one had 1 and one had 0   -> (1 & 0) = 0, (1 | 0) = 1!
        // 
        // LOOK AT THAT THIRD CASE:
        // the 1 bit literally just moved from nums[i] to nums[j]!
        // it means 1 bits NEVER get destroyed or created out of thin air...
        // we can literally shift '1' bits across elements freely along the same bit position!
        // 
        // now why concentrate all 1s into one single number?
        // simple math: (x - d)^2 + (y + d)^2 > x^2 + y^2 (when y > x)!
        // squaring heavily rewards making large numbers even LARGER!
        // so greedily, we want to cram as many 1-bits as possible into the first number, 
        // then as many leftover 1-bits into the second number, and so on for k numbers!
        // count[b] will store the total number of '1' bits present at bit position b (0 to 31) across the entire array
        int[] count = new int[32];
        for (int num : nums) {
            for (int b = 0; b < 32; b++) {
                // check if the b-th bit of 'num' is set (is 1)
                if ((num & (1 << b)) != 0) {
                    count[b]++; // record that this bit position has an available 1
                }
            }
        }
        long result = 0; // stores the accumulated sum of squares
        // we only need to pick k elements with the maximum sum of squares...
        // so we greedily construct the k largest possible numbers!
        while (k > 0) {
            long current = 0; // build current largest number from scratch
            // try to set EVERY bit position b from 0 to 31 if an available '1' bit exists in count[b]
            for (int b = 0; b < 32; b++) {
                if (count[b] > 0) {
                    // yes! we have an available '1' bit at position b...
                    // so consume 1 count of it:
                    count[b]--;
                    // and set the b-th bit on our 'current' number using bitwise OR:
                    current |= (1L << b);
                }
            } 
            // square current number and add it to total sum modulo 10^9 + 7
            long squared = (current * current) % mod;
            result = (result + squared) % mod;
            k--; // one of our k numbers constructed
        }
        return (int) result;
    }
}