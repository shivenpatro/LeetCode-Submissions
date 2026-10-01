class Solution {
    public boolean isValid(String s) {
        Deque<Character> dq = new ArrayDeque<>();
        char[] str = s.toCharArray();
        for(int i = 0; i < str.length; i++) {
            if(str[i] == '(' || str[i] == '{' || str[i] == '[') {
                dq.addFirst(str[i]);//adding all the open brackets to the deque
            } else {
                if(dq.isEmpty()) { // if its empty already or haven't recieved any ( or [ or { then no point in continuing 
                    return false; 
                }
                if(str[i] == ')' && dq.peek() == '(') {
                    dq.removeFirst();
                } else if (str[i] == '}' && dq.peek() == '{') {
                    dq.removeFirst();
                } else if (str[i] == ']' && dq.peek() == '[') {
                    dq.removeFirst();
                } else {
                    return false; // If it is a closing bracket but doesn't match the top, it is invalid
                }
            }
        }
        return dq.isEmpty();
    }
}