//2nd much more optimal proper approach, better thann kmp one
class Solution {
    public boolean check(int[] nums, int len) {
        int n = nums.length;
        // see... minn tracks the MAXIMUM element from the PREVIOUS block!
        // initialize to Integer.MIN_VALUE instead of 0 so negative numbers don't break our check!
        int minn = Integer.MIN_VALUE;
        
        // iterate block by block of size 'len'
        for (int i = 0; i < n; i += len) {
            
            // first element of this block must be >= the max of previous block!
            // because if even one element is smaller than previous block's max,
            // then after rotation, the boundary between blocks will violate sorted order!
            if (nums[i] < minn) return false;
            
            int maxx = nums[i]; // tracks the maximum element of the CURRENT block
            boolean point = false; // tracks whether we have encountered an internal descent / drop
            
            // traverse the rest of the current block from i + 1 to i + len - 1
            for (int j = i + 1; j < i + len; j++) {
                // EVERY element in this block must be >= max of previous block!
                // because after rotation, the smallest element of this block becomes the first element...
                // so the smallest element must be >= previous block's maximum!
                if (nums[j] < minn) return false;
                
                // check for a drop where previous element > current element
                if (nums[j - 1] > nums[j]) {
                    // see... in a cyclically rotated sorted array, there can be AT MOST ONE drop!
                    // if we see a second drop, it's physically impossible to rotate it into sorted order...
                    // so return false immediately!
                    if (point) return false;
                    point = true; // record our single allowed drop
                }
                
                maxx = Math.max(maxx, nums[j]);
            }
            
            // now see the wrap-around condition (exact same concept as LeetCode 1752):
            // if there was an internal drop (point == true)...
            // that means the array is split into two sorted halves: Left and Right!
            // when we rotate it so Right comes before Left...
            // the last element of Right (which is nums[i + len - 1]) must connect to first element of Left (nums[i])!
            // meaning we MUST have: nums[i + len - 1] <= nums[i]!
            // if nums[i] < nums[i + len - 1], that connection creates a SECOND drop, which is illegal!
            if (point) {
                if (nums[i] < nums[i + len - 1]) return false;
            }
            
            // this block passed all tests!
            // update minn to be the maximum of this current block...
            // so the next block knows the lower bound it must beat!
            minn = maxx;
        }
        
        // every single block is independently rotatable to sorted order,
        // and all block boundaries are strictly non-decreasing!
        return true;
    }
    
    public int sortableIntegers(int[] nums) {
        int n = nums.length;
        int ans = 0;
        
        // test every possible block size k from 1 to n
        for (int k = 1; k <= n; k++) {
            // first condition: k must divide n evenly
            if (n % k != 0) continue;
            
            // if all blocks of size k can be cyclically rotated into place, add k to total!
            if (check(nums, k)) ans += k;
        }
        
        return ans;
    }
}

/*class Solution {
    public int sortableIntegers(int[] nums) {
        int n = nums.length;//length of the original array
        
        /*
         * =========================================================================
         * BIG PICTURE / INTUITION:
         * =========================================================================
         * so see... what is the question actually asking us to do?
         * it gives us an array 'nums' and says:
         * pick an integer k. 
         * 1. k MUST divide n evenly (because we have to partition nums into equal blocks of size k)
         * 2. for each block of size k, we can cyclically rotate it independently as many times as we want!
         * 3. after rotating these blocks, can the entire array become sorted in non-decreasing order?
         * 
         * if yes, then that k is called a "sortable integer", and we need to add all such valid k values!
         * 
         * now think... if the entire array DOES become sorted after rotating the blocks...
         * what will it look like?
         * IT WILL LOOK EXACTLY LIKE Arrays.sort(nums)!
         * there is no other sorted version of nums... there is only ONE target sorted array!
         * 
         * and notice something super important:
         * the blocks are LOCKED in their positions!
         * block 0 covers indices [0 ... k-1]
         * block 1 covers indices [k ... 2k-1]
         * block 2 covers indices [2k ... 3k-1]
         * 
         * elements CANNOT jump from block 0 to block 1! they can only rotate WITHIN their own block!
         * so for the whole array to become sorted:
         * - block 0 of nums MUST be able to rotate into block 0 of sorted!
         * - block 1 of nums MUST be able to rotate into block 1 of sorted!
         * - every single block independently must match its exact corresponding slice in sorted!
         * if even a SINGLE block fails... that k is dead, simple!
         * =========================================================================
         */
        
        /*// step 1: make a copy and sort it so we have our target blueprint!
        int[] sorted = nums.clone();
        Arrays.sort(sorted);//this tells us what every block SHOULD look like after rotation
        
        int totalSum = 0;//this will accumulate all valid sortable integers k
        
        // step 2: iterate over all possible divisors k from 1 up to n
        for (int k = 1; k <= n; k++) {
            // first condition from problem statement: k must divide n!
            // if n % k != 0, we can't even partition the array into equal blocks of size k...
            // so why waste time? skip immediately!
            if (n % k != 0) {
                continue;
            }
            
            // assume this k will work, until one block proves us wrong!
            boolean isValidK = true;
            
            // step 3: slice both arrays into blocks of size k and test each block
            // we start at 0 and jump by k each time: 0, k, 2k, 3k ...
            for (int start = 0; start < n; start += k) {
                // extract the k-sized chunk from our original nums array
                int[] blockNums = Arrays.copyOfRange(nums, start, start + k);
                
                // extract the exact same k-sized chunk from our sorted blueprint array
                int[] blockSorted = Arrays.copyOfRange(sorted, start, start + k);
                
                // now check: can blockNums be cyclically shifted to match blockSorted?
                if (!isRotationPossible(blockNums, blockSorted, k)) {
                    // see... if even ONE block cannot match its sorted slice...
                    // then the entire array can never become sorted using this k!
                    // so mark isValidK as false and break out of the inner loop immediately...
                    // no point checking the remaining blocks for this k!
                    isValidK = false;
                    break;
                }
            }
            
            // if we checked ALL blocks of size k and none of them failed...
            // that means every single block can be rotated into place!
            // so this k is a valid sortable integer, add it to our running total!
            if (isValidK) {
                totalSum += k;
            }
        }
        
        return totalSum;//return the final sum of all valid k values
    }
    
    /*
     * =========================================================================
     * HOW DO WE CHECK IF ARRAY 'source' CAN BE CYCLICALLY ROTATED INTO 'target'?
     * =========================================================================
     * 
     * imagine you have:
     * target = [5, 6, 7]
     * source = [6, 7, 5]
     * 
     * what are all the possible cyclic rotations of target?
     * rotation 0: [5, 6, 7]
     * rotation 1: [6, 7, 5]
     * rotation 2: [7, 5, 6]
     * 
     * now see the classic trick... what happens if you concatenate target with itself?
     * text = target + target = [5, 6, 7, 5, 6, 7]
     * 
     * look at every window of size k = 3 inside this doubled array:
     * index 0 to 2: [5, 6, 7]  <--- rotation 0!
     * index 1 to 3: [6, 7, 5]  <--- rotation 1! (our source!)
     * index 2 to 4: [7, 5, 6]  <--- rotation 2!
     * 
     * THAT'S THE WHOLE MAGIC!
     * doubling the target array naturally contains EVERY SINGLE CYCLIC ROTATION
     * as a simple contiguous subarray of length k!
     * 
     * so checking "can source rotate to target?"
     * is 100% equivalent to:
     * "does source exist as a contiguous subarray inside (target + target)?"
     * 
     * and to find a subarray/pattern of length k inside an array of length 2k in O(k) linear time...
     * we use KMP pattern matching!
     * =========================================================================
     */
    /*private boolean isRotationPossible(int[] source, int[] target, int k) {
        // create the doubled target array of length 2 * k
        int[] text = new int[2 * k];
        for (int i = 0; i < k; i++) {
            text[i] = target[i];
            text[i + k] = target[i];//append the second copy right after
        }
        
        // now use KMP to check if 'source' (our pattern) appears anywhere inside 'text'
        return kmpSearch(text, source);
    }
    
    // standard KMP search adapted for integer arrays
    private boolean kmpSearch(int[] text, int[] pattern) {
        int n = text.length;    // length is 2 * k
        int m = pattern.length; // length is k
        
        // build the LPS table for the pattern
        int[] lps = computeLPS(pattern);
        
        int i = 0;//pointer for text (doubled target array)
        int j = 0;//pointer for pattern (source array)
        
        while (i < n) {
            // if current elements match, advance both pointers forward!
            if (text[i] == pattern[j]) {
                i++;
                j++;
            }
            
            // if j reached m, that means we matched all m elements of pattern continuously!
            if (j == m) {
                return true;//match found! source is indeed a valid cyclic rotation of target!
            } else if (i < n && text[i] != pattern[j]) {
                // mismatch happened after j matches!
                // now instead of resetting i back to the beginning (which would make it O(k^2) naive)...
                // KMP's genius is: we don't touch i at all!
                // we only move j back to lps[j - 1], because lps tells us the longest prefix
                // that is already matching as a suffix, so we avoid redundant comparisons!
                if (j != 0) {
                    j = lps[j - 1];
                } else {
                    // if j is already 0 and it doesn't match, we just move to the next element in text
                    i++;
                }
            }
        }
        
        return false;//exhausted the whole text and never matched the pattern
    }
    
    // computes the Longest Prefix Suffix (LPS) array
    private int[] computeLPS(int[] pattern) {
        int m = pattern.length;
        int[] lps = new int[m];
        
        // lps[i] stores the length of the longest proper prefix of pattern[0...i]
        // that is ALSO a suffix of pattern[0...i]
        int len = 0;//length of the previous longest prefix suffix
        int i = 1;
        
        // lps[0] is always 0 because a single character has no proper prefix
        lps[0] = 0;
        
        while (i < m) {
            if (pattern[i] == pattern[len]) {
                // we extended the matching prefix-suffix by 1!
                len++;
                lps[i] = len;
                i++;
            } else {
                // mismatch occurred while trying to extend 'len'
                if (len != 0) {
                    // fallback to the previous known prefix length from lps table
                    // notice we do NOT increment i here! we re-evaluate pattern[i] with the smaller len!
                    len = lps[len - 1];
                } else {
                    // no prefix matches at all, so lps for this index is 0
                    lps[i] = 0;
                    i++;
                }
            }
        }
        
        return lps;
    }
}
*/