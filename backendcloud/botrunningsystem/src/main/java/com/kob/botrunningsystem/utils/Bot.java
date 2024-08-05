package com.kob.botrunningsystem.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
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

    public Integer nextMove(String input) {     // 这是主函数，这个函数返回的值决定了蛇下一步走的方向
        String[] strs = input.split("#");   // 前十回合蛇的长度每回合都增加，之后，每隔三回合增加一次
        int[][] g = new int[13][14];
        for (int i = 0, k = 0; i < 13; i ++) {
            for (int j = 0; j < 14; j++, k ++) {
                if (strs[0].charAt(k) == '1') {
                    g[i][j] = 1;
                }
            }
        }

        int aSx = Integer.parseInt(strs[1]), aSy = Integer.parseInt(strs[2]);
        int bSx = Integer.parseInt(strs[4]), bSy = Integer.parseInt(strs[5]);

        List<Cell> aCells = getCells(aSx, aSy, strs[3]);
        List<Cell> bCells = getCells(bSx, bSy, strs[6]);

        for (Cell c: aCells) g[c.x][c.y] = 1;
        for (Cell c: bCells) g[c.x][c.y] = 1;
        // 所有的操作已经提前做好了，g是一个13*14的数组，1表示障碍物或者被蛇的身体，也就是不能走的地方，0表示能走的地方
        int[] dx = {-1, 0, 1, 0}, dy = {0, 1, 0, -1};

        int[] r = {0, 1, 2, 3};



        for (int i = 0; i < 4; i ++){
            Random random = new Random();
            int rand = random.nextInt(i + 1);
            int tmp = r[i];
            r[i] = r[rand];
            r[rand] = tmp;
        }
        // aCells表示你的蛇，这个数组存放了的身体的所有坐标，从数组尾表示蛇的头，数组头表示蛇的尾部。
        // 而bCells表示敌人的蛇，存放的顺序同上。
        for (int i = 0; i < 4; i++) {
            int t = r[i];
            int x = aCells.get(aCells.size() - 1).x + dx[t];
            int y = aCells.get(aCells.size() - 1).y + dy[t];
            if (x >= 0 && x < 13 && y >= 0 && y < 14 && g[x][y] == 0) return t;
        }
        return 2;
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
