class Solution {
    public int scoreOfParentheses(String s) {
        int n = s.length();
        
        // THIS APPROACH IS PURE OBSERVATION AND RUNS IN O(1) AUXILIARY SPACE!
        // see... think carefully: where does the actual raw score even come from?
        // it ONLY comes from the innermost "()" pairs!
        // all other outer brackets are literally just multipliers of 2!
        //
        // just trace it:
        // "()"       -> score is 1, which is 2^0
        // "(())"     -> score is 2 * 1 = 2, which is 2^1
        // "((()))"   -> score is 2 * (2 * 1) = 4, which is 2^2
        //
        // so see the pattern... if a basic core pair "()" is enclosed inside 'd' outer layers of brackets,
        // its total contribution to the final sum is simply 2^d!
        // and instead of doing Math.pow(2, d) which is slow and floating point...
        // we can just use bitwise left shift: (1 << d) which gives 2^d directly in O(1)!
        
        int score = 0;//stores total accumulated score
        int depth = 0;//tracks how many levels deep inside parentheses we currently are
        
        for (int i = 0; i < n; i++) {
            char ch = s.charAt(i);
            
            if (ch == '(') {
                // whenever we see '(', we are stepping one level deeper inside... so depth++
                depth++;
            } else {
                // whenever we see ')', one level is ending... so decrement depth first
                depth--;
                
                // now this is the main trick:
                // check if this closing bracket immediately follows an opening bracket!
                // if s.charAt(i - 1) == '(', it means we just completed an atomic core "()" pair!
                if (s.charAt(i - 1) == '(') {
                    // since we already did depth-- above, 'depth' right now represents exactly how many
                    // outer brackets are enclosing this specific "()" pair!
                    // so we directly add 2^depth to our answer using bit shift (1 << depth)
                    score += (1 << depth);
                }
                // and see... if s.charAt(i - 1) was ')', we do NOTHING to score!
                // why? because that was just an outer bracket closing around an already accounted block...
                // its doubling effect was already naturally included in the 2^depth calculation of the inner pairs!
            }
        }
        
        return score;//final total score
    }
}

/*class Solution {
    public int scoreOfParentheses(String s) {
        int n = s.length();//length of the string
        // stack here because as we go deeper into nested brackets, we need to save whatever score
        // we accumulated at the current outer level before diving inside...
        // and once that inner part finishes calculating, we pop that outer score back and combine them simple
        Stack<Integer> st = new Stack<>();
        int score = 0;//this will maintain the score of the current level/block we are currently exploring
        for (int i = 0; i < n; i++) {
            char ch = s.charAt(i);
            if (ch == '(') {
                // see... whenever we see an opening bracket, it means a brand new frame or nested level is starting!
                // so whatever score we had computed till now at the current level... we must push it into the stack to save it!
                // and then reset score to 0 so this new inner subproblem can start calculating from fresh 0
                st.push(score);
                score = 0;
            } else {
                // else branch means we found a closing bracket ')'
                // now there are 2 possibilities according to the problem statement:
                // Case 1: is it the simplest base unit "()"?
                // Case 2: or is it wrapping a whole nested block "(A)"?
                
                if (s.charAt(i - 1) == '(') {
                    // CASE 1: BASE CORE UNIT "()"!
                    // see... if current is ')' and just previous at i-1 was '('... that means this is directly "()"!
                    // problem clearly says "()" has an exact score of 1.
                    // so whatever score was sitting on top of the stack from before... we pop it and just add +1 to it!
                    score = st.pop() + 1;
                } else {
                    // CASE 2: NESTED STRUCTURE "(A)"!
                    // see... if i-1 was also ')', that means we just finished closing an entire nested block inside!
                    // and rule 3 says "(A)" has a score of 2 * score(A)...
                    // so the inner score that was accumulated gets multiplied by 2...
                    // and then we pop whatever was waiting outside in the stack before this block started and add them up!
                    score = st.pop() + 2 * score;
                }
            }
        }
        // by the time the loop ends, all levels are popped and combined into score... so return it
        return score;
    }
}*/