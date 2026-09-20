class Solution {
    public int reverseDegree(String s) {
        int n = s.length();
        int sum = 0;
        for(int i = 0;i<n;i++){
            char c = s.charAt(i);
            int val = 'z'-c+1;
            sum += val*(i+1);
        }
        return sum;
    }
}