class Solution {
    public void merge(int[] nums1, int m, int[] nums2, int n) {
        // copy all elements of nums2 into the zero-padded end of nums1
        for (int i = 0; i < n; i++) {
            nums1[m + i] = nums2[i];
        }
        // sort the entire combined array
        Arrays.sort(nums1);
    }
}