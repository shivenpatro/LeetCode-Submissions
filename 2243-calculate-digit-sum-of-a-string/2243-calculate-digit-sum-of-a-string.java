class Solution {
    public String digitSum(String s, int k) {
        /* what is this problem actually asking? we are given a string s made up of digits and an integer k. as long as the length of s is strictly greater than k, we have to keep doing rounds: in each round, we chop s into consecutive chunks of size k (the last chunk might have fewer than k digits if it doesnt divide evenly), add up the digits inside each chunk, convert that sum back into a string, nad glue all those sum strings together to form our new s! we stop the moment s.length() <= k and return whatever string is left! */

        /* lets do a quick dry run on s = "11111222223", k = 3 so it clicks.. currently length is 11 > 3, so we enter round 1: chunks of size 3 are "111" (sum 3), "112" (sum 4), "222" (sum 6), and the leftover chunk "23" (sum 5). gluing them gives new s = "3465". now length is 4 > 3, so round 2: chunks of size 3 are "346" (sum 13) and leftover "5" (sum 5). gluing them gives new s = "135". now length is 3 <= 3, so the while loop terminates and we return "135"! and notice why we use StringBuilder instead of doing s += sum: strings in java are immutable, so StringBuilder avoids creating tons of garbage string objects every iteration, simple na! */

        /* keep repeating rounds until the string shrinks down to length <= k */
        while (s.length() > k) {
            StringBuilder nextRound = new StringBuilder(); // builds the newly merged string for teh current round

            /* jump through the string in steps of k so each i marks the exact starting index of a group */
            for (int i = 0; i < s.length(); i += k) {
                int groupSum = 0;

                /* sum up digits from i up to i + k - 1, using Math.min so the final smaller chunk doesnt go out of bounds */
                int end = Math.min(i + k, s.length());
                for (int j = i; j < end; j++) {
                    groupSum += s.charAt(j) - '0'; // subtract ascii '0' to convert digit charcter into actual integer value
                }

                nextRound.append(groupSum); // convert sum to string and append directly to nextRound
            }

            s = nextRound.toString(); // replace old s with the newly compressed string before checking while condition again
        }

        return s;
    }
}