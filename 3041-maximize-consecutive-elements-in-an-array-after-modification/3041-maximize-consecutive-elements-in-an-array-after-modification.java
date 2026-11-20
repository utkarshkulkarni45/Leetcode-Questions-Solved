
class Solution {
    public int maxSelectedElements(int[] nums) {

        Arrays.sort(nums);

        int dp0 = 1;
        int dp1 = 1;
        int ans = 1;

        for (int i = 1; i < nums.length; i++) {

            int prev = nums[i - 1];
            int curr = nums[i];

            if (curr == prev) {
                dp1 = dp0 + 1;
            }
            else if (curr == prev + 1) {
                dp0++;
                dp1++;
            }
            else if (curr == prev + 2) {
                dp0 = dp1 + 1;
                dp1 = 1;
            }
            else {
                dp0 = 1;
                dp1 = 1;
            }

            ans = Math.max(ans, Math.max(dp0, dp1));
        }

        return ans;
    }
}
