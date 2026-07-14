class Solution {
    private static final int MOD = 1_000_000_007;

    public int subsequencePairCount(int[] nums) {
        // Find the maximum value in nums to bound our GCD state size
        int maxVal = 0;
        for (int num : nums) {
            maxVal = Math.max(maxVal, num);
        }

        // dp[g1][g2] stores the number of pairs with GCDs g1 and g2 respectively.
        // Dimension is maxVal + 1 to account for 0 (empty subsequence) to maxVal.
        int[][] dp = new int[maxVal + 1][maxVal + 1];
        
        // Base case: 1 way to have both subsequences empty
        dp[0][0] = 1;

        for (int x : nums) {
            int[][] nextDp = new int[maxVal + 1][maxVal + 1];
            
            for (int g1 = 0; g1 <= maxVal; g1++) {
                for (int g2 = 0; g2 <= maxVal; g2++) {
                    if (dp[g1][g2] == 0) continue;

                    long currentWays = dp[g1][g2];

                    // Choice 1: Skip the current element x
                    nextDp[g1][g2] = (int) ((nextDp[g1][g2] + currentWays) % MOD);

                    // Choice 2: Add x to seq1
                    int nextG1 = (g1 == 0) ? x : gcd(g1, x);
                    nextDp[nextG1][g2] = (int) ((nextDp[nextG1][g2] + currentWays) % MOD);

                    // Choice 3: Add x to seq2
                    int nextG2 = (g2 == 0) ? x : gcd(g2, x);
                    nextDp[g1][nextG2] = (int) ((nextDp[g1][nextG2] + currentWays) % MOD);
                }
            }
            dp = nextDp;
        }

        // Sum up all valid pairs where g1 == g2 and both are non-empty (g1 > 0)
        long totalPairs = 0;
        for (int g = 1; g <= maxVal; g++) {
            totalPairs = (totalPairs + dp[g][g]) % MOD;
        }

        return (int) totalPairs;
    }

    // Helper method to calculate Greatest Common Divisor
    private int gcd(int a, int b) {
        while (b != 0) {
            int temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }
}