package com.breakpoint;

import java.util.ArrayDeque;

/**
 * @author :breakpoint/赵立刚
 * @date : 2020/04/01
 */
public class TreeUtils {

    /**
     * 创建我们的二叉树
     *
     * @param treeNodes
     * @return
     */
    public static TreeNode createTree(Integer[] treeNodes) {
        ArrayDeque<TreeNode> queue = new ArrayDeque<>(16);
        TreeNode returnTreeNode = new TreeNode(treeNodes[0]);
        queue.push(returnTreeNode);


        int i = 1;


        while (!queue.isEmpty()) {
            TreeNode pop = queue.removeLast();
            if (i < treeNodes.length && null != treeNodes[i]) {
                TreeNode left = new TreeNode(treeNodes[i]);
                pop.left = left;
                queue.push(left);
                //i++;
            }
            i++;
            if (i < treeNodes.length && null != treeNodes[i]) {
                TreeNode right = new TreeNode(treeNodes[i]);
                pop.right = right;
                queue.push(right);

            }
            i++;
        }
        return returnTreeNode;
    }
}
