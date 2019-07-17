package com.charles.jvm.clazz;

/**
 * Charles Lee original, reprint please indicate the source
 * <p>
 * 数组创建的本质
 * <p>
 * 对于数组实例来说,其类型是由jvm在运行期间动态生成的, 表示为以下方法中的结果
 * 动态生成的类型其父类型就是{@link java.lang.Object}
 * <p>
 * 对于数组来说,javaDoc经常将构成数组的元素为Component, 实际上就是将数组降低一个维度后的类型
 *
 * @author CharlesLee
 */
public class CreateArrayOperation {
    public static void main(String[] args) {
        // 一维数组的输出结果为: class [Lcom.charles.jvm.clazz.CreateArrayOperation;
        System.out.println(new CreateArrayOperation[1].getClass());
        // 二维数组的输出结果为: class [[Lcom.charles.jvm.clazz.CreateArrayOperation;
        System.out.println(new CreateArrayOperation[1][2].getClass());
        // 三维数组的输出结果为: class [[[Lcom.charles.jvm.clazz.CreateArrayOperation;
        System.out.println(new CreateArrayOperation[1][2][1].getClass());

        // 原生数据类型数组
        System.out.println(new int[1].getClass());
        System.out.println(new double[1].getClass());
    }
}
