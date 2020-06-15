package com.breakpoint;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author :breakpoint/赵立刚
 * @date : 2020/06/06
 */
public class Main46 {

    public static void main(String[] args) {
        int[] nums = {1, 2, 3};
        List<List<Integer>> permute = new Main46().permute(nums);
        System.out.println(permute);
    }

    int[] tag;
    int[] temp;

    List<List<Integer>> permute=new ArrayList<>();

    public List<List<Integer>> permute(int[] nums) {



        tag = new int[nums.length];
        temp = new int[nums.length];

        int index = 0;

        for (int i = 0; i < nums.length; i++) {
            if (tag[i] == 0) {
                tag[i] = 1;
                temp[i] = nums[index];
                permute(nums, index+1);
                tag[i] = 0;
            }
        }

        return permute;
    }

    private void permute(int[] nums, int i) {
        if (i < nums.length) {
            for (int j = 0; j < nums.length; j++) {
                if (tag[j] == 0) {
                    tag[j] = 1;
                    temp[j] = nums[i];
                    permute(nums, i+1);
                    tag[j] = 0;
                }
            }
        }else {
            ArrayList<Integer> objects = new ArrayList<>();

            for (int j = 0; j < nums.length; j++) {
                objects.add(temp[j]);
            }
            permute.add(objects);
            //System.out.println(Arrays.toString(temp));
        }
    }


}
