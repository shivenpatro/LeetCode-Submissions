//this is a not so classic backtracking question,how? because see.. we remove can either add one bracket or remove one bracket..list either we take or not take scenario na... so classic recursion/backtracking question...and the template of
//1. do 2. explore 3. undo
//also the 3rd point in the contraints is very imp here,... its written max of 20 parentheses will be there that means what na ki... ITS A HINT BROTHER...HOW? BECAUSE...we can weither take or not take... so 2^n.. and obv we dc about other characters... so 2^20... which is 10^6 approx... well inside the time limit.. so it works!! hence we can apply the backtrack approch
//and also if u think its a classic take not tkae... shuoldnt it be DP...WELL IT CAN BE BRO BUT BUT...the constraits are enough to handle the recursion way(backtracking)... so we never need to optimize to DP!
//why this is pure backtrack and not dp,dp works when you are asked for an aggregated scalar value: min removals, number of ways, true/false (is it possible?), or finding one optimal path.But this problem asks you to generate and return ALL unique valid strings!When you need to build and return all actual concrete strings, you must traverse and build every valid combination, and als here the overlapping subproblem doesnt come muc which is core of dp,, ath a might build ((), and path b can (a(... so no resusable state as such for dp
class Solution {
    int n; // total length of the original string
    int maxLen; // tracks the longest valid string length found so far (since max length = minimum removals!)
    HashSet<String> result; // set to automatically handle and store only UNIQUE strings
    public List<String> removeInvalidParentheses(String s) {
        n = s.length();
        maxLen = 0; // reset max length
        result = new HashSet<>(); // reset result set for fresh function call
        StringBuilder current = new StringBuilder(); // string builder to efficiently append and pop characters
        // start backtracking from index 0 with initial openCount = 0
        solve(0, s, current, 0);
        // onvert our unique set of max-length strings to a list and return
        return new ArrayList<>(result);
    }
    private void solve(int i, String s, StringBuilder current, int count) {
        // EARLY PRUNING / DEAD-END CHECK:
        // see... count tracks (open brackets - close brackets) in the current prefix...
        // if at ANY point count becomes negative (< 0), it means we have more ')' than '('!
        // and because brackets can never match backwards, there is literally NO way any future character
        // can save this string... so this entire branch is permanently dead! return immediately!
        if (count < 0) {
            return;
        }
        // BASE CASE: when we have processed/explored all characters up to index n
        if (i == n) {
            // we reached the end... but we can ONLY consider this string if it is fully balanced (count == 0) na
            if (count == 0) {
                int currLen = current.length();
                // now remember the core problem condition: "MINIMUM NUMBER OF REMOVALS"!
                // minimum removals literally means MAXIMUM resulting string length!
                if (currLen > maxLen) {
                    // if we found a string whose length is strictly GREATER than maxLen...
                    // that means all our previous answers in the set had MORE removals than this new one!
                    // so all those previous answers are now obsolete/inferior!
                    maxLen = currLen; // update maxLen to this new best length
                    result.clear(); // wipe out all older, shorter strings!
                    result.add(current.toString()); // add this new king string
                } else if (currLen == maxLen) {
                    // if this string has the EXACT same length as our current best maxLen...
                    // that means it used the same minimum number of removals!
                    // so we just add it to our set (set handles duplicates automatically)
                    result.add(current.toString());
                }
            }
            return;
        }
        char ch = s.charAt(i);
        // CASE 1: THE CHARACTER IS AN ALPHABET ('a' through 'z')
        // letters have nothing to do with parentheses balance, and the question says we only remove parentheses!
        // so we HAVE to take every letter, no take/not-take choice here simple!
        if (ch != '(' && ch != ')') {
            current.append(ch); // TAKE the letter
            solve(i + 1, s, current, count); // explore next index (count doesn't change)
            current.deleteCharAt(current.length() - 1); // UNDO / BACKTRACK (pop the letter)
            return;
        }
        // CASE 2: THE CHARACTER IS A PARENTHESIS ('(' or ')')
        // now we have 2 choices: either TAKE it into our string, or NOT TAKE (remove) it!
        // --- CHOICE A: TAKE IT ---
        current.append(ch);
        int nextCount = count + (ch == '(' ? 1 : -1);
        solve(i + 1, s, current, nextCount); // explore with the bracket included
        current.deleteCharAt(current.length() - 1); // UNDO / BACKTRACK for Choice B
        // --- CHOICE B: DO NOT TAKE IT (REMOVE IT) ---
        // we skip adding 'ch' to current, so 'count' remains unchanged as we move to i + 1
        solve(i + 1, s, current, count);
    }
}