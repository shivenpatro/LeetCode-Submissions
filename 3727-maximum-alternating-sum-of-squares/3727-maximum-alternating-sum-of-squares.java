class Solution {
    public long maxAlternatingSum(int[] nums) {
        int n = nums.length;
        long arr[] = new long[n];
        for(int i = 0;i<n;i++){
            arr[i] = nums[i] * nums[i];
        }
        Arrays.sort(arr);
        int x = 0;
        int y = n-1;
        long sum = 0;
        while(y>=x){
            if(y==x){
                sum = sum + arr[y];
                y--;
                x++;
                continue;
            }
            sum = sum + arr[y];
            sum = sum - arr[x];
            y--;
            x++;
        }
        return sum;
    }
}