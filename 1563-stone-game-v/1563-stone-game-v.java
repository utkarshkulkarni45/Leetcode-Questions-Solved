import java.util.Arrays;

class Solution {
    private int[][] memo;
    private int[] pref;

    public int stoneGameV(int[] stoneValue) {
        int n = stoneValue.length;
        memo = new int[n][n];
        for (int[] row : memo) {
            Arrays.fill(row, -1);
        }

        // Compute prefix sums
        pref = new int[n + 1];
        for (int i = 0; i < n; i++) {
            pref[i + 1] = pref[i] + stoneValue[i];
        }

        return solve(0, n - 1);
    }

    private int solve(int i, int j) {
        // Base case: only one stone left
        if (i == j) return 0;
        
        // Return cached result if available
        if (memo[i][j] != -1) return memo[i][j];

        int maxScore = 0;

        // Try every valid partition point k between i and j - 1
        for (int k = i; k < j; k++) {
            int leftSum = pref[k + 1] - pref[i];
            int rightSum = pref[j + 1] - pref[k + 1];

            if (leftSum < rightSum) {
                maxScore = Math.max(maxScore, leftSum + solve(i, k));
            } else if (leftSum > rightSum) {
                maxScore = Math.max(maxScore, rightSum + solve(k + 1, j));
            } else { // Equal sums
                int option1 = leftSum + solve(i, k);
                int option2 = rightSum + solve(k + 1, j);
                maxScore = Math.max(maxScore, Math.max(option1, option2));
            }
        }

        return memo[i][j] = maxScore;
    }
}