
import java.util.*;

class Solution {

    static class Block {
        int value;
        int count;

        Block(int value, int count) {
            this.value = value;
            this.count = count;
        }
    }

    public long countNonDecreasingSubarrays(int[] nums, int k) {

        Deque<Block> deque = new ArrayDeque<>();

        int n = nums.length;
        int right = n - 1;

        long cost = 0;
        long ans = 0;

        for (int left = n - 1; left >= 0; left--) {

            int x = nums[left];
            int count = 1;

            // Merge blocks whose values are <= x
            while (!deque.isEmpty() &&
                   deque.peekFirst().value <= x) {

                Block b = deque.pollFirst();

                cost += (long) (x - b.value) * b.count;

                count += b.count;
            }

            // Add the new block
            deque.addFirst(new Block(x, count));

            // Shrink window if operations exceed k
            while (cost > k) {

                Block b = deque.peekLast();

                cost -= (long) b.value - nums[right];

                b.count--;

                if (b.count == 0) {
                    deque.pollLast();
                }

                right--;
            }

            // Count valid subarrays starting at left
            ans += right - left + 1;
        }

        return ans;
    }
}