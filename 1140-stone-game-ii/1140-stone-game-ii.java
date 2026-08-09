import java.util.Arrays;

class Solution {

    private int[][] memo;
    private int[] suffixSum;
    private int n;

    public int stoneGameII(int[] piles) {
        n = piles.length;
        suffixSum = new int[n];
        
        // Compute suffix sums: suffixSum[i] = total stones from index i to n-1
        suffixSum[n - 1] = piles[n - 1];
        for (int i = n - 2; i >= 0; i--) {
            suffixSum[i] = suffixSum[i + 1] + piles[i];
        }

        // Memoization table: memo[i][M] stores the max stones Alice can get from index i with parameter M
        memo = new int[n][n + 1];

        return dp(0, 1);
    }

    private int dp(int i, int M) {
        // Base case: if we can take all remaining piles
        if (i + 2 * M >= n) {
            return suffixSum[i];
        }

        if (memo[i][M] != 0) {
            return memo[i][M];
        }

        int maxStones = 0;

        // Try taking X piles where 1 <= X <= 2M
        for (int X = 1; X <= 2 * M; X++) {
            int nextM = Math.max(M, X);
            // Stones taken = Total remaining from index i - Best opponent can do from index i + X
            int stones = suffixSum[i] - dp(i + X, nextM);
            maxStones = Math.max(maxStones, stones);
        }

        memo[i][M] = maxStones;
        return maxStones;
    }
}