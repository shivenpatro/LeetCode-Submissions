//this is my method,+2 -1 way
/*
class Solution {
    public int minInsertions(String s) {
        int res = 0;//tracks total insertions needed
        int needed = 0;//tracks how many ')' closing brackets are currently needed
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == '(') {
                // now see... if we find '(' but 'needed' is ODD...
                // that means an earlier ')' was left hanging alone as an incomplete pair!
                // because brackets MUST come in pairs of 2!
                // so we must immediately insert one ')' right now to fix that previous pair, so res++ and needed--!
                if (needed % 2 != 0) {
                    res++;
                    needed--;
                }
                // and for this current '('... each '(' requires 2 closing brackets! so needed += 2!
                // and see to answer your question: if needed == 0 && c == '(', we NEVER do res++ here!
                // because it's just opening a new bracket, not creating any violation!
                needed += 2;
            } else {
                // c == ')'! a closing bracket arrived, so one requirement is met, hence needed--!
                needed--;
                // if needed drops below 0 (meaning needed == -1)...
                // that means a ')' arrived when NO '(' was expecting it!
                // so we MUST insert a '(' to save it! inserting '(' adds 2 to needed...
                // but since this current ')' consumed 1 of them... (-1 + 2) leaves needed = 1!
                // and we did 1 insertion for '(', so res++!
                if (needed < 0) {
                    res++;
                    needed += 2;
                }
            }
        }
        // at the end, whatever needed ')' are still pending must be inserted, so just return res + needed!
        return res + needed;
    }
}*/

class Solution {
    public int minInsertions(String s) {
        int n = s.length();//length of string
        int count = 0;//this will maintain the number of unmatched open brackets '(' we have seen so far
        int ans = 0;//total minimum insertions needed
        int i = 0;//pointer to traverse the string
        while (i < n) {
            char ch = s.charAt(i);
            if (ch == '(') {
                // see... whenever we find '(', we simply record it in count!
                // because according to problem statement... each '(' needs TWO consecutive ')' later on to get balanced
                count++;
                i++;
            } else {
                // ch == ')'! this means we encountered a closing bracket!
                // first thing to check: do we have any '(' waiting before this?
                if (count > 0) {
                    // yes! we have an open bracket, so this closing bracket starts balancing it...
                    // so we decrement count by 1!
                    count--;
                } else {
                    // count is 0! meaning there is NO '(' before this to defend or balance this closing sequence!
                    // so we MUST insert one '(' before this! hence ans++!
                    ans++;
                }
                // now remember the core rule: ONE '(' needs TWO consecutive '))'!
                // so we MUST check if the next character at i + 1 is ALSO ')'!
                if (i + 1 < n && s.charAt(i + 1) == ')') {
                    // YES! we found two consecutive closing brackets "))"!
                    // so both closing brackets are consumed together to balance the '('!
                    // we skip both characters by jumping i by 2!
                    i += 2;
                } else {
                    // NO! the next character is either '(' or we reached the end of the string!
                    // meaning we only found a SINGLE ')'!
                    // but we needed a pair of "))", so we MUST insert one extra ')' to complete the pair!
                    ans++;
                    // and since we only consumed the single ')' at index i, move pointer by 1
                    i++;
                }
            }
        }
        // now see... what if after the whole loop, count is still > 0?
        // that means some '(' were left open with NO closing brackets after them at all!
        // since every single '(' needs TWO ')', for each leftover '(' we must insert 2 closing brackets!
        // so we add count * 2 to ans!
        return ans + (count * 2);
    }
}