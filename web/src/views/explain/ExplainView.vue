<template>
    <ContentField>
        <div>
            <h3 style="font-style:italic; font-weight: 1000;" >Game Rules:</h3>
            <hr>
            <div>
                <div>On the <p class="stress">Battle</p> page, click <div class="stress">Start Matching</div> to begin the game.</div>
                <div>After creating a <p class="stress">Bot</p> on the <p class="stress">My Bots</p> page, you can select which AI to use via the <p class="stress">dropdown</p> above the match button. Choose <p class="stress">Play Myself</p> to control the snake manually.</div>
                <div><p class="stress">Controls:</p> Use WASD for up, down, left, and right. You have 5 seconds each round to choose a direction; after that, the snake moves in the last direction you pressed. If you make no choice, you lose.</div>
                <div>At the start, you are randomly placed at the bottom-left or bottom-right. Your position is shown at the <p class="stress">bottom of the map</p>.</div>
            </div>
            <hr>
            <h3 style="font-style:italic; font-weight: 1000;" >Sample Code:</h3>
            <div>
                <VAceEditor v-model:value="content" @init="editorInit"
                                                                lang="java" theme="textmate" style="height: 1765px"
                                                                :options="{
                                                                    enableBasicAutocompletion: true,
                                                                    enableSnippets: true, 
                                                                    enableLiveAutocompletion: true, 
                                                                    fontSize: 15, 
                                                                    tabSize: 4, 
                                                                    showPrintMargin: false, 
                                                                    highlightActiveLine: true,
                                                                    readOnly: true
                                                                }" />
            </div>
        </div>

        
    </ContentField>
</template>

<script>
import ContentField from '../../components/ContentField'
import { ref } from 'vue';
import ace from 'ace-builds';
import { VAceEditor } from 'vue3-ace-editor';
import 'ace-builds/src-noconflict/mode-c_cpp';
import 'ace-builds/src-noconflict/mode-java';
import 'ace-builds/src-noconflict/mode-json';
import 'ace-builds/src-noconflict/theme-chrome';
import 'ace-builds/src-noconflict/ext-language_tools';

export default {
components: {
    ContentField,
    VAceEditor,
},
setup() {
    let content = ref('');

    content.value = `package com.kob.botrunningsystem.utils;

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

    private boolean check_tail_increase(int step) {    // whether snake length increases this step
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

    public Integer nextMove(String input) {     // main entry: return value is next move direction
        String[] strs = input.split("#");   // length increases every turn for first 10 steps, then every 3 steps
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
        // g is 13x14: 1 = wall or snake body (blocked), 0 = walkable
        int[] dx = {-1, 0, 1, 0}, dy = {0, 1, 0, -1};

        int[] r = {0, 1, 2, 3};

        for (int i = 0; i < 4; i ++){
            Random random = new Random();
            int rand = random.nextInt(i + 1);
            int tmp = r[i];
            r[i] = r[rand];
            r[rand] = tmp;
        }
        // aCells: your snake (tail at index 0, head at last index); bCells: opponent, same order
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
`;

    ace.config.set(
            "basePath",
            "https://cdn.jsdelivr.net/npm/ace-builds@" +
            require("ace-builds").version +
            "/src-noconflict/");

    return {
        content,
    }

}
}

</script>

<style scoped>

.stress{
    font-weight: bold;
    display: inline-block;
}

</style>