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
    int n = 0;
    public int maxDepth(TreeNode root) {
        return depth(root);
    }
    public int depth(TreeNode node){
        if(node == null) return 0;//base case
        int lefttreedepth = depth(node.left);
        int righttreedepth = depth(node.right);
        return 1+Math.max(lefttreedepth,righttreedepth);//try to understand thisss and remember, its going like down to up ok? imagine in that way, u go to bottom like at node.left... depth will be called with node 9, and then node.left  and node.right both return 0.. so so.... return  1+max(0,0)... so 1 is returned to the leftreedepth of node 3!!... which is correct... now righttreedepth is called on 3... goes to 20... 20 calls for 15... 15 has nothing.. so 15 returns 1+max(0,0).. to 20...lefttreedepth = 1...now for function call fo 20... for node 7(reighttreedepth), is called and that also returns 1+max(0,0)(since 7 has no children... so 20 gets 1,1... and 20 returns 1+max(1,1) to 3 now!!... so 3 gets 2 from right and 1 from left and finally 1+max(1,2)  is returned... answr 3....   SO WE GO FROM DOWN TO UP!, SO THAT WE CAN KEEP LIKE...we reach the bottom na.. and everytime we return 1+.. because we are going up 1 level..so we kinda keep track of that, and max from both branches becasue like.. obv we are finiding depth so we care about the lowermost point,.. so from down to up.. keep passing the max val..
    }
}