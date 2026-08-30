class Solution {
    public int minimumDeletions(int[] nums) {
        int n = nums.length;
        if (n == 1) return 1;

        int minIdx = 0;
        int maxIdx = 0;

        // Find indices of minimum and maximum elements
        for (int i = 1; i < n; i++) {
            if (nums[i] < nums[minIdx]) {
                minIdx = i;
            }
            if (nums[i] > nums[maxIdx]) {
                maxIdx = i;
            }
        }

        int left = Math.min(minIdx, maxIdx);
        int right = Math.max(minIdx, maxIdx);

        // Option 1: Remove both from the front
        int removeFromFront = right + 1;

        // Option 2: Remove both from the back
        int removeFromBack = n - left;

        // Option 3: Remove left from front and right from back
        int removeFromBoth = (left + 1) + (n - right);

        return Math.min(removeFromFront, Math.min(removeFromBack, removeFromBoth));
    }
}