class Solution {
    public int numberOfSets(int n, int k) {
        long MOD = 1_000_000_007;
        
        // dp[j][0]: ways to place j segments, not currently inside a segment
        // dp[j][1]: ways to place j segments, currently inside a segment
        long[][] dp = new long[k + 1][2];
        
        // Base initialization
        // For any point i, we can transition. Let's iterate through the points from 0 to n-1.
        
        long[][] memo = new long[k + 1][2];
        // Actually, let's use the standard 2D DP table:
        // dp[i][j] = number of ways to place j segments using i points
        // To optimize space to O(n * k), let's use standard DP:
        
        long[][][] dpTable = new long[n][k + 1][2];
        
        for (int i = 0; i < n; i++) {
            for (int j = 1; j <= k; j++) {
                if (i > 0) {
                    // Case 0: point i is not part of an active line ending here
                    dpTable[i][j][0] = (dpTable[i - 1][j][0] + dpTable[i - 1][j][1]) % MOD;
                    
                    // Case 1: point i ends a line segment
                    long startNew = (j == 1) ? 1 : (dpTable[i - 1][j - 1][0] + dpTable[i - 1][j - 1][1]) % MOD;
                    long extendExisting = dpTable[i - 1][j][1];
                    dpTable[i][j][1] = (extendExisting + startNew) % MOD;
                }
            }
            // Base case for j = 1 starting at point i
            if (i > 0) {
                // We can always start the first segment ending at point i from any previous point
                // Handled implicitly if we consider transitions properly, but let's initialize:
            }
        }
        
        // Let's refine with a simpler O(n * k) DP state:
        // dp[i][0]: total ways with i points and k segments (not extending)
        // dp[i][1]: total ways with i points and k segments (extending)
        
        long[][] curr = new long[k + 1][2];
        
        for (int i = 0; i < n; i++) {
            long[][] next = new long[k + 1][2];
            for (int j = 0; j <= k; j++) {
                // Carry over state without using point i as a segment end/start
                if (i > 0) {
                    // Option A: Do nothing at point i
                    next[j][0] = (next[j][0] + curr[j][0] + curr[j][1]) % MOD;
                }
                
                // Option B: If j > 0, we can start or continue a segment
                if (j > 0 && i > 0) {
                    // Continue previous segment ending at i
                    long extend = curr[j][1];
                    // Start a new segment ending at i (from any point < i)
                    long start = (curr[j - 1][0] + curr[j - 1][1]) % MOD;
                    if (j == 1) {
                        // For the very first segment, we can start it at point 0 and end at i
                        // Sum of all ways to start a segment ending at i is i choices for the start point.
                        // Wait, let's use the standard formulation:
                    }
                }
            }
        }
        
        // Let's use the cleanest accepted O(n*k) DP approach:
        long[][] dpWays = new long[n + 1][k + 1];
        long[][] sumDp = new long[n + 1][k + 1]; // prefix sums to optimize to O(n*k)
        
        // dp[i][j] means j segments using i points where the j-th segment ends at point i-1
        // Standard recurrence: dp[i][j] = sum(dp[prev][j-1] + (i - prev)) ...
        // Even simpler:
        // Let's write the O(n*k) solution directly:
        
        long[] memoNotEnd = new long[k + 1];
        long[] memoEnd = new long[k + 1];
        
        // Proper initialization for point 0:
        // At i = 0, 0 segments = 1 way.
        
        for (int i = 0; i < n; i++) {
            long[] newNotEnd = new long[k + 1];
            long[] newEnd = new long[k + 1];
            
            newNotEnd[0] = 1;
            
            for (int j = 1; j <= k; j++) {
                // If point i is not used to end/start a segment edge directly in a new way:
                // Actually, let's look at the standard O(n*k) accepted logic:
            }
        }
        
        // Let's provide the concise O(n*k) DP solution:
        long[] dpNot = new long[k + 1];
        long[] dpIn = new long[k + 1];
        dpNot[0] = 1;
        
        for (int i = 1; i < n; i++) {
            long[] nextNot = new long[k + 1];
            long[] nextIn = new long[k + 1];
            
            nextNot[0] = 1;
            for (int j = 0; j <= k; j++) {
                nextNot[j] = (dpNot[j] + dpIn[j]) % MOD;
                if (j > 0) {
                    nextIn[j] = (dpIn[j] + dpNot[j - 1] + dpIn[j - 1]) % MOD;
                }
            }
            dpNot = nextNot;
            dpIn = nextIn;
        }
        
        return (int)((dpNot[k] + dpIn[k]) % MOD);
    }
}