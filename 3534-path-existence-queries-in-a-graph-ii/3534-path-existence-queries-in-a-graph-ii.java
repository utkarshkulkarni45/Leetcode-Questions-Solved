import java.util.*;

class Solution {
    public int[] pathExistenceQueries(int n, int[] nums, int maxDiff, int[][] queries) {
        // Pair structure to store original index and value
        Integer[] ids = new Integer[n];
        for (int i = 0; i < n; i++) {
            ids[i] = i;
        }
        
        // Sort indices based on their corresponding values in nums
        Arrays.sort(ids, (a, b) -> Integer.compare(nums[a], nums[b]));
        
        // up[i][j] stores the 2^j-th optimal step to the right from node i
        int LOG = 18; // 2^17 = 262144 > 10^5
        int[][] up = new int[n][LOG];
        
        // Two-pointer approach to find the immediate next greedy step for each node
        int r = 0;
        for (int l = 0; l < n; l++) {
            int currIdx = ids[l];
            // Move the right pointer as far as possible within maxDiff
            while (r < n && nums[ids[r]] <= nums[currIdx] + maxDiff) {
                r++;
            }
            // ids[r - 1] has the maximum value reachable from currIdx
            int bestNext = ids[r - 1];
            // If the best next node has the same value, it means we cannot go further right
            if (nums[bestNext] == nums[currIdx]) {
                up[currIdx][0] = currIdx;
            } else {
                up[currIdx][0] = bestNext;
            }
        }
        
        // Fill the binary lifting table
        for (int j = 1; j < LOG; j++) {
            for (int i = 0; i < n; i++) {
                up[i][j] = up[up[i][j - 1]][j - 1];
            }
        }
        
        int[] ans = new int[queries.length];
        for (int q = 0; q < queries.length; q++) {
            int u = queries[q][0];
            int v = queries[q][1];
            
            if (u == v) {
                ans[q] = 0;
                continue;
            }
            
            // Ensure nums[u] <= nums[v] for unidirectional traversal
            if (nums[u] > nums[v]) {
                int temp = u;
                u = v;
                v = temp;
            }
            
            // If they can already reach each other directly
            if (nums[v] - nums[u] <= maxDiff) {
                ans[q] = 1;
                continue;
            }
            
            int steps = 0;
            // Lift u to the furthest node that CANNOT reach v directly
            for (int j = LOG - 1; j >= 0; j--) {
                int nxt = up[u][j];
                if (nums[v] - nums[nxt] > maxDiff && nums[nxt] > nums[u]) {
                    u = nxt;
                    steps += (1 << j);
                }
            }
            
            // Move one more step from the lifted position
            u = up[u][0];
            steps++;
            
            // Check if we can now reach v directly
            if (nums[v] - nums[u] <= maxDiff) {
                ans[q] = steps + 1;
            } else {
                ans[q] = -1; // Unreachable
            }
        }
        
        return ans;
    }
}