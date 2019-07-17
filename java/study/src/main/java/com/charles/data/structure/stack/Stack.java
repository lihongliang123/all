package com.charles.data.structure.stack;

/**
 * Charles Lee original, reprint please indicate the source
 * <p>
 * 栈是一个先入后出的有序列表(FILO, first in last out).
 * 栈是限制线性表中元素的插入和删除只能在线性表的同一端进行的一种特殊线性表.允许插入和删除的一端,为变化的一端,称之为栈顶(top),另一端为固定的一端,称为栈底(bottom)
 * 根据栈的定义,最先放入栈的元素在栈底,最后放入的元素在栈顶,删除元素刚好相反,最后放入的元素最先删除,最先放入的元素最后删除
 * <p>
 * 栈的应用场景
 * 1: 子程序的调用,在跳往子程序前,会先将下个指令的地址存到堆栈中,直到子程序执行完毕以后再将地址取出,以回到原来的程序中
 * 2: 处理递归调用,和子程序调用类似,只是除了存储下一个指令的地址外,也将参数、区域变量等数据存入堆栈中.
 * 3: 表达式的转换[中缀表达式转后缀表达式]与求值(实际需求)
 * 4: 二叉树遍历
 * 5: 图形的深度优先(depth - first)搜索算法
 *
 * @author CharlesLee
 */
public interface Stack<E> {
}
