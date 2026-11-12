class Solution {
    public long minimumCost(int cost1, int cost2, int costBoth, int need1, int need2) {
        /* so for this qn, what is this problem actually asking? we have three types of items: type 1 costs cost1 and gives 1 unit of type 1, type 2 costs cost2 and gives 1 unit of type 2, and type 3 costs costBoth and gives 1 unit of BOTH type 1 and type 2 simultaneously! we need to collect AT LEAST need1 units of type 1 and AT LEAST need2 units of type 2, and return the minimum total cost. also notice the constraints: cost values go up to 10^6 and need values go up to 10^9! so multiplying them together can reach 10^15, which easily overflows a 32-bit int.. so we MUST cast to long everywhere! */
        /* now break this down with pure greedy math intuition.. there are only two possible cases:
        case 1: buying type 1 and type 2 separately (cost1 + cost2) is cheaper than or equal to buying the combo item (costBoth)! if (cost1 + cost2 <= costBoth), why would we EVER buy the combo item? even when we need both items, buying them separately costs less or the same, and when we only need one of them, buying individually is obviously cheaper! so we never touch costBoth at all and just return (need1 * cost1 + need2 * cost2).
        case 2: the combo item is strictly cheaper than buying both separately (costBoth < cost1 + cost2)! in this case, as long as we need BOTH type 1 and type 2, buying costBoth is strictly optimal because it satisfies 1 unit of each at a discount! how many times can we satisfy both simultaneously? exacty min(need1, need2) times! after buying min(need1, need2) combo items, one of our requirements drops to 0, nad only one requirement remains. for whatever leftover units we still need, we just check whether it is cheaper to buy the individual item (cost1 or cost2) OR keep buying the combo item (costBoth) even though the extra item goes to waste, simple na! */

        /* case 1: buying type 1 and type 2 separately is cheaper or equal to buying costBoth.. never use costBoth */
        if ((long) cost1 + cost2 <= costBoth) {
            return (long) need1 * cost1 + (long) need2 * cost2;
        }

        /* case 2: costBoth < cost1 + cost2.. greedily buy costBoth for as many pairs as both requirements overlap */
        long bothCount = Math.min(need1, need2);
        long totalCost = bothCount * costBoth;

        /* subtract the units already covered by the combo purchases from both requirements */
        long remNeed1 = need1 - bothCount;
        long remNeed2 = need2 - bothCount;

        /* if type 1 still has leftover demand, pick whichever is cheaper between individual cost1 or combo costBoth */
        if (remNeed1 > 0) {
            totalCost += remNeed1 * Math.min(cost1, costBoth);
        }

        /* if type 2 still has leftover demand, pick whichever is cheaper between individual cost2 or combo costBoth */
        if (remNeed2 > 0) {
            totalCost += remNeed2 * Math.min(cost2, costBoth);
        }

        return totalCost;
    }
}