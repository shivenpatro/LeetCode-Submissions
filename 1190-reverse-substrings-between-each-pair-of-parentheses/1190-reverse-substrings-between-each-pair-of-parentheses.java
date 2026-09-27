//optimal
// now the OPTIMAL O(n) solution using the "Wormhole / Teleportation" technique!!
// WHY DOES THIS WORK?
// think about what happens when you reverse a word inside brackets:
// normally you read left-to-right (L -> R)..
// but the moment you hit '(', instead of reading the inside characters straight, you should read them backwards (R -> L)!!
// and where does the backward reading start from? from its matching CLOSING bracket ')'!!
// so:
// 1. whenever you hit '(', teleport directly to its matching ')' and flip your walking direction!!
// 2. whenever you hit ')', teleport directly to its matching '(' and flip your walking direction!!
// 3. regular characters are just printed as you walk past them!!
//
// STEP-BY-STEP GAME PLAN (APPROACH 2):
// 1. FIRST PASS (Precompute matching bracket pairs):
//    - we use an array door[] of size n.
//    - run a loop over s:
//      * if '(', push its index into a stack.
//      * if ')', pop the opening index 'j'.
//      * create a two-way teleportation door: door[i] = j and door[j] = i!!
// 2. SECOND PASS (Walk, Teleport & Collect):
//    - start pointer i = 0 with direction flag = 1 (moving left-to-right).
//    - while i is within bounds (0 <= i < n):
//      * if s.charAt(i) is '(' or ')':
//          - teleport: i = door[i]!! (jump instantly to the partner bracket!)
//          - flip direction: flag = -flag!! (if going forward, now go backward; if backward, go forward!)
//      * else (it's a regular letter):
//          - append s.charAt(i) to our result StringBuilder!!
//      * move i forward by direction: i += flag!!
// 3. Return result.toString()!! Linear O(n) time, no repeated string reversals!!
class Solution {
    public String reverseParentheses(String s) {
        int n = s.length();// length of string s
        int[] door = new int[n]; // so see this is like doraemon's anywhere door array, door[i] will store where you land when you jump from index i.. basically matching index of bracket at index i
        Stack<Integer> openBrackets = new Stack<>(); // stack to store the indices of open brackets so that whenever a closing bracket comes, we can map both to each other
        // Step 1: first pass to link every '(' to its matching ')' and vice-versa (two-way teleportation door)
        for (int i = 0; i < n; i++) {
            char ch = s.charAt(i);
            if (ch == '(') {
                // found opening bracket, so push its index to stack to match with future closing bracket
                openBrackets.push(i);
            } else if (ch == ')') {
                // found closing bracket, pop the top of stack which is the nearest matching open bracket index
                int j = openBrackets.pop();
                door[i] = j; // from ')' at index i we can jump to '(' at index j
                door[j] = i; // from '(' at index j we can jump to ')' at index i.. two way mapping done simple!
            }
        }
        StringBuilder result = new StringBuilder(); // this will build our final answer string without brackets
        int flag = 1; // direction flag: +1 means we are walking left-to-right, -1 means right-to-left
        int i = 0; // our moving pointer
        // Step 2: second pass.. walk through the string and whenever bracket comes, teleport and flip direction!
        while (i >= 0 && i < n) {
            char ch = s.charAt(i);
            if (ch == '(' || ch == ')') {
                // see see, whenever we hit ANY bracket (either open or close), we need to jump to its partner bracket!
                i = door[i];   // teleport directly to the matching bracket index using our door array!
                flag = -flag;  // flip direction!! if we were going forward (+1), now go backward (-1).. if backward, now forward! because entering/leaving bracket inverts the reading order!
            } else {
                // regular lowercase english letter, not a bracket, so blindly add to result
                result.append(ch);
            }
            i += flag; // move pointer one step in the CURRENT direction (if flag is +1 it does i++, if flag is -1 it does i--)
        }
        return result.toString(); // all brackets handled and proper reversed string collected in O(n) time!
    }
}

/*
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
}*/