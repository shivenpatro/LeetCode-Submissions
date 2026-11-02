class Solution {
    public int minMoves2(int[] nums) {
        //so see.. in 453 it was diff we has to do -1 or +1 to n-1 elements ok.. here we have the full freedom and we have to minimize it.. think logically now... to minimize,... how can we reduce the number... to do that 1st we have to finalize a number to which we will make al the elements equal right na.. so now think think... if we sort the array and then take the median.. which is the middlen element.. kust tjink.. prettyt sure that will take the least possible steps na?? because think about it... it sits exactly at the middle... like for example in 1,2,3... choosin to make all equal to 1 or 3 will need 3 steps...(making 1 to 3..2 steps..2 to 3..1 step.. and 3 to 3 0... same for making all equal to 1..)but for median which is 2.. total is 2 steps... 
        //and obv even if even elements are there..like in ex 2... any of the 2 median are fine.. u can check for ex 2... it will lead to  same number of steps 
        Arrays.sort(nums); // sort the array so that elements are arranged in non-decreasing order
        int n = nums.length;
        int median = nums[n / 2]; // the element sitting at n / 2 is our optimal median target
        int moves = 0;
        /* loop through each number and accumulate how many steps are needed to reach the median */
        for (int num : nums) {
            moves += Math.abs(num - median); // absolute difference is the exact number of increments or decrements
        }
        return moves; // return total minimum moves required
    }
}