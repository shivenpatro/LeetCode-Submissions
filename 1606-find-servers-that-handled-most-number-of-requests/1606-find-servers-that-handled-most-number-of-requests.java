class Solution {
    public List<Integer> busiestServers(int k, int[] arrival, int[] load) {
        /* ok so see.. what is this problem actually asking? we have k servers numbered from 0 to k - 1, and requests arriving at strictly increasing timestamps given in arrival[]. the i-th request arrives at arrival[i] and takes load[i] time to process. each server can only handle 1 request at a time! according to the rules, the i-th request prefers to go to server (i % k).. if server (i % k) is busy, we check (i % k + 1), then (i % k + 2), and if we reach the end we wrap around to server 0, 1, etc. if ALL servers are busy, the request is dropped completely! at teh end, we need to return a list of server IDs that handled the MAXIMUM number of requests! */

        /* now why does a brute force search give TLE? because both k and arrival.length can be up to 10^5! if for every request we linearly scan servers to find the next available one, that would be O(N * k) which is 10^10 operations! we need two things done super fast: 
        1. when a new request arrives at time arrival[i], we need to instantly free up all servers whose previous jobs finished on or before arrival[i]. to do this efficiently, we keep a min-heap 'busyServers' storing pairs of (finishTime, serverId). since its ordered by finishTime, we can just peek and pop all expired servers in O(log k) without scanning everyone! 
        2. once those servers become free, we need to find the first available server >= (i % k). and if none exists >= (i % k), we wrap around and pick the smallest available server >= 0! in java, a TreeSet is litrally built for this: availableServers.ceiling(target) gives the smallest available server >= target in O(log k) time! and if ceiling returns null (meaning all free servers have index < target), wrap-around is just taking availableServers.first()! */

        /* busyServers min-heap: stores {finishTime, serverId} sorted by finishTime ascending.. note: finishTime MUST be a long because arrival[i] + load[i] can reach 2 * 10^9 which is right near the 32-bit int limit! */
        PriorityQueue<long[]> busyServers = new PriorityQueue<>(Comparator.comparingLong(a -> a[0]));

        /* availableServers stores the IDs of all currently idle servers in sorted order so we can binary search with ceiling() */
        TreeSet<Integer> availableServers = new TreeSet<>();
        for (int i = 0; i < k; i++) {
            availableServers.add(i); // initially all k servers are completely free
        }

        int[] requestCount = new int[k]; // tracks how many requests each server successfully handled
        int n = arrival.length;

        for (int i = 0; i < n; i++) {
            long currTime = arrival[i];
            long duration = load[i];

            /* step 1: release all busy servrs that have finished their previous task on or before currTime and return them back to our availableServers TreeSet */
            while (!busyServers.isEmpty() && busyServers.peek()[0] <= currTime) {
                long[] finished = busyServers.poll();
                availableServers.add((int) finished[1]);
            }

            /* step 2: if no servers are free right now, the current request must be dropped as per the problem description */
            if (availableServers.isEmpty()) {
                continue;
            }

            /* step 3: find the next available server starting from preferred index (i % k).. ceiling(target) finds the smallest server ID >= target in O(log k) */
            int target = i % k;
            Integer assignedServer = availableServers.ceiling(target);

            /* wrap-around logic: if no available server has an ID >= target, wrap around to the beginning and pick the smallest free server using first() */
            if (assignedServer == null) {
                assignedServer = availableServers.first();
            }

            /* assign the task to assignedServer, mark it busy by removing from TreeSet, and push its finishTime into the min-heap */
            availableServers.remove(assignedServer);
            busyServers.offer(new long[] {currTime + duration, assignedServer});
            requestCount[assignedServer]++;
        }

        /* step 4: find the maximum request count handled by any server */
        int maxRequests = 0;
        for (int count : requestCount) {
            maxRequests = Math.max(maxRequests, count);
        }

        /* collect all server IDs that achieved this maximum count */
        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < k; i++) {
            if (requestCount[i] == maxRequests) {
                result.add(i);
            }
        }

        return result;
    }
}