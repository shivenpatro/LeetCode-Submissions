class Solution {
    public int minOperations(int[] nums) {
        int n = nums.length;//length of the permutation array
        
        /*
         * =========================================================================
         * BIG PICTURE / INTUITION:
         * =========================================================================
         * so see... what are the only two allowed operations?
         * 1. Reverse the entire array.
         * 2. Rotate the array left by 1 (move first element to the very end).
         * 
         * think about what these two operations actually do to the elements on a circle:
         * - Rotate left simply changes WHICH element is considered the start/head!
         * - Reverse simply flips the direction (clockwise becomes counterclockwise)!
         * 
         * NOTICE THE MOST IMPORTANT INVARIANT:
         * NEITHER operation can ever scramble or change who your circular neighbors are!
         * if 2 is sitting next to 3 and 1 on the circle, 
         * rotating or reversing will NEVER separate 2 from 1 and 3!
         * 
         * that means:
         * if the array is to EVER become sorted [0, 1, 2, ..., n - 1],
         * the original input array MUST ALREADY be either:
         * 1. Cyclic Ascending (e.g., [3, 4, 0, 1, 2] -> moving clockwise always adds +1)
         * 2. Cyclic Descending (e.g., [3, 2, 1, 0, 4] -> moving clockwise always subtracts 1)
         * 
         * if it is neither (i.e. numbers are scrambled in a bad order like [0, 3, 1, 2])...
         * no combination of full-reverses and left-shifts can EVER fix it!
         * so we return -1 immediately! simple!
         * =========================================================================
         */
        
        int ascendingCount = 0;
        int descendingCount = 0;
        
        // step 1: check if the array is cyclic ascending or cyclic descending
        for (int i = 1; i < n; i++) {
            // in cyclic ascending, every element nums[i] should be (nums[i-1] + 1) % n
            if (nums[i] == (nums[i - 1] + 1) % n) {
                ascendingCount++;
            }
            
            // in cyclic descending, the previous element nums[i-1] should be (nums[i] + 1) % n
            if (nums[i - 1] == (nums[i] + 1) % n) {
                descendingCount++;
            }
        }
        
        // =========================================================================
        // CASE 1: THE ARRAY IS CYCLIC ASCENDING (ascendingCount == n - 1)
        // =========================================================================
        if (ascendingCount == n - 1) {
            // if index 0 is already 0, and the whole array is cyclic ascending...
            // that means it's ALREADY [0, 1, 2, ..., n - 1]!
            // 0 operations needed!
            if (nums[0] == 0) {
                return 0;
            }
            
            // now see... 0 is sitting somewhere in the middle, and we need to bring 0 to index 0!
            // we have two choices:
            // 
            // Choice A: rotate left until 0 reaches index 0!
            // how many elements are currently sitting BEFORE 0?
            // since nums[0] = x, the elements before 0 are x, x+1, ..., n-1.
            // total elements before 0 is: (n - 1) - x + 1 = n - x = (n - nums[0])!
            // so each left shift moves one element out of the way...
            // total cost = (n - nums[0]) left rotations!
            int leftRotations = n - nums[0];
            
            // Choice B: what if we rotate RIGHT to bring 0 to the front?
            // but wait... we DO NOT have a rotate-right operation!
            // how do you simulate rotating right?
            // reverse + 1 left rotation + reverse = 1 right rotation!
            // so to rotate right 'k' times:
            // reverse (1 op) + k left rotations (k ops) + reverse (1 op) = k + 2 ops!
            // 
            // and how many right rotations are needed to bring 0 to index 0?
            // 0 needs to travel across all elements from 0 to the end of the array...
            // the elements from 0 to the end are [0, 1, ..., nums[n - 1]],
            // which is exactly (nums[n - 1] + 1) elements!
            // so we need (nums[n - 1] + 1) right rotations, costing:
            int rightRotations = (nums[n - 1] + 1) + 2;//+2 accounts for the two full reverses!
            
            return Math.min(leftRotations, rightRotations);
        }
        
        // =========================================================================
        // CASE 2: THE ARRAY IS CYCLIC DESCENDING (descendingCount == n - 1)
        // =========================================================================
        if (descendingCount == n - 1) {
            // see... because it's in descending order, we MUST reverse it at least once
            // to make it ascending!
            // 
            // Choice A: reverse the array first! (cost = 1)
            // reversing turns the cyclic descending array into a cyclic ascending array!
            // after reverse:
            // the element that used to be at nums[n - 1] is now the NEW nums[0]!
            // and from Choice A of ascending above,
            // the number of left rotations needed is (n - new_nums[0]) = (n - nums[n - 1])!
            // total cost = 1 (for reverse) + (n - nums[n - 1]) (for left rotations)
            int optionA = 1 + (n - nums[n - 1]);
            
            // Choice B: what if we left rotate FIRST, and reverse at the very end?
            // if we left rotate until 0 lands at the very LAST index (index n - 1)...
            // then the array looks like [n - 1, n - 2, ..., 1, 0]!
            // then ONE single reverse at the end will flip it directly into [0, 1, ..., n - 1]!
            // 
            // how many left rotations does it take to move 0 to the very last index?
            // nums[0] tells us there are nums[0] elements before 0 in descending order...
            // rotating nums[0] times brings 0 to index 0...
            // rotating 1 more time pushes 0 to the last index!
            // so (nums[0] + 1) left rotations!
            // BUT wait: what if 0 is ALREADY at the last index (nums[n - 1] == 0)?
            // then (nums[0] + 1) equals n, and rotating n times does nothing!
            // so we take modulo n: ((nums[0] + 1) % n)!
            // total cost = ((nums[0] + 1) % n) left rotations + 1 reverse!
            int optionB = ((nums[0] + 1) % n) + 1;
            
            return Math.min(optionA, optionB);
        }
        
        // =========================================================================
        // CASE 3: NEITHER ASCENDING NOR DESCENDING
        // =========================================================================
        // the array has a broken cyclic permutation order...
        // it can never be sorted with just full-reversals and cyclic shifts!
        return -1;
    }
}