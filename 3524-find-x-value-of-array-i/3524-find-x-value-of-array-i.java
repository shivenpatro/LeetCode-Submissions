//one of the shittiest description of questions along with the question itself, complicated for no fking reason but lets do it, CUZ WHAT CAN WE EVEN DO t_t(bahat behuda question, pakka bhul jaunga mujhe bhi pata hai sala)
//dp on remainders + prefix tracking for subarrays
class Solution {//so basically we have to find the total number of ways to pick prefix and suffix such that remaining subarray product % k gives x, but wait, removing prefix and suffix literally just leaves us with a subarray!! so the whole question is just asking: find how many non empty subarrays are there whose product % k gives remainder x, where x can be from 0 to k-1... and since n is like 10^5, doing n^2 brute force will give TLE obviously, so we will use dynamic programming / frequency count of remainders ending at each index to do this in O(n * k) time!!
    public long[] resultArray(int[] nums, int k) {
        int n = nums.length;
        //result array of size k... this will store our final answers for each remainder from 0 to k-1, so result[x] means total subarrays in whole array whose elements product % k == x
        long[] result = new long[k];
        //prevCount array keeps track of all subarrays ending at previous index (i-1)... basically prevCount[rem] tells ki index i-1 pe end hone wale kitne aise subarrays the jinka total product % k equal to rem aaya tha, so that when we move to index i, we can just multiply current element to all of them directly without recomputing from start!
        long[] prevCount = new long[k];
        for (int i = 0; i < n; i++) {//looping through whole array from 0 to n-1
            //now for current index i, we make a fresh array curCount... this will store ki current index i pe end hone wale kitne subarrays ban rahe hain for each remainder 0 to k-1
            long[] curCount = new long[k];
            //1. EXTENDING PREVIOUS SUBARRAYS:
            //now what we do is, we check every possible remainder oldRem from 0 to k-1 that was formed at index i-1...
            for (int oldRem = 0; oldRem < k; oldRem++) {
                if (prevCount[oldRem] > 0) {//if there was at least one subarray ending at i-1 with remainder oldRem, only then we can extend it by multiplying nums[i] to it
                    //now see, why we do % k % k here?? basically nums[i] can be huge up to 10^9, so first doing (nums[i] % k) scales it down to smaller than k, and oldRem is already < k, then we multiply them using (long) so it doesnt overflow 32 bit int, and then we take % k on the whole thing again to get the final new remainder between 0 to k-1!
                    int newRem = (int) (((long) oldRem * (nums[i] % k)) % k);
                    //so jitne subarrays pehle oldRem de rahe the, un sabme nums[i] multiply karne pe ab newRem aayega, so add that entire count to curCount[newRem]
                    curCount[newRem] += prevCount[oldRem];
                }
            }
            //2. STANDALONE SINGLE ELEMENT SUBARRAY:
            //dont forget this!! a subarray can also just be a single element [nums[i]] starting and ending at index i itself, which doesn't extend anything from behind!
            int singleRem = nums[i] % k;//its remainder will just be nums[i] % k
            curCount[singleRem]++; //so we just increment the count for this remainder by 1 because this is a brand new standalone subarray formed at index i
            //3. ADDING TO FINAL RESULT:
            //now whatever subarrays ended at index i for each remainder, they are all valid subarrays for our final answer, so we just add their counts into our main result array
            for (int rem = 0; rem < k; rem++) {
                result[rem] += curCount[rem];//adding to the final answer for that remainder rem
            }
            //now before going to next i, we update prevCount with curCount... because for index i+1, the current index i will act as the "previous index", so prevCount will have all info ready for next iteration!
            prevCount = curCount;
        }
        return result;//finally return the result array which has counts for all remainders 0 to k-1
    }
}

/*
========================================================================================
LINE-BY-LINE EXECUTION DRY RUN FOR nums = [1, 2, 3, 4, 5], k = 3
========================================================================================

Initial State before loop:
  n = 5, k = 3
  result = [0, 0, 0]  (size k=3, tracks total count of subarrays giving remainder 0, 1, 2)
  prevCount = [0, 0, 0] (tracks remainder counts of subarrays ending at index i-1)

----------------------------------------------------------------------------------------
ITERATION 1: i = 0, nums[0] = 1
----------------------------------------------------------------------------------------
  - curCount initialized to [0, 0, 0]

  1. Extend previous subarrays:
     - prevCount is all 0s, so nothing to extend.

  2. Standalone single element subarray [nums[0]] = [1]:
     - singleRem = nums[0] % k = 1 % 3 = 1
     - curCount[1]++ -> curCount becomes [0, 1, 0]
     (subarrays ending at index 0: [1] -> product 1 % 3 = 1)

  3. Add curCount to result:
     - result[0] += 0 -> result[0] = 0
     - result[1] += 1 -> result[1] = 1
     - result[2] += 0 -> result[2] = 0
     - result is now: [0, 1, 0]

  4. State update:
     - prevCount = [0, 1, 0]

----------------------------------------------------------------------------------------
ITERATION 2: i = 1, nums[1] = 2
----------------------------------------------------------------------------------------
  - curCount initialized to [0, 0, 0]

  1. Extend previous subarrays from prevCount = [0, 1, 0]:
     - oldRem = 1 has count = 1 (this was subarray [1])
       * newRem = (1 * (2 % 3)) % 3 = (1 * 2) % 3 = 2
       * curCount[2] += prevCount[1] -> curCount[2] += 1
       (extended subarray [1, 2] -> product 2 % 3 = 2)

  2. Standalone single element subarray [nums[1]] = [2]:
     - singleRem = nums[1] % k = 2 % 3 = 2
     - curCount[2]++ -> curCount[2] becomes 2
     (new subarray [2] -> product 2 % 3 = 2)
     - curCount is now: [0, 0, 2]
     (subarrays ending at index 1: [1, 2] with rem 2, and [2] with rem 2)

  3. Add curCount to result:
     - result[0] += 0 -> result[0] = 0
     - result[1] += 0 -> result[1] = 1
     - result[2] += 2 -> result[2] = 2
     - result is now: [0, 1, 2]

  4. State update:
     - prevCount = [0, 0, 2]

----------------------------------------------------------------------------------------
ITERATION 3: i = 2, nums[2] = 3
----------------------------------------------------------------------------------------
  - curCount initialized to [0, 0, 0]

  1. Extend previous subarrays from prevCount = [0, 0, 2]:
     - oldRem = 2 has count = 2 (these were subarrays [1, 2] and [2])
       * newRem = (2 * (3 % 3)) % 3 = (2 * 0) % 3 = 0
       * curCount[0] += prevCount[2] -> curCount[0] += 2
       (extended subarrays [1, 2, 3] and [2, 3] -> both have product % 3 = 0)

  2. Standalone single element subarray [nums[2]] = [3]:
     - singleRem = nums[2] % k = 3 % 3 = 0
     - curCount[0]++ -> curCount[0] becomes 3
     (new subarray [3] -> product 3 % 3 = 0)
     - curCount is now: [3, 0, 0]
     (subarrays ending at index 2: [1, 2, 3], [2, 3], and [3] -> all have rem 0)

  3. Add curCount to result:
     - result[0] += 3 -> result[0] = 0 + 3 = 3
     - result[1] += 0 -> result[1] = 1
     - result[2] += 0 -> result[2] = 2
     - result is now: [3, 1, 2]

  4. State update:
     - prevCount = [3, 0, 0]

----------------------------------------------------------------------------------------
ITERATION 4: i = 3, nums[3] = 4
----------------------------------------------------------------------------------------
  - curCount initialized to [0, 0, 0]

  1. Extend previous subarrays from prevCount = [3, 0, 0]:
     - oldRem = 0 has count = 3 (subarrays [1, 2, 3], [2, 3], [3])
       * newRem = (0 * (4 % 3)) % 3 = (0 * 1) % 3 = 0
       * curCount[0] += prevCount[0] -> curCount[0] += 3
       (extended subarrays [1, 2, 3, 4], [2, 3, 4], [3, 4] -> all have rem 0)

  2. Standalone single element subarray [nums[3]] = [4]:
     - singleRem = nums[3] % k = 4 % 3 = 1
     - curCount[1]++ -> curCount[1] becomes 1
     (new subarray [4] -> product 4 % 3 = 1)
     - curCount is now: [3, 1, 0]
     (subarrays ending at index 3: 3 giving rem 0, and 1 giving rem 1)

  3. Add curCount to result:
     - result[0] += 3 -> result[0] = 3 + 3 = 6
     - result[1] += 1 -> result[1] = 1 + 1 = 2
     - result[2] += 0 -> result[2] = 2
     - result is now: [6, 2, 2]

  4. State update:
     - prevCount = [3, 1, 0]

----------------------------------------------------------------------------------------
ITERATION 5: i = 4, nums[4] = 5
----------------------------------------------------------------------------------------
  - curCount initialized to [0, 0, 0]

  1. Extend previous subarrays from prevCount = [3, 1, 0]:
     - oldRem = 0 has count = 3:
       * newRem = (0 * (5 % 3)) % 3 = (0 * 2) % 3 = 0
       * curCount[0] += prevCount[0] -> curCount[0] += 3
       (extended subarrays [1, 2, 3, 4, 5], [2, 3, 4, 5], [3, 4, 5] -> rem 0)

     - oldRem = 1 has count = 1 (subarray [4]):
       * newRem = (1 * (5 % 3)) % 3 = (1 * 2) % 3 = 2
       * curCount[2] += prevCount[1] -> curCount[2] += 1
       (extended subarray [4, 5] -> product 20 % 3 = 2)

  2. Standalone single element subarray [nums[4]] = [5]:
     - singleRem = nums[4] % k = 5 % 3 = 2
     - curCount[2]++ -> curCount[2] becomes 1 + 1 = 2
     (new subarray [5] -> product 5 % 3 = 2)
     - curCount is now: [3, 0, 2]
     (subarrays ending at index 4: 3 giving rem 0, and 2 giving rem 2)

  3. Add curCount to result:
     - result[0] += 3 -> result[0] = 6 + 3 = 9
     - result[1] += 0 -> result[1] = 2
     - result[2] += 2 -> result[2] = 2 + 2 = 4
     - result is now: [9, 2, 4]

  4. State update:
     - prevCount = [3, 0, 2]

----------------------------------------------------------------------------------------
END OF LOOP
----------------------------------------------------------------------------------------
Final result returned: [9, 2, 4]
  * x = 0 (remainder 0): 9 subarrays
  * x = 1 (remainder 1): 2 subarrays
  * x = 2 (remainder 2): 4 subarrays
Matches example output perfectly!
========================================================================================
*/