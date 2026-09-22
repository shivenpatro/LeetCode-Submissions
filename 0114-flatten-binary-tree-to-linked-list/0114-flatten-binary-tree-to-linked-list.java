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
 /*
//optimal morris traversal approach spcae O(1) time O(n)
class Solution{
    public void flatten(TreeNode root){
        TreeNode curr = root;//keeping record  of current node, which will slowly point to its right later
        while(curr!=null){//as  long as this is not  null, lets run the loop
            if(curr.left!=null){//the main aim is...if there is no left its fine, if left is there, we will go to the rightmost of that left, and make that point to the right of  the current curr, this is hard to visualize so watch striver if needed for morris traversal once, but still tryu to imagine
                TreeNode prev = curr.left;//now see see... we have kept our curr safe at root, now from the left of curr  we have to reach the rightmost of the left subtree of root na? and iterative to manage recursive stack space nad hence the morris appraoch, so we just go right right right untill we reach null simple
                while(prev.right != null)  prev = prev.right;//until prev becomes null, go right right right of the prev, and once we reach the rightmost of the left... 
                prev.right = curr.right;//point the right of this prev(which will be 4 in ex1... to 5)...to curr.right.. just trust the process
                curr.right = curr.left;//now we makethe curr.right to  point to curr.left...curr.left and curr.right both point to curr.left now
                curr.left = null;//so make curr.left null
            }
            curr = curr.right;//now this is the most imp shit... whtever we did above, we have to keep doing it... we wil have to move the curr from root to curr.right(which will be 2 now since we did curr.right = curr.left as well!!), it might be confusing, but before explaining, lemme explain in siomple manner....curr is 1... prev comes to 4(which is right right right of the left subtree for 1).. 4.right points to 5 and 1.right points to 2...1.left is null, now curr becomes 2(curr = curr.right(1.right is 2)), now again repeat for node 2...curr is 2...prev is curr.left which is 3.. 3 has no child so prev is 3... 3.right points to curr.right!! 2.right is 4!! so 3 points to 4!...curr.right = curr.left...2.right = 2.left(which is 3..) so 2 points to 3.. current flow...1->2->3->4->5->6... now curr = curr.right.. curr becomes 3 ..3.left == null..wont run..curr becomes 3.right which is 4.. so curr becomes 4.. curr becomes 5..(because none have left child only) and curr becomes 6..then curr becomes null..answer is done
        }
    }
}*/


// what we are technically doing in this question is that, we have to flatten the tree into a linked list in pre-order order right... meaning root -> left -> right...
// but the trick is, if we do normal pre-order from the front, as soon as we change root.right to point to left, we will lose the original right subtree completely... , like see see,... there was a small tree 1 as root, 1.left as 4 and 1.right as 5.. we want 1->4->5... so now if we right 1.right directlly as 1.left..1->4 is formed but that link  of 1->5 is gone forever!! thats the issue we have to fix
// so the intuition here is: why not traverse in reverse pre-order?! meaning right -> left -> root... kind of reverse post-order!
// think about it... like simply..we  will reach to the right most and then we will ahve to attach the right of that node to the latest processed node which we have stored in prev na.. obv first first(like 6 in ex1, will get attached to prev(which is null at beginning), and left also to null.. thne prev becomes 6.. and 5 attaches to it(MAIN CATCH: ALREADY ATTACHED AS IN QUESTION AND THATS THE CATCH!!), then this 5 is prev.. and left of 1 will get executed and finally reaches to 4..1..1.left is called.. and then 2.right is called(following the plan of right->left->root)..then 2.right is 4 and 4 gets connected to 5! prev becomes 4.. and 2.left whihc is  3 gets connectedd to 4..with 3.left null(all lefts are being made null)), and 3 is prev.. then root comes which is 2, 2.right points  to 3...and 2.left is null.. and finally coming to main node(after following the right->left->null),1.right points to prev(which is 2) and answer is done
// we maintain a global prev pointer which starts at null... whenever a node finishes its right and left calls, that means this node should come immediately before 'prev' in our final list...
// so we simply point this node's right to prev, make its left null, and then update prev to be this current node! so it builds the chain backwards from tail to head smoothly!!
class Solution {
    // global pointer to remember the node we processed just before this one in reverse traversal... basically the head of the already flattened tail part!
    TreeNode prev = null;
    public void flatten(TreeNode root) {
        if (root == null) return; // base condition... if we hit null, just return back, nothing to flatten here
        // first go deep into the right subtree... because in reverse pre-order, right comes first! we want to process the tail-end nodes of the pre-order first
        flatten(root.right);
        // after right is completely processed and linked backwards, now we go into the left subtree...
        flatten(root.left);
        // now both right and left subtrees of this 'root' are already flattened and waiting in 'prev'!
        // so for this current node, its next node in pre-order should be whatever 'prev' is currently pointing to!
        root.right = prev; // attach current node's right to the already formed chain in prev
        root.left = null;  // the question strictly demands a right-skewed linked list, so left must be nullified, no branches allowed!
        // now this current root itself becomes the new head of our flattened linked list so far...
        // so update prev to this root, so the parent node above us can attach its right to us!
        prev = root;
    }
}

/*
================================================================================
DRY RUN / EXECUTION TRACE (Reverse Pre-Order: Right -> Left -> Root)
Tree:
        1
       / \
      2   5
     / \   \
    3   4   6

Desired Pre-Order: 1 -> 2 -> 3 -> 4 -> 5 -> 6
Reverse Pre-Order order of execution: 6 -> 5 -> 4 -> 3 -> 2 -> 1
================================================================================

Initial state: prev = null

1. flatten(1):
   - calls flatten(1.right) which is flatten(5)

2. flatten(5):
   - calls flatten(5.right) which is flatten(6)

3. flatten(6):
   - calls flatten(6.right) -> null, returns
   - calls flatten(6.left)  -> null, returns
   - 6.right = prev (which is null)
   - 6.left = null
   - prev = 6
   - returns back to flatten(5)

4. Back at flatten(5):
   - right is done! Now calls flatten(5.left) -> null, returns
   - 5.right = prev (which is node 6)  ==> chain formed: 5 -> 6 -> null
   - 5.left = null
   - prev = 5
   - returns back to flatten(1)

5. Back at flatten(1):
   - right of 1 is completely done!
   - Now calls flatten(1.left) which is flatten(2)

6. flatten(2):
   - calls flatten(2.right) which is flatten(4)

7. flatten(4):
   - calls flatten(4.right) -> null, returns
   - calls flatten(4.left)  -> null, returns
   - 4.right = prev (which is node 5)  ==> chain formed: 4 -> 5 -> 6 -> null
   - 4.left = null
   - prev = 4
   - returns back to flatten(2)

8. Back at flatten(2):
   - right of 2 is done! Now calls flatten(2.left) which is flatten(3)

9. flatten(3):
   - calls flatten(3.right) -> null, returns
   - calls flatten(3.left)  -> null, returns
   - 3.right = prev (which is node 4)  ==> chain formed: 3 -> 4 -> 5 -> 6 -> null
   - 3.left = null
   - prev = 3
   - returns back to flatten(2)

10. Back at flatten(2):
    - both right and left calls of 2 are done!
    - 2.right = prev (which is node 3)  ==> chain formed: 2 -> 3 -> 4 -> 5 -> 6 -> null
    - 2.left = null
    - prev = 2
    - returns back to flatten(1)

11. Back at root flatten(1):
    - both right and left calls of root 1 are done!
    - 1.right = prev (which is node 2)  ==> chain formed: 1 -> 2 -> 3 -> 4 -> 5 -> 6 -> null
    - 1.left = null
    - prev = 1
    - DONE! Entire tree flattened in-place into a single right-skewed list!
================================================================================
*/