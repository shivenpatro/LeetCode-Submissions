//sliding window + keeping the min memory till that index 

class Solution {//we are gonna use the sliding window approach and then, to prevent overlapping, we will be storing the min length found till that index j(ending of the window), so that since result is we need to submit the sum of length of 2 min  length non overlapping subarrays, storing the min till that point, in  another array, for each index is the best way, and then we are gonna keep checking the result  with that lentgh and the min length found till i-1, which will be added to result(which has the length of the current subarray), and we will also be minimizing, like always doing min check, of the result  for this index and prev index, because its possible that we finder a better (smaller) combination in the future
    public int minSumOfLengths(int[] arr, int target) {
        int n = arr.length;
        int i = 0;//pointers for sliding window, i and j
        int j = 0;
        int currentSum = 0;//this is storing the sum of the current window, between j and i
        int minlengthtillthisindex[] = new int[n];//we define this array and store the minimum length of any subarray which sum of each value = target, is found till this index, if target was 7, and array was 7,3,4,7... then all values will be 1,1,1 only because theres 7 at 0th index , and we will be keeping record of the min len subarray fouind  tilll that index point, if array was 3,4,7... then this array values had been  infinity,2,1...  now at 0th..3<7.. so cuz no subarray, at 1st index, 3+4...7  equal to target.. so length wil be j-i+1.. which is 1-0+1...2.. so 2 is stored at index 1... and finally 2nd index 7=7..length 1.. minlen = min(minlen,len)(2,1)...  so 2rd index will store 1 now
        Arrays.fill(minlengthtillthisindex, Integer.MAX_VALUE);//fill the array with max value because we are trying to find the minimum till that point, so obv we will keep on doing minimum check between the current len found and the best minimum found till  that point
        int result = Integer.MAX_VALUE;//this also we have to find the 2 sub arrays with adding having the minium length sum
        int bestMinLen = Integer.MAX_VALUE;
        while(j<n){//to not cross the whole array obv
            currentSum += arr[j];//this is the find the sum of the current active window, so this is what we are doing here
            while(i<=j && currentSum>target){//so what happens is, if the currentSum becomes bigger than target, then obv we have to remove from left, reduce the size of window
                currentSum -= arr[i];//so so, basic sliding window shit, if the currentSum > target, that means we have to puish the i forward and remove the elemnent from left side, now since its out, its no longer in the current active sliding window, so remove it and do i++; so that the sliding window "moves"
                i++;
            }
            //finding a valid subarray
            if(currentSum == target){
                int length = j-i+1;//finding rthe lenght of that valid subarray
                bestMinLen = Math.min(bestMinLen,length);//now what we are doing is, we are keeping a track of the minimum length found till now,not tracking, we are just storing the minimum found till that point, so  that we can add this at that particular j index!! which will keep record of minlengthtillthisindex
                if(i>0 && minlengthtillthisindex[i-1] != Integer.MAX_VALUE){//i>0 to check, because  see, obv we are doing i-1 check, if its at 0.. it will lead to arrayindexoutofbound, thats why this check is there, and then we check, if wherever i is, we check 1 index behind it and that value is not infinite, that means there is already a valid subarray found, like its pakka found no matter the length, and we will have to add it to the result 
                    result = Math.min(result, length + minlengthtillthisindex[i-1]);//so so we are just finding the minimum result here, how? we add the length of current found subarray with the minimum most legth subarray present behind the i of this valid subarray(or window), and sum it.., which will be the possible answer, and then for next time whne we find, we do the min check with the prev result with this len + min before thingy.. and store in result whtever is minimum 
                }
            }
            minlengthtillthisindex[j] = bestMinLen;//we are storing at j index... i mean obv because... we are keep storing everytim the j moves... so everytime its getting filled with the min length found till that j
            j++;//and we move the window.. right side of the window.. the 
        }
        return result == Integer.MAX_VALUE ? -1 : result;//simple final  check, if the result still has infinit or  max cval... that means 2 subarrays where never found only, so return -1.. or  else the result
    }
}