//without stack most optimal solution
//open keeps record of open and close keeps record of number of open brackets that will be needed to balance that many close brackets
class Solution {
    public int minAddToMakeValid(String s) {
        int open = 0;
        int clos = 0;//2 variables for open and close
        for(int i = 0; i < s.length(); i++) {
            if(s.charAt(i) == '(') {
                open++;//simple shit, if open comes then do open++, to record it
            } else if (s.charAt(i) == ')') {
                if(open > 0) {
                    open--;//now see see.. if close comes and open is > 0.. that means there was an open found before this close.. so both are balanced.. simply do open--.. because its balanced so reduce 1 count.. so that open is gone.. and we never added this close as well..so that also didnt get added... so balanced
                } else {
                    clos++;//now this means see, so open is 0 only...that means there is no open before this close.. so we need to do clos++..i mean there is no open before this close.. so this close will keep record of "NUMBER OF OPEN WE NEED", to balance any close
                }
            }
        }
        return open + clos;//at the end we return the sum because.. if ex is "))((".. then close  =2... and open also = 2..so answer will be 4 which is correct na.. so ya thinking like that for edge cases
    }
}