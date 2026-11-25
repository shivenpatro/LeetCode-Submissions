class Solution {
    //so we need to find the smallest length of a subarray that appears ONLY ONCE in the whole array (no exact duplicate copy of it anywhere else)..
    //notice one cool thing: if some subarray of length 3 is unique, then adding more numbers to it to make length 4 will ALSO stay unique.. so sizes behave in sorted order and we can do binary search on the length from 1 to n!!
    //now inside check(size), we need to check all subarrays of that fixed size.. if we put full subarrays/lists inside a map, comparing them takes O(n) every time which gives TLE..
    //so instead of storing full subarrays, we turn each subarray into one single number (rolling hash)!! just like turning [1, 1, 2] into 112 by doing (num * 10 + nextDigit)..
    //only catch is we cant use 10 becoz numbers can be bigger than 10 (like 12 and 3 vs 1, 2, 3 would both become 123).. so we pick base = (biggest number in array + 1) so no number ever clashes with our base!!
    long MOD = 1_000_000_007L;
    public int smallestUniqueSubarray(int[] nums) {
        int n = nums.length;
        long maxVal = 0;
        for (int num : nums) {
            maxVal = Math.max(maxVal, num); //find biggest number in array to decide our base
        }
        long base = maxVal + 1; //always 1 more than the biggest number so hash values dont mix up
        int l = 1;
        int r = n;
        int ans = n; //worst case the whole array itself of size n is always unique
        //normal binary search on subarray size from 1 to n
        while (l <= r) {
            int mid = l + (r - l) / 2;
            if (check(nums, mid, base)) {
                ans = mid; //found at least one unique subarray of size mid.. save it and try even smaller size on left
                r = mid - 1;
            } else {
                l = mid + 1; //no unique subarray of size mid exists so we have to try bigger sizes on right
            }
        }
        return ans;
    }
    //checks if there is at least one unique subarray of exact length 'size' using sliding window + rolling hash
    private boolean check(int[] nums, int size, long base) {
        int n = nums.length;
        HashMap<Long, Integer> map = new HashMap<>(); //stores hash number -> how many times we saw this exact subarray
        long hash = 0;
        long p = findPower(base, size - 1); //precompute base^(size - 1) once so we can easily remove the leftmost number from window later
        int i = 0;
        int j = 0;
        while (j < n) {
            hash = (hash * base + nums[j]) % MOD; //shift old hash left and add new number nums[j] at the end
            if (j - i + 1 < size) {
                j++; //window still smaller than needed size so just keep expanding right
            } else {
                map.put(hash, map.getOrDefault(hash, 0) + 1); //window hit exact size so record this subarray's hash count in map
                long leftPart = (nums[i] * p) % MOD; //value contributed by leftmost number nums[i] sitting at the highest place value
                hash = (hash - leftPart + MOD) % MOD; //kick out leftmost number before sliding.. +MOD makes sure subtraction never turns negative
                i++; //slide whole window 1 step forward
                j++;
            }
        }
        //if any hash showed up exacty 1 time, that means a unique subarray of this size exists!
        for (int freq : map.values()) {
            if (freq == 1) {
                return true;
            }
        }
        return false;
    }
    //fast power function to calculate (a^b) % MOD in O(log b) time instead of looping b times
    private long findPower(long a, long b) {
        long res = 1;
        a %= MOD;
        while (b > 0) {
            if ((b & 1) == 1) {
                res = (res * a) % MOD; //if power is odd multiply current base into answer
            }
            a = (a * a) % MOD; //square the base
            b >>= 1; //cut power in half
        }
        return res;
    }
}