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
}