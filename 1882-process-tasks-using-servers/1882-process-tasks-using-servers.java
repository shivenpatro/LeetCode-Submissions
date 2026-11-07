class Solution {
    public int[] assignTasks(int[] servers, int[] tasks) {
        /* ok so see.. what is this problem asking us? we have n servers with given weights, and m tasks where tasks[j] is the time needed to process task j. at second j, task j enters the queue. we always want to assign the next task to a free server with the SMALLEST weight, and if there is a tie, pick the one with the SMALLEST index! if no server is free, the task has to wait until a server finishes and becomes available. our goal is to return an array of length m showing which server index processed each task. 
        now why do we need two priority queues here? think about it.. we constantly need two completely different things: 
        1. from all the FREE servers, we need the one with min weight (and min index on tie). so we need a min-heap 'freeServers' ordered by (weight, index). 
        2. but when servers are BUSY working on tasks, we need to know which server finishes the earliest! so we need a second min-heap 'busyServers' ordered by (finishTime, weight, index). 
        and very important detail: finishTime MUST be a long! why? because if tasks pile up and servers are busy for long durations, finish times keep adding up and can easily exceed 2 * 10^9, which overflows standard 32-bit int and causes subtle negative-number bugs! */

        int n = servers.length;
        int m = tasks.length;
        int[] result = new int[m];
        /* freeServers min-heap: stores {weight, index} ordered by weight ascending, tie-break by index ascending */
        PriorityQueue<int[]> freeServers = new PriorityQueue<>((a, b) -> {
            if (a[0] != b[0]) return Integer.compare(a[0], b[0]);
            return Integer.compare(a[1], b[1]);
        });
        /* busyServers min-heap: stores {finishTime, weight, index} ordered by finishTime ascending, then weight, then index */
        PriorityQueue<long[]> busyServers = new PriorityQueue<>((a, b) -> {
            if (a[0] != b[0]) return Long.compare(a[0], b[0]);
            if (a[1] != b[1]) return Long.compare(a[1], b[1]);
            return Long.compare(a[2], b[2]);
        });
        /* initially all servers are idle and free, so push every server into freeServers */
        for (int i = 0; i < n; i++) {
            freeServers.offer(new int[] {servers[i], i});
        }
        /* now process each task i arriving at second i */
        for (int i = 0; i < m; i++) {
            /* step 1: release all busy servrs that have finished their job by current time i (finishTime <= i) and move them back to freeServers so they can be considered for task i */
            while (!busyServers.isEmpty() && busyServers.peek()[0] <= i) {
                long[] curr = busyServers.poll();
                freeServers.offer(new int[] {(int) curr[1], (int) curr[2]});
            }
            /* step 2: now assign task i to a server.. there are two cases here */
            if (freeServers.isEmpty()) {
                /* case A: all servers are currently busy! we litrally cannot process task i at second i.. so we must wait for the earliest server to finish. we pop the top of busyServers (which has the smallest finishTime), assign task i to it, and push it back with its new finishTime = (finTime + tasks[i])! */
                long[] curr = busyServers.poll();
                long finTime = curr[0];
                int wt = (int) curr[1];
                int idx = (int) curr[2];
                result[i] = idx;
                busyServers.offer(new long[] {finTime + tasks[i], wt, idx});
            } else {
                /* case B: we have at least one free server available right now at second i! we grab the best one from freeServers (smallest weight and index), assign task i, and push it to busyServers with finishTime = (i + tasks[i])! */
                int[] curr = freeServers.poll();
                int wt = curr[0];
                int idx = curr[1];
                result[i] = idx;
                busyServers.offer(new long[] {(long) i + tasks[i], wt, idx});
            }
        }
        return result;
    }
}