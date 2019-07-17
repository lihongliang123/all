package com.charles.data.structure.linked;

import java.util.List;

/**
 * Charles Lee original, reprint please indicate the source
 * <p>
 * 双向链表
 *
 * @author CharlesLee
 */
public class DoubleLinkedList<E> implements LinkedList<E> {

    /**
     * 头节点,用来定位链表的开始位置
     */
    private Node<E> first;

    /**
     * 尾节点,用来定位链表的最后一个节点
     */
    private Node<E> last;

    /**
     * 这个链表中有多少个节点
     */
    private int size;

    public int getSize() {
        return size;
    }

    /**
     * 获取链表中的指定位置的节点
     * <p>
     * 如果在指定位置获取不到任何节点信息,则返回为null
     */
    private Node<E> getNode(int index) {
        int v = 0;
        Node<E> n = first;
        while (n != null) {
            v += 1;
            if (index == v) {
                return n;
            }
            n = n.next;
        }
        return null;
    }

    /**
     * 修改节点的值,这个很简单,就不细说了
     */
    public boolean update(int index, E data) {
        Node<E> n = getNode(index);
        if (n == null) {
            return false;
        } else {
            n.data = data;
            return true;
        }
    }

    /**
     * 向单链表中添加一个元素
     */
    public boolean add(E e) {
        return add(new Node<>(last, e, null));
    }

    public boolean add(Node<E> node) {
        // 如果说链表中一个元素都没有,那么将头和尾的链表分别设置为添加的第一个元素
        if (first == null) {
            first = node;
            last = node;
        } else {
            // 如果链表中已经有元素了, 那么这个时候链表的头则不需要动, 将链表的尾节点的下一个节点设置为新的节点,然后将新的节点设置成为尾节点
            last.next = node;
            last = node;
        }
        size += 1;
        return true;
    }

    /**
     * 从链表中删除元素,该链表实现方式删除元素较为复杂
     * <p>
     * 双向链表可以实现自我删除
     */
    public boolean remove(int index) {
        if (size == 0) {
            return false;
        }
        int v = 0;
        Node<E> n = first;
        while (n != null) {
            v += 1;
            if (index == v) {
                // 如果链表长度为1,那么清空该链表所有节点即可
                if (size == 1) {
                    first = null;
                    last = null;
                    size -= 1;
                    return true;
                } else if (1 == index) {
                    // 如果需要删除的节点为第一个元素
                    first = first.next;
                    size -= 1;
                    return true;
                } else if (index == size) {
                    // 如果需要删除的是该链表的最后一个节点
                    last = last.pre;
                    size -= 1;
                    return true;
                } else {
                    // 删除中间的节点,进行自我删除
                    n.pre.next = n.next;
                    n.next.pre = n.pre;
                    size -= 1;
                    return true;
                }
            }
            n = n.next;
        }
        return false;
    }

    @Override
    public String toString() {
        Node<E> n = first;
        while (n != null) {
            if (n.pre != null) {
                System.out.println(n.data.toString() + " pre = " + n.pre.toString());
            }
            if (n.data != null) {
                System.out.println(n.data.toString() + " data = " + n.data.toString());
            }
            if (n.next != null) {
                System.out.println(n.data.toString() + " next = " + n.next.toString());
            }
            n = n.next;
        }
        return "";
    }

    private static class Node<E> {
        /**
         * 指向上一个节点,默认为null
         */
        private Node<E> pre;

        /**
         * 当前节点所存放的具体的数据信息
         */
        private E data;

        /**
         * 指向下一个节点,默认为null
         */
        private Node<E> next;

        Node(Node<E> pre, E data, Node<E> next) {
            this.pre = pre;
            this.data = data;
            this.next = next;
        }

        @Override
        public String toString() {
            return "Node{" +
                    "data=" + data +
                    '}';
        }
    }
}
