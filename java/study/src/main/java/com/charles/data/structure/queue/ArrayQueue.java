package com.charles.data.structure.queue;

/**
 * Charles Lee original, reprint please indicate the source
 * <p>
 * 使用数组来创建队列,该队列是一个单向队列
 * <p>
 * 该队列有一个问题,那就是不可重复使用
 *
 * @author CharlesLee
 */
public class ArrayQueue<V> implements Queue<V> {

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


    public ArrayQueue(int maxSize) {
        this.maxSize = maxSize;
        array = new Object[maxSize];

        // 初始化为-1的原因是并未使用该队列
        this.readPointer = -1;
        this.addPointer = -1;
    }


    /**
     * 判断该队列是否已经满了
     */
    public boolean isFull() {
        return addPointer == maxSize - 1;
    }

    /**
     * 判断该队列是否是空的
     */
    public boolean isEmpty() {
        return addPointer == -1;
    }

    /**
     * 向队列中添加一个元素
     */
    public boolean add(V v) {
        if (isFull()) {
            throw new RuntimeException("队列已满,无法添加新的元素, 元素为 [ " + v.toString() + " ]");
        } else {
            addPointer += 1;
            array[addPointer] = v;
            return true;
        }
    }

    /**
     * 获取但不删除队首元素
     */
    public V get() {
        if (isEmpty() || addPointer == readPointer) {
            throw new RuntimeException("队列中没有元素");
        }

        return (V) array[readPointer + 1];
    }

    /**
     * 获取并清空队首元素
     */
    public V getAndRemove() {
        if (isEmpty() || addPointer == readPointer) {
            throw new RuntimeException("队列中没有元素");
        }
        V res = (V) array[readPointer + 1];
        array[readPointer + 1] = null;
        readPointer += 1;
        return res;
    }
}
