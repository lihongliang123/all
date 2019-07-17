package com.charles.data.structure.queue;

/**
 * Charles Lee original, reprint please indicate the source
 * <p>
 * 环形数组队列
 *
 * @author CharlesLee
 */
public class AnnularArrayQueue<E> implements Queue<E> {

    /**
     * 表示数组的最大容量
     */
    private int maxSize;

    /**
     * 读取指针, 用来读取的元素在队列中的位置
     */
    private int readPointer;

    /**
     * 元素添加指针,用来表示需要将元素添加至数组的哪一个位置
     */
    private int addPointer;

    /**
     * 储存元素的数组
     */
    private Object[] array;

    public AnnularArrayQueue(int maxSize) {
        this.maxSize = maxSize;
        this.array = new Object[this.maxSize];
    }

    /**
     * 队列是否满
     */
    public boolean isFull() {
        return (addPointer + maxSize) % maxSize == readPointer && array[readPointer] != null;
    }

    /**
     * 队列是否为空
     */
    public boolean isEmpty() {
        return addPointer == readPointer && array[addPointer] == null;
    }


    public boolean add(E e) {
        if (isFull()) {
            throw new RuntimeException("队列已满,无法添加新的元素, 元素为 [ " + e.toString() + " ]");
        } else {
            array[addPointer] = e;
            addPointer += 1;
            if (addPointer == maxSize) {
                addPointer = 0;
            }
            return true;
        }
    }

    public E get() {
        if (isEmpty()) {
            throw new RuntimeException("队列中没有元素");
        }
        return (E) array[readPointer];
    }

    public E getAndRemove() {
        if (isEmpty()) {
            throw new RuntimeException("队列中没有元素");
        }
        E e = (E) array[readPointer];
        array[readPointer] = null;
        readPointer += 1;
        if (readPointer == maxSize) {
            readPointer = 0;
        }
        return e;
    }
}
