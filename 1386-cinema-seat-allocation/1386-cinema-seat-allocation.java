import java.util.*;

class Solution {
    public int maxNumberOfFamilies(int n, int[][] reservedSeats) {
        // Group reserved seats by row using bitmasks
        Map<Integer, Integer> rowMasks = new HashMap<>();
        
        for (int[] seat : reservedSeats) {
            int row = seat[0];
            int col = seat[1];
            
            // Only care about seats 2 to 9
            if (col >= 2 && col <= 9) {
                rowMasks.put(row, rowMasks.getOrDefault(row, 0) | (1 << (col - 2)));
            }
        }

        // Unreserved rows automatically give 2 groups each
        int totalGroups = (n - rowMasks.size()) * 2;

        // Bitmasks for the three possible seat blocks (adjusted for 0-based offset from col 2)
        int leftBlock = 0b00001111;   // Seats 2, 3, 4, 5
        int middleBlock = 0b00111100; // Seats 4, 5, 6, 7
        int rightBlock = 0b11110000;  // Seats 6, 7, 8, 9

        for (int mask : rowMasks.values()) {
            boolean leftFree = (mask & leftBlock) == 0;
            boolean rightFree = (mask & rightBlock) == 0;

            if (leftFree && rightFree) {
                totalGroups += 2; // Both left and right blocks fit
            } else if (leftFree || rightFree || (mask & middleBlock) == 0) {
                totalGroups += 1; // Either left, right, or middle block fits
            }
        }

        return totalGroups;
    }
}