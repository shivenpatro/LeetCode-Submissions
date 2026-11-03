//first appraoch... way i did after covering edge cases and all.. easy  only but below is an easire approach.. 
/*class Solution {
    public int lengthOfLastWord(String s) {
        String p = s.trim();//trim to remove first and last spaces
        int n = p.length();
        if(n==1) return 1;//if only 1 letter word.. just return 1
        int pos = 0;//stores the position of the last space... if the string was just one word ex "day" or  "    day".. then issue is it wont be space.. now issue will be while using the substring funciton.. because it  will be 1 less than the answer... because if pos is at " ".. that wint be counted and the proper length will be counted... because substring counts from next of starting to end.. so basic thing.. if its space.. then simple find length of substring from pos to n-1... if its not space.. that means there was only one word.. increase the answer by +1 ... to include the charcter at that"pos" place as well.. like in day...d..a..y...3...but substring willl count on a...y... 2.. so anwer has to be 2+1
        for(int i = 0; i<n; i++){
            if(p.charAt(i) == ' '){
                pos = i;//finding positiong of last space
            }
        }
        if(p.charAt(pos) == ' '){
            int m = p.substring(pos,n-1).length();///case when pos is " "
            return m;
        }
        else{
            int m = p.substring(pos,n-1).length()+1;//when not space
            return m;
        }
    }
}*/
//second simpler easier approach
class Solution {
    public int lengthOfLastWord(String s) {
        int n = s.length();
        int i = n - 1;//goes from back
        int len = 0;//will count the length of last word
        while(i >= 0 && s.charAt(i) == ' '){
            i--;//see see.. if there is a string which has space after the last word... we will make the i move from end to the last letter of last word.. evidently skip all the spaces after the last word thats all
        }
        while(i >= 0 && s.charAt(i) != ' '){
            len++;//now after the above loop.. if the string has spaces after last word... like "  i like  "...i will be at position of e of last word(like)... end letter of end word.. and if no space.. then by default endletter of end word... so fione only.. just do i-- adnd len++... till we find again " "... and then return lenght!!!! simplee!!
            i--;
        }
        return len;
    }
}