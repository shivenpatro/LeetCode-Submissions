class Solution {
    public int reverseDegree(String s) {
        int n = s.length();
        int sum = 0;//final sum answer 
        for(int i = 0;i<n;i++){
            char c = s.charAt(i);
            int val = 'z'-c+1;//basic subtraction to find the reverse position for each alphabet
            sum += val*(i+1);//that position * index
        }
        return sum;
    }
}