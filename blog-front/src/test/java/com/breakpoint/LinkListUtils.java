package com.breakpoint;


/**
 * @author :breakpoint/赵立刚
 * @date : 2020/05/07
 */
public class LinkListUtils {

    public static ListNode getListNodeByArray(int[] array) {

        ListNode head, cur;

        if (array.length > 0) {
            head = new ListNode(array[0]);
        } else {
            head = null;
        }

        if (null != head) {
            cur = head;
            for (int i = 1; i < array.length; i++) {
                cur.next = new ListNode(array[i]);
                cur = cur.next;
            }
        }

        return head;

    }


    public static ListNode getListNodeByArray(int[] array, int pos) {


        if (-1 == pos) {
            return getListNodeByArray(array);
        } else {

            //
            ListNode head, cur = null, posNode = null;

            if (array.length > 0) {
                head = new ListNode(array[0]);
            } else {
                return null;
            }

            if (0 == pos) {
                posNode = head;
            }

            if (null != head) {
                cur = head;
                for (int i = 1; i < array.length; i++) {
                    cur.next = new ListNode(array[i]);
                    cur = cur.next;
                    if (pos == i) {
                        posNode = cur;
                    }
                }
            }

            cur.next = posNode;

            return head;
        }

    }


}