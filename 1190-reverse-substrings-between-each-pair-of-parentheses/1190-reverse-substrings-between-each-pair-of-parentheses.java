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
        StringBuilder result = new StringBuilder(); // builds our result string on the fly, this is literally the final result string after all the reversals
        Stack<Integer> lastSkipLength = new Stack<>(); // now see, this will keep the track of all the open bracket locations like where where they were found(EXCLUDING PREV BRACKETS OBV), like that, so how it will do is,... from left to right we keep pushing to the result string, and the moment we reach a opening bracket, we push the result.length at that exact point to the stack, WHICH IMPLY  KEEPS TRACK HOW MANY CHARACTERS ARE THERE BEFORE THAT EXACT OPENING BRACKET, so that we can later reverse the string from begin(0 or literal start value)+this value(THIS BRING THE START POINT TO OPNE BRACK) to end(where closing bracket was found), and thus that substring will get reversed
        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            if (ch == '(') {
                // bracket opens: store current length of result in stack so we know where this bracket's content starts
                lastSkipLength.push(result.length());//this  is the length of number of characters before this opening brackets(characters  means only alphabets no other brackets obv)
            } else if (ch == ')') {
                // bracket closes: pop the skip length L
                int l = lastSkipLength.pop();//because on the top of the stack, this value will represent how much far from 0 is the beginning of the opening bracket.. for this exact closing bracket where ch==) became true... then we call the reverse funciton we have made(java doesnt have one.. to go from one custom index to another.. stringbuilder reverse() reverse the whole stringbuilder so ya)
                // reverse result from index 'l' to result.length() - 1
                reverse(result, l, result.length() - 1);
            } else {
                // regular character, blindly append to result
                result.append(ch);//simple  appending.. keep on going 
            }
        }
        return result.toString();//final answer
    }
    // helper function to reverse a section of StringBuilder between start and end indices
    private void reverse(StringBuilder sb, int start, int end) {
        while (start < end) {//basic reverse algo
            char temp = sb.charAt(start);
            sb.setCharAt(start, sb.charAt(end));//we use setCharAt... to reverse and set the characters.. like setCharAt start with setCharAt end
            sb.setCharAt(end, temp);//and setCharAt  end with temp(which has setCharAt start)
            start++;
            end--;
        }
    }
}