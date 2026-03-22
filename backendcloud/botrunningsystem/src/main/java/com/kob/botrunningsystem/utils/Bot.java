package com.kob.botrunningsystem.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;

public class Bot implements java.util.function.Supplier<Integer> {

    static class Cell {
        public int x, y;
        public Cell(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    private boolean check_tail_increase(int step) {    // 检查当前回合，蛇的长度是否增加
        if (step <= 10) return true;
        return step % 3 == 1;
    }

    public List<Cell> getCells(int sx, int sy, String steps) {
        steps = steps.substring(1, steps.length() - 1);
        List<Cell> res = new ArrayList<>();

        int[] dx = {-1, 0, 1, 0}, dy = {0, 1, 0, -1};
        int x = sx, y = sy;
        int step = 0;
        res.add(new Cell(x,y));
        for (int i = 0; i < steps.length(); i++) {
            int d = steps.charAt(i) - '0';
            x += dx[d];
            y += dy[d];
            res.add(new Cell(x,y));
            if (!check_tail_increase(++ step)) {
                res.remove(0);
            }
        }
        return res;
    }

    private int[][] copyGrid(int[][] g) {
        int[][] copy = new int[13][14];
        for (int i = 0; i < 13; i++) {
            for (int j = 0; j < 14; j++) {
                copy[i][j] = g[i][j];
            }
        }
        return copy;
    }

    /** 从新头 (nx,ny) 的四个邻居出发 BFS，统计可达的空格数量（避免走进死路） */
    private int bfsReachable(int[][] sim, int nx, int ny) {
        int[] dx = {-1, 0, 1, 0}, dy = {0, 1, 0, -1};
        boolean[][] vis = new boolean[13][14];
        Queue<int[]> q = new LinkedList<>();
        int count = 0;
        for (int i = 0; i < 4; i++) {
            int x = nx + dx[i], y = ny + dy[i];
            if (x >= 0 && x < 13 && y >= 0 && y < 14 && sim[x][y] == 0 && !vis[x][y]) {
                q.add(new int[]{x, y});
                vis[x][y] = true;
                count++;
            }
        }
        while (!q.isEmpty()) {
            int[] cur = q.poll();
            for (int i = 0; i < 4; i++) {
                int x = cur[0] + dx[i], y = cur[1] + dy[i];
                if (x >= 0 && x < 13 && y >= 0 && y < 14 && sim[x][y] == 0 && !vis[x][y]) {
                    vis[x][y] = true;
                    q.add(new int[]{x, y});
                    count++;
                }
            }
        }
        return count;
    }

    private int manhattan(int ax, int ay, int bx, int by) {
        return Math.abs(ax - bx) + Math.abs(ay - by);
    }

    public Integer nextMove(String input) {     // 这是主函数，这个函数返回的值决定了蛇下一步走的方向
        String[] strs = input.split("#");   // 前十回合蛇的长度每回合都增加，之后，每隔三回合增加一次
        int[][] g = new int[13][14];
        for (int i = 0, k = 0; i < 13; i++) {
            for (int j = 0; j < 14; j++, k++) {
                if (strs[0].charAt(k) == '1') {
                    g[i][j] = 1;
                }
            }
        }

        int aSx = Integer.parseInt(strs[1]), aSy = Integer.parseInt(strs[2]);
        int bSx = Integer.parseInt(strs[4]), bSy = Integer.parseInt(strs[5]);

        List<Cell> aCells = getCells(aSx, aSy, strs[3]);
        List<Cell> bCells = getCells(bSx, bSy, strs[6]);

        for (Cell c : aCells) g[c.x][c.y] = 1;
        for (Cell c : bCells) g[c.x][c.y] = 1;
        // 所有的操作已经提前做好了，g是一个13*14的数组，1表示障碍物或者被蛇的身体，也就是不能走的地方，0表示能走的地方
        int[] dx = {-1, 0, 1, 0}, dy = {0, 1, 0, -1};

        int stepsALen = strs[3].length() - 2;  // 己方已走步数（去掉括号）
        Cell head = aCells.get(aCells.size() - 1);
        Cell tail = aCells.get(0);
        Cell oppTail = bCells.get(0);

        int bestDir = 2;
        int bestScore = -1;

        for (int d = 0; d < 4; d++) {
            int nx = head.x + dx[d], ny = head.y + dy[d];
            if (nx < 0 || nx >= 13 || ny < 0 || ny >= 14 || g[nx][ny] != 0) {
                continue;  // 不合法
            }
            int[][] sim = copyGrid(g);
            sim[nx][ny] = 1;
            if (!check_tail_increase(stepsALen + 1)) {
                sim[tail.x][tail.y] = 0;
            }
            int score = bfsReachable(sim, nx, ny);
            if (score > bestScore) {
                bestScore = score;
                bestDir = d;
            } else if (score == bestScore && bestScore >= 0) {
                // tie-break: 更靠近对方蛇尾，便于后续追击
                int curDist = manhattan(nx, ny, oppTail.x, oppTail.y);
                int bestNx = head.x + dx[bestDir], bestNy = head.y + dy[bestDir];
                int bestDist = manhattan(bestNx, bestNy, oppTail.x, oppTail.y);
                if (curDist < bestDist) {
                    bestDir = d;
                }
            }
        }

        if (bestScore < 0) {
            return 2;  // 无合法方向时 fallback
        }
        return bestDir;
    }

    @Override
    public Integer get() {
        File file = new File("input.txt");
        try {
            Scanner sc = new Scanner(file);
            return nextMove(sc.next());
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
