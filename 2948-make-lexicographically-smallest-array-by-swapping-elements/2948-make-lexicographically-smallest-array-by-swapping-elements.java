import java.util.*;

class Solution {
    public int[] lexicographicallySmallestArray(int[] nums, int limit) {
        int n = nums.length;

        // Pair each element with its original index: [value, index]
        int[][] paired = new int[n][2];
        for (int i = 0; i < n; i++) {
            paired[i][0] = nums[i];
            paired[i][1] = i;
        }

        // Sort pairs by value
        Arrays.sort(paired, (a, b) -> Integer.compare(a[0], b[0]));

        int[] result = new int[n];
        int i = 0;

        // Group elements into components where adjacent sorted values differ by <= limit
        while (i < n) {
            int j = i;
            // Find the boundary of the current connected component
            while (j + 1 < n && paired[j + 1][0] - paired[j][0] <= limit) {
                j++;
            }

            // Extract values and indices for the current component
            List<Integer> indices = new ArrayList<>();
            for (int k = i; k <= j; k++) {
                indices.add(paired[k][1]);
            }

            // Sort indices so smaller values go to smaller original indices
            Collections.sort(indices);

            // Assign sorted values (paired[k][0]) to sorted original positions (indices.get(k - i))
            for (int k = i; k <= j; k++) {
                result[indices.get(k - i)] = paired[k][0];
            }

            // Move to the next component
            i = j + 1;
        }

        return result;
    }
}