import java.util.Arrays;

class Solution {
    // modulo constant 10^9 + 7
    long mod = 1_000_000_007L;
    
    // see... from MIK's video intuition:
    // we need 3 trees of size 4 * n:
    // 1. sqTree: stores the sum of SQUARES of distinct counts for each segment [00:52:13]
    // 2. sumTree: stores the simple linear sum of distinct counts for each segment [00:54:25]
    // 3. lazyTree: stores lazy pending updates (+val) that need to be propagated down to children [00:54:34]
    long[] sqTree;
    long[] sumTree;
    long[] lazyTree;
    
    public int sumCounts(int[] nums) {
        int n = nums.length;
        
        // standard segment tree sizing: 4 * n is completely safe to prevent any array out of bounds!
        sqTree = new long[4 * n];
        sumTree = new long[4 * n];
        lazyTree = new long[4 * n];
        
        // lastSeen[x] stores the last index where number 'x' appeared in nums [00:21:55]
        // constraints say nums[i] <= 10^5, so an array of size 100001 is much faster than HashMap!
        int[] lastSeen = new int[100001];
        Arrays.fill(lastSeen, -1); // initialize with -1 meaning not seen yet
        
        long totalResult = 0;
        
        // we process the array left-to-right from index 0 to n - 1 [01:01:59]
        for (int i = 0; i < n; i++) {
            int val = nums[i];
            
            // see... where was this value last seen?
            int prevIdx = lastSeen[val];
            
            // KEY RANGE INTUITION:
            // if 'val' was last seen at prevIdx, then for any subarray starting in the range [prevIdx + 1, i]
            // this 'val' is a BRAND NEW distinct element!
            // so the distinct count of all subarrays starting from (prevIdx + 1) to i increases by +1! [00:14:14]
            // but for subarrays starting <= prevIdx, 'val' was ALREADY present earlier, so their count does not change!
            int start = prevIdx + 1;
            int end = i;
            
            // range update in our segment tree: add +1 to all elements in range [start, end]
            // root node is at index 0, representing the full array range [0, n - 1]
            updateRange(start, end, 0, 0, n - 1, 1);
            
            // after updating, the root node of sqTree (sqTree[0]) contains the sum of squares
            // of distinct counts for ALL subarrays ending at index i! [00:25:49]
            totalResult = (totalResult + sqTree[0]) % mod;
            
            // update last seen index of current number to i
            lastSeen[val] = i;
        }
        
        return (int) totalResult;
    }
    
    // helper method to apply lazy additions and update both sqTree and sumTree in one go [00:57:32]
    private void lazyAddition(int i, int l, int r, long val) {
        long len = (long) r - l + 1; // number of leaf elements covered under this node
        
        /*
         * =========================================================================
         * MATHEMATICAL DERIVATION FOR SQUARED SUM UPDATE:
         * =========================================================================
         * Suppose current count of a subarray is c.
         * When we add 'val' to all elements in this range, new count becomes (c + val).
         * 
         * New squared sum = sum of (c + val)^2
         *                 = sum of (c^2 + 2 * val * c + val^2)
         *                 = sum(c^2) + 2 * val * sum(c) + len * val^2 [00:58:54]
         * 
         * Notice:
         * - sum(c^2) is already stored in sqTree[i] (the old squared sum)
         * - sum(c) is already stored in sumTree[i] (the linear sum)
         * - len * val^2 is just the constant shift added across all 'len' elements!
         * =========================================================================
         */
        
        // 1. update sqTree using the expansion formula:
        sqTree[i] = (sqTree[i] + (2 * val % mod) * sumTree[i] % mod + (len * ((val * val) % mod)) % mod) % mod;
        
        // 2. update sumTree: each element in range increases by 'val', so total sum increases by len * val [01:00:09]
        sumTree[i] = (sumTree[i] + (len * val) % mod) % mod;
    }
    
    // standard segment tree range update with lazy propagation [01:03:13]
    private void updateRange(int start, int end, int i, int l, int r, int val) {
        // Step 1: if there are pending lazy updates for this node, apply them first!
        if (lazyTree[i] != 0) {
            lazyAddition(i, l, r, lazyTree[i]);
            
            // if this is NOT a leaf node, pass on the lazy value to its children
            if (l != r) {
                lazyTree[2 * i + 1] = (lazyTree[2 * i + 1] + lazyTree[i]) % mod;
                lazyTree[2 * i + 2] = (lazyTree[2 * i + 2] + lazyTree[i]) % mod;
            }
            
            // clear lazy value for current node since it is now applied!
            lazyTree[i] = 0;
        }
        
        // Step 2: out of bounds check
        if (l > end || r < start || l > r) {
            return;
        }
        
        // Step 3: if current node's range [l, r] is COMPLETELY INSIDE our query range [start, end]
        if (l >= start && r <= end) {
            lazyAddition(i, l, r, val);
            
            // if not a leaf node, inform children lazily so we don't traverse deeper right now! [01:05:49]
            if (l != r) {
                lazyTree[2 * i + 1] = (lazyTree[2 * i + 1] + val) % mod;
                lazyTree[2 * i + 2] = (lazyTree[2 * i + 2] + val) % mod;
            }
            return;
        }
        
        // Step 4: partial overlap -> recurse on left and right children
        int mid = l + (r - l) / 2;
        updateRange(start, end, 2 * i + 1, l, mid, val);
        updateRange(start, end, 2 * i + 2, mid + 1, r, val);
        
        // combine children values back to current node
        sumTree[i] = (sumTree[2 * i + 1] + sumTree[2 * i + 2]) % mod;
        sqTree[i] = (sqTree[2 * i + 1] + sqTree[2 * i + 2]) % mod;
    }
}