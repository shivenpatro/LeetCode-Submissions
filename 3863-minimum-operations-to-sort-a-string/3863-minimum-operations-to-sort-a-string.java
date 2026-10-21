class Solution {
    public int minOperations(String s) {
        int n = s.length();//length of the string
        // see... first condition check:
        // what if the string is ALREADY sorted?
        // then obviously 0 operations needed na!
        boolean alreadySorted = true;
        for (int i = 1; i < n; i++) {
            if (s.charAt(i) < s.charAt(i - 1)) {
                alreadySorted = false;
                break;
            }
        }
        if (alreadySorted) {
            return 0;//already in non-decreasing order!
        }
        // now see... why is n == 2 an impossible edge case?
        // problem clearly says:
        // "you can select ANY substring of s that is NOT the entire string"!
        // meaning the substring length MUST be strictly less than n (length < n)!
        // if n == 2 and the string is not sorted (like "ba")...
        // the only allowed substrings are of length 1 ("b" or "a")!
        // sorting a 1-character substring does literally nothing!
        // you can never ever swap or sort them... so it's physically impossible! return -1!
        if (n == 2) {
            return -1;
        }
        // find the absolute minimum and maximum characters across the whole string
        char minChar = 'z';
        char maxChar = 'a';
        for (int i = 0; i < n; i++) {
            char ch = s.charAt(i);
            if (ch < minChar) minChar = ch;
            if (ch > maxChar) maxChar = ch;
        }
        // now see... when can we sort the entire string in just 1 SINGLE operation?
        // 
        // Case 1: if the smallest character is ALREADY sitting at index 0 (s[0] == minChar)
        // then index 0 is already in its perfect final place!
        // we can simply pick the remaining substring from index 1 to n - 1 (length is n - 1 < n, which is allowed!)
        // and sort it! the entire string instantly becomes sorted!
        // 
        // Case 2: if the largest character is ALREADY sitting at index n - 1 (s[n - 1] == maxChar)
        // then the last index is already in its perfect final place!
        // we just sort the substring from index 0 to n - 2 (length n - 1 < n)... done!
        // 
        // in either case, 1 operation is enough!
        if (s.charAt(0) == minChar || s.charAt(n - 1) == maxChar) {
            return 1;
        }
        // now if 1 operation wasn't enough...
        // can it be done in 2 operations or does it need 3 operations?
        // (notice: any string can be sorted in AT MOST 3 operations!)
        // 
        // to decide between 2 and 3... look at the middle chunk: indices 1 to n - 2!
        char minMid = 'z';
        char maxMid = 'a';
        for (int i = 1; i <= n - 2; i++) {
            char ch = s.charAt(i);
            if (ch < minMid) minMid = ch;
            if (ch > maxMid) maxMid = ch;
        }
        // now think about the absolute worst-case scenario:
        // what if index 0 has the absolute maximum character (s[0] > maxMid),
        // AND index n - 1 has the absolute minimum character (s[n - 1] < minMid)?
        // like "rdca":
        // - 'r' (max) is trapped at the very beginning (index 0)
        // - 'a' (min) is trapped at the very end (index n - 1)
        // - and NEITHER of them exists anywhere in the middle chunk!
        // 
        // trace what happens:
        // step 1: we sort [0 ... n - 2] -> 'r' moves to index n - 2, but 'a' is STILL stuck at index n - 1!
        // step 2: we sort [1 ... n - 1] -> 'r' finally reaches index n - 1, and 'a' moves to index 1...
        //         but 'a' is still at index 1, not index 0!
        // step 3: we must sort [0 ... n - 2] once more to finally pull 'a' into index 0!
        // 
        // so if the max is strictly at index 0 AND min is strictly at index n - 1...
        // it requires exactly 3 operations!
        if (s.charAt(0) > maxMid && s.charAt(n - 1) < minMid) {
            return 3;
        }
        // for all other permutations, 2 operations are always enough:
        // either min or max can be ferried to its rightful end in step 1, 
        // leaving the remaining (n - 1) characters to be sorted in step 2!
        return 2;
    }
}