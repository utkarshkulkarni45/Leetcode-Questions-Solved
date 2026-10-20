class Solution {
    public int sortableIntegers(int[] nums) {
        int n = nums.length;
        
        // Precompute prefix maximums, suffix minimums, and prefix drop counts
        int[] prefMax = new int[n + 1];
        prefMax[0] = 0;
        for (int i = 0; i < n; i++) {
            prefMax[i + 1] = Math.max(prefMax[i], nums[i]);
        }
        
        int[] suffMin = new int[n + 1];
        suffMin[n] = Integer.MAX_VALUE;
        for (int i = n - 1; i >= 0; i--) {
            suffMin[i] = Math.min(nums[i], suffMin[i + 1]);
        }
        
        int[] prefDrop = new int[n];
        for (int i = 1; i < n; i++) {
            prefDrop[i] = prefDrop[i - 1] + (nums[i - 1] > nums[i] ? 1 : 0);
        }
        
        long totalSum = 0;
        
        // Check all divisors of n
        for (int k = 1; k * k <= n; k++) {
            if (n % k == 0) {
                if (isValid(k, nums, prefMax, suffMin, prefDrop, n)) {
                    totalSum += k;
                }
                int k2 = n / k;
                if (k2 != k) {
                    if (isValid(k2, nums, prefMax, suffMin, prefDrop, n)) {
                        totalSum += k2;
                    }
                }
            }
        }
        
        return (int) totalSum;
    }
    
    private boolean isValid(int k, int[] nums, int[] prefMax, int[] suffMin, int[] prefDrop, int n) {
        for (int i = 0; i < n; i += k) {
            int l = i;
            int r = i + k - 1;
            
            // Check inter-block ordering
            if (l > 0 && prefMax[l] > suffMin[l]) {
                return false;
            }
            
            // Count drops inside block [l, r]
            int drops = 0;
            if (l < r) {
                int dropsBefore = (l > 0) ? prefDrop[l] : 0;
                int dropsEnd = prefDrop[r];
                drops = dropsEnd - dropsBefore;
            }
            
            if (drops > 1) {
                return false;
            }
            if (drops == 1) {
                // The single drop must happen such that nums[r] <= nums[l]
                if (nums[r] > nums[l]) {
                    return false;
                }
            }
        }
        return true;
    }
}