//stack approach
class Solution {
    public int longestValidParentheses(String s) {
        Deque<Integer> stack = new ArrayDeque<>();
        // Push -1 as the initial boundary/sentinel index
        stack.push(-1);
        int maxLen = 0;
        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);

            if (ch == '(') {
                stack.push(i);
            } else {
                stack.pop();
                if (stack.isEmpty()) {
                    // No matching '(': this index becomes the new boundary
                    stack.push(i);
                } else {
                    // Valid substring spans from (stack.peek() + 1) to i
                    maxLen = Math.max(maxLen, i - stack.peek());
                }
            }
        }
        return maxLen;
    }
}
/*
//open close counter approach
class Solution {
    public int longestValidParentheses(String s) {
        int n = s.length();
        int open = 0, close = 0;//counter for open and close brakcets
        int result = 0;//this will store the longest valid parenthesis length na.. so ya..max
        //1. first we will do left to right pass, check left to right first then right to left pass so that every valid set is recorded
        for(int i = 0;i<n; i++){
            char c = s.charAt(i);
            if(c == '('){
                open++;
            }else close++;//increasing the open or close count
            if(open == close){
                result = Math.max(result, open + close);//so see, what we are doping is we are storing the max longest length na, thats what we want to find, so see... we store that open + close in result, and when next time another open + close comes.. we check if that is greater than earlier.. and continue and find the longest valid parentheses,  if it was ())()... then for first open = 1 and close = 1 happens and result stores 2.. then reset because closing came,.. and close>open executes.. which means that close can never be balanced.. so after that again open 1 and close 1.. so again 2 comes and final answer is 2... if ex was ()())()...answer would be 4 here.. because first open 1 close 1... result = 2... then open will become 2 and close becomes 2.. because nothing breaks the sequence...the longest valid should be in "SEQUENCE"
            }
            if(open>close) continue;//this means.. there are more open than close na.. since its open, we can wait for more close to come when we go from left to right, so we jsut continue
            if(close>open) {
                open = 0;
                close = 0;//so see see, if close>open.. we are going from left to right na...  if a close came, that means a opening can never balance this close na!! because a opening after this close can never balance it.. so no pt of continuing the old count of sequence, reset both counter
            }
        }
        open = 0;
        close = 0;//once we are done with left to right, we need to do right to left so we reset, and again we do both traversal because, to protect from cases like, if test case is  ((), then see open will be 1 then 2.. and then close is 1.. so see.. answer shouldhv been 2.. but answer will be 0 because open == close NEVER HAPPENED!!..this case is saved by right to left traversal.. and exact opposite for ()).. this is saved  by left to right traversal
        //2. right to left traversal now, to cover the edge cases like ((), as explained above
        for(int i = n-1; i>=0 ; i--){
            char c = s.charAt(i);
            if (c == '(') {
                open++;
            } else {
                close++;//exact same as above traversal
            }
            if (open == close) {
                result = Math.max(result, open + close);//again see above comments
            }
            if(close>open) continue;//btw u can remove this conditoin itslef because it dpoesnt do a shit but i added for understanding
            if(open>close) {
                open = 0;
                close = 0;//same as left to right now.. if more open that means no close  cnan balance this open one
            }
        }
        return result;
    }
}*/