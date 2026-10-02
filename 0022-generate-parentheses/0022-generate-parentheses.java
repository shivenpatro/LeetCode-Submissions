//simple backtracking question
class Solution {
    List<String> res = new ArrayList<>();
    public List<String> generateParenthesis(int n) {
        StringBuilder sb = new StringBuilder();
        // start with empty string, need to build up to 2*n length
        solve(sb, n);
        return res;
    }
    private void solve(StringBuilder sb, int n) {
        // base case: length hit 2*n, formed one full combo
        if (sb.length() == 2 * n) {
            // brute force check if whatever we built is even balanced
            if (isValid(sb.toString())) {
                res.add(sb.toString());
            }
            return;
        }
        // choice 1: throw in '('
        sb.append('(');
        solve(sb, n); // explore
        sb.deleteCharAt(sb.length() - 1); // backtrack / undo
        // choice 2: throw in ')'
        sb.append(')');
        solve(sb, n); // explore
        sb.deleteCharAt(sb.length() - 1); // backtrack / undo
    }
    private boolean isValid(String s) {
        int count = 0;
        for (char ch : s.toCharArray()) {
            if (ch == '(') count++;
            else count--;
            // at any point if ')' beats '(', instantly invalid
            if (count < 0) return false;
        }
        // count must end at clean 0 to be properly closed
        return count == 0;
    }
}