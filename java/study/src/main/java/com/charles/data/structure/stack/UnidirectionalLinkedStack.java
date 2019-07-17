package com.charles.data.structure.stack;

/**
 * Charles Lee original, reprint please indicate the source
 * <p>
 * 使用链表来实现栈
 *
 * @author CharlesLee
 */
public class UnidirectionalLinkedStack<E> implements Stack<E> {

    /**
     * 链表的头结点
     */
    private Node<E> first;

    /**
     * 栈顶
     */
    private int top;

    private boolean addNode(E e) {
        return addNode(new Node<>(e, null));
    }

    private boolean addNode(Node<E> node) {
        if (first == null) {
            first = node;
            return true;
        } else {
            Node<E> n = first;
            while (n != null) {
                if (n.next == null) {
                    n.next = node;
                    break;
                }
                n = n.next;
            }
            return true;
        }
    }

    /**
     * 进行入栈操作
     */
    public void push(E e) {
        addNode(e);
        top++;
    }

    /**
     * 出栈操作
     */
    public E pop() {
        if (top == 0) {
            return null;
        }
        Node<E> n = first;
        int count = 0;
        while (n != null) {
            count++;
            if (count == top) {
                top--;
                return n.data;
            }
            n = n.next;
        }
        return null;
    }

    /**
     * 链表的节点对象, 这是单向链表
     */
    private static class Node<E> {
        private E data;
        private Node<E> next;

        Node(E data, Node<E> next) {
            this.data = data;
            this.next = next;
        }

        @Override
        public String toString() {
            return "Node{" + "data=" + data + '}';
        }
    }
}
