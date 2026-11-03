class Solution {
    public int minMoves(int[] nums) {
        /* so see.. what is the problem asking us? we are given an integer array nums of size n, and in one move we can increment n - 1 elements by 1. our goal is to find the minimum number of moves required to make all elements equal. 
        
        now think about it.. incrementing n - 1 elements by 1 is mathematically completely equivalent to decrementing just 1 single element by 1! why? because if you want all numbers to be equal, only the relative difference between elements matters, not their absolute values. instead of doing the annoying thing of constantly hunting down the max element and adding 1 to all other n - 1 elements (which would be super slow and take n^2 time), we can flip the perspective: we just want to bring every single element down to match the minimum element in the array! 
        
        so the total number of moves is literally just the sum of (nums[i] - minElement) for every element in the array. if we expand that mathematical expression: sum(nums[i]) - (n * minElement), we get a clean O(N) solution in a single pass without needing to simulate any moves at all, simple na! */

        long sum = 0;
        int minVal = Integer.MAX_VALUE;

        /* single pass to calculate the total sum of all elements and find the smallest element in the array */
        for (int num : nums) {
            sum += num;
            minVal = Math.min(minVal, num);
        }

        /* total moves required is the total sum minus (n * minElement) */
        return (int) (sum - (long) minVal * nums.length);
    }
}