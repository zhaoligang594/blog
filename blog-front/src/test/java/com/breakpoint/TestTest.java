package com.breakpoint;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

/**
 * @author :breakpoint/赵立刚
 * @date : 2019/04/08
 */
public class TestTest{


    @Test
    public void test() throws Exception {



        File file = new File("/Users/breakpoint/idea_work/blog/blog-front/src/test/java/com/breakpoint/read.txt");


        BufferedReader bufferedReader=new BufferedReader(new FileReader(file));

        String len=bufferedReader.readLine();

        System.out.println("[");
        while (len!=null){

            len=len.replaceAll(" \t" ," ").trim();
            System.out.println(len+";");

            len=bufferedReader.readLine();
        }
        System.out.println("]");


    }


}
