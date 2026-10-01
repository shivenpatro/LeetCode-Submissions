class Solution {
    public int[] maxDepthAfterSplit(String seq) {
        int n = seq.length();
        int[] res = new int[n];
        int d = 0;//depth
        for(int i = 0;i<n;i++){
            char c = seq.charAt(i);
            if(c=='('){//watch cwm video for easy viwsualization + understanding
                d++;//so since we found a open bracket we increase the depth tahts all.. simple, because depth means number of open bracket in contiguous manner
                res[i] = d%2;//now very simple and normal shit see, to minimize the max nested depth out of both groups(means we have to divide the string into 2 groups and then find the max of the depth for both groups... and we have to try to minimize it as much as possible...we have to minimize  the max(depth of group1, depth of group2)), and best way to do is do equal division.. 1 to grp 1 ... 1 to group 2 like that.. so so.. if d(depth) is even, add  it to 0 group... or else add to odd group(thats why d%2 direct.. cuz even means direct gone to group 0 only)
            }
            else{
                //if its close bracket.. obv we have to add that to the group where its exact corresponding open bracket is there na.. so so see.. since its close we have to reduce the depth, but before reducing the depth.. we will check d%2 ... if that gives an even na.. that means the open bracket is in grp 0.. else grp 1.. we are doing the d%2 check before doing d-- and not after... because fking think about it.. if u do d--...  a () means..if d at ( was 8...and went to grp 0.. and i came to )... it will check d=8..find its grp 0..and put ) in grp 0 and thenonly reduce na.. if u reduce before checking.. it will go to grp 1.. sinve it will become odd and the whole point is gone
                res[i] = d%2;
                d--;
            }
        }
        return res;
    }
}