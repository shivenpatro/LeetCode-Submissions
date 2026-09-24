/*
// Definition for a Node.
class Node {
    public int val;
    public List<Node> neighbors;
    public Node() {
        val = 0;
        neighbors = new ArrayList<Node>();
    }
    public Node(int _val) {
        val = _val;
        neighbors = new ArrayList<Node>();
    }
    public Node(int _val, ArrayList<Node> _neighbors) {
        val = _val;
        neighbors = _neighbors;
    }
}
*/
/*
class Solution {
    HashMap<Node, Node> map = new HashMap<>();//this will keep the track of which nodes are cloned
    public Node cloneGraph(Node node) {
        if(node == null) return null;//base case normal thing
        Node clonenode = new Node(node.val);//well well, the first node .. we will make clone of that na.. this will only be returned, because this is the 1st node for cloned graph
        map.put(node, clonenode);//this keeps the record of which node to which node is connected thats all
        dfs(node,clonenode);//calling the dfs.. pass that node and the cloned version of that node, so that all the neighbors from that node is explored.... concurrently clones for those are created and main thing, "connections b/w clones", like normal graph are also mande
        return clonenode;//this is the first clone node.. representing the first node of the actual graph so we have to return this only
    }
    public void dfs(Node node, Node clonenode){
        for(Node n : node.neighbors){//checking all the neighbors of node node, which came here
            if(!map.containsKey(n)){///first and foremost check,  does the map have that node n already... that means its already clonedd!! no need to make a clone and all.. this condition is if the node is not there in the map, that means we will have to make a clone of it 
                Node clone = new Node(n.val);//making the clone with that particular node n.. for that i mean, for which the dfs was called
                map.put(n,clone);//obv mark it in the map that this clone was made for this n, classic tracking which ew are doing using map
                clonenode.neighbors.add(clone);//now see see.. what did we do? there is a normmal graph with node node(this was the name of the starting of the normal graph na).. and connected to all that... clonenode was clone of node node(clonenode is like the starting node for clone graph and node is starting of normal graph).. and dfs was called on it.. and clone is the clone of node n here, liek now we are cloning the neighbors.. but but once cloned,  WE NEED TO CONNECT ALL THE CLONED ONES NA? OR HOW IS IT A GRAPH BRUH.. SO WE ADD THIS CLONE TO THE NEIGHBOR LIST OF CLONENODE... SO THAT CONNECTION IS MADE... THINK LIKE THIS... 1->2...1 is "node"(starting) .. so we make "clonenode 1"(stating for clone graph)... now call dfs on 1(passing both normal 1 and clone 1.. we traverse using normal 1.. and on the fly make clones of neighbors and keep attaching to clonenode 1... to make the graph..).. node.neighbors means 1.neighbors have 2... so we check and 2 is new(not there in map but 1 is there because its clone was already made), we make clone of 2 now.. which is named "clone 2", now we have to connect clonenode 1  to clone 2.. so clonenode.neighbor.add(clone)... does 1.neighbor.add(2).. makign 1->2!!.. and this is how it will work  for every node of graph.. and if  its alreaedy there in map.. that means node is made.. we just need to conncet to clonenode ya
                dfs(n,clone);//call dfs again with this n and its corresponding clone to follow the same above idea and algo
            }
            else{
                //means the clone node is already there.. so just add it to clonenode neightbor.. like ex 1... whi
                clonenode.neighbors.add(map.get(n));//get that clone node coresponmding to n and add to neighbor
            }
        }
    }
}

/*
========================================================================================================
FULL LINE-BY-LINE STEP-BY-STEP TRACE FOR THE 4-NODE CYCLE EXAMPLE:
Original Graph: 1 <--> 2 <--> 3 <--> 4 <--> 1
Neighbors:
1: [2, 4]
2: [1, 3]
3: [2, 4]
4: [1, 3]
========================================================================================================

1. START AT cloneGraph(node = 1):
   - Node 1 is not null.
   - We create: Node clonenode = new Node(1) -> let's call it C1 (Clone of 1).
   - map.put(1, C1) -> Map is now: { 1: C1 }
   - Call dfs(node = 1, clonenode = C1).

--------------------------------------------------------------------------------------------------------
2. INSIDE dfs(node = 1, clonenode = C1):
   - node.neighbors for 1 are [2, 4].

   --> NEIGHBOR n = 2:
       - !map.containsKey(2) is TRUE (2 is NOT in map yet!).
       - Create: Node clone = new Node(2) -> let's call it C2 (Clone of 2).
       - map.put(2, C2) -> Map is now: { 1: C1, 2: C2 }
       - C1.neighbors.add(C2) -> Edge drawn: C1 --> C2!!
       - Call dfs(node = 2, clonenode = C2) [DFS dives deep into 2!]

--------------------------------------------------------------------------------------------------------
3. INSIDE dfs(node = 2, clonenode = C2):
   - node.neighbors for 2 are [1, 3].

   --> NEIGHBOR n = 1:
       - !map.containsKey(1) is FALSE (1 IS ALREADY IN MAP as C1!).
       - Hits ELSE block:
       - C2.neighbors.add(map.get(1)) -> adds C1 into C2's neighbors!
       - Edge drawn: C2 --> C1!! (Now both C1 <--> C2 are linked!).
       - No recursive call fired, cycle back to 1 prevented!

   --> NEIGHBOR n = 3:
       - !map.containsKey(3) is TRUE (3 is NOT in map yet!).
       - Create: Node clone = new Node(3) -> let's call it C3 (Clone of 3).
       - map.put(3, C3) -> Map is now: { 1: C1, 2: C2, 3: C3 }
       - C2.neighbors.add(C3) -> Edge drawn: C2 --> C3!!
       - Call dfs(node = 3, clonenode = C3) [DFS dives deep into 3!]

--------------------------------------------------------------------------------------------------------
4. INSIDE dfs(node = 3, clonenode = C3):
   - node.neighbors for 3 are [2, 4].

   --> NEIGHBOR n = 2:
       - !map.containsKey(2) is FALSE (2 IS ALREADY IN MAP as C2!).
       - Hits ELSE block:
       - C3.neighbors.add(map.get(2)) -> adds C2 into C3's neighbors!
       - Edge drawn: C3 --> C2!! (Now both C2 <--> C3 are linked!).
       - No recursive call fired!

   --> NEIGHBOR n = 4:
       - !map.containsKey(4) is TRUE (4 is NOT in map yet!).
       - Create: Node clone = new Node(4) -> let's call it C4 (Clone of 4).
       - map.put(4, C4) -> Map is now: { 1: C1, 2: C2, 3: C3, 4: C4 }
       - C3.neighbors.add(C4) -> Edge drawn: C3 --> C4!!
       - Call dfs(node = 4, clonenode = C4) [DFS dives deep into 4!]

--------------------------------------------------------------------------------------------------------
5. INSIDE dfs(node = 4, clonenode = C4):
   - node.neighbors for 4 are [1, 3].

   --> NEIGHBOR n = 1:
       - !map.containsKey(1) is FALSE (1 IS ALREADY IN MAP as C1!).
       - Hits ELSE block:
       - C4.neighbors.add(map.get(1)) -> adds C1 into C4's neighbors!
       - Edge drawn: C4 --> C1!! (Cycle closure edge!).

   --> NEIGHBOR n = 3:
       - !map.containsKey(3) is FALSE (3 IS ALREADY IN MAP as C3!).
       - Hits ELSE block:
       - C4.neighbors.add(map.get(3)) -> adds C3 into C4's neighbors!
       - Edge drawn: C4 --> C3!! (Now both C3 <--> C4 are linked!).

   - All neighbors of 4 are explored! dfs(4, C4) completes and pops off stack back to 3!

--------------------------------------------------------------------------------------------------------
6. BACKTRACKING & FINISHING:
   - Back at dfs(3, C3): all neighbors [2, 4] explored! Pops back to 2!
   - Back at dfs(2, C2): all neighbors [1, 3] explored! Pops back to 1!
   - Back at dfs(1, C1): now loop advances to second neighbor of 1:
     --> NEIGHBOR n = 4:
         - !map.containsKey(4) is FALSE (4 was already cloned as C4 during the deep dive!).
         - Hits ELSE block:
         - C1.neighbors.add(map.get(4)) -> adds C4 into C1's neighbors!
         - Edge drawn: C1 --> C4!! (Now both C1 <--> C4 are linked!).

   - All neighbors of 1 are explored! dfs(1, C1) finishes completely!
   - Finally returns clonenode (C1).

FINAL RESULT:
C1.neighbors = [C2, C4]
C2.neighbors = [C1, C3]
C3.neighbors = [C2, C4]
C4.neighbors = [C1, C3]
A 100% deep-copied identical graph with completely fresh memory addresses!
========================================================================================================
*/

//bfs approach
// now doing clone graph using BFS with a standard Queue!!
// logic is identical conceptually: we still need the HashMap<Node, Node> to map original -> clone and act as visited check!!
// we clone the starting node, put it in map, and push the ORIGINAL starting node into Queue<Node>..
// inside while(!q.isEmpty()), we poll the current original node curr..
// we look at all neighbors n of curr:
// if neighbor n is NOT in map yet:
//   1. create its clone: new Node(n.val)
//   2. register in map: map.put(n, clone)
//   3. push the ORIGINAL neighbor n into queue so we can process its neighbors later!!
// whether it was already in map or newly created, we just do map.get(curr).neighbors.add(map.get(n))!!
// this connects the cloned nodes together!! once queue empties, the whole deep copy is ready!!
class Solution {
    HashMap<Node,Node> map = new HashMap<>();//to keep the record of the nodes which are already cloned
    public Node cloneGraph(Node node) {
        if(node == null) return null;///simple base and edge case check
        Node clonenode = new Node(node.val);//creating the clone of  the first node coming
        map.put(node,clonenode);//making the note and tracking so since clone was made, first thing is to add it to map to make sure we dont duplicate clone creation
        Queue<Node> q = new LinkedList<>();//queue defination
        q.offer(node);//now see see.. what we are doing is... basic simple bfs we will do... but like.. we use the nodes of actual graph to make clones on fly.. its like we do simple bfs traversal on original graph.. same push pop push neighbor and all.. and if anything is not there in map, we make clone of it then and there and add it to neighbor of the eearlier above clonednode.. which was popped!! because currently we are at the neighbor of that popped on na!! so make clone of neighbor and join this clone to the neighbor of the clonenode(this is the clone of the node which was popped).. so like both clones are connected like its connected in the real graph
        while(!q.isEmpty()){//basic queue traversal, we have to travers untill the queue is empty na..  pop the start one and then add its neighbors by piushhing its neighbors to the queue
            Node start = q.poll();//poll the first node in the queue
            for(Node s : start.neighbors){//explore all the nodes in the neighbor to the start.. like we polled it.. now we explore the neighbors and check if its in map then clone is made.. just add it.. if not there in map.. we need to make clone.. we need ot join the clone to this start, and then we need to add to queue as well!! because it was never made or explored na.. but if its made then its already in queue or explored.. so just add to clone neighbor for making the edge connections
                if(!map.containsKey(s)){
                    //map doesnt have that node
                    Node clone = new Node(s.val);//making the clone of it
                    map.put(s,clone);//tracking it  and marking that it was created and put in queue for exploration as well
                    q.offer(s);//most imp thing, we add the original and not the clones cuz obv.. original is full made, clones we make on the fly.. originals we traverse and clones are made and connected on the fly
                    map.get(start).neighbors.add(clone);//getting the clone of start and adding this clone(which is clone of s), and add it to the clone of start
                }
                else{
                    map.get(start).neighbors.add(map.get(s));//now see.. if the clone is already made that means... we can directly get the clone of start and add to that clone of s(which exists) and join both simple,// connect the clone of start to the clone of neighbor s(which is neighbor of start)!!
                    // map.get(start) gives start's clone, map.get(s) gives neighbor's clone
                }   
            }
        }
        return clonenode;
    }
}
/*
========================================================================================================
FULL LINE-BY-LINE STEP-BY-STEP TRACE FOR BFS (4-NODE CYCLE EXAMPLE):
Original Graph: 1 <--> 2 <--> 3 <--> 4 <--> 1
Neighbors:
1: [2, 4]
2: [1, 3]
3: [2, 4]
4: [1, 3]
========================================================================================================

1. INITIALIZATION:
   - Call cloneGraph(node = 1)
   - Node 1 is not null.
   - Create: Node clonenode = new Node(1) -> let's call it C1 (Clone of 1).
   - map.put(1, C1) -> Map is now: { 1: C1 }
   - Queue<Node> q = new LinkedList<>()
   - q.offer(1) -> Queue state: [ 1 ]

--------------------------------------------------------------------------------------------------------
2. ITERATION 1 (Processing Node 1):
   - !q.isEmpty() is TRUE.
   - Node start = q.poll() -> start = 1. Queue is now: [ ]
   - We inspect neighbors of 1: [2, 4].

   --> NEIGHBOR s = 2:
       - !map.containsKey(2) is TRUE (2 is fresh, not cloned yet!).
       - Create: Node clone = new Node(2) -> C2.
       - map.put(2, C2) -> Map is now: { 1: C1, 2: C2 }
       - q.offer(2) -> Queue is now: [ 2 ]
       - map.get(1).neighbors.add(C2) -> Edge drawn: C1 --> C2!!

   --> NEIGHBOR s = 4:
       - !map.containsKey(4) is TRUE (4 is fresh, not cloned yet!).
       - Create: Node clone = new Node(4) -> C4.
       - map.put(4, C4) -> Map is now: { 1: C1, 2: C2, 4: C4 }
       - q.offer(4) -> Queue is now: [ 2, 4 ]
       - map.get(1).neighbors.add(C4) -> Edge drawn: C1 --> C4!!

   - Finished neighbors of 1.
   - Current State:
     * Queue: [ 2, 4 ]
     * Clones built: C1.neighbors = [ C2, C4 ]

--------------------------------------------------------------------------------------------------------
3. ITERATION 2 (Processing Node 2):
   - !q.isEmpty() is TRUE.
   - Node start = q.poll() -> start = 2. Queue is now: [ 4 ]
   - We inspect neighbors of 2: [1, 3].

   --> NEIGHBOR s = 1:
       - !map.containsKey(1) is FALSE (1 is already in map as C1!).
       - Hits ELSE branch:
       - map.get(2).neighbors.add(map.get(1)) -> adds C1 into C2's neighbors!
       - Edge drawn: C2 --> C1!! (Both directions C1 <--> C2 are now connected!).
       - NOTE: 1 is NOT added to the queue because it was already visited/cloned!

   --> NEIGHBOR s = 3:
       - !map.containsKey(3) is TRUE (3 is fresh, not cloned yet!).
       - Create: Node clone = new Node(3) -> C3.
       - map.put(3, C3) -> Map is now: { 1: C1, 2: C2, 3: C3, 4: C4 }
       - q.offer(3) -> Queue is now: [ 4, 3 ]
       - map.get(2).neighbors.add(C3) -> Edge drawn: C2 --> C3!!

   - Finished neighbors of 2.
   - Current State:
     * Queue: [ 4, 3 ]
     * Clones built: C2.neighbors = [ C1, C3 ]

--------------------------------------------------------------------------------------------------------
4. ITERATION 3 (Processing Node 4):
   - !q.isEmpty() is TRUE.
   - Node start = q.poll() -> start = 4. Queue is now: [ 3 ]
   - We inspect neighbors of 4: [1, 3].

   --> NEIGHBOR s = 1:
       - !map.containsKey(1) is FALSE (1 is already in map as C1!).
       - Hits ELSE branch:
       - map.get(4).neighbors.add(map.get(1)) -> adds C1 into C4's neighbors!
       - Edge drawn: C4 --> C1!! (Cycle closure edge!).
       - Not pushed to queue.

   --> NEIGHBOR s = 3:
       - !map.containsKey(3) is FALSE (3 was already cloned as C3 during Node 2's iteration!).
       - Hits ELSE branch:
       - map.get(4).neighbors.add(map.get(3)) -> adds C3 into C4's neighbors!
       - Edge drawn: C4 --> C3!!
       - Not pushed to queue.

   - Finished neighbors of 4.
   - Current State:
     * Queue: [ 3 ]
     * Clones built: C4.neighbors = [ C1, C3 ]

--------------------------------------------------------------------------------------------------------
5. ITERATION 4 (Processing Node 3):
   - !q.isEmpty() is TRUE.
   - Node start = q.poll() -> start = 3. Queue is now: [ ]
   - We inspect neighbors of 3: [2, 4].

   --> NEIGHBOR s = 2:
       - !map.containsKey(2) is FALSE (2 is already in map as C2!).
       - Hits ELSE branch:
       - map.get(3).neighbors.add(map.get(2)) -> adds C2 into C3's neighbors!
       - Edge drawn: C3 --> C2!! (Both directions C2 <--> C3 are now connected!).

   --> NEIGHBOR s = 4:
       - !map.containsKey(4) is FALSE (4 is already in map as C4!).
       - Hits ELSE branch:
       - map.get(3).neighbors.add(map.get(4)) -> adds C4 into C3's neighbors!
       - Edge drawn: C3 --> C4!! (Both directions C3 <--> C4 are now connected!).

   - Finished neighbors of 3.
   - Current State:
     * Queue: [ ] (Empty!)
     * Clones built: C3.neighbors = [ C2, C4 ]

--------------------------------------------------------------------------------------------------------
6. TERMINATION & RETURN:
   - while(!q.isEmpty()) check fails because queue is now empty.
   - BFS loop ends.
   - Return clonenode (C1).

FINAL RECONSTRUCTED CLONE GRAPH:
C1.neighbors = [C2, C4]
C2.neighbors = [C1, C3]
C4.neighbors = [C1, C3]
C3.neighbors = [C2, C4]

Every node has fresh memory references, all bidirectional edges are preserved, and queue cycle loops were prevented!
========================================================================================================
*/