class Solution {
    public int[] nodesBetweenCriticalPoints(ListNode head) {
        // Base case: at least 3 nodes are required to form a critical point
        if (head == null || head.next == null || head.next.next == null) {
            return new int[]{-1, -1};
        }

        int firstCritical = -1;
        int prevCritical = -1;
        int minDistance = Integer.MAX_VALUE;

        ListNode prev = head;
        ListNode curr = head.next;
        int currIndex = 1; // 0-indexed: head is 0, curr starts at 1

        while (curr.next != null) {
            // Check for local maxima or local minima
            boolean isLocalMaxima = (curr.val > prev.val) && (curr.val > curr.next.val);
            boolean isLocalMinima = (curr.val < prev.val) && (curr.val < curr.next.val);

            if (isLocalMaxima || isLocalMinima) {
                if (firstCritical == -1) {
                    // First critical point found
                    firstCritical = currIndex;
                } else {
                    // Subsequent critical point: update minDistance
                    minDistance = Math.min(minDistance, currIndex - prevCritical);
                }
                prevCritical = currIndex;
            }

            prev = curr;
            curr = curr.next;
            currIndex++;
        }

        // If fewer than two critical points exist
        if (firstCritical == -1 || firstCritical == prevCritical) {
            return new int[]{-1, -1};
        }

        int maxDistance = prevCritical - firstCritical;
        return new int[]{minDistance, maxDistance};
    }
}