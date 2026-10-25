class Solution {
    public int sortPermutation(int[] nums) {
        int n = nums.length;//length of our permutation
        
        /*
         * =========================================================================
         * BIG PICTURE / THE BITWISE AND INTUITION:
         * =========================================================================
         * so see... what is the swap rule given in the problem?
         * we can swap nums[i] and nums[j] IF AND ONLY IF:
         * nums[i] & nums[j] == k!
         * 
         * now think deeply about how bitwise AND (&) works at the bit level:
         * if k has a '1' at some bit position b (the b-th bit of k is set)...
         * then for nums[i] & nums[j] to equal k, 
         * BOTH nums[i] AND nums[j] MUST ALSO have a '1' at that exact bit position b!
         * why? because (1 & 0) = 0 and (0 & 0) = 0... 
         * if even ONE of them had a 0 at bit b, their AND would result in 0 at bit b,
         * which would immediately violate nums[i] & nums[j] == k!
         * 
         * now look at what swapping actually does:
         * nums is a permutation of integers from 0 to n - 1.
         * in a fully sorted array, every number x belongs at index x (nums[i] == i)!
         * if nums[i] != i, that number is MISPLACED!
         * and to get back to its home, it must be swapped (either directly or via a chain of swaps).
         * 
         * but see... if ANY number participates in ANY swap under this rule...
         * it MUST possess all the '1' bits that k has!
         * if even a SINGLE misplaced number is missing a bit (has a 0 where k has a 1)...
         * that number can NEVER be legally swapped! it would be permanently stuck!
         * 
         * that means:
         * EVERY SINGLE MISPLACED ELEMENT must have a 1 at every bit position where k has a 1!
         * 
         * and since we want to find the MAXIMUM possible value of k...
         * we shouldn't arbitrarily turn off any bits that everyone shares!
         * k should simply be the cumulative bitwise AND of ALL misplaced elements!
         * simple as that!
         * =========================================================================
         */
        
        // initialize k with -1 (0xFFFFFFFF in two's complement binary)
        // why -1? because -1 has literally ALL bits set to 1!
        // so it serves as the neutral starting identity for bitwise AND (x & -1 == x)!
        int k = -1;
        
        // iterate through the entire permutation to find all misplaced numbers
        for (int i = 0; i < n; i++) {
            // if nums[i] == i, the element is already sitting in its rightful sorted position!
            // it doesn't need to be swapped at all, so it puts no restriction on k!
            if (nums[i] != i) {
                // this element is misplaced and MUST participate in swaps...
                // so we AND it into k to shave off any bits that this number does not have!
                k &= nums[i];
            }
        }
        
        // see... what if k is STILL -1 after the loop?
        // that means nums[i] != i was NEVER true for any element!
        // meaning every single number was already at its correct index (already sorted)!
        // the problem explicitly states: "If nums is already sorted, return 0."
        if (k == -1) {
            return 0;
        }
        
        return k;//return the maximum valid k that allows all necessary swaps!
    }
}