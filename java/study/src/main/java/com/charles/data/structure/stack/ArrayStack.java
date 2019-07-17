package com.charles.data.structure.stack;

/**
 * Charles Lee original, reprint please indicate the source
 * <p>
 * 用数组来模拟栈
 *
 * @author CharlesLee
 */
public class ArrayStack<E> implements Stack<E> {

    public ArrayStack(int capacity) {
        if (capacity <= 0) {
            throw new RuntimeException("栈空间的容量不能为 不大于0的一个数");
        }
        this.capacity = capacity;
        array = new Object[capacity];
    }

    /**
     * 容量,用来表示该栈最多可以存放多少个元素
     */
    private int capacity;

    /**
     * 用来存储数据的数组
     */
    private Object[] array;

    /**
     * 表示栈顶指针,指向栈顶的那个数据,
     */
    private int top = -1;

    /**
     * 栈空间是否满了
     */
    public boolean isFull() {
        return top == capacity - 1;
    }


    /**
     * 栈空间是否为空的
     */
    public boolean isEmpty() {
        return top == -1;
    }


    /**
     * 进行入栈操作
     */
    public void push(E e) {
        if (!isFull()) {
            array[++top] = e;
        }
    }

    /**
     * 出栈操作
     */
    public E pop() {
        if (!isEmpty()) {
            return (E) array[top--];
        }
        return null;
    }

    /**
     * 遍历栈,从栈顶开始遍历
     */
    public void ergodic() {
        if (!isEmpty()) {
            for (int i = top; i > -1; i--) {
                System.out.println(array[i].toString());
            }
        }
    }
}
