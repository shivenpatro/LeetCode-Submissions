//very very easy and direct question
//juast see, use a nested loop, find every subarray, and while finding each subarrayy store all the elements for that subarray in a hashset so that only uniqye elements stay.. and then simply do  sum + hashset.size^2... thats it na
class Solution {
    public int sumCounts(List<Integer> nums) {
        int n = nums.size();
        int totalSum = 0;
        // see... we need to find all possible subarrays from index i to j.
        // constraints are tiny (n <= 100), so n^2 is around 5000 operations only!
        // a simple nested loop with a HashSet on the fly is more than enough simple
        for (int i = 0; i < n; i++) {
            // create a fresh set for every starting index i to keep track of unique elements
            HashSet<Integer> set = new HashSet<>();
            for (int j = i; j < n; j++) {
                // add current element to the set
                set.add(nums.get(j));
                // set.size() automatically gives the number of distinct values in subarray nums[i...j]
                int distinctCount = set.size();
                // square the distinct count and add it to our running total sum
                totalSum += (distinctCount * distinctCount);//.. see while we are forming the subarray..i to j... every combination is itself a subarray of unique elements.. so on the flow we do summation not once the hwole subarray is formed ok?
            }
        }
        return totalSum;
    }
}