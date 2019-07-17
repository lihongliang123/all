package com.charles.data.structure.linked;

/**
 * Charles Lee original, reprint please indicate the source
 * <p>
 * 单向的环形链表
 * <p>
 * Joseph(约瑟夫问题) :
 * 设编号1,2,...n的n个人围坐在一圈,约定编号k(1<=k<=n) 的人从1开始报数,数到m的那个人出列,它的下一位又从1开始报数,
 * 数到m的那个人又出列,依次类推,直到所有人都出列为止,由此产生一个出队编号的序列
 *
 * @author CharlesLee
 */
public class UnidirectionalCircleLinkedList<E> implements LinkedList<E> {


    /**
     * 头节点,用来定位链表的开始位置
     * <p>
     * 因为是环形链表,所以开始的位置也就是结束的位置
     */
    private Node<E> first;

    /**
     * 当前链表一共有多少个元素,添加该成员变量是为了去除不必要的遍历操作,因为那真的很费时
     */
    private int size;

    @Override
    public String toString() {
        Node<E> n = first;
        while (n != null) {
            System.out.println(n.data);
            if (n.next.equals(first)) {
                break;
            }
            n = n.next;
        }
        return "";
    }

    public boolean add(E e) {
        return add(new Node<>(e, null));
    }

    public boolean add(Node<E> node) {
        if (first == null) {
            first = node;
            first.next = first;
            size = 1;
        } else {
            Node<E> n = first;
            while (n != null) {
                if (n.next != null && n.next.equals(first)) {
                    // 到这里说明节点n是最后添加的一个节点,所以
                    n.next = node;
                    node.next = first;
                    size += 1;
                    break;
                }
                n = n.next;
            }
        }
        return true;
    }


    /**
     * 约瑟夫问题的解决方案
     *
     * @param startNum 从哪个位置开始数
     * @param countNum 数几个
     * @author CharlesLee
     */
    public void josephProblem(int startNum, int countNum) {
        if (countNum < 1 || startNum > size || startNum < 1) {
            throw new RuntimeException("传递的参数不对");
        }
        if (size == 1) {
            System.out.println(first.data);
            return;
        }
        int i = 0;
        while (first != null) {
            i++;
            if (i == 1) {
                calculation(startNum, countNum + 1);
            } else {
                calculation(1, countNum);
            }
        }
    }

    public void calculation(int startNum, int countNum) {
        // 计算从第某个位置开始计算
        Node<E> e = first;
        int ct = 0;
        while (e != null) {
            ct++;
            if (ct == startNum) {
                // 把当前记录为1,且根据 countNum 计算下一个人
                int count = 0;
                Node<E> cn = e;
                while (cn != null) {
                    count++;
                    if (count == countNum - 1) {
                        System.out.println(cn.next.data);
                        remove(cn.next);
                        return;
                    }
                    cn = cn.next;
                }
            }
            e = e.next;
        }
    }

    public boolean remove(Node<E> node) {
        int index = 0;
        Node<E> e = first;
        while (e != null) {
            index++;
            if (e == node) {
                return remove(index);
            }
            e = e.next;
        }
        return false;
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

    public boolean remove(int index) {
        int v = 0;
        // 当链表中没有元素的时候,自然也就不存在删除这一说了
        Node<E> n = first;
        while (n != null) {
            v++;
            if (index == v) {
                if (size == 1) {
                    first = null;
                    size = 0;
                    return true;
                } else if (index == 1) {
                    first = getNode(1 + 1);
                    size--;
                    getNode(size).next = first;
                    return true;
                } else if (size == index) {
                    getNode(size - 1).next = first;
                    size--;
                    return true;
                } else {
                    // 删除中间的节点, 那么就需要将中间的节点剔除,查询到需要删除节点的上一个节点,然后将上一节点的next指向需要删除的节点的下一个节点即可
                    Node<E> node = getNode(index - 1);
                    node.next = node.next.next;
                    size--;
                    return true;
                }
            }
            n = n.next;
        }
        return false;
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
            return "Node{data=" + data + "}";
        }
    }
}

/**
 * 为约瑟夫问题特别创建的对象
 */
class People {


    private String name;

    /**
     * 编号
     */
    private int number;

    public People(String name, int number) {
        this.name = name;
        this.number = number;
    }

    @Override
    public String toString() {
        return "People{" +
                "name='" + name + '\'' +
                ", number=" + number +
                '}';
    }
}
