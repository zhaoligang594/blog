package com.breakpoint;


/**
 * @author :breakpoint/赵立刚
 * @date : 2020/03/26
 */
public class Solution {


    public static void main(String[] args) {

        MinStack minStack = new MinStack();

        minStack.push(1);
        minStack.push(25);
        System.out.println(1);

    }


}

class MinStack {


    class Node {
        Integer value;
        Node pre;
        Node next;
        public Node(Integer value) {
            this.value = value;
        }
    }

    private transient Node head;

    private transient Node tail;


    /**
     * initialize your data structure here.
     */
    public MinStack() {

    }

    public void push(Integer x) {
        if (null != x) {
            Node p = new Node(x);
            if (null == head && null == tail) {
                head = p;
                tail = p;
                p.next = tail;
                p.pre = head;
            } else {
                p.next = tail.next;
                p.pre = tail;
                tail.next = p;
                tail = p;
            }
        }
    }

    public void pop() {
        if(null!=tail){

        }

    }

    public Integer top() {


        return null;

    }

    public Integer getMin() {

        return null;
    }
}


