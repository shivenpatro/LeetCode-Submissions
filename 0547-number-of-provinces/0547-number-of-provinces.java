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
}