class Solution {
    // modulo constant 10^9 + 7 since result can be massive as mentioned in problem description
    long mod = 1_000_000_007L;
    
    // 2D table to precompute Pascal's Triangle so we can look up nCr combinations in O(1) time!
    // because see... calculating nCr using factorials (n! / (r! * (n-r)!)) will cause crazy overflows,
    // and using division under modulo needs modular inverse which is unnecessarily complex...
    // Pascal's triangle table gives us nCr directly with simple additions under mod!
    long[][] pt;

    public int numOfWays(int[] nums) {
        int n = nums.length;
        
        // Step 1: Precompute Pascal's Triangle table up to n+1 rows and cols
        // pt[i][j] will directly store i C j % mod!
        pt = new long[n + 1][n + 1];
        
        for (int i = 0; i <= n; i++) {
            // in Pascal's triangle, first element (i C 0) and last element (i C i) are always 1
            pt[i][0] = 1;
            pt[i][i] = 1;
            
            // filling intermediate values using recurrence: pt[i][j] = pt[i-1][j] + pt[i-1][j-1]
            for (int j = 1; j < i; j++) {
                pt[i][j] = (pt[i - 1][j] + pt[i - 1][j - 1]) % mod;
            }
        }
        
        // convert our primitive int array into a List so we can easily pass sub-lists to recursion
        List<Integer> list = new ArrayList<>();
        for (int num : nums) {
            list.add(num);
        }
        
        // Step 2: solve recursively to find total ways to construct the same BST
        long totalWays = solve(list);
        
        // now see... why do we do (totalWays - 1 + mod) % mod?
        // because the question asks for "number of DIFFERENT / REORDERED ways"!
        // our solve() function counts ALL valid permutations, including the original given ordering itself!
        // so we HAVE to subtract 1 to exclude that original input permutation simple na!
        return (int) ((totalWays - 1 + mod) % mod);
    }

    private long solve(List<Integer> list) {
        int n = list.size();
        
        // BASE CASE:
        // if list has <= 2 elements (e.g., size 0, 1, or 2)...
        // there is only 1 possible BST structure and only 1 way to form it!
        // so we simply return 1!
        if (n <= 2) {
            return 1;
        }
        
        // see... what is the root of the BST formed by this list?
        // in BST insertion, the VERY FIRST element in the array ALWAYS becomes the root!
        // so list.get(0) is fixed at the root position!
        int root = list.get(0);
        
        // separate all remaining elements into left subtree (elements < root) and right subtree (elements > root)
        List<Integer> left = new ArrayList<>();
        List<Integer> right = new ArrayList<>();
        
        for (int i = 1; i < n; i++) {
            int val = list.get(i);
            if (val < root) {
                left.add(val);
            } else {
                right.add(val);
            }
        }
        
        // now see the core intuition:
        // to get an identical BST:
        // 1. the RELATIVE order of elements in the left subtree must be preserved!
        // 2. the RELATIVE order of elements in the right subtree must be preserved!
        // BUT... elements of the left subtree and right subtree can freely interleave with each other!
        // 
        // Example: root is fixed at index 0. We have (n - 1) total remaining slots.
        // how many slots are needed for left subtree elements? left.size() slots!
        // so out of (n - 1) open positions, we choose left.size() positions to place the left elements!
        // that's literally combinations: (n - 1) C (left.size())!
        // and once those are chosen, the remaining slots automatically hold the right elements in their relative order!
        
        long leftWays = solve(left);   // recursive ways to form identical left subtree
        long rightWays = solve(right); // recursive ways to form identical right subtree
        
        // lookup (n - 1) C (left.size()) from our precomputed Pascal's triangle:
        long z = pt[n - 1][left.size()];
        
        // total combinations for current state = leftWays * rightWays * z (all under modulo mod!)
        long ways = (leftWays * rightWays) % mod;
        ways = (ways * z) % mod;
        
        return ways;
    }
}