//better solution
import java.util.Random;

class Solution {
    Random rand = new Random();

    public int minMoves2(int[] nums) {
        /* now think.. do we actually need to sort the ENTIRE array just to get the median at index n / 2? not at all! sorting costs O(N log N) time, but all we care about is finding the single element that WOULD sit at index n / 2 if the array were sorted! in c++ they have std::nth_element, but in java we can just write Quickselect (the partitioning logic from quicksort). quickselect finds the k-th smallest element in O(N) average time by picking a pivot, partitioning elements smaller to the left and larger to the right, and only recursing into the half where index k lies! once the median is placed at index n / 2, we just run a single linear pass to sum up Math.abs(nums[i] - median) in total O(N) time without full sorting, simple na! */

        int n = nums.length;
        int targetIdx = n / 2;
        int median = quickSelect(nums, 0, n - 1, targetIdx); // find the element that belongs at index n / 2

        int moves = 0;
        /* calculate total moves to bring every element to the median */
        for (int num : nums) {
            moves += Math.abs(num - median);
        }

        return moves;
    }

    /* standard quickselect to partition and isolate the k-th indexed element in O(N) average time */
    private int quickSelect(int[] nums, int left, int right, int k) {
        if (left == right) return nums[left];

        /* pick a random pivot to avoid worst-case O(N^2) on already sorted or repeated testcases */
        int pivotIndex = left + rand.nextInt(right - left + 1);
        pivotIndex = partition(nums, left, right, pivotIndex);

        if (k == pivotIndex) {
            return nums[k]; // found the exact median element!
        } else if (k < pivotIndex) {
            return quickSelect(nums, left, pivotIndex - 1, k); // target lies in left partition
        } else {
            return quickSelect(nums, pivotIndex + 1, right, k); // target lies in right partition
        }
    }

    /* lomuto-style partition placing pivot in its final sorted position with smaller elements on left and larger on right */
    private int partition(int[] nums, int left, int right, int pivotIndex) {
        int pivotValue = nums[pivotIndex];
        swap(nums, pivotIndex, right); // move pivot temporarily to the end
        int storeIdx = left;

        for (int i = left; i < right; i++) {
            if (nums[i] < pivotValue) {
                swap(nums, storeIdx, i);
                storeIdx++;
            }
        }

        swap(nums, storeIdx, right); // move pivot back to its final resting spot
        return storeIdx;
    }

    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
}

/*
class Solution {
    public int minMoves2(int[] nums) {
        //so see.. in 453 it was diff we has to do -1 or +1 to n-1 elements ok.. here we have the full freedom and we have to minimize it.. think logically now... to minimize,... how can we reduce the number... to do that 1st we have to finalize a number to which we will make al the elements equal right na.. so now think think... if we sort the array and then take the median.. which is the middlen element.. kust tjink.. prettyt sure that will take the least possible steps na?? because think about it... it sits exactly at the middle... like for example in 1,2,3... choosin to make all equal to 1 or 3 will need 3 steps...(making 1 to 3..2 steps..2 to 3..1 step.. and 3 to 3 0... same for making all equal to 1..)but for median which is 2.. total is 2 steps... 
        //and obv even if even elements are there..like in ex 2... any of the 2 median are fine.. u can check for ex 2... it will lead to  same number of steps 
        Arrays.sort(nums); // sort the array so that elements are arranged in non-decreasing order
        int n = nums.length;
        int median = nums[n / 2]; // the element sitting at n / 2 is our optimal median target
        int moves = 0;
        // loop through each number and accumulate how many steps are needed to reach the median 
        for (int num : nums) {
            moves += Math.abs(num - median); // absolute difference is the exact number of increments or decrements
        }
        return moves; // return total minimum moves required
    }
}*/