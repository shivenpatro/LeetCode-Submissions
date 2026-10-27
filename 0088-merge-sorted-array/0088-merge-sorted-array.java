
//more optimkmal
class Solution {
    public void merge(int[] nums1, int m, int[] nums2, int n) {
        // p1 points to the last actual element of nums1
        int p1 = m - 1;
        // p2 points to the last element of nums2
        int p2 = n - 1;
        // p points to the very end of nums1 (where the empty 0s are)
        int p = m + n - 1;
        // fill nums1 backwards by placing the larger element at the back
        while (p1 >= 0 && p2 >= 0) {
            if (nums1[p1] > nums2[p2]) {
                nums1[p] = nums1[p1];
                p1--;
            } else {
                nums1[p] = nums2[p2];
                p2--;
            }
            p--;
        }
        // if nums2 still has leftover elements, copy them in
        // (if nums1 has leftovers, they are already in their correct places!)
        while (p2 >= 0) {
            nums1[p] = nums2[p2];
            p2--;
            p--;
        }
    }
}

/*class Solution {
    public void merge(int[] nums1, int m, int[] nums2, int n) {
        // copy all elements of nums2 into the zero-padded end of nums1
        for (int i = 0; i < n; i++) {
            nums1[m + i] = nums2[i];
        }
        // sort the entire combined array
        Arrays.sort(nums1);
    }
}*/