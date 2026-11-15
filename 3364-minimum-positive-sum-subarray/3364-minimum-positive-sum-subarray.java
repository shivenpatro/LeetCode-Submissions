/*
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
//TC was O(n*r)... i runs n times and j will run till r for every i
*/

//better approach
class Solution {
    public int minimumSumSubarray(List<Integer> nums, int l, int r) {
        /* now look at what approach 1 was wasting time on.. when starting at index i, why did we walk j through lengths 1, 2, ..., l - 1 when we already know any length strictly less than l is completely useless?! if we precompute a prefix sum array first, we can jump DIRECTLY to length l (so j starts at i + l - 1) and test only valid lengths from l up to r! for each valid length len, the subarray ends at j = i + len - 1, and its sum is computed in O(1) using prefSum[j + 1] - prefSum[i]! this cuts out all wasted shorter subarrays and brings time down to O(N * (r - l + 1)), simple affffff */
        int n = nums.size();
        int[] prefSum = new int[n + 1];//pref sum defination
        // build 1-indexed prefix sum array so sum of nums[i..j] is prefSum[j + 1] - prefSum[i] 
        for (int i = 0; i < n; i++) {
            prefSum[i + 1] = prefSum[i] + nums.get(i);
        }
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < n; i++) {
            /* skip lengths < l completely and test only valid lengths from l to r */
            for (int len = l; len <= r; len++) {
                int j = i + len - 1;
                if (j >= n) break; // out of bounds, larger lengths for this i will also be out of bounds
                int subSum = prefSum[j + 1] - prefSum[i];//this is the sum of the subarray
                if (subSum > 0) {
                    min = Math.min(min, subSum);
                }
            }
        }
        return min == Integer.MAX_VALUE ? -1 : min;
    }
}