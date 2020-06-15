package com.android.app.test;

import java.util.ArrayDeque;
import java.util.Scanner;

/**
 * 主要想法  因为是满二叉树 所以可以直接求得 不用其他的创建树的方法
 *
 * @author :breakpoint/赵立刚
 * @date : 2019/02/18
 */
public class Main2 {
    public static void main(String[] args) {


        Scanner sc = new Scanner(System.in);
        /**
         * 数的节点的个数
         */
        int n = sc.nextInt();


        int[] treeNodes = new int[n + 1];

        /**
         * 获取到树的节点
         */
        for (int i = 1; i < treeNodes.length; i++) {
            treeNodes[i] = sc.nextInt();
        }

        /**
         * tree depth
         */
        int d = sc.nextInt();


        ArrayDeque<Integer> queue = new ArrayDeque<>();


        queue.push(1);

        String result = "";

        /**
         * 下标志的
         */
        int start = 2 << (d - 2);
        int end = 2 << (d-1);

        /**
         * 移除
         */
        while (!queue.isEmpty()) {
            int i = queue.removeLast();
            if (i >= start & i < end) {

                result += treeNodes[i] + " ";
            }
            int childL = 2 * i;
            if (childL < treeNodes.length) {
                queue.push(childL);
            }

            int childR = 2 * i + 1;
            if (childR < treeNodes.length) {
                queue.push(childR);
            }
        }

        if(result.trim().equals("")){
            System.out.println("EMPTY");
        }else {
            System.out.println(result.trim());
        }


    }


}
