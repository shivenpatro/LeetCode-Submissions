class ProductOfNumbers {
    /* brute force: just do literally what the problem describes! keep a normal ArrayList storing every number as it arrives in O(1). whenever getProduct(k) is called, start a loop from index (n - k) all the way to (n - 1) and multiply those last k numbers together! the constraints are small enough (at most 4 * 10^4 calls) that this actually passes on leetcode, though an interviewer will definitely ask for the O(1) prefix product follow-up above! */
    List<Integer> nums;
    public ProductOfNumbers() {
        nums = new ArrayList<>();
    }
    public void add(int num) {
        nums.add(num); // just append to the list in O(1)
    }
    public int getProduct(int k) {
        int prod = 1;
        int n = nums.size();
        /* start at (n - k) so we multiply exacty the last k elements of the list */
        for (int i = n - k; i < n; i++) {//n-k matlb.... agar k 2 hai.. then start from 2 elements before4 from last(n).. nd go tilll n
            prod *= nums.get(i);
        }
        return prod;
    }
}