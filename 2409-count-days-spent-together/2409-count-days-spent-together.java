//simple.. core idea is.. change every string into like;... day number.. like 01-2..is 2nd day... 08-15 is like 227th day of the year na... so if everything goes to 365 number form.. commion finding is easy
class Solution {
    public int countDaysTogether(String arriveAlice, String leaveAlice, String arriveBob, String leaveBob) {
        int[] cumsum = {0, 31, 59, 90, 120, 151, 181, 212, 243, 273, 304, 334};//see,  what this is we just stored .. like  this is cumulative sum array for the days in months... like 1st is before 1st month.. then 2nd is till 1st mnth.. then till 2nd...its 31+28...jan+feb na... just cum sum array... so that na.. if we land on 3rd month which is 3 indes(march) in this array.. we can find number of days nefore march na.. whihc is jan+feb.. so 59... like that
        int startAl = cumsum[Integer.parseInt(arriveAlice.substring(0,2))-1] + Integer.parseInt(arriveAlice.substring(3,5));//so now lets understand its just basic thing we did to extract the days and months from the given string thats all ok??... so see.. first substring.. first 2 ... os month comes.. now now why "-1??"... because see.. if its like month 08 which is aug... we need to find the number of days till 7th month na?(july here)..and then we will add the days mentioned which will include the days of that 8th month na?? thats how the final day number is found na?.. so we found the number of days till before the given month..
        //then we do + number of days... so number(parseint.. of other substring)... we follow this for all
        int endAl = cumsum[Integer.parseInt(leaveAlice.substring(0,2))-1] + Integer.parseInt(leaveAlice.substring(3,5));
        int startBob   = cumsum[Integer.parseInt(arriveBob.substring(0, 2)) - 1]  + Integer.parseInt(arriveBob.substring(3, 5));
        int endBob     = cumsum[Integer.parseInt(leaveBob.substring(0, 2)) - 1] + Integer.parseInt(leaveBob.substring(3, 5));// found start end for both alice and bob
        //now now... see seee... whoever came later... will have the start of the overlap window
        //whoever left faster will have the end of the overlap window
        int start  = Math.max(startAl, startBob);
        int end = Math.min(endAl, endBob);//start and end found
        //edge case.. like if they never overlap.. the start > end.. then gotta handle it
        return Math.max(0,end-start+1);//to handle negatives.. we cant just return end-start na...and + 1 obv cuz like bruh... 230-227 will be 3... but it was 4 days na.. so like that
    }
}