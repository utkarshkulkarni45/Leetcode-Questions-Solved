
import java.util.*;

class Solution {
    public int shortestPath(int n, int[][] edges, String labels, int k) {

        // Build adjacency list
        List<int[]>[] graph = new ArrayList[n];

        for (int i = 0; i < n; i++) {
            graph[i] = new ArrayList<>();
        }

        for (int[] e : edges) {
            graph[e[0]].add(new int[]{e[1], e[2]});
        }

        // dist[node][count]
        int[][] dist = new int[n][k + 1];

        for (int[] row : dist) {
            Arrays.fill(row, Integer.MAX_VALUE);
        }

        // {distance, node, consecutiveCount}
        PriorityQueue<int[]> pq = new PriorityQueue<>(
            (a, b) -> Integer.compare(a[0], b[0])
        );

        dist[0][1] = 0;
        pq.offer(new int[]{0, 0, 1});

        while (!pq.isEmpty()) {

            int[] curr = pq.poll();

            int d = curr[0];
            int node = curr[1];
            int count = curr[2];

            // Skip outdated states
            if (d != dist[node][count]) {
                continue;
            }

            // Destination reached
            if (node == n - 1) {
                return d;
            }

            for (int[] next : graph[node]) {

                int v = next[0];
                int weight = next[1];

                int newCount;

                if (labels.charAt(node) == labels.charAt(v)) {
                    newCount = count + 1;
                } else {
                    newCount = 1;
                }

                // Invalid consecutive characters
                if (newCount > k) {
                    continue;
                }

                int newDist = d + weight;

                if (newDist < dist[v][newCount]) {

                    dist[v][newCount] = newDist;

                    pq.offer(new int[]{
                        newDist, v, newCount
                    });
                }
            }
        }

        return -1;
    }

}