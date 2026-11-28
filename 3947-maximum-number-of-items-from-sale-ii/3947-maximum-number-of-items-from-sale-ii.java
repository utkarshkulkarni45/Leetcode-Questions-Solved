
import java.util.*;

class Solution {

    public int maximumSaleItems(int[][] items, int budget) {

        int n = items.length;
        int maxFactor = 0;
        int minPrice = Integer.MAX_VALUE;

        // Find maximum factor and minimum price
        for (int[] item : items) {
            maxFactor = Math.max(maxFactor, item[0]);
            minPrice = Math.min(minPrice, item[1]);
        }

        // Step 1: Frequency of factors
        int[] freq = new int[maxFactor + 1];

        for (int[] item : items) {
            freq[item[0]]++;
        }

        // Step 2: Calculate gain for each factor
        int[] gain = new int[maxFactor + 1];

        for (int i = 1; i <= maxFactor; i++) {

            for (int j = i; j <= maxFactor; j += i) {
                gain[i] += freq[j];
            }

            // Exclude only the item itself
            gain[i]--;
        }

        // Step 3: Sort items by price
        Arrays.sort(items, (a, b) ->
            Integer.compare(a[1], b[1])
        );

        long remaining = budget;
        long ans = 0;

        // Step 4: Purchase bonus-generating copies
        for (int[] item : items) {

            int factor = item[0];
            int price = item[1];

            // Bonus purchase must be cheaper than
            // buying two ordinary items
            if (price >= 2L * minPrice) {
                break;
            }

            long take = Math.min(
                (long) gain[factor],
                remaining / price
            );

            ans += 2L * take;
            remaining -= take * price;
        }

        // Step 5: Spend remaining budget on cheapest item
        ans += remaining / minPrice;

        return (int) ans;
    }
}
