
class Solution {
    public long minimumCost(int cost1, int cost2, int costBoth, int need1, int need2) {

        long option1 = (long) need1 * cost1
                     + (long) need2 * cost2;

        long minNeed = Math.min(need1, need2);
        long maxNeed = Math.max(need1, need2);

        long remainingCost = (need1 > need2) ? cost1 : cost2;

        long option2 = minNeed * costBoth
                     + (maxNeed - minNeed) * remainingCost;

        long option3 = maxNeed * costBoth;

        return Math.min(option1, Math.min(option2, option3));
    }
}
