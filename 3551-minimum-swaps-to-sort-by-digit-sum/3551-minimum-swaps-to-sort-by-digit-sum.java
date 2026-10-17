import java.util.*;

class Solution {
    // helper function to calculate the sum of digits of a given number [00:04:20]
    private int getDigitSum(int num) {
        int sum = 0;
        while (num > 0) {
            sum += (num % 10); // extract rightmost digit and add to sum
            num /= 10;         // trim the rightmost digit
        }
        return sum;
    }

    public int minSwaps(int[] nums) {
        int n = nums.length;
        
        // see... problem gives us an array of distinct positive integers!
        // and we need to sort it based on two conditions [00:00:37]:
        // 1. increasing order of digit sum!
        // 2. if digit sums are tied/equal, the smaller actual number comes first!
        // then we need to find the MINIMUM number of swaps to make original nums match that sorted order!
        
        // let's create an array of objects/boxes to store [val, digitSum] so we don't recalculate digitSum repeatedly
        // or we can simply box them as Integer[] so Arrays.sort can use custom comparator!
        Integer[] sortedNums = new Integer[n];
        for (int i = 0; i < n; i++) {
            sortedNums[i] = nums[i];
        }
        
        // Step 1: custom sort sortedNums based on the problem's rules [00:03:27]
        Arrays.sort(sortedNums, (a, b) -> {
            int sumA = getDigitSum(a);
            int sumB = getDigitSum(b);
            
            // if digit sums are different, smaller digit sum comes first!
            if (sumA != sumB) {
                return Integer.compare(sumA, sumB);
            }
            // tie-breaker: if digit sums are identical, smaller actual value comes first! [00:05:09]
            return Integer.compare(a, b);
        });
        
        // Step 2: we need a fast way to find where any number currently sits in nums! [00:07:10]
        // because all numbers in nums are DISTINCT (given in constraints)...
        // a HashMap<Value, Index> gives us the exact current location of any number in O(1) time!
        Map<Integer, Integer> valToIndex = new HashMap<>();
        for (int i = 0; i < n; i++) {
            valToIndex.put(nums[i], i);
        }
        
        int swaps = 0; // tracks total swaps performed
        
        // Step 3: greedily place each element into its rightful sorted position [00:06:47]
        for (int i = 0; i < n; i++) {
            int expectedVal = sortedNums[i]; // what SHOULD be at index i
            int currentVal = nums[i];        // what is CURRENTLY sitting at index i
            
            // if current element is already the expected element... no swap needed!
            if (currentVal == expectedVal) {
                continue;
            }
            
            // otherwise, where is that expectedVal currently hiding in nums?
            // ask our map to give its current index! [00:07:54]
            int expectedValIdx = valToIndex.get(expectedVal);
            
            // now see... we perform the swap between index i and expectedValIdx:
            // 1. put expectedVal at index i (now index i is permanently fixed and sorted!) [00:08:49]
            // 2. move currentVal to expectedValIdx
            nums[i] = expectedVal;
            nums[expectedValIdx] = currentVal;
            
            // and don't forget to update the map so subsequent lookups know the new positions! [00:09:09]
            valToIndex.put(expectedVal, i);
            valToIndex.put(currentVal, expectedValIdx);
            
            // one swap done, which guarantees at least one element (at index i) is fixed in place!
            swaps++;
        }
        
        return swaps;
    }
}