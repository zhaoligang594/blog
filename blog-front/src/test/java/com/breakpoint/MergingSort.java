package com.breakpoint;

/**
 * 归并排序
 *
 * @author :breakpoint/赵立刚
 * @date : 2020/05/08
 */
public class MergingSort {

    public static int[] algorithm(int[] a) {

        if (null != a && a.length > 0) {
            sort(a, 0, a.length - 1);
        }
        return a;
    }


    private static void sort(int[] a, int l, int r) {
        if (l == r) {
            return;
        } else {
            int m = l + (r >> 1);
            sort(a, l, m);
            sort(a, m + 1, r);
            merge(a, l, r, m);
        }
    }


    private static void merge(int[] a, int l, int r, int m) {

        for (int i = l; i < m; i++) {

        }
    }
}
