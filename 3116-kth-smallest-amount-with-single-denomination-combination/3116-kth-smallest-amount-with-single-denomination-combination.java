import java.util.*;

class Solution {
    public long findKthSmallest(int[] coins, int k) {
        long low = 1;
        
        // Find the minimum coin to set a tight upper bound
        long minCoin = coins[0];
        for (int c : coins) {
            minCoin = Math.min(minCoin, c);
        }
        long high = minCoin * k;
        long result = high;

        // Binary search for the kth smallest value
        while (low <= high) {
            long mid = low + (high - low) / 2;
            
            if (countAmounts(mid, coins) >= k) {
                result = mid;
                high = mid - 1; // Try to find a smaller valid amount
            } else {
                low = mid + 1;  // Increase search space
            }
        }

        return result;
    }

    // Helper function using Inclusion-Exclusion Principle
    private long countAmounts(long target, int[] coins) {
        long count = 0;
        int n = coins.length;
        int totalSubsets = 1 << n; // 2^n subsets

        // Iterate through all subsets (excluding the empty set)
        for (int mask = 1; mask < totalSubsets; mask++) {
            long currentLcm = 1;
            int bitCount = 0;

            for (int i = 0; i < n; i++) {
                if ((mask & (1 << i)) != 0) {
                    bitCount++;
                    currentLcm = lcm(currentLcm, coins[i]);
                    // Optimization: stop if LCM exceeds target
                    if (currentLcm > target) break;
                }
            }

            if (currentLcm <= target) {
                if (bitCount % 2 != 0) {
                    count += target / currentLcm; // Odd size: Add
                } else {
                    count -= target / currentLcm; // Even size: Subtract
                }
            }
        }

        return count;
    }

    private long gcd(long a, long b) {
        while (b != 0) {
            long temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }

    private long lcm(long a, long b) {
        if (a == 0 || b == 0) return 0;
        return (a / gcd(a, b)) * b;
    }
}