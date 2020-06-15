package com.breakpoint;


import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * 测试表达式的计算
 *
 * @author :breakpoint/赵立刚
 * @date : 2019/06/11
 */
public class ExpressionTest {

    /**
     * 一共有4种表达式
     */
    /*
     *      + - *  /
     *   +  0 0 -1 -1
     *   -  0 0 -1 -1
     *   *  1 1  0  0
     *   /  1 1  0  0
     *
     *
     * */
    int[][] opt = {
            {0, 0, -1, -1},
            {0, 0, -1, -1},
            {1, 1, 0, 0},
            {1, 1, 0, 0}
    };


    static Map<String, Integer> map = new HashMap<>(4);

    static {
        map.put("+", 0);
        map.put("-", 1);
        map.put("*", 2);
        map.put("/", 3);
    }


    String express = "2*3-2*4";


    static Stack optS = new Stack();
    static Stack numS = new Stack();


    @Test
    public void test() {

        String[] split = express.split("[+|\\-|*|/]");

        for (String str : split) {
            System.out.println(str);
        }

        char[] chars = express.toCharArray();

        /**
         * 分解表达式
         */
        for (char str : chars) {

            if (str > '9' || str < '0') {
                /**
                 * 说明是操作符号
                 */

            }else {

            }
        }


    }
}
