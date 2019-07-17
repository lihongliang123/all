package com.charles.algorithm.search;

/**
 * Charles Lee original, reprint please indicate the source
 * <p>
 * 查找算法
 * 1,线性查找
 * 2,二分查找/折半查找
 * 3,插值查找
 * 4,斐波拉契查找
 *
 * @author CharlesLee
 */
public interface Search<E, V, R> {

    /**
     * 查找, 从E中查找V,存在则返回R,否则返回null
     */
    R search(E e, V v);
}
