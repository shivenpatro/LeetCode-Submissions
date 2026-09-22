/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */
class Solution {
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        // we can directly return the result of our recursive function, no need of extra answer variable bro
        return lca(root, p, q);
    }

    public TreeNode lca(TreeNode node, TreeNode p, TreeNode q) {
        // base case: if node is null we return null, or if we found either p or q, we return that node directly to parent!
        if (node == null || node == p || node == q) {
            return node;
        }

        // trust recursion to find p or q in left and right subtrees
        TreeNode left = lca(node.left, p, q);
        TreeNode right = lca(node.right, p, q);

        // case 1: both left and right returned non-null! that means one guy (p) is in left subtree and other guy (q) is in right subtree
        // so current node is the lowest point where both paths meet... so this current node is our LCA!!
        if (left != null && right != null) {
            return node;
        }

        // case 2: if only one side found something (either left is not null or right is not null),
        // we just pass that found node upwards to the parent... and if both are null, it will return null anyway!
        return left != null ? left : right;
    }
}