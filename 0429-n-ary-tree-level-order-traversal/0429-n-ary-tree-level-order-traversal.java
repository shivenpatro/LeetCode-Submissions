/*
// Definition for a Node.
class Node {
    public int val;
    public List<Node> children;

    public Node() {}

    public Node(int _val) {
        val = _val;
    }

    public Node(int _val, List<Node> _children) {
        val = _val;
        children = _children;
    }
};
*/

// bro so basically what we are doing here is... everyone does level order using queue and BFS iteratively na.. 
// but we can do it recursively using DFS itself!! (DFS)
// just imagine.. our answer is a list of lists: List<List<Integer>>... where index 0 stores all nodes of level 0, index 1 stores level 1, index 2 stores level 2, and so on...
// so whenever we visit ANY node in the tree, if we just know WHAT LEVEL it is currently sitting at, we can directly dump its value into that level's list!!
// and since in an n-ary tree, children are stored in a list from LEFT TO RIGHT... when we loop through children, the left child will ALWAYS be visited first, then the next sibling, then the next!!
// so inside each level, nodes will automatically get added from left to right naturally without doing anything extra!! TRUST THE DFS FLOW BRO!!
class Solution {
    public List<List<Integer>> levelOrder(Node root) {
        List<List<Integer>> answer = new ArrayList<>();// this is our main list which will hold sublists for every level
        ans(root, 0, answer);// root is node, 0 is the starting LEVEL (root is at level 0 obviously), and answer is our main list
        return answer;// finally return the full list of lists simple
    }
    public void ans(Node node, int level, List<List<Integer>> answer){
        if(node == null) return; // base case.. if the tree itself is empty (like root is null), or any node is null, just go back nothing to do
        // now see the coolest part here!! 
        // answer.size() tells us how many levels we have created, for each level so far!!, like samjho.. if we have 2 sublists in answer, that means 2 levels are done, and thats level 0 and level 1!!..  i mean if its made.. that means atleast all of level 0(level 0 will have only root obv), and some of level 1 are added only(in progress or dn)
        // suppose answer has 2 sublists (for level 0 and level 1), so answer.size() is 2!
        // now if our current node is at level 2... index 2 DOES NOT EXIST YET in answer!!(because level 2 comes after level 1.. so 3rd level.. so we are kinda tracknig the size of ans and if it matches  to level val.. that means we are one step below the current ans size(matlb naya sublist banana padega, pehli baar ye number mil raha))
        // so answer.size() == level (2 == 2) becomes true! that means: "Bhai, this is the FIRST time we have ever stepped into this level!"
        // so we immediately create a brand new empty ArrayList for this level and add it to answer!!
        // next time another sibling comes at level 2... answer.size() will be 3, and level is 2... 3 == 2 is false, so it WONT create a new list! it will just use the existing one!! SO SMART!!
        if(answer.size() == level){
            answer.add(new ArrayList<>());
        }
        // now we just fetch that particular level's sublist using answer.get(level) and dump our current node's value into it!!
        answer.get(level).add(node.val);
        // now this is n-ary tree.. binary tree had only left and right... here one parent can have 2, 3, 5, 10 children in an array/list!!
        // so simple, just run a for-each loop on node.children!!
        // for every child in that list, we call recursion: ans(child, level + 1, answer)
        // why level + 1? because child is sitting in the NEXT level down!!
        // AND REMEMBER: this is call by value!! so when child call finishes and recursion comes back up to this parent... level is STILL the same original level for the next sibling in the loop!!
        for(Node child : node.children){
            ans(child, level + 1, answer);// calling for child with level + 1.. keeps going deeper and deeper left to right!
        }
    }
}
//now bfs iterative one
/*

// okay now the classic iterative BFS queue approach.. here unlike the dfs one where we were cheating with levels and indexing into buckets while diving deep.. here we ACTUALLY go level by level horizontally left to right in pure BFS style!! so how do we do that? simple, we use a Queue because queue is FIFO (first in first out)... we put the root first, and then in each step we see how many guys are currently sitting in the queue (thats our level size!!), we take that many guys out one by one, put their values in a current level list, and while taking them out we dump all their children into the queue for the NEXT level!! so by the time this inner loop finishes, that entire level is completely processed and the queue ONLY has nodes of the next level!! loop keeps running untill queue is empty and boom level order is done!!
class Solution {
    public List<List<Integer>> levelOrder(Node root) {
        List<List<Integer>> ans = new ArrayList<>();// main list to store all the level sublists
        if(root == null) return ans;// basic base case bro, if tree itself has nothing then queue will throw or give empty so just return empty ans list directly
        Queue<Node> q = new LinkedList<>();// taking standard queue using linkedlist because queue is interface in java
        q.offer(root);// first we push root into the queue.. so level 0 starts with just root sitting inside
        while(!q.isEmpty()){// as long as there are still levels left to process, keep running the while loop
            int size = q.size();// THIS IS THE MOST IMPORTANT STEP IN BFS!! we MUST fix the size right now!! why? because right now q.size() is strictly the number of nodes at the CURRENT level!! if you dont store it in a variable and directly write i < q.size() in for loop, the size will keep changing as we push children and everything will get mixed up and cooked!!
            List<Integer> currentLevel = new ArrayList<>();// sublist to collect all node values of this particular level only
            for(int i = 0; i < size; i++){// run loop exactly 'size' times so we only pop out current level guys
                Node curr = q.poll();// take out the front node from queue
                currentLevel.add(curr.val);// add its value to our current level list
                for(Node child : curr.children){// now for binary tree we did if(curr.left != null) and right != null.. but here its n-ary tree so curr.children is a list of nodes!! just loop through each child
                    if(child != null){// safety check just in case, if child exists then push into queue for the next level
                        q.offer(child);// push child into queue, so it lines up for the next level iteration
                    }
                }
            }
            ans.add(currentLevel);// after the for loop finishes, one whole level is completely done so add this sublist into main answer list!
        }
        return ans;// finally return the level order list of lists, fully sorted level by level from left to right!
    }
}*/