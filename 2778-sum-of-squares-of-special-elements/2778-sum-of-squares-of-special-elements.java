//stupidly easy question, i dont think line by line comments also needed
class Solution {
    public int sumOfSquares(int[] nums) {
        int n = nums.length;
        int sum =0;//final answer
        for(int i = 0;i<n;i++){
            if(n%(i+1) == 0) sum+= (nums[i] * nums[i]);
        }
        return sum;
    }
}