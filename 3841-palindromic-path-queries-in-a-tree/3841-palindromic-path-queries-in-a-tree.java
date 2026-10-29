
import java.util.*;

class Solution {

    int LOG;
    int[][] up;
    int[] depth, tin, tout;
    int timer;
    Fenwick bit;

    // Fenwick Tree with XOR range updates and point queries
    class Fenwick {
        int n;
        int[] tree;

        Fenwick(int n) {
            this.n = n;
            tree = new int[n + 2];
        }

        void update(int i, int val) {
            while (i <= n) {
                tree[i] ^= val;
                i += i & -i;
            }
        }

        int query(int i) {
            int result = 0;

            while (i > 0) {
                result ^= tree[i];
                i -= i & -i;
            }

            return result;
        }

        void rangeUpdate(int l, int r, int val) {
            update(l, val);

            if (r + 1 <= n) {
                update(r + 1, val);
            }
        }
    }

    // Find Lowest Common Ancestor
    int lca(int u, int v) {

        if (depth[u] < depth[v]) {
            int temp = u;
            u = v;
            v = temp;
        }

        // Bring u to the same depth as v
        int diff = depth[u] - depth[v];

        for (int j = 0; j < LOG; j++) {
            if ((diff & (1 << j)) != 0) {
                u = up[j][u];
            }
        }

        if (u == v) return u;

        // Lift both nodes
        for (int j = LOG - 1; j >= 0; j--) {
            if (up[j][u] != up[j][v]) {
                u = up[j][u];
                v = up[j][v];
            }
        }

        return up[0][u];
    }

    public List<Boolean> palindromePath(
        int n, int[][] edges, String s, String[] queries
    ) {

        // Build adjacency list
        List<Integer>[] graph = new ArrayList[n];

        for (int i = 0; i < n; i++) {
            graph[i] = new ArrayList<>();
        }

        for (int[] e : edges) {
            graph[e[0]].add(e[1]);
            graph[e[1]].add(e[0]);
        }

        char[] chars = s.toCharArray();

        // Binary lifting setup
        LOG = 1;

        while ((1 << LOG) <= n) {
            LOG++;
        }

        up = new int[LOG][n];
        depth = new int[n];
        tin = new int[n];
        tout = new int[n];

        int[] basePrefix = new int[n];

        // Iterative DFS to avoid stack overflow
        int[] stackNode = new int[n];
        int[] stackParent = new int[n];
        int[] stackState = new int[n];

        int top = 0;
        stackNode[top] = 0;
        stackParent[top] = -1;
        stackState[top] = 0;

        timer = 0;

        while (top >= 0) {

            int u = stackNode[top];

            // Enter node
            if (stackState[top] == 0) {

                int p = stackParent[top];

                up[0][u] = (p == -1) ? u : p;

                for (int j = 1; j < LOG; j++) {
                    up[j][u] = up[j - 1][up[j - 1][u]];
                }

                basePrefix[u] =
                    (p == -1 ? 0 : basePrefix[p])
                    ^ (1 << (chars[u] - 'a'));

                tin[u] = ++timer;

                stackState[top] = 1;
            }

            // Visit children
            else if (stackState[top] <= graph[u].size()) {

                int idx = stackState[top]++;

                int v = graph[u].get(idx - 1);

                if (v == stackParent[top]) {
                    continue;
                }

                depth[v] = depth[u] + 1;

                top++;

                stackNode[top] = v;
                stackParent[top] = u;
                stackState[top] = 0;
            }

            // Exit node
            else {

                tout[u] = timer;
                top--;
            }
        }

        bit = new Fenwick(n);

        List<Boolean> answer = new ArrayList<>();

        // Process queries
        for (String q : queries) {

            String[] parts = q.split(" ");

            if (parts[0].equals("update")) {

                int u = Integer.parseInt(parts[1]);
                char c = parts[2].charAt(0);

                int oldMask = 1 << (chars[u] - 'a');
                int newMask = 1 << (c - 'a');

                int delta = oldMask ^ newMask;

                // Update all descendants' prefix masks
                bit.rangeUpdate(tin[u], tout[u], delta);

                chars[u] = c;
            }

            else {

                int u = Integer.parseInt(parts[1]);
                int v = Integer.parseInt(parts[2]);

                int ancestor = lca(u, v);

                // Current root-to-node prefix masks
                int prefixU = basePrefix[u] ^ bit.query(tin[u]);
                int prefixV = basePrefix[v] ^ bit.query(tin[v]);

                // Include LCA exactly once
                int pathMask = prefixU ^ prefixV
                             ^ (1 << (chars[ancestor] - 'a'));

                // At most one character can have odd frequency
                answer.add(Integer.bitCount(pathMask) <= 1);
            }
        }

        return answer;
    }
}
