class Solution {
    public int maxLength(int[] nums) {
        //ok so simple problem hai.. we need the longest subarray where product of all numbers == lcm * gcd of all numbers in that subarray..
        //since array size is small (at most 100) we can just check every possible subarray using two loops i and j!!
        //wait one big trap here!! what if array has 100 numbers and all are 10? then multiplying them gives 10^100 which will overflow even long!!
        //so how do we stop early before overflow? think about it.. the lcm of any subarray can NEVER be bigger than the lcm of the whole array (maxLcm).. and gcd can NEVER be bigger than the biggest number in the array (maxVal)..
        //so lcm * gcd can NEVER cross (maxLcm * maxVal).. and since all numbers are positive, our running product only grows bigger or stays same.. so the moment prod > maxLcm * maxVal, it can NEVER become equal to lcm * gcd again for this starting i, so we just break right there!! no overflow ever simple na!
        int n = nums.length;
        long maxLcm = 1;
        long maxVal = 0;
        for (int num : nums) {
            maxLcm = lcm(maxLcm, num); //find overall lcm of whole array to set our safe upper limit
            maxVal = Math.max(maxVal, num); //biggest number in array
        }
        long limit = maxLcm * maxVal; //lcm * gcd of any subarray can never go above this limit
        int ans = 0;
        for (int i = 0; i < n; i++) {
            long prod = 1; //reset prod, g, l for every new starting point i
            long g = nums[i];
            long l = nums[i];
            for (int j = i; j < n; j++) {
                prod *= nums[j]; //keep multiplying new elemnt into running product
                if (prod > limit) {
                    break; //prod already crossed the max possible value of lcm * gcd.. going further will only make prod bigger so just stop here to avoid overflow
                }
                g = gcd(g, nums[j]); //update gcd of subarray from i to j
                l = lcm(l, nums[j]); //update lcm of subarray from i to j
                if (prod == l * g) {
                    ans = Math.max(ans, j - i + 1); //valid subarray found so check if its length (j - i + 1) is the biggest so far
                }
            }
        }
        return ans;
    }
    //normal euclidean gcd function.. keep taking remainder until b becomes 0, whatever is left in a is the gcd
    private long gcd(long a, long b) {
        while (b != 0) {
            long temp = a % b;
            a = b;
            b = temp;
        }
        return a;
    }
    //simple math formula: lcm(a, b) = (a * b) / gcd(a, b).. we divide first (a / gcd) then multiply by b so numbers stay smaller nd dont overflow
    private long lcm(long a, long b) {
        return (a / gcd(a, b)) * b;
    }
}