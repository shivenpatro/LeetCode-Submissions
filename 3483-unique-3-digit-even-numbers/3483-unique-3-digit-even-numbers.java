//optimal recursive approach
/*
====================================================================================================
DETAILED DRY-RUN & EXECUTION TRACE
====================================================================================================
Problem: Count total unique 3-digit even numbers (LeetCode 3483 / totalNumbers)
Example Input: digits = [2, 1, 2, 0]

INITIAL SETUP:
- Count frequencies into mp array of size 10:
    mp[0] = 1
    mp[1] = 1
    mp[2] = 2
    mp[3..9] = 0
- count variable starts at 0

----------------------------------------------------------------------------------------------------
CALL 1: backtrack(mp, depth = 0)  --> HUNDREDS PLACE
Rules: start = 1, end = 9, step = 1 (No leading zeros allowed!)
----------------------------------------------------------------------------------------------------
  * Trying d = 1:
      - mp[1] is 1 (> 0), so we can pick it!
      - DO:      mp[1]--  (mp[1] becomes 0)
      - EXPLORE: Call backtrack(mp, depth = 1)
                 |
                 |--- CALL 2: backtrack(mp, depth = 1)  --> TENS PLACE
                 |    Rules: start = 0, end = 9, step = 1 (Any digit 0-9 allowed)
                 |    Available pool right now: mp[0]=1, mp[1]=0, mp[2]=2
                 |
                 |    * Trying d = 0:
                 |        - mp[0] is 1 (> 0), valid!
                 |        - DO:      mp[0]--  (mp[0] becomes 0)
                 |        - EXPLORE: Call backtrack(mp, depth = 2)
                 |                   |
                 |                   |--- CALL 3: backtrack(mp, depth = 2)  --> UNITS PLACE
                 |                   |    Rules: start = 0, end = 8, step = 2 (EVEN ONLY: 0, 2, 4, 6, 8)
                 |                   |    Available pool: mp[0]=0, mp[1]=0, mp[2]=2
                 |                   |
                 |                   |    * Trying d = 0: mp[0] == 0  --> SKIP
                 |                   |    * Trying d = 2: mp[2] == 2 (> 0)  --> VALID!
                 |                   |        - DO:      mp[2]--  (mp[2] becomes 1)
                 |                   |        - EXPLORE: Call backtrack(mp, depth = 3)
                 |                   |                   --> BASE CASE HIT (depth == 3)!
                 |                   |                   --> Formed number: 102
                 |                   |                   --> count++ (count is now 1)
                 |                   |                   --> Return back
                 |                   |        - UNDO:    mp[2]++  (mp[2] restored to 2)
                 |                   |    * Trying d = 4, 6, 8: All counts are 0  --> SKIP
                 |                   |    [CALL 3 FINISHES]
                 |                   |
                 |        - UNDO:    mp[0]++  (mp[0] restored to 1)
                 |
                 |    * Trying d = 1: mp[1] == 0  --> SKIP
                 |
                 |    * Trying d = 2:
                 |        - mp[2] is 2 (> 0), valid!
                 |        - DO:      mp[2]--  (mp[2] becomes 1)
                 |        - EXPLORE: Call backtrack(mp, depth = 2)
                 |                   |
                 |                   |--- CALL 4: backtrack(mp, depth = 2)  --> UNITS PLACE
                 |                   |    Rules: start = 0, end = 8, step = 2 (EVEN ONLY)
                 |                   |    Available pool: mp[0]=1, mp[1]=0, mp[2]=1
                 |                   |
                 |                   |    * Trying d = 0: mp[0] == 1 (> 0)  --> VALID!
                 |                   |        - DO:      mp[0]--  (mp[0] becomes 0)
                 |                   |        - EXPLORE: Call backtrack(mp, depth = 3)
                 |                   |                   --> BASE CASE HIT (depth == 3)!
                 |                   |                   --> Formed number: 120
                 |                   |                   --> count++ (count is now 2)
                 |                   |                   --> Return back
                 |                   |        - UNDO:    mp[0]++  (mp[0] restored to 1)
                 |                   |
                 |                   |    * Trying d = 2: mp[2] == 1 (> 0)  --> VALID!
                 |                   |        - DO:      mp[2]--  (mp[2] becomes 0)
                 |                   |        - EXPLORE: Call backtrack(mp, depth = 3)
                 |                   |                   --> BASE CASE HIT (depth == 3)!
                 |                   |                   --> Formed number: 122
                 |                   |                   --> count++ (count is now 3)
                 |                   |                   --> Return back
                 |                   |        - UNDO:    mp[2]++  (mp[2] restored to 1)
                 |                   |
                 |                   |    * Trying d = 4, 6, 8: All counts are 0  --> SKIP
                 |                   |    [CALL 4 FINISHES]
                 |                   |
                 |        - UNDO:    mp[2]++  (mp[2] restored to 2)
                 |
                 |    * Trying d = 3..9: All counts are 0  --> SKIP
                 |    [CALL 2 FINISHES]
                 |
      - UNDO:    mp[1]++  (mp[1] restored to 1)

----------------------------------------------------------------------------------------------------
CONTINUING CALL 1 (depth = 0):
Pool is fully restored: mp[0]=1, mp[1]=1, mp[2]=2
----------------------------------------------------------------------------------------------------
  * Trying d = 2:
      - mp[2] is 2 (> 0), valid!
      - DO:      mp[2]--  (mp[2] becomes 1)
      - EXPLORE: Call backtrack(mp, depth = 1)
                 |
                 |--- Explores tens digit d = 0:
                 |      - Units digit d = 2 works --> forms 202 --> count++ (count is 4)
                 |
                 |--- Explores tens digit d = 1:
                 |      - Units digit d = 0 works --> forms 210 --> count++ (count is 5)
                 |      - Units digit d = 2 works --> forms 212 --> count++ (count is 6)
                 |
                 |--- Explores tens digit d = 2 (mp[2] still has 1 copy left):
                 |      - Units digit d = 0 works --> forms 220 --> count++ (count is 7)
                 |
      - UNDO:    mp[2]++  (mp[2] restored to 2)

  * Trying d = 3..9: All counts are 0  --> SKIP

FINAL COUNT RETURNED = 7 (numbers formed: 102, 120, 122, 202, 210, 212, 220)
====================================================================================================
*/

class Solution {
    // Tracks the total number of unique 3-digit even numbers formed
    private int count = 0;

    public int totalNumbers(int[] digits) {
        // Reset the global counter on every function call
        count = 0;

        // Step 1: Count frequency of each digit (0 to 9) from the input array
        int[] mp = new int[10];
        for (int digit : digits) {
            mp[digit]++;
        }

        // Step 2: Start backtracking from depth 0 (hundreds place)
        backtrack(mp, 0);

        return count;
    }

    private void backtrack(int[] mp, int depth) {
        // BASE CASE:
        // When depth reaches 3, all three places (hundreds, tens, units) have been validly picked.
        if (depth == 3) {
            count++;
            return;
        }

        int start;
        int end;
        int step;

        if (depth == 0) {
            // HUNDREDS PLACE:
            // Cannot start with 0 (no leading zeros allowed in a 3-digit number).
            start = 1;
            end = 9;
            step = 1;
        } else if (depth == 1) {
            // TENS PLACE:
            // Middle digit can be any value from 0 to 9.
            start = 0;
            end = 9;
            step = 1;
        } else {
            // UNITS / ONES PLACE (depth == 2):
            // The number must be even, so units place must strictly be 0, 2, 4, 6, or 8.
            start = 0;
            end = 8;
            step = 2;
        }

        // Iterate through all valid candidates for the current position
        for (int d = start; d <= end; d += step) {
            // If digit 'd' has no available instances in our pool, skip it
            if (mp[d] == 0) {
                continue;
            }

            // 1. DO / CHOOSE: consume one copy of digit 'd'
            mp[d]--;

            // 2. EXPLORE: proceed to pick the next place value
            backtrack(mp, depth + 1);

            // 3. UNDO / BACKTRACK: put the digit back into the pool for alternative branches
            mp[d]++;
        }
    }
}

/*
//optimal(iterative approach)
class Solution{
    public int totalNumbers(int[] digits){
        int map[] = new int[10];//to store the count of all the numbers from 0 to 9
        for(int digit : digits){//we are storing the frequency of all the numbers in this frequency array
            map[digit]++;
        }
        int count = 0;
        for(int i = 1; i<=9;i++){//100s digit, cant be 0 so start from 1
            if(map[i]==0) continue;//because no frequency so skip it
            map[i]--;//same backtracking thing, do so we are using this so we just do -- to reduce  the frequency by 1, of that particular number

            for(int j = 0;j<=9;j++){//10s number, can be anything
                if(map[j] == 0) continue;//same reason as above
                map[j]--;//backtrack do state

                for(int k = 0; k<=8; k=k+2){//ones number, hsa to be even so +2
                    if(map[k] == 0) continue; //this simply means that, the frequency of the number where the k is at index, is 0, that means obv continue because the number only doesnt exist
                    map[k]--;//this is backtracking in iterative, the template of do, check and restore works, we DO, SO WE MINUS BECAUSE WE USED THAT NUMBER ONCE, SO REDUCE THE FREQUENCY

                    count++;//this is handling the count right, so its just the count of 3 digit even numbers, if the loop has reaeched to this stage that means this is a 3 digit even number only, orelse it wont enter the k loop

                    map[k]++;//so see, we used that digit at k place to form the number na?, so like do and explore stage is done, now REVERT!!, SO WE HAVE TO ADD THE FREQUENCY BACK, WHICH MEANS SO THAT THIS DIGIT CAN BE USED FOR OTHER COMBINATIONS NA!!, so we inc back the frequeuncy by 1, revert back, classic backtrack thats all
                }
                map[j]++;//same backtrack thing, for this digit at this state
            }
            map[i]++;//same for this digit as well, class simple backtracking thats all
        }
        return count;
    }
}
*/
/*
//brute force
class Solution {
    public int totalNumbers(int[] digits) {
        int n = digits.length;
        Set<Integer> uniqueNums = new HashSet<>();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                for (int k = 0; k < n; k++) {
                    if (i == j || j == k || i == k) {
                        continue;//this is done, because think, if i or j or k is at the same number or same index, then just skip because it will lead to using the number at same index multiple times, repetition of number is not allowed, as written in questino, "once per number", so ya
                    }
                    int num = digits[i] * 100 + digits[j] * 10 + digits[k];
                    // 3-digit number (no leading zero) and even
                    if (num >= 100 && num % 2 == 0) {
                        uniqueNums.add(num);//very very easy brute force, use 3 pointers, traverse every possible number, check this condition for 3 digit and even, and if yes add in the sete to prevent duplicacy and print the size
                    }
                }
            }
        }
        return uniqueNums.size();
    }
}*/