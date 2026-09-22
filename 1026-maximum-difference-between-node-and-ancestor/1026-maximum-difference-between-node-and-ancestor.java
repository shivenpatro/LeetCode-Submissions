/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode() {}
 *     TreeNode(int val) { this.val = val; }
 *     TreeNode(int val, TreeNode left, TreeNode right) {
 *         this.val = val;
 *         this.left = left;
 *         this.right = right;
 *     }
 * }
 */
 //what we  are technically doing in this question is that, while traversing down any path, we are storing the max and the min we are meeting in the whole path... like we keep finding  the min and max and store untill we reach the leaf node, this way we make sure to have the biggest and smallest values along the whole path while reching the leaf node, once  we reach the leaf node, we will do  max - min
class Solution {
    public int maxAncestorDiff(TreeNode root) {
        return  answer(root, root.val, root.val);
    }
    public int answer(TreeNode node, int minval, int maxval){
        if(node == null) return maxval - minval;//so see this condition came that means we reached one past the leaf node, that means we have the max and min, both we have down the path when we reached here, so here we simply so max - min, and thats how we make sure that, down the path we have the best possible max difference, so simply, keep calling the lower nodes and subtrees, and since we keep recordnig max and min.. and alwauys max diff is max-min, on reaching bottom, return that diff to above nodes.., we find the maax and min before the recursion call(left or right) for every recursion call(every node in the sense)
        minval = Math.min(minval,node.val);//keeping record of min found amongst all the calls , obv for that path only cuz we have to find for node and thaat ancestor
        maxval = Math.max(maxval, node.val);
        int leftsubtree = answer(node.left,minval,maxval);//calling the left subtree with the node min and max, left subtree for the above node which called it
        int rightsubtree = answer(node.right,minval,maxval);//now calling the right with node min and max, same right subtree for the  above node which called it
        return Math.max(leftsubtree,rightsubtree);//now we have to return the max, because because...i mean the answer has come... for that whole path, we have got the max possible difference, down that subtree.. so keep returning it up.. so that the upper root or parent will have idea about the max difference it got from the left or right or whtever path, and finally to the top  root na.. this max woudlhv reached either from left or right side... covering all the max differences from all the paths
    }
}
/*
================================================================================
DRY RUN / EXECUTION TRACE FOR EXAMPLE 1:
Tree: [8, 3, 10, 1, 6, null, 14, null, null, 4, 7, 13]
================================================================================

1. Entry:
   - maxAncestorDiff(node: 8)
   - calls answer(node: 8, minval: 8, maxval: 8)

2. answer(node: 8, minval: 8, maxval: 8)
   - node != null
   - minval = min(8, 8) = 8
   - maxval = max(8, 8) = 8
   - leftsubtree = answer(node: 3, minval: 8, maxval: 8)  --> [GO TO STEP 3]
   - rightsubtree = answer(node: 10, minval: 8, maxval: 8) --> [GO TO STEP 19]

--------------------------------------------------------------------------------
LEFT SUBTREE OF 8 (Exploring Node 3)
--------------------------------------------------------------------------------
3. answer(node: 3, minval: 8, maxval: 8)
   - node != null
   - minval = min(8, 3) = 3
   - maxval = max(8, 3) = 8
   - leftsubtree = answer(node: 1, minval: 3, maxval: 8)  --> [GO TO STEP 4]
   - rightsubtree = answer(node: 6, minval: 3, maxval: 8) --> [GO TO STEP 9]

4. answer(node: 1, minval: 3, maxval: 8)
   - node != null
   - minval = min(3, 1) = 1
   - maxval = max(8, 1) = 8
   - leftsubtree = answer(null, minval: 1, maxval: 8)
       -> node == null -> returns maxval - minval = 8 - 1 = 7
   - rightsubtree = answer(null, minval: 1, maxval: 8)
       -> node == null -> returns maxval - minval = 8 - 1 = 7
   - returns Math.max(7, 7) = 7 back to Node 3

5. Left call of Node 3 completed: leftsubtree = 7
   Now Node 3 calls right child: answer(node: 6, minval: 3, maxval: 8)

6. answer(node: 6, minval: 3, maxval: 8)
   - node != null
   - minval = min(3, 6) = 3
   - maxval = max(8, 6) = 8
   - leftsubtree = answer(node: 4, minval: 3, maxval: 8)  --> [GO TO STEP 7]
   - rightsubtree = answer(node: 7, minval: 3, maxval: 8) --> [GO TO STEP 8]

7. answer(node: 4, minval: 3, maxval: 8)
   - node != null
   - minval = min(3, 4) = 3
   - maxval = max(8, 4) = 8
   - leftsubtree = answer(null, minval: 3, maxval: 8)  -> returns 8 - 3 = 5
   - rightsubtree = answer(null, minval: 3, maxval: 8) -> returns 8 - 3 = 5
   - returns Math.max(5, 5) = 5 back to Node 6

8. answer(node: 7, minval: 3, maxval: 8)
   - node != null
   - minval = min(3, 7) = 3
   - maxval = max(8, 7) = 8
   - leftsubtree = answer(null, minval: 3, maxval: 8)  -> returns 8 - 3 = 5
   - rightsubtree = answer(null, minval: 3, maxval: 8) -> returns 8 - 3 = 5
   - returns Math.max(5, 5) = 5 back to Node 6

9. Node 6 resolves:
   - leftsubtree = 5, rightsubtree = 5
   - returns Math.max(5, 5) = 5 back to Node 3

10. Node 3 resolves:
    - leftsubtree = 7 (from Node 1)
    - rightsubtree = 5 (from Node 6)
    - returns Math.max(7, 5) = 7 back to Node 8 (as leftsubtree)

--------------------------------------------------------------------------------
RIGHT SUBTREE OF 8 (Exploring Node 10)
--------------------------------------------------------------------------------
11. answer(node: 10, minval: 8, maxval: 8)
    - node != null
    - minval = min(8, 10) = 8
    - maxval = max(8, 10) = 10
    - leftsubtree = answer(null, minval: 8, maxval: 10)
        -> node == null -> returns maxval - minval = 10 - 8 = 2
    - rightsubtree = answer(node: 14, minval: 8, maxval: 10) --> [GO TO STEP 12]

12. answer(node: 14, minval: 8, maxval: 10)
    - node != null
    - minval = min(8, 14) = 8
    - maxval = max(10, 14) = 14
    - leftsubtree = answer(node: 13, minval: 8, maxval: 14) --> [GO TO STEP 13]
    - rightsubtree = answer(null, minval: 8, maxval: 14)
        -> node == null -> returns maxval - minval = 14 - 8 = 6

13. answer(node: 13, minval: 8, maxval: 14)
    - node != null
    - minval = min(8, 13) = 8
    - maxval = max(14, 13) = 14
    - leftsubtree = answer(null, minval: 8, maxval: 14)  -> returns 14 - 8 = 6
    - rightsubtree = answer(null, minval: 8, maxval: 14) -> returns 14 - 8 = 6
    - returns Math.max(6, 6) = 6 back to Node 14

14. Node 14 resolves:
    - leftsubtree = 6 (from Node 13)
    - rightsubtree = 6 (from null right child)
    - returns Math.max(6, 6) = 6 back to Node 10

15. Node 10 resolves:
    - leftsubtree = 2 (from null left child)
    - rightsubtree = 6 (from Node 14)
    - returns Math.max(2, 6) = 6 back to Node 8 (as rightsubtree)

--------------------------------------------------------------------------------
FINAL RESOLUTION AT ROOT (Node 8)
--------------------------------------------------------------------------------
16. Node 8 resolves:
    - leftsubtree = 7
    - rightsubtree = 6
    - returns Math.max(7, 6) = 7

Final Output = 7
================================================================================
*/