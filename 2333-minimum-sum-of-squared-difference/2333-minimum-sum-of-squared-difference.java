/*
//brute approach first, with priority queue, to reduce the time comp, like see, technically what tf are we even doing..  we doing have to care about k1 and k2 and all that.. see we have to care aobut the total number of reductions we can do thats all... because at the end the only thing we care about is teh "difference tahts all"
class Solution{
    public long minSumSquareDiff(int[] nums1, int nums2[], int k1, int k2){
        int n = nums1.length;
        // modifying nums1[i] by -1 or modifying nums2[i] by +1 both reduce the diff by 1!
        // so we don't care which array is modified... we just merge both operation budgets into one big k!
        long k = (long) k1+k2;//type conversion
        //nwo the main thing is we use a priority queue.. cuz think a bit... we will always want and try to reduce the biggest difference number na? and once we decrease we need to sort or check again if we reduced it to taht extent that another difference is bigger... whcih will cause extra O(n) na... so just use pqueue because it does auto sorting and saves time na
        PriorityQueue<Integer> pq = new PriorityQueue<>(Collections.reverseOrder());//default is min heap, so we use Collections.reverseOrder() for max-heap
        for (int i = 0; i < n; i++) {
            int diff = Math.abs(nums1[i] - nums2[i]);//calculate absolute difference
            pq.add(diff);//push all differences into our max-heap
        }
        // greedy thought: always shave off from the current largest difference!
        // but see... pq.peek() > 0 is crucial because if differences become 0, shaving further makes them negative...
        // and (-1)^2 = 1 which increases our squared sum... so stop at 0!
        while (k > 0 && !pq.isEmpty() && pq.peek() > 0) {//so simple thing... its max heap.. greedy and we always reduce the largest difference, if the pq.peek is 0... that means largest is 0 that menas everything is 0.. we cant reduce anymore and square of negative = positive and will add in sum... so stop....
            int maxDiff = pq.poll();
            pq.add(maxDiff - 1);//reduce largest diff by 1 and push it back
            k--;//use up 1 operation
        }
        // calculate the final sum of squared differences
        long result = 0;
        while (!pq.isEmpty()) {
            long d = pq.poll();//simple thing, our final queue is already made after all k being used... just find result which is square of all
            result += (d * d);
        }
        return result;
    }
}
*/
//optimal one now
class Solution {
    public long minSumSquareDiff(int[] nums1, int[] nums2, int k1, int k2) {
        int n = nums1.length;
        // merge total operation budget k1 + k2 into a single k
        long k = (long) k1 + k2;
        // from constraints: max element is 10^5, so max possible absolute difference is 10^5
        // instead of hardcoding 100001, we can dynamically find maxDiff to keep array size tight!
        int maxDiff = 0;
        int[] diff = new int[n];
        for (int i = 0; i < n; i++) {
            diff[i] = Math.abs(nums1[i] - nums2[i]);
            maxDiff = Math.max(maxDiff, diff[i]);
        }
        // countDiff array acts as a frequency map (counting sort technique)
        // countDiff[d] stores how many pairs currently have an absolute difference equal to 'd'
        int[] countDiff = new int[maxDiff + 1];
        for (int d : diff) {
            countDiff[d]++;
        }
        // now see... the key intuition from MIK's video:
        // instead of decrementing one by one like in the heap...
        // we process the largest difference level by level in BULK!
        for (int currentDiff = maxDiff; currentDiff > 0 && k > 0; currentDiff--) {
            if (countDiff[currentDiff] == 0) continue;//no elements at this diff level, move down
            // how many operations can we actually perform at this level?
            // it's the minimum of:
            // 1. how many elements exist at this diff level (countDiff[currentDiff])
            // 2. our remaining operations budget (k)
            long ops = Math.min((long) countDiff[currentDiff], k);
            // all 'ops' elements of size currentDiff drop down to (currentDiff - 1)!
            countDiff[currentDiff] -= (int) ops;//frequency of currentDiff decreases
            countDiff[currentDiff - 1] += (int) ops;//frequency of (currentDiff - 1) increases
            k -= ops;//deduct the operations we just used
            // note: if k didn't finish all elements at currentDiff, k is now 0 and loop terminates cleanly!
        }
        // now simply compute the sum of squares:
        // for each remaining diff 'd', it contributes (countDiff[d] * d^2) to the total answer!
        long result = 0;
        for (int d = 1; d <= maxDiff; d++) {
            if (countDiff[d] > 0) {
                // use long casting to avoid 32-bit integer overflow!
                result += (long) countDiff[d] * ((long) d * d);
            }
        }
        return result;
    }
}
