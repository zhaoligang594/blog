package com.breakpoint;

import java.util.Map;

/**
 * @author :breakpoint/赵立刚
 * @date : 2020/05/13
 */
public class Main {
    public static void main(String[] args) {

//        int[][] grid={{0,0,1,0,0,0,0,1,0,0,0,0,0},
//        {0,0,0,0,0,0,0,1,1,1,0,0,0},
//        {0,1,1,0,1,0,0,0,0,0,0,0,0},
//        {0,1,0,0,1,1,0,0,1,0,1,0,0},
//        {0,1,0,0,1,1,0,0,1,1,1,0,0},
//        {0,0,0,0,0,0,0,0,0,0,1,0,0},
//    {0,0,0,0,0,0,0,1,1,1,0,0,0},
//        {0,0,0,0,0,0,0,1,1,0,0,0,0}};

        int[][] grid={{1}};
        int i = new Main().maxAreaOfIsland(grid);

        System.out.println(i);


    }


    int[][] tag = null;


    public int maxAreaOfIsland(int[][] grid) {

        tag = new int[grid.length][grid[0].length];
        int max = 0;

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                count(i, j, grid);
                if (temp > max) {
                    max = temp;
                }
                temp = 0;
            }
        }


        return max;
    }

    int temp = 0;

    private void count(int i, int j, int[][] grid) {
        if (i >= 0 && i < grid.length && j >= 0 && j < grid[0].length && tag[i][j] != 1 && grid[i][j] == 1) {
            tag[i][j] = 1;
            temp++;
            count(i + 1, j, grid);
            count(i - 1, j, grid);
            count(i, j + 1, grid);
            count(i, j - 1, grid);
        }
    }


}

