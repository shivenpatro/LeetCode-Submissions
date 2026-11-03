class Solution {
    public int lengthOfLastWord(String s) {
        String p = s.trim();
        int n = p.length();
        if(n==1) return 1;
        int pos = 0;
        for(int i = 0; i<n; i++){
            if(p.charAt(i) == ' '){
                pos = i;
            }
        }
        if(p.charAt(pos) == ' '){
            int m = p.substring(pos,n-1).length();
            return m;
        }
        else{
            int m = p.substring(pos,n-1).length()+1;
            return m;
        }
    }
}