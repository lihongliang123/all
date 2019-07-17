package com.charles.algorithm.recursion;

/**
 * Charles Lee original, reprint please indicate the source
 * 递归(程序调用自身的编程技巧称之为递归,递归作为一种算法在程序设计语言中被广泛应用)
 * 递归的重要守则
 * 1, 执行一个方法的时候,就创建一个新的,受保护的独立空间(栈空间)
 * 2, 方法的基本数据类型局部变量是独立的,不会相互影响.引用类型的变量是共享的
 * 3, 递归必须向退出递归的条件逼近,否则就会无限递归,在java中栈空间深度是有限的,无限递归会出现 {@link java.lang.StackOverflowError} 错误
 * 4, 当一个方法执行完毕的时候,或者遇到return的时候,就会返回,遵守谁调用谁,就将结果返回给谁,同时当方法执行完毕或者返回时,该方法也就执行完毕了
 *
 * @author CharlesLee
 */
public class Recursion {
    public static void main(String[] args) {

    }
}
