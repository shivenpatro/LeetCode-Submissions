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
class Solution {
    public boolean isValidBST(TreeNode root) {
        return check(root, Long.MIN_VALUE, Long.MAX_VALUE);
    }
    public boolean check(TreeNode node, long minofrange, long maxofrange){
        if(node == null)return true;//so we reached the end, we just return true, becasue its not violating anything as such
        if(node.val <= minofrange || node.val >=maxofrange) return false;//now this is a violation, it shouldnt break the range, how is the range defined?? explained below
        boolean leftsubtreecheck = check(node.left, minofrange, node.val);//now the most imp shit, what tf is this? lets understand now, take ex 2 for sense, 5 comes and no if conditions are invalid, all are valid, so now first we will call for left subtree and then for right subtree as well normally do right? but but but, see, if we go left, our  all the values to the left of this root must be smaller than this root, thats all we fking want for left for which ever recursion call is this(recursion call as in whichever node this current recursive function stands at in the memory), so we define the max limit as root.val and min stays the same, BECAUSE WE ONLY CARE THAT THE LEFT SUBTREE ALL NODES MUST BE SMALLER THAN THE CURRENT NODE THATS THE MIAN THIGN AND  ONLY THING FOR  LEFT
        boolean rightsubtreecheck = check(node.right, node.val, maxofrange);//NOW FOR RIGHT, EVERY VALUE TO THE RIGHT MUST  BE LARGER THAN THE CURRENT ROOT!!.. so os,... we will define the smaller ... like minofrange na?  because we want everything to be bigger than this root.val... and we dc about the max... whtever the max that has been passed from above will go on... lets do a dry run
        //5 comes... 1 is called with 1,-infinite,5...passed...after that nothing so 5 receives true from down... 5 calls on right... 4 goes with 4,5,infinite... min of  the range is 5 and range is 5 to infinite where 4 doesnt lie!!!.. so  false is returned directly to 5.. which does true(from left) && false(from right) and false is returned
        return leftsubtreecheck && rightsubtreecheck; //just the final check so that we make sure all the trues are coming, even a single false will return false to the calling function 
    }
}*/
//inorder traversal property method to check valid BST!!
class Solution {//so so see, another super clean way to validate BST is using INORDER TRAVERSAL!! why?? because we know for a fact that inorder of any binary search tree is ALWAYS strictly increasing sorted order, like 1, 2, 3, 4, 5... so instead of making a whole arraylist and then checking if its sorted (which takes extra O(n) memory and doesn't even exit early), we just keep a single global pointer 'prev' which remembers the last node we visited in the inorder sequence!! and compare it on the fly...
    
    //this prev node will store the previously visited node in our inorder sequence (Left -> Root -> Right)... initially null because for the very first smallest element at the extreme bottom left, there is no previous element to compare with!
    private TreeNode prev = null;
    public boolean isValidBST(TreeNode root) {
        //base case: if current root is null, it means we reached empty leaf or null branch, which obviously violates nothing... so return true!
        if (root == null) return true;

        //1. FIRST GO LEFT (Inorder: Left -> Root -> Right):
        //we call isValidBST on left child first because we need to visit the smallest elements first... and if ANY node inside left subtree fails the BST condition, it returns false, so (!isValidBST(root.left)) triggers and we immediately return false without even checking right side!!
        if (!isValidBST(root.left)) return false;

        //2. PROCESS ROOT (The actual sorted check):
        //now we are at the current node... if prev is not null (meaning this is NOT the very first node of inorder), then current node.val MUST be strictly greater than prev.val!!
        //if root.val <= prev.val (meaning it is smaller OR even equal, because BST doesn't allow duplicates), then the ascending order is broken bro!! so immediately return false!
        //and see, the beauty of this is we don't even need Long.MIN_VALUE or Long.MAX_VALUE here, because we compare directly with actual node values, so no Integer.MIN_VALUE edge case issues at all!!
        if (prev != null && root.val <= prev.val) {
            return false;
        }
        //now current node becomes the previous node for the next guy in inorder traversal, so update prev to current root!
        prev = root;
        //3. NOW GO RIGHT:
        //after processing root, we now check the right subtree... whatever right subtree returns (true if all right nodes are valid, or false if any right node fails) will be our final result for this subtree!
        return isValidBST(root.right);
    }
}

/*
========================================================================================
LINE-BY-LINE RECURSIVE TRACE FOR RANGE-BASED (PREORDER) METHOD
Tree: root = [5, 1, 4, null, null, 3, 6]
========================================================================================

Function signature:
  isValidBST(root, minVal, maxVal)

Rules for any node:
  1. Base case: if (root == null) return true;
  2. Range violation: if (root.val <= minVal || root.val >= maxVal) return false;
  3. When going LEFT:  upper bound becomes current node.val -> (minVal, root.val)
  4. When going RIGHT: lower bound becomes current node.val -> (root.val, maxVal)

----------------------------------------------------------------------------------------
STEP 1: Call on Root Node (5)
----------------------------------------------------------------------------------------
Call 1: isValidBST(node 5, minVal = -INF, maxVal = +INF)
  - Check 1: node == null? No, node is 5.
  - Check 2: is 5 <= -INF or 5 >= +INF? No, 5 is well within range (-INF, +INF). Valid!
  - Now, we do the PREORDER check:
    * First, recurse on LEFT subtree:
      minVal stays -INF, maxVal becomes 5 (since everything to the left of 5 MUST be < 5).
      -> calls isValidBST(node 1, minVal = -INF, maxVal = 5)

----------------------------------------------------------------------------------------
STEP 2: Explore Left Child (1)
----------------------------------------------------------------------------------------
Call 2: isValidBST(node 1, minVal = -INF, maxVal = 5)
  - Check 1: node == null? No, node is 1.
  - Check 2: is 1 <= -INF or 1 >= 5? No, 1 is within range (-INF, 5). Valid!
  - Now, explore its children:
    * Left child of 1:
      calls isValidBST(null, -INF, 1) -> hits null -> returns true
    * Right child of 1:
      calls isValidBST(null, 1, 5) -> hits null -> returns true
  - Both children returned true: true && true = true.
  - Call 2 finishes and returns TRUE back to Call 1 (node 5).

----------------------------------------------------------------------------------------
STEP 3: Explore Right Child (4) from Root (5)
----------------------------------------------------------------------------------------
Back to Call 1 (node 5):
  - Left subtree check succeeded (returned true).
  - Because of '&&', it now moves on to check the RIGHT subtree:
    lower bound becomes 5 (everything in right subtree MUST be > 5), maxVal stays +INF.
    -> calls isValidBST(node 4, minVal = 5, maxVal = +INF)

Call 3: isValidBST(node 4, minVal = 5, maxVal = +INF)
  - Check 1: node == null? No, node is 4.
  - Check 2: Range check:
      Condition: if (root.val <= minVal || root.val >= maxVal)
      Here:
        root.val = 4
        minVal   = 5
        maxVal   = +INF
      Does 4 <= 5 evaluate to true?
      YES! 4 <= 5 is TRUE!! (VIOLATION DETECTED!)

  - Why did this fail?
    Because node 4 is in the RIGHT subtree of 5, so every single node here was required 
    to be strictly greater than 5 (> 5), but we got 4!
  - It does NOT even bother checking 4's children (3 and 6).
  - Call 3 IMMEDIATELY RETURNS FALSE!

----------------------------------------------------------------------------------------
STEP 4: Back to Root & Final Result
----------------------------------------------------------------------------------------
Back to Call 1 (node 5):
  - Left subtree returned:  true
  - Right subtree returned: false
  - Final evaluation:
      return left && right
      return true && false -> returns FALSE

Final Answer: false (Tree is NOT a valid BST)
========================================================================================
*/