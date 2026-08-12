import java.util.HashMap;
import java.util.Map;

class Solution {
    public int maxSubarrayLength(int[] nums, int k) {
        Map<Integer, Integer> freqMap = new HashMap<>();
        int left = 0;
        int maxLength = 0;

        for (int right = 0; right < nums.length; right++) {
            int currentNum = nums[right];
            freqMap.put(currentNum, freqMap.getOrDefault(currentNum, 0) + 1);

            // Shrink window if the frequency of current element exceeds k
            while (freqMap.get(currentNum) > k) {
                freqMap.put(nums[left], freqMap.get(nums[left]) - 1);
                left++;
            }

            // Update maximum good subarray length found so far
            maxLength = Math.max(maxLength, right - left + 1);
        }

        return maxLength;
    }
}