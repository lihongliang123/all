package com.charles.data.structure.linked;

import java.util.Stack;

/**
 * Charles Lee original, reprint please indicate the source
 * <p>
 * 单向链表,只能按照一个方向进行数据的添加
 * <p>
 * 问题：1,查找方向固定; 2,单向链表不可以自我删除
 * <p>
 * todo 单链表的反转功能未完成
 *
 * @author CharlesLee
 */
public class UnidirectionalLinkedList<E> implements LinkedList<E> {

    /**
     * 头节点,用来定位链表的开始位置
     */
    private Node<E> first;

    /**
     * 尾节点,用来定位链表的最后一个节点
     */
    private Node<E> last;

    /**
     * 向单链表中添加一个元素
     */
    public boolean add(E e) {
        return add(new Node<>(e, null));
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
        return true;
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
     * 获取链表中的有效个数
     * <p>
     * 从链表表首开始,循环遍历所有有效的节点,并将数量想加
     * <p>
     * 其实可以用成员变量的方式来提升效率,在设置元素的时候增加,删除元素的时候减少,不过这里为了做演示就不用这种方式了
     */
    public int size() {
        Node<E> n = first;
        int size = 0;
        while (n != null) {
            n = n.next;
            size += 1;
        }
        return size;
    }

    public E get(int index) {
        Node<E> n = getNode(index);
        return n == null ? null : n.data;
    }

    /**
     * 从链表中删除元素,该链表实现方式删除元素较为复杂
     */
    public boolean remove(int index) {
        int v = 0;
        // 当链表中没有元素的时候,自然也就不存在删除这一说了
        Node<E> n = first;
        while (n != null) {
            v += 1;
            // 遍历整个链表,并获取到链表节点的位置值,当当前节点的位置和需要删除的位置是同一个位置的时候,进入删除逻辑
            if (index == v) {
                int size = size();
                // 如果链表只有一个节点,删除这个节点以后整个链表就是空的了,所以只需要置空首和尾的引用即可
                if (size == 1) {
                    first = null;
                    last = null;
                    // 如果删除的是链表的第一个节点,那么将链表表首的节点直接指向下一个节点即可,原first节点的数据因为没有引用指向,所以会被垃圾回收
                } else if (index == 1) {
                    first = getNode(1 + 1);
                    // 如果删除的是链表的最后一个节点,原理同删除链表的第一个节点,只需要把最后一个节点的前一个节点的next置空即可
                } else if (size == index) {
                    Node<E> node = getNode(index - 1);
                    node.next = null;
                    last = node;
                } else {
                    // 删除中间的节点, 那么就需要将中间的节点剔除,查询到需要删除节点的上一个节点,然后将上一节点的next指向需要删除的节点的下一个节点即可
                    Node<E> node = getNode(index - 1);
                    node.next = node.next.next;
                }
                return true;
            }
            n = n.next;
        }
        return false;
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

    @Override
    public String toString() {
        Node<E> n = first;
        while (n != null) {
            System.out.println(n.data);
            n = n.next;
        }
        return "";
    }

    /**
     * 获取链表中倒数的第 index 个节点
     */
    public Node<E> getCountingBackwardNode(int index) {
        int size = size();
        // 从链表中获取倒数的第 index个元素, 那么就是从正数的第  size - index + 1 个元素, 比如链表中有8个元素,获取倒数第三个就是获取 8 - 3 + 1个元素
        int n = size - index + 1;
        // 当链表为空的时候是不可能获取到元素的,所以返回null
        if (n <= 0 || n > size) {
            return null;
        }
        int temp = 0;
        Node<E> node = first;
        while (node != null) {
            temp += 1;
            if (temp == n) {
                return node;
            }
            node = node.next;
        }
        return null;
    }

    /**
     * 将当前单链表进行一个翻转操作,返回新的链表,原链表保持不动
     */
    public UnidirectionalLinkedList<E> flipNew() {
        int temp = size();
        UnidirectionalLinkedList<E> result = new UnidirectionalLinkedList<>();
        Node<E> n;
        while ((n = getNode(temp--)) != null) {
            result.add(n.data);
        }
        return result;
    }

    /**
     * 逆序打印输出该链表,使用栈的方式实现
     */
    public void reverseOrderPrintOutputByStack() {
        Stack<E> stack = new Stack<>();
        Node<E> n = first;
        while (n != null) {
            stack.push(n.data);
            n = n.next;
        }
        while (stack.size() > 0) {
            System.out.println(stack.pop().toString());
        }
    }

    private static class Node<E> {
        private E data;
        private Node<E> next;

        Node(E data, Node<E> next) {
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
