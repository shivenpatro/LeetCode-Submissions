//brute approach..just check every subarrayh adn whereever the i and j condirtion matches with the l and r and then find the sum of that subarray and keep a min vairaible to keep recording the min sum of all valid subarrays
class Solution {
    public int minimumSumSubarray(List<Integer> nums, int l, int r) {
        int n = nums.size();
        int min = Integer.MAX_VALUE;//min recorder simple shit
        for(int i=0; i<n ;i++){
            int sum = 0;
            for(int j=i; j<n ;j++){//for every plausioble subarray from i.. we reset the sum always before it
                sum += nums.get(j);//keep adding this j to the sum ok.. and then everytime keep checking the conditinos which are done below.. if it is followed... then at that moment the subarray formed till that time is valiud that means.. so check with the min... simpke... keep repeating this
                if(j-i+1 <= r && j-i+1 >= l && sum>0){
                    min = Math.min(sum,min);
                }
            }
        }
        if(min == Integer.MAX_VALUE) return -1;
        return min;
    }
}