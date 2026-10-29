class Solution {
    /* why LOG = 17? because N is up to 5 * 10^4.. 2^15 is 32768 which is slightly smaller than 50000, but 2^16 is 65536 which strictly covers everything up to 50000 nodes! so taking 17 levels (powers 0 through 16) is more than enough for binary lifting to jump to any ancestor in O(log N) */
    int LOG = 17;
    int[][] up;
    int[] depth;
    int[] tin;
    int[] tout;
    int timer;
    int[] eulerTour;
    int[] segTree;
    char[] sChar;
    List<List<Integer>> adj;

    public List<Boolean> palindromePath(int n, int[][] edges, String s, String[] queries) {
        /* ok so lets do a complete brain dump of teh whole system first so future me doesnt get lost.. what does palindrome rearrangement actually mean? suppose path has characters ['a', 'b', 'c', 'a', 'b'].. can we form a palindrome? yes! 'a' appears 2 times, 'b' appears 2 times, 'c' appears 1 time.. we put 'c' in the center and mirror 'a' nad 'b' on both sides -> "abcba"! what if path has ['a', 'b', 'c']? all counts are 1 (three odd counts), no matter how you shuffle it you can NEVER make it a palindrome! so the mathematical rule is: a string can be rearranged into a palindrome if and only if AT MOST ONE charcter has an odd frequency! exact count doesnt matter at all, only parity (odd or even) matters! */

        /* and this parity property connects directly to XOR! think about it.. in XOR, x ^ x = 0 (even cancels out) nad x ^ 0 = x (odd survives)! so if we map character 'a' -> bit 0 (1 << 0), 'b' -> bit 1 (1 << 1), 'c' -> bit 2 (1 << 2)... up to 'z' -> bit 25 (1 << 25).. whenever a charcter appears an even number of times on the path, its bit flips back to 0! if it appears an odd number of times, its bit stays 1! at the end of the path, if the final XOR mask has either zero set bits (all characters even) OR exactly one set bit (one character odd), it can be a palindrome! and how to check if a number has at most 1 set bit in O(1)? classic bit trick: (mask & (mask - 1)) == 0! eg if mask = 4 (binary 100), mask - 1 = 3 (binary 011), 100 & 011 = 0! but if mask = 6 (binary 110), mask - 1 = 5 (binary 101), 110 & 101 = 100 != 0! */

        /* now the tree part.. why Euler Tour flatening? imagine a tree 0 -> 1 -> 2. when we do dfs, we record timer when entering node u (tin[u]) and timer when leaving node u (tout[u]). we store the node's bitmask in an array eulerTour of size 2*n at BOTH tin[u] nad tout[u]! why at both? because when we query prefix XOR from 0 to tin[u] in eulerTour, consider any node x that is NOT an ancestor of u: that node x was entered and completely exited before we reached u, so BOTH tin[x] and tout[x] lie in the range [0, tin[u]]! since eulerTour[tin[x]] == eulerTour[tout[x]], they XOR with each other and completely CANCEL to 0! but what about the ancestors of u? an ancestor was entered before u, but has NOT exited yet! so only its tin appears in [0, tin[u]], and its tout is way ahead after tin[u]! so every ancestor of u appears EXACTLY ONCE! that means query(0, tin[u]) magically gives the exact XOR sum of all nodes on the path from root to u! */

        /* and path(u, v) using LCA: path from u to v is rootTo(u) ^ rootTo(v) ^ lcaMask! why? because rootTo(u) has all ancestors of LCA, plus LCA itself, plus nodes down to u. rootTo(v) has all ancestors of LCA, plus LCA itself, plus nodes down to v. when you XOR them, all nodes above LCA cancel out (good, they arent on path u-v). BUT teh LCA node itself was in both, so it ALSO cancelled out (x ^ x = 0)! but LCA is litrally the peak of the path between u and v, so we must XOR lcaMask back in so it remains included! and whenever a charcter at node u updates, u only appears at two indices in eulerTour: tin[u] nad tout[u]! so we just update those two points in a Segment Tree in O(log N) time! */

        sChar = s.toCharArray();
        adj = new ArrayList<>();
        for (int i = 0; i < n; i++) adj.add(new ArrayList<>());
        
        /* standard adjacency list construction for undirected graph.. since its a tree with n-1 edges, each edge goes both ways */
        for (int[] edge : edges) {
            adj.get(edge[0]).add(edge[1]);
            adj.get(edge[1]).add(edge[0]);
        }

        up = new int[n][LOG];
        depth = new int[n];
        tin = new int[n];
        tout = new int[n];
        timer = 0;
        eulerTour = new int[2 * n];

        /* start dfs from root node 0 with parent 0 and depth 0 to populate tin, tout, eulerTour, nad direct parent up[u][0] */
        dfs(0, 0, 0);

        /* binary lifting precomputation: up[i][j] stores the (2^j)-th ancestor of node i.. recurrence is up[i][j] = up[up[i][j - 1]][j - 1] meaning to jump 2^j steps, first jump 2^(j-1) steps to reach halfway ancestor, then from that node jump another 2^(j-1) steps! since 2^(j-1) + 2^(j-1) = 2^j, this fills teh entire ancestor table in O(N log N) */
        for (int j = 1; j < LOG; j++) {
            for (int i = 0; i < n; i++) {
                up[i][j] = up[up[i][j - 1]][j - 1];
            }
        }

        /* segment tree size: eulerTour has size 2*N, and standard segment tree array requires 4 * (size of array), so 4 * (2*N) = 8*N space to prevent any index out of bound during recursive splits */
        segTree = new int[8 * n];
        buildSeg(0, 0, 2 * n - 1);

        List<Boolean> ansList = new ArrayList<>();
        
        /* process queries one by one.. each query is either an update or a path query */
        for (String q : queries) {
            String[] parts = q.split(" ");
            
            if (parts[0].equals("update")) {
                /* update query format: "update u c" -> change charcter at node u to c */
                int u = Integer.parseInt(parts[1]);
                char c = parts[2].charAt(0);
                sChar[u] = c; // keep raw character array updated so future LCA charcter lookups fetch the new updated charcter
                
                int newMask = 1 << (c - 'a'); // convert new char to bitmask where only (c - 'a')th bit is 1
                
                /* node u was recorded twice in eulerTour: when we entered (tin[u]) and when we left (tout[u]).. so update both points in segment tree */
                updateSeg(0, 0, 2 * n - 1, tin[u], newMask);
                updateSeg(0, 0, 2 * n - 1, tout[u], newMask);
            } else {
                /* path query format: "query u v" -> can the string on path between u and v be rearranged into a palindrome? */
                int u = Integer.parseInt(parts[1]);
                int v = Integer.parseInt(parts[2]);
                
                /* step 1: find lowest common ancestor of u and v using binary lifting in O(log N) */
                int lca = getLCA(u, v);
                
                /* step 2: prefix XOR from 0 to tin[u] gives root-to-u path XOR because all non-ancestor subtrees canceld out */
                int xorU = querySeg(0, 0, 2 * n - 1, 0, tin[u]);
                
                /* step 3: prefix XOR from 0 to tin[v] gives root-to-v path XOR */
                int xorV = querySeg(0, 0, 2 * n - 1, 0, tin[v]);
                
                /* step 4: LCA node was present in both prefix XORs, so xorU ^ xorV cancelled LCA out completely.. fetch LCAs current bitmask and XOR it back in so LCA stays on the path */
                int lcaMask = 1 << (sChar[lca] - 'a');
                int pathXor = xorU ^ xorV ^ lcaMask;
                
                /* step 5: check if at most one bit is set in pathXor.. if (pathXor & (pathXor - 1)) == 0 it means either 0 bits (all characters apear even times) or 1 bit (single odd charcter that can sit in the middle of palindrome) is set.. so its valid! */
                ansList.add((pathXor & (pathXor - 1)) == 0);
            }
        }

        /* convert dynamic list of booleans to primitive boolean array to match leetcode signature */
        boolean[] result = new boolean[ansList.size()];
        for (int i = 0; i < ansList.size(); i++) {
            result[i] = ansList.get(i);
        }
        return ansList;
    }

    private void dfs(int u, int p, int d) {
        depth[u] = d; // store depth from root (root depth is 0)
        up[u][0] = p; // direct parent (2^0 = 1 step jump) is p
        
        /* entering node u: record tin[u] as current timer nad write its character bitmask into eulerTour array */
        tin[u] = timer;
        eulerTour[timer] = 1 << (sChar[u] - 'a');
        timer++;

        /* traverse all neighbors.. if neighbor is parent, skip it to prevent infinite ping-pong in undirectd tree */
        for (int v : adj.get(u)) {
            if (v != p) {
                dfs(v, u, d + 1);
            }
        }

        /* exiting node u: all descendants of u have been fully explord and recorded.. now record tout[u] as exit timestamp and write the SAME bitmask into eulerTour again so that any query outside this subtree will see both tin and tout and cancel them out! */
        tout[u] = timer;
        eulerTour[timer] = 1 << (sChar[u] - 'a');
        timer++;
    }

    private int getLCA(int u, int v) {
        /* make sure u is always the deeper node so we only have to lift u upwards to match v's depth */
        if (depth[u] < depth[v]) {
            int temp = u;
            u = v;
            v = temp;
        }

        /* lift u up by the difference in depths using binary lifting powers of 2 so both u nad v end up at the exact same depth level */
        int diff = depth[u] - depth[v];
        for (int i = 0; i < LOG; i++) {
            if (((diff >> i) & 1) == 1) {
                u = up[u][i];
            }
        }

        /* if v was already an ancestor of u, lifting u to v's depth means u is now equal to v, so v is teh LCA! */
        if (u == v) return u;

        /* jump both u and v upwards together from largest jump power (LOG-1) down to 0, jumping ONLY when their ancestors are diferent.. this stops right beneath their common ancestor! */
        for (int i = LOG - 1; i >= 0; i--) {
            if (up[u][i] != up[v][i]) {
                u = up[u][i];
                v = up[v][i];
            }
        }

        /* since we stopped just below LCA, LCA is the direct parent of u (or v) */
        return up[u][0];
    }

    private void buildSeg(int node, int l, int r) {
        /* leaf node represents a single index in eulerTour array */
        if (l == r) {
            segTree[node] = eulerTour[l];
            return;
        }
        int mid = l + (r - l) / 2;
        /* recursively build left child (2*node + 1) nad right child (2*node + 2) */
        buildSeg(2 * node + 1, l, mid);
        buildSeg(2 * node + 2, mid + 1, r);
        /* segment tree node stores XOR of its left and right segments */
        segTree[node] = segTree[2 * node + 1] ^ segTree[2 * node + 2];
    }

    private void updateSeg(int node, int l, int r, int idx, int val) {
        /* reached the target leaf index in eulerTour: overwrite with new bitmask value */
        if (l == r) {
            segTree[node] = val;
            return;
        }
        int mid = l + (r - l) / 2;
        /* navigate to left or right subtree depending on where target idx falls */
        if (idx <= mid) {
            updateSeg(2 * node + 1, l, mid, idx, val);
        } else {
            updateSeg(2 * node + 2, mid + 1, r, idx, val);
        }
        /* recalculate parent XOR after child leaf has been updatd */
        segTree[node] = segTree[2 * node + 1] ^ segTree[2 * node + 2];
    }

    private int querySeg(int node, int l, int r, int ql, int qr) {
        /* segment [l, r] is completely outside query range [ql, qr]: return 0 because in XOR, x ^ 0 = x (neutral element) */
        if (ql > r || qr < l) return 0;
        /* segment [l, r] is completely inside query range: return precomputed XOR sum of this segment */
        if (ql <= l && r <= qr) return segTree[node];
        /* segment partially overlaps: recurse into both halves and XOR results together */
        int mid = l + (r - l) / 2;
        return querySeg(2 * node + 1, l, mid, ql, qr) ^ querySeg(2 * node + 2, mid + 1, r, ql, qr);
    }
}