import java.util.*;

class Solution {
    public int shortestPath(int n, int[][] edges, String labels, int k) {
        /* so see.. this problem is basically asking us to find the shortest path from node 0 to node n - 1 in a directed weighted graph, but with a weird catch: the concatenated characters of the nodes along the path can contain AT MOST k consecutive identical characters! this means we cant just run a normal dijkstra using only distance[u], because two paths reaching the same node u with different lengths might have different consecutive character counts, and the one with the longer distance right now might turn out valid later while the shorter one blows past k and gets disqualified! */

        /* so how do we fix dijkstra to handle this? we add the current consecutive identical character count as part of our state! that means our distance table becomes a 2D array distance[u][count], where distance[u][count] stores the minimum edge weight sum to reach node u such that the current block of consecutive identical characters has length equal to count. since count can go from 1 up to k, our state space is n * k, which is totally small enough given the constraints. and our priority queue will store state tuples of (weightSum, node, count), always popping the minimum weight state first just like standard dijkstra! */

        char[] sChar = labels.toCharArray();
        List<List<int[]>> adj = new ArrayList<>();
        for (int i = 0; i < n; i++) adj.add(new ArrayList<>());
        
        /* standard directed weighted adjacency list construction.. each edge entry stores {neighborNode, edgeWeight} */
        for (int[] edge : edges) {
            int u = edge[0];
            int v = edge[1];
            int w = edge[2];
            adj.get(u).add(new int[] {v, w});
        }

        /* distance[u][c] stores min weight to reach node u with consecutive duplicate count c.. initialized to infinity */
        long[][] dist = new long[n][k + 1];
        for (int i = 0; i < n; i++) {
            Arrays.fill(dist[i], Long.MAX_VALUE);
        }

        /* priority queue ordered by minimum weight sum first.. each element in pq is {weightSum, node, consecutiveCount} */
        PriorityQueue<long[]> pq = new PriorityQueue<>(Comparator.comparingLong(a -> a[0]));

        /* base case: starting at node 0, weight is 0, and consecutive character count starts at 1 */
        dist[0][1] = 0;
        pq.offer(new long[] {0, 0, 1});

        while (!pq.isEmpty()) {
            long[] curr = pq.poll();
            long d = curr[0];
            int u = (int) curr[1];
            int count = (int) curr[2];

            /* if we reached the destination node n - 1, return the minimum accumulated weight sum immediately! */
            if (u == n - 1) {
                return (int) d;
            }

            /* if we found a strictly shorter or equal way to reach state (u, count) already, skip this redundant state */
            if (d > dist[u][count]) continue;

            /* explore all outgoing directed edges from current node u */
            for (int[] neighbor : adj.get(u)) {
                int v = neighbor[0];
                int weight = neighbor[1];

                /* calculate new consecutive duplicate count when moving from u to v */
                int newCount;
                if (sChar[v] == sChar[u]) {
                    newCount = count + 1; // character is same as previous node, increment streak
                } else {
                    newCount = 1; // character changed, streak resets to 1
                }

                /* if the new consecutive count exceeds k, this path violates the rule, so skip it completely! */
                if (newCount > k) continue;

                long newDist = d + weight;

                /* if we found a better (smaller) weight sum to reach neighbor v with this specific newCount, update distance table and push to priority queue */
                if (newDist < dist[v][newCount]) {
                    dist[v][newCount] = newDist;
                    pq.offer(new long[] {newDist, v, newCount});
                }
            }
        }

        /* if the priority queue empties and we never managed to reach node n - 1, return -1 */
        return -1;
    }
}