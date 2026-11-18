class Solution {
    public int minChanges(String s) {
        /* so for this one, we are given a binary string s of EVEN length riht?. we need to partition s into one or more substrings such that EVERY single substring has an EVEN length, and every substring contains ONLY '1's or ONLY '0's! we can flip any character ('0' <-> '1') to achieve this, and we want the minimum number of flips required. */

        /* now heres the big greedy observation: notice that there is NO limit on how many substrings we partition s into! whether we make one big partition of length 4 like "0000" or two small partitions of length 2 like "00" and "00", both are completely valid! since every valid substring must have an EVEN length (2, 4, 6, ...), the smallest atomic building block of ANY valid partition is a pair of length 2! if we just slice the entire string into consecutive non-overlapping pairs of size 2: (s[0], s[1]), (s[2], s[3]), (s[4], s[5]), ..., each pair is already of even length 2! if the two characters in a pair are equal ("00" or "11"), it costs 0 changes.. if they are different ("01" or "10"), we MUST flip one of them to match the other, which costs 1 change! summing this up across all pairs gives the exact global minimum, simple na! */

        int changes = 0;

        /* jump by 2 steps each iteration to inspect consecutive pairs (i, i + 1) */
        for (int i = 0; i < s.length(); i += 2) {
            /* if the two characters in this length-2 block dont match, flip one of them (+1 change) */
            if (s.charAt(i) != s.charAt(i + 1)) {
                changes++;
            }
        }

        return changes;
    }
}