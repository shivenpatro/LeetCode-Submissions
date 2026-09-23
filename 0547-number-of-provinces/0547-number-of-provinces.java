//dfs approach
/*  
class Solution {
    int n;//global level so it can be usewd in all the functions
    public int findCircleNum(int[][] isConnected) {
        n = isConnected.length;//finding the number of nodes(number of rows/number of  cols = number of nodes)
        boolean[] visited = new boolean[n];//the visited array check, simple shit
        int count = 0;//keeping the count of number of provinces
        for(int i = 0;i<n;i++){//now see, we have to traverse  through all the nodes obv right, so we write this for loop  for that, thats simple 
            if(!visited[i]){//this means we havnt visited that node yet, so we have to call dfs on it, since we havnt visited it we call dfs on it, we call dfs on it to check for neighbours(obv if any), and find if it makes a province
                dfs(i, isConnected,visited);//so we call the dfs function on this node
                count++;//once control comes here, that means dfs has been done for  the whole province connected to the primary node for whihc the above funciton was called, that means one province is done and we need to add it so do count++ for that
            }
        } 
        return count;//final return of the number of  provinces
    }
    public void dfs(int ind,  int[][] arr, boolean[] visited){//now understand this, here that node will come whihc hasnt been visited thats all
        visited[ind] = true;//first and foremost, since that node came, obv mark it visited na, like because this function was called on it, toh ye toh visit ho hin gaya  na
        for(int i = 0; i<n; i++){//now now... we run a loop for all the nodes from beginning to end, for all  the nodes matlb bhai, SAARE
            if(arr[ind][i] == 1 && !visited[i]){//most  imp check.. 1st check.. arr[ind][i]...matlb mmatlb.. node ind aur node i.. are both connected?? 2nd check,  have we visited the node i yet? if both pass then only dfs will be called obv..i mean if its viisted obv skip(pointless hai na it will just increase number of recursion calls), and also obv, if its connected to node ind, for which this function was called, then only we call this because then only it will be coming under one province, nai toh alag province hogaya
                dfs(i, arr, visited);//now, so we call again, since not visited and connected, it will go ahead and explore more.. and once the whole explore is done for each each node.. recursion will start coming back.. falling back and finally control will return back to findCircleNum function, and count++ will     be done because this whole came under one province bhai
            }
        }
    }
}*/

//// okay now solving number of provinces using BFS instead of DFS!!
// logic is literally identical to connected components.. loop through every city from 0 to n-1.. 
// if any city i is NOT visited yet, that means we found a fresh new province!
// so count++ right there, and then we run BFS starting from that city i!!
// inside BFS, we use a standard Queue<Integer>.. push city i, mark it visited right away.. 
// then pop from queue one by one, check all its connected neighbors directly from the isConnected matrix row.. 
// and whichever neighbor is connected (val == 1) and not visited, push it into queue and mark it visited immediately so it doesn't get pushed multiple times!
// once the queue becomes completely empty, that entire connected component/province is fully visited!!
// loop moves to next unvisited city and repeats untill all nodes are covered!!
class Solution {
    int n;
    public int findCircleNum(int[][] isConnected) {
        n = isConnected.length;// total number of nodess!!!  number of cols= number of nodes only
        boolean[] visited = new boolean[n];// visited array to track which nodes we already covered, just basic true/false tracking
        int count = 0;// will store total number of provinces

        for(int i = 0; i < n; i++){// check every single node from 0 to n-1
            if(!visited[i]){// unvisited means new province found!!, so obv since not visited, we call bfs on it and start exploring and all the nodes connected to this as well
                count++;// increment province count, control came here means province is going to be found, this can be written after the bfs call line as well, same shit only doesnt matter, because whtever is connected to this node(or if notihng also), its a node so ya
                bfs(i, isConnected, visited);// run BFS to visit this entire province
            }
        }
        return count;// return final number of provinces
    }

    public void bfs(int start, int[][] isConnected, boolean[] visited){
        Queue<Integer> q = new LinkedList<>();// standard BFS queue
        q.offer(start);// push starting city of this province, the basic bfs shit, push the first node
        visited[start] = true;// ALWAYS mark visited when pushing into queue so duplicate pushes never happenobv, we pushed into queue, and this bfs was called on that node, so we mark this node visited only na, obv thing, since its in queue and its gonna get popped adn visited only, and add all nodes connected to this to the queue, simple BFS THING, SAME AS IN TREE FOR CHILDREN(or 2 for binary)

        while(!q.isEmpty()){// run till this queue is empty, because see see.. only nodes whihc are connected to the prev nodes.. and to prev nodes.. and finally to the start.. are gonna get adddedn in queue so ya..1 province!!
            int curr = q.poll();// take out current node, and now for this node, we need to scan all the vertices again, i mean we have to check na for this exact node, what what nodes are connected to this one, and we will add all that true as well, because all that will come under this province only
            // now scan the entire row(row means like every col na... like every node in 2d so ya) for curr to see which cities are directly connected to it
            for(int v = 0; v < n; v++){
                // if connected (val == 1) and city v is not visited yet
                if(isConnected[curr][v] == 1 && !visited[v]){//2 conds obv... if that city and v is connected, and v is not visited.. then count it inside the current going province.. its like a net expanding to its contact
                    visited[v] = true;// mark visited immediately
                    q.offer(v);// push to queue so we can explore its neighbors next, basic bfs
                }
            }
        }
    }
}