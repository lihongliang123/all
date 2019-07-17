package com.charles.data.structure.expression;


import java.math.BigDecimal;
import java.util.*;

/**
 * Charles Lee original, reprint please indicate the source
 * <p>
 * 前缀表达式(波兰式, 前缀记法), 中缀表达式(中缀记法), 后缀表达式(逆波兰式, 后缀记法)
 * <pre>
 *   (3 + 4) × 5 - 6 中缀表达式
 *   - × + 3 4 5 6 前缀表达式
 *   3 4 + 5 × 6 - 后缀表达式
 * </pre>
 * 1, 前缀表达式 : 前缀表达式的运算符位于操作数之前。
 * 2, 中缀表达式 : 一种常用的算术或逻辑公式表示方法, 操作符以中缀的形式处于操作数之间,是人们最常用的算术表示方法,
 * 对于人脑来说中缀是最容易理解的表达式,但是对于计算机来说是非常复杂的,所以在计算机计算表达式的时候,通常需要将中缀表达式转换为前缀或后缀表达式。
 * 然后进行计算求值,对于计算机来说,计算前缀和后缀是非常简单的一件事情。
 * 3, 后缀表达式 : 后缀表达式运算符位于操作数之后。
 * <p>
 * 将中缀表达式转换为后缀表达式
 * 1, 初始化两个栈,运算符栈(标记为 s1 )和存储中间结果的栈(标记为 s2 )
 * 2, 从左到右扫描中缀表达式
 * 3, 遇到操作数的时候将其压入s2
 * 4, 遇到运算符的时候,将其压入s1,在压入之前需要与栈顶的运算符比较其优先级,
 * 4.1, 如果s1为空,或者栈顶的运算符为左括号"(", 则直接将此运算符压入栈
 * 4.2, 若优先级比栈顶运算符优先级高,也直接压入
 * 4.3, 若优先级比栈顶运算符优先级低,将栈顶的运算符弹出并压入s2, 再次转到 4.1 与s1中的新的栈顶运算符相比较。
 * 5, 遇到括号的时候
 * 5.1, 如果遇到的是左括号"(",则直接压入s1
 * 5.2, 如果是右括号")",则一次弹出s1栈顶的运算符,并压入s2, 直到遇到左括号为止, 此时将这一对括号丢弃
 * 6, 重复步骤 2到5 , 直到表达式的最右边
 * 7, 将s1中剩余的运算符依次弹出并压入s2
 * 8. 依次弹出s2中的元素并输出,结果的逆序即为中缀表达式对应的后缀表达式
 *
 * @author CharlesLee
 */
public class Expression {

    /**
     * 提前将 符号的优先级定义好
     */
    private static final Map<Character, Integer> basic = new HashMap<Character, Integer>();

    static {
        basic.put('-', 1);
        basic.put('+', 1);
        basic.put('*', 2);
        basic.put('/', 2);
        // 在运算中 () 的优先级最高，但是此处因程序中需要 故设置为0
        basic.put('(', 0);
    }

    public static void main(String[] args) {
        String str = " 111.323 x 224.5213 X ( 1231233.1441 + 4123123.12355 ) / 5123123.1231232";
        Expression expression = new Expression();
        String res = expression.infixExpressionToList(str);
        System.out.println(expression.toSuffix(res));
        System.out.println(expression.dealEquation(expression.toSuffix(res)));
    }

    /**
     * 将一个后缀表达式进行计算，返回运算的结果
     *
     * @param equation 需要计算的后缀表达式
     * @author CharlesLee
     */
    public String dealEquation(String equation) {
        String[] arr = equation.split(",");
        List<String> list = new ArrayList<>();
        for (String s : arr) {
            int index = list.size();
            switch (s) {
                case "+": {
                    BigDecimal b1 = new BigDecimal(list.remove(index - 2));
                    BigDecimal b2 = new BigDecimal(list.remove(index - 2));
                    list.add(b1.add(b2).toString());
                    break;
                }
                case "-": {
                    BigDecimal b1 = new BigDecimal(list.remove(index - 2));
                    BigDecimal b2 = new BigDecimal(list.remove(index - 2));
                    list.add(b1.subtract(b2).toString());
                }
                break;
                case "*": {
                    BigDecimal b1 = new BigDecimal(list.remove(index - 2));
                    BigDecimal b2 = new BigDecimal(list.remove(index - 2));
                    list.add(b1.multiply(b2).toString());
                }
                break;
                case "/": {
                    BigDecimal b1 = new BigDecimal(list.remove(index - 2));
                    BigDecimal b2 = new BigDecimal(list.remove(index - 2));
                    list.add(b1.divide(b2, 20, BigDecimal.ROUND_HALF_UP).toString());
                }
                break;
                default:
                    list.add(s);
            }
        }

        return list.size() == 1 ? list.get(0) : "计算失败";
    }

    /**
     * 将一个标准的中缀表达式转换为后缀表达式
     */
    public String toSuffix(String infix) {
        // 定义队列,用来存储数字以及最后的后缀表达式
        List<String> resultList = new ArrayList<>();

        // 定义队列,用来存储运算符,最后该空间会被清空
        List<Character> stack = new ArrayList<>();

        char[] charArr = infix.trim().toCharArray();
        // 判断标准,用来表示表达式中可能出现的所有符号
        String standard = "*/+-()";
        // 用来临时保存数据
        char ch;

        // 用来记录字符的长度
        int len = 0;

        for (int i = 0; i < charArr.length; i++) {
            ch = charArr[i];
            // 如果当前变量为数字, 字母, 或者 小数点 .
            if (Character.isDigit(ch) || Character.isLetter(ch) || ch == '.') {
                len++;
            } else if (Character.isSpaceChar(ch)) {
                // 如果是空格,那么就说明可以往队列中存入了,这里判断大于0是因为如果空格是在开头,那么没有任何数据可以存储,所以需要跳过
                if (len > 0) {
                    resultList.add(String.valueOf(Arrays.copyOfRange(charArr, i - len, i)));
                    len = 0;
                }
                continue;
            } else if (standard.indexOf(ch) != -1) {
                // 如果是上面标记的任意一个符号
                if (len > 0) {
                    // 如果有长度, 说明截取到任意的运算符了, 一个数字就已经截取完毕了, 可以入栈了
                    resultList.add(String.valueOf(Arrays.copyOfRange(charArr, i - len, i)));
                    len = 0;
                }
                // 如果是左括号,那么就放入栈,进入下一次循环
                if (ch == '(') {
                    stack.add(ch);
                    continue;
                }
                // 如果栈空间里面有符号的存在,那么
                if (!stack.isEmpty()) {
                    // 获取最后一个元素的下标
                    int lastIndex = stack.size() - 1;
                    boolean flag = false;
                    // 如果当前为右括号,那么就需要一直取数据,取到左括号为止
                    // 当前这个ch并没有插入队列;查找到左括号的时候,循环结束了,所以左括号也不会放入队列
                    while (lastIndex >= 0 && ch == ')' && stack.get(lastIndex) != '(') {
                        resultList.add(String.valueOf(stack.remove(lastIndex)));
                        lastIndex--;
                        flag = true;
                    }

                    // 如果取得的不是括号里面的元素,那么就需要比较当前栈顶部运算符的优先级,如果当前队列的最后一个元素大于等于对比元素,那么就从符号队列中取出插入结果队列
                    while (lastIndex >= 0 && !flag && basic.get(stack.get(lastIndex)) >= basic.get(ch)) {
                        resultList.add(String.valueOf(stack.remove(lastIndex)));
                        lastIndex--;
                    }
                }

                // 如果当前的元素不是右括号,那么就需要保证这个符号进入队列,否则需要保证出队列
                if (ch != ')') {
                    stack.add(ch);
                } else {
                    stack.remove(stack.size() - 1);
                }
            }
            // 如果已经走到了中缀表达式的最后一位
            if (i == charArr.length - 1) {
                if (len > 0) {
                    resultList.add(String.valueOf(Arrays.copyOfRange(charArr, i - len + 1, i + 1)));
                }
                int index = stack.size() - 1;
                while (index >= 0) {
                    resultList.add(String.valueOf(stack.remove(index)));
                    index--;
                }
            }
        }
        return String.join(",", resultList);
    }

    /**
     * 将一个表达式进行去空格, 去特殊符号，转特殊服务的操作
     */
    public String infixExpressionToList(String infixExpression) {
        return infixExpression.replaceAll(" ", "").replaceAll("x", "*").replaceAll("X", "*");
    }
}
