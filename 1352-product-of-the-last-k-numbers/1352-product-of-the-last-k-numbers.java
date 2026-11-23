class ProductOfNumbers {
    //so for optoimal one, we need O(1) for both add and getProduct.. brute force takes O(k) for getProduct which is slow.. so just like prefix sum we keep a prefix product list here.. suppose numbers are 3,2,2,3 then list becomes [3,6,12,36].. now if someone asks last k=2 product (2*3=6), we just take total product at end (36) and divide it by the product right before those last k elements (at index size-k-1 which is 6).. 36/6 = 6 simple!!
    //only catch is 0.. if 0 comes in between then multiplying with 0 turns everything after it into 0 and later 0/0 will crash.. so whenever num == 0 we just clear the whole list nd start fresh!! why? becoz any future query whose last k elements cross this 0 is guaranteed to have product 0 anyway.. so later if k > prod.size() we instantly know a 0 came inside those last k elements and just return 0 directly
    List<Integer> prod;
    public ProductOfNumbers() {
        prod = new ArrayList<>(); //no need to keep separate n or n++ becoz prod.size() already tracks size in O(1)
    }
    public void add(int num) {
        if (num == 0) {
            prod.clear(); //0 ruins all old products so just wipe the list clean and start over from size 0
        } else if (prod.isEmpty()) {
            prod.add(num); //first non zero number at start or right after a 0 reset.. so just add it directly
        } else {
            prod.add(prod.get(prod.size() - 1) * num); //keep multiplying with the last stored prefix product so last element always has full product till now
        }
    }
    public int getProduct(int k) {
        if (k > prod.size()) {
            return 0; //k is bigger than our current list size matlb pakka somewhere in the last k elements a 0 had come nd wiped the list.. so answer has to be 0
        }
        if (k == prod.size()) {
            return prod.get(k - 1); //exact same size as list so we want product of ALL elemnts currently in list.. no need to divide anything (otherwise size-k-1 becomes -1 and crashes).. just return last element directly
        }
        return prod.get(prod.size() - 1) / prod.get(prod.size() - k - 1); //total product at very end divided by prefix product right before the last k elemnts.. eg [3,6,12,36] size=4 k=2 -> prod[3]/prod[1] = 36/6 = 6.. gives exact last k product in O(1)
    }
}

//hbruteeeeeeee
/*
class ProductOfNumbers {
    // brute force: just do literally what the problem describes! keep a normal ArrayList storing every number as it arrives in O(1). whenever getProduct(k) is called, start a loop from index (n - k) all the way to (n - 1) and multiply those last k numbers together! the constraints are small enough (at most 4 * 10^4 calls) that this actually passes on leetcode, though an interviewer will definitely ask for the O(1) prefix product follow-up above! 
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
        // start at (n - k) so we multiply exacty the last k elements of the list 
        for (int i = n - k; i < n; i++) {//n-k matlb.... agar k 2 hai.. then start from 2 elements before4 from last(n).. nd go tilll n
            prod *= nums.get(i);
        }
        return prod;
    }
}*/