class Solution {
    public int maxDepth(String s) {
        //optimal no space approach, without stack or anything... just maintaion the count fking simple.. if open bracket comes do ++ and if close bracket comes do -- to eliminate that open bracket thats all, O(1) space
        int n = s.length();//find the length of the string
        int counter = 0;//maintain the counter
        int max = Integer.MIN_VALUE;//the final max nesting count maintained here
        for(int i = 0;i<n;i++){
            char c = s.charAt(i);//we simply traverse the whole string and find each char
            if(c=='(') counter++;//if the char is open bracket.. incerase the count and if the char is clsoing bracket decrease the count... and this  extreme simple approach can cover everything!!!.. because we jsut care... how much open we have seen till  that point... thats why eveery step we calculate max till that point as well
            if(c==')') counter--;
            max = Math.max(max,counter);//final max answer
        }
        return max;
    }
}
