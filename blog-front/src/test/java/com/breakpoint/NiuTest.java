package com.breakpoint;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.Test;

/**
 * @author :breakpoint/赵立刚
 * @date : 2019/10/11
 */
public class NiuTest {

    /**
     * 是n位数
     */
    private static final int n = 4;


    @Test
    public void test01() {

        TreeNode [] treeNodes=new TreeNode[10];

        /*     首先创建一个树      */

        for (int i=1;i<treeNodes.length;i++){
            TreeNode t_root = new TreeNode(0);
            treeNodes[i]=t_root;

        }


    }

    private void createTree(TreeNode t_root, int n) {
        if (n >= 0) {

        }
    }

}

