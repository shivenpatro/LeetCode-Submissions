/*
This question asks us to return an ans array of same length as nums, such that ans[i] = nums[nums[i]]. thats all
*/
class Solution {
    public int[] buildArray(int[] nums) {
        int[] ans = new int[nums.length];
        for(int i = 0; i < nums.length; i++) {
            ans[i] = nums[nums[i]];
        }
        return ans;
    }
}