/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */

 //understand the question well before reading comments
class Solution {
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if(root == null || root == p || root == q){
            return root;//so see, simple thing, if that node is anything, if its null return it, if its p return it, if its q, return it
        }
        TreeNode left = lowestCommonAncestor(root.left, p,q);//now, this for traversing the whole left subtree for that particular root ok, we keep on going to the left left left for that root(node), untill a base case is hit, and then the left will either get, null, or a node(which will be either p or q)
        TreeNode right = lowestCommonAncestor(root.right, p,q);//same, just keep traversing right right right, and it receirves either null,p or q
        if(left == null && right == null){//now the main conditioning happens here, if this node receives null from both subtrees, that means everything else below that returned null(if its a leaf node also, or any node doesnt matter, ITS RECURSION BOI!), so obv result is not found, return null above
            return null;
        }
        else if(left == null){//now most imp, if only left is null for that root, taht means after calling all right nodes(the whole right subtree that recursive call node), if the right has something not null, that means its either p or q, that means its an answer and we need to return it above, so return right if left is null
            return right;
        }
        else if(right == null) return left;//same logic as above exact, if right is null, then left needs to be moved up
        else return root;//now now the final one, if this comes, that means left and right both are not null, that means we recerived p and q both from left and right, either left has p or q, or right has p or q... any combo.. but but...WE GOT BOTH THE NODES FROM THE LOWER LEFT AND RIGHT TREE OF THAT PARTICULAR NODE, THAT MEANS THIS NODE WILL BE THE "LOWEST COMMON ANCESTOR RIGHT?", because we have already recerived the p and q, from left and right, or right and left whtever, that means, we have to return taht root only, because "LOWEST COMMON" we have to find, so keep returning this, and above also, if the other tree(left or right, depending if this was right or left for the above nodes(obv if there is anything above)), the left==null or right == null will be triggered and will be made sure that this particular root, WHERE BOTH LEFT AND RIGHT WERE NOT NULL.. THIS PARENT ROOT ONLY GETS PASSED, until the final root and is returned...obv this pass doesnt happen when the only common ancestor is 3 lol.. like if p is 5 and q is 0, in ex 1/2
    }
}