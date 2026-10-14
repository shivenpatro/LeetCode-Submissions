class Solution {
    public String maxSumOfSquares(int num, int sum) {
        // CORNER / IMPOSSIBILITY CHECK:
        // think simply... we need a number having EXACTLY 'num' digits...
        // what is the absolute maximum digit sum we can ever achieve with 'num' digits?
        // if every single digit is 9! which gives num * 9!
        // so if the required 'sum' is strictly greater than num * 9, 
        // it is physically impossible to form such a number! return empty string "" immediately!
        // (casting to long to avoid any potential integer overflow)
        if ((long) num * 9 < sum) {
            return "";
        }
        
        // now see... why greedy works here?
        // remember the math proof from LeetCode 2897:
        // when we want to maximize sum of squares (a^2 + b^2), 
        // making the larger number even LARGER gives a much bigger jump than keeping numbers balanced!
        // e.g., 9^2 + 3^2 = 81 + 9 = 90
        // while 6^2 + 6^2 = 36 + 36 = 72
        // so we greedily want as many 9's as possible!
        // and because we want the largest number in case of ties, 
        // all those 9's should sit at the most significant positions (leftmost side)!
        
        int nines = sum / 9; // how many complete 9's we can squeeze out from sum
        int rem = sum % 9;   // the leftover digit after using all possible 9's
        
        StringBuilder result = new StringBuilder();
        
        // Step 1: append all the 9's first
        for (int i = 0; i < nines; i++) {
            result.append('9');
        }
        
        // Step 2: if there's any leftover remainder (from 1 to 8), append it right after the 9's
        if (rem > 0) {
            result.append(rem);
        }
        
        // Step 3: fill the remaining positions with '0' to satisfy the exact 'num' digits requirement!
        // see... putting 0's at the end keeps the number as large as possible,
        // and 0^2 contributes 0 so it doesn't mess with our sum of squares!
        while (result.length() < num) {
            result.append('0');
        }
        
        return result.toString();
    }
}