
class Solution {
    public int maximumSaleItems(int[][] items, int budget) {

        int n = items.length;

        int[] dp = new int[budget + 1];

        // Initialize unreachable states
        java.util.Arrays.fill(dp, -1000000000);
        dp[0] = 0;

        int minPrice = Integer.MAX_VALUE;

        for (int i = 0; i < n; i++) {

            int factor = items[i][0];
            int price = items[i][1];

            minPrice = Math.min(minPrice, price);

            // Count total gain from first purchase
            int gain = 0;

            for (int j = 0; j < n; j++) {
                if (items[j][0] % factor == 0) {
                    gain++;
                }
            }

            // 0/1 Knapsack
            for (int c = budget; c >= price; c--) {

                if (dp[c - price] != -1000000000) {

                    dp[c] = Math.max(
                        dp[c],
                        dp[c - price] + gain
                    );
                }
            }
        }

        int ans = 0;

        // Buy cheapest items with remaining budget
        for (int c = 0; c <= budget; c++) {

            if (dp[c] != -1000000000) {

                int extra = (budget - c) / minPrice;

                ans = Math.max(ans, dp[c] + extra);
            }
        }

        return ans;
    }
}