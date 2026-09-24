// bro so basically what this problem is asking is super straightforward:
// we are given 'n' vertices (from 0 to n-1), a list of bidirectional edges, a source node, and a destination node!!
// we just need to check: can we start walking from 'source' and reach 'destination'??
// if yes return true, otherwise return false!
//
// STEP-BY-STEP GAME PLAN (DFS):
// 1. First build the adjacency list 'adj' because the input is given as an edge list edges[][]!!
//    Since edges are bidirectional, for every edge [u, v], add v to adj.get(u) AND add u to adj.get(v)!!
// 2. We use a boolean[] visited array of size n so we don't walk in circles/infinite loops (like 0 -> 1 -> 0 -> 1...)!
// 3. Fire DFS starting from 'source':
//    - Base case 1: if current node == destination, jackpot! return true right away!
//    - Base case 2: if already visited, return false (we already explored this path)!
//    - Mark current node visited = true!
//    - Explore all neighbors: if ANY neighbor's dfs call returns true, propagate true all the way up!
// 4. If all paths from source are exhausted and destination is never hit, return false!

class Solution {
    public boolean validPath(int n, int[][] edges, int source, int destination) {
        //base check, if sounce == destination.. that means retrun true na because source and destination is  same
        if(source == destination) return true;
        //build the graph first now.. so build graph we need to build adjacency list using list of lists.. classic thing
        List<List<Integer>> adj = new ArrayList<>();
        //step 1: build the adjacency list and fill the edges
        //we made the adjacency list
        for(int i = 0; i<n; i++){
            adj.add(new ArrayList<>());// so see since we know that n is the number of  nodes in the graph so that means in the adj list we  need to n number of lists.. its a list inside list.. so inside this list we need  n number of lists, where each list is for a particular node that will hold  the neighbors for that particular node in that list
        }
        //this list of lists is empty na.. now we need to fill it such that 
        //0->1,2 ,1->0,2, 2->0,1.... like this.. but the list will be filled  like {{1,2},{0,2},{0,1}}, like see see, each index in the list is a node.. and in that index, that index list holds the nodes connected to the node of that index... like 1,2 on index 0 means 0 connected to 1 and 2
        //so now since the list of list is empty we need to fill it with real connections
        for(int edge[] : edges){
            int u = edge[0];
            int v = edge[1];
            adj.get(u).add(v);
            adj.get(v).add(u);//so see we need to add both ways because its undirected na thats why, and obv... adj.get me we get taht index and add that node at that index.. sayuing that index node connects to that particular node which we just added
        }
        //step 2: setup visiited array track
        boolean[] visited = new boolean[n];
        //step 3: start the dfs man
        return dfs(source, destination, adj, visited);
    }
    public boolean dfs(int curr, int dest, List<List<Integer>> adj, boolean[] visited){
        if(curr == dest) return true;//reached the destination, no need to search more!!
        //mark the curr as visited
        visited[curr] = true;//marking visited
        for(int neighbor : adj.get(curr)){//now see.. we are traversing thorugh all the neighbors connected to curr na.. we made the adj and now using it and traversing neighbors of curr thats all
            if(!visited[neighbor]){
                if(dfs(neighbor,dest,adj,visited)){//we call dfs on that neighbor with goal for that destination, and now most imp thing.. if we ever reached the base case that the neighbor which goes as curr becomes equal to destination, then obv we return true from there and this return should ocme back back back from the depths of recursive calls of dfs.. so if true there, return true here also na.. because anywyas we found the answre, nothing lef to search
                    return true;
                }
            }
        }
        // explored all neighbors and couldn't reach destination from this path, now if the dfs goes wrong we need to return false na,... and since its recursion.. every recursion call below as well will need to return false obv...
        return false;// if curr is a dead end (like node 1 or 2 in Example 2 which only connects back to already-visited 0), 
        // its loop finishes with no valid moves and returns false back to the caller (node 0), 
        // allowing 0 to continue checking its remaining neighbors before returning false itself if all paths fail!
    }
}