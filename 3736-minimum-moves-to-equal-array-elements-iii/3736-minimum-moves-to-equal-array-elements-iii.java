//too easuy to need an explanation
class Solution {
    public int minMoves(int[] nums) {
        int n = nums.length;
        int max = 0;
        for(int i = 0;i<n;i++){
            if(nums[i] > max){
                max = nums[i];
            }
        }
        int ans = 0;
        for(int i = 0;i<n;i++){
            ans+= max-nums[i];
        }
        return ans;
    }
}