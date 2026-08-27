import java.util.*;

class Solution {
    public String lexGreaterPermutation(String s, String target) {
        int n = s.length();
        int[] freq = new int[26];
        for (char ch : s.toCharArray()) {
            freq[ch - 'a']++;
        }

        // Check how long s can match target as a prefix
        int[] prefixFreq = freq.clone();
        int matchLen = 0;
        for (int i = 0; i < n; i++) {
            char tChar = target.charAt(i);
            if (prefixFreq[tChar - 'a'] > 0) {
                prefixFreq[tChar - 'a']--;
                matchLen++;
            } else {
                break;
            }
        }

        // Try divergence positions from longest prefix match down to 0
        for (int i = matchLen; i >= 0; i--) {
            // Reconstruct frequency map up to prefix of length i
            int[] currentFreq = freq.clone();
            boolean possible = true;
            for (int j = 0; j < i; j++) {
                char tChar = target.charAt(j);
                if (currentFreq[tChar - 'a'] > 0) {
                    currentFreq[tChar - 'a']--;
                } else {
                    possible = false;
                    break;
                }
            }
            if (!possible || i == n) continue;

            // Try to find a character strictly greater than target[i]
            char tChar = target.charAt(i);
            for (int c = tChar - 'a' + 1; c < 26; c++) {
                if (currentFreq[c] > 0) {
                    // Valid divergence point found!
                    currentFreq[c]--;
                    
                    StringBuilder sb = new StringBuilder();
                    // 1. Prefix matches target[0...i-1]
                    sb.append(target.substring(0, i));
                    // 2. Divergence character
                    sb.append((char) ('a' + c));
                    // 3. Remaining characters sorted ascendingly
                    for (int ch = 0; ch < 26; ch++) {
                        while (currentFreq[ch] > 0) {
                            sb.append((char) ('a' + ch));
                            currentFreq[ch]--;
                        }
                    }
                    return sb.toString();
                }
            }
        }

        return "";
    }
}