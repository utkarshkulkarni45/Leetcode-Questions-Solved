class Solution {
    public int firstStableIndex(int[] nums, int k) {
        int n = nums.length;
        
        // Step 1: Precompute suffix minimums
        int[] suffixMin = new int[n];
        suffixMin[n - 1] = nums[n - 1];
        for (int i = n - 2; i >= 0; i--) {
            suffixMin[i] = Math.min(nums[i], suffixMin[i + 1]);
        }
        
        // Step 2: Iterate left-to-right and track prefix maximum
        int prefixMax = Integer.MIN_VALUE;
        for (int i = 0; i < n; i++) {
            prefixMax = Math.max(prefixMax, nums[i]);
            
            int instabilityScore = prefixMax - suffixMin[i];
            if (instabilityScore <= k) {
                return i; // Return the first index satisfying the condition
            }
        }
        
        return -1; // No stable index found
    }
}