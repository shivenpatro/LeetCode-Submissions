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
}