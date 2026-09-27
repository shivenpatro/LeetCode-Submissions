//brute approach for O(n2)
// so basically what we are doing here is:
// we are given a string 's' with lowercase english letters and parentheses like "(u(love)i)"!!
// we have to reverse the characters inside every matching pair of parentheses, starting from innermost!!
// and the final answer should NOT have any parentheses at all!!
// STEP-BY-STEP GAME PLAN (APPROACH 1):
// 1. We maintain a StringBuilder 'result' where we append characters as we walk along string 's'!!
// 2. But wait! When an opening bracket '(' appears, we need to know:
//    "How many characters did we ALREADY put into 'result' before this bracket opened?"
//    Because whatever characters were written before '(' do NOT belong to this bracket's reversal!!
// 3. So we push result.length() into our Stack<Integer> 'lastSkipLength' whenever we see '('!!
//    - This integer tells us: "Hey, when the matching ')' comes, SKIP the first 'L' characters of result,
//      and ONLY reverse from index 'L' to the end of result!!"
// 4. When a closing bracket ')' appears:
//    - Pop 'L' from stack (LIFO: innermost bracket's skip length comes out first!!).
//    - Reverse the substring in 'result' from index 'L' to result.length() - 1!!
// 5. If it's a regular lowercase letter:
//    - Just append it directly into 'result'!!
// 6. Return result.toString()!!

class Solution{
    public String reverseParentheses(String s) {
        StringBuilder result = new StringBuilder(); // builds our result string on the fly
        Stack<Integer> lastSkipLength = new Stack<>(); // stores how many characters to skip before reversing
        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            if (ch == '(') {
                // bracket opens: store current length of result in stack so we know where this bracket's content starts
                lastSkipLength.push(result.length());
            } else if (ch == ')') {
                // bracket closes: pop the skip length L
                int l = lastSkipLength.pop();
                // reverse result from index 'l' to result.length() - 1
                reverse(result, l, result.length() - 1);
            } else {
                // regular character, blindly append to result
                result.append(ch);
            }
        }
        return result.toString();
    }
    // helper function to reverse a section of StringBuilder between start and end indices
    private void reverse(StringBuilder sb, int start, int end) {
        while (start < end) {
            char temp = sb.charAt(start);
            sb.setCharAt(start, sb.charAt(end));
            sb.setCharAt(end, temp);
            start++;
            end--;
        }
    }
}