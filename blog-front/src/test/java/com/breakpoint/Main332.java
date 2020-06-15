package com.breakpoint;

/**
 * @author :breakpoint/赵立刚
 * @date : 2020/06/04
 */
public class Main332 {

    public static void main(String[] args) {

        int[] coins = {186,419,83,408};

        int i = new Main332().coinChange(coins, 6249);
        System.out.println(i);
    }

    public int coinChange(int[] coins, int amount) {

        int count = 0, index = coins.length - 1;

        qSort(coins, 0, coins.length - 1);

        count += amount / coins[index];

        amount %= coins[index];

        index--;

        while (index >= 0 && (amount % coins[index]) != 0) {
            count += amount / coins[index];
            amount %= coins[index];
            index--;
        }

        if (amount > 0 && index >= 0) {
            count += amount / coins[index];
            amount %= coins[index];
        }

        if (count >= 0 && amount == 0) {
            return count;
        }

        return -1;
    }


    private void qSort(int[] arr, int l, int r) {
        if (l < r) {
            int partition = getPartition(arr, l, r);
            qSort(arr, l, partition - 1);
            qSort(arr, partition + 1, r);
        }
    }

    private int getPartition(int[] arr, int l, int r) {
        int temp = arr[l];
        while (l < r) {
            while (l < r && arr[r] >= temp) r--;
            if (l < r) arr[l] = arr[r];
            while (l < r && arr[l] <= temp) l++;
            if (l < r) arr[r] = arr[l];
        }
        arr[r] = temp;
        return l;
    }


}
