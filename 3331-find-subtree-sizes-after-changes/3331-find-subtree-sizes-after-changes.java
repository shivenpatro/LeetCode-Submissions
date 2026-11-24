class Solution {
    //so basically we have a tree starting from 0 and every node has a small letter on it..
    //for every node x, we look up at its ancestors (parents above it) and find the closest one with the SAME letter.. if found, we cut x from its old parent and attach x directly under that ancestor.. after doing this for all nodes, return the final subtree size of every node!!
    //simple trick is to do one normal DFS from root 0 and keep a 26-size array 'last' that remembers which node above us had each letter most recently..
    //as we go down to kids, we update last[letter] = curr so kids know curr is their closest match.. and when we come back up, we put the old value back (undo it) so other side branches dont get confused!!
    //and why does adding subtree size while coming back up work? becoz by the time DFS finishes all kids under curr, any node below curr that wanted to attach to curr has ALREADY finished and added its size to curr.. so curr has its full final size ready, and now curr just adds its own size to its new parent (either closest matching ancestor or old parent) simple!!
    List<List<Integer>> adj;
    int[] resSize;
    int[] last;
    public int[] findSubtreeSizes(int[] parent, String s) {
        int n = parent.length;
        adj = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            adj.add(new ArrayList<>()); //make empty kids list for every node
        }
        for (int i = 1; i < n; i++) {
            adj.get(parent[i]).add(i); //start from 1 becoz node 0 is root and has parent -1.. parent[i] -> i means i is kid of parent[i]
        }
        resSize = new int[n];
        Arrays.fill(resSize, 1); //every node counts itself as 1 in its own subtree at start
        last = new int[26];
        Arrays.fill(last, -1); //-1 means we havent seen this letter anywhere above us yet
        dfs(0, s, parent); //start dfs from root 0 nd let recursion handle everything
        return resSize;
    }
    private void dfs(int curr, String s, int[] parent) {
        int c = s.charAt(curr) - 'a'; //convert letter to 0..25 index
        int nearest = last[c]; //check where we last saw this same letter above curr (closest matching ancestor)
        last[c] = curr; //before going to kids, update last[c] to curr so kids see curr as their closest match for this letter
        for (int child : adj.get(curr)) {
            dfs(child, s, parent); //go solve all kids first.. trust recursion that their sizes get updated properly
        }
        last[c] = nearest; //coming back up now so undo the change and put old ancestor back.. otherwise sibling branches will wrongly see curr as their ancestor!
        if (curr != 0) { //root 0 has no parent above it so skip root
            int newParent = (nearest != -1) ? nearest : parent[curr]; //if we found a same-letter ancestor above, attach to it.. else stick with old parent
            resSize[newParent] += resSize[curr]; //add curr's full final subtree size into its new parent's size
        }
    }
}