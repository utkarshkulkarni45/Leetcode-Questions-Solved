import java.util.*;

class Solution {
    public int minMoves(String[] classroom, int energy) {
        int m = classroom.length;
        int n = classroom[0].length();
        
        int startR = -1, startC = -1;
        List<int[]> litters = new ArrayList<>();
        
        for (int r = 0; r < m; r++) {
            for (int c = 0; c < n; c++) {
                char ch = classroom[r].charAt(c);
                if (ch == 'S') {
                    startR = r;
                    startC = c;
                } else if (ch == 'L') {
                    litters.add(new int[]{r, c});
                }
            }
        }
        
        int totalLitter = litters.size();
        if (totalLitter == 0) return 0;
        
        // Map (r, c) of each litter to its bit index
        int[][] litterIdx = new int[m][n];
        for (int i = 0; i < m; i++) Arrays.fill(litterIdx[i], -1);
        for (int i = 0; i < totalLitter; i++) {
            int[] pos = litters.get(i);
            litterIdx[pos[0]][pos[1]] = i;
        }
        
        // max energy stored in visited state: maxEnergy[r][c][mask] = max energy seen so far
        int[][][] maxEnergySeen = new int[m][n][1 << totalLitter];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                Arrays.fill(maxEnergySeen[i][j], -1);
            }
        }
        
        // Queue stores {row, col, mask, currentEnergy}
        Queue<int[]> queue = new LinkedList<>();
        
        // Handle start position if 'S' is on 'R' or 'L' (though usually S is distinct)
        int initialMask = 0;
        int initialEnergy = energy;
        if (litterIdx[startR][startC] != -1) {
            initialMask |= (1 << litterIdx[startR][startC]);
        }
        
        queue.offer(new int[]{startR, startC, initialMask, initialEnergy});
        maxEnergySeen[startR][startC][initialMask] = initialEnergy;
        
        int steps = 0;
        int targetMask = (1 << totalLitter) - 1;
        
        int[][] dirs = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                int[] curr = queue.poll();
                int r = curr[0], c = curr[1], mask = curr[2], e = curr[3];
                
                if (mask == targetMask) {
                    return steps;
                }
                
                // If out of energy and not on 'R', cannot make next move
                if (e == 0) continue;
                
                for (int[] d : dirs) {
                    int nr = r + d[0];
                    int nc = c + d[1];
                    
                    if (nr >= 0 && nr < m && nc >= 0 && nc < n) {
                        char cell = classroom[nr].charAt(nc);
                        if (cell == 'X') continue;
                        
                        int nextE = e - 1;
                        int nextMask = mask;
                        
                        if (cell == 'R') {
                            nextE = energy;
                        }
                        if (cell == 'L' && litterIdx[nr][nc] != -1) {
                            nextMask |= (1 << litterIdx[nr][nc]);
                        }
                        
                        // Pruning: only push to queue if we have strictly more energy than previously seen for (nr, nc, nextMask)
                        if (nextE > maxEnergySeen[nr][nc][nextMask]) {
                            maxEnergySeen[nr][nc][nextMask] = nextE;
                            queue.offer(new int[]{nr, nc, nextMask, nextE});
                        }
                    }
                }
            }
            steps++;
        }
        
        return -1;
    }
}