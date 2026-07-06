import java.util.Arrays;

class Solution {
    public int removeCoveredIntervals(int[][] intervals) {
        // Step 1: Sort by start ascending. If starts match, sort by end descending.
        Arrays.sort(intervals, (a, b) -> {
            if (a[0] == b[0]) {
                return b[1] - a[1]; // Descending by end
            }
            return a[0] - b[0]; // Ascending by start
        });
        
        int remainingCount = 0;
        int maxEnd = 0;
        
        // Step 2: Traverse and find covered intervals
        for (int[] interval : intervals) {
            int currentEnd = interval[1];
            
            // If the current interval's end is <= maxEnd, it is fully covered
            if (currentEnd <= maxEnd) {
                continue; // Skip it (it's removed)
            } else {
                // It's a brand new uncovered interval
                remainingCount++;
                maxEnd = currentEnd; // Update the boundary
            }
        }
        
        return remainingCount;
    }
}