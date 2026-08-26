class Solution {
    public String shortestBeautifulSubstring(String s, int k) {
        int n = s.length();
        String result = "";

        // Track the indices of all '1's in the string
        List<Integer> ones = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            if (s.charAt(i) == '1') {
                ones.add(i);
            }
        }

        // If total '1's are less than k, no beautiful substring exists
        if (ones.size() < k) {
            return "";
        }

        // Check every sliding window of size k of '1's
        for (int i = 0; i <= ones.size() - k; i++) {
            int start = ones.get(i);
            int end = ones.get(i + k - 1);
            String sub = s.substring(start, end + 1);

            // Update result if shorter, or equal length but lexicographically smaller
            if (result.isEmpty() || sub.length() < result.length() || 
               (sub.length() == result.length() && sub.compareTo(result) < 0)) {
                result = sub;
            }
        }

        return result;
    }
}