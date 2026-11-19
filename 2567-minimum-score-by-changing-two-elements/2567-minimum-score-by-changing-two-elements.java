class Solution {
    public int minimizeSum(int[] nums) {
        /* so for this qn, wwe are given an integer array nums (length is at least 3), and we are allowed to change AT MOST 2 elements to ANY integer value we want! after those 2 changes, our total score is (low + high), where:
        - low = minimum absolute difference between ANY two elements in the array
        - high = maximum absolute difference between ANY two elements (which is literally just maxElement - minElement!)
        and our goal is to minimize (low + high)! */

        /* now heres the super clean greedy observation: since we can change 2 elements to whatever we want, we can ALWAYS force 'low' to become 0! how? just by changing an element to equal another existing element in the array, their absolute difference becomes 0! so 'low' is basically free to make 0, which means the entire problem boils down to minimizing 'high' (max - min) while making sure we create duplicates so low stays 0! */

        /* let's sort the array first so the smallest elements sit at the left (0, 1, 2...) and the largest sit at teh right (..., n-3, n-2, n-1). suppose after sorting we have [1, 4, 5, 7, 8].. to shrink the gap between max and min using 2 changes, we only ever want to touch the extreme outer elements (touching middle elements never reduces max - min!). that leaves exacty 3 possible strategies:
        1. drop the 2 largest elements (7, 8) down to match nums[n - 3] (5) -> array becomes [1, 4, 5, 5, 5]. low = 0, high = nums[n - 3] - nums[0] = 5 - 1 = 4.
        2. raise the 2 smallest elements (1, 4) up to match nums[2] (5) -> array becomes [5, 5, 5, 7, 8]. low = 0, high = nums[n - 1] - nums[2] = 8 - 5 = 3.
        3. drop the 1 largest (8 -> 7) AND raise the 1 smallest (1 -> 4) -> array becomes [4, 4, 5, 7, 7]. low = 0, high = nums[n - 2] - nums[1] = 7 - 4 = 3.
        we just compute all 3 cases and return the minimum of them, simple na! */

        Arrays.sort(nums); // sort ascending so extremes are directly accessible at both ends in O(N log N)
        int n = nums.length;

        /* case 1: change the 2 biggest numbers (n-1 and n-2) to match nums[n-3].. new max is nums[n-3], min stays nums[0] */
        int case1 = nums[n - 3] - nums[0];

        /* case 2: change the 2 smallest numbers (0 and 1) to match nums[2].. max stays nums[n-1], new min is nums[2] */
        int case2 = nums[n - 1] - nums[2];

        /* case 3: change 1 biggest (n-1 -> n-2) nad 1 smallest (0 -> 1).. new max is nums[n-2], new min is nums[1] */
        int case3 = nums[n - 2] - nums[1];

        /* low is guaranteed 0 in all 3 cases because we made duplicates, so just return the smallest possible high score! */
        return Math.min(case1, Math.min(case2, case3));
    }
}