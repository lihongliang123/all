package com.charles.jvm.clazz;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.UUID;

/**
 * Charles Lee original, reprint please indicate the source
 * <pre>
 * 类加载(指的是将类的.class文件中的二进制数据读取到内存中,然后再内存中创建一个  {@link java.lang.Class} 对象(虚拟机规范中并未说明Class对象位于哪里,HotSpot虚拟机将其放入在了方法区中)用来封装类在方法区内的数据结构)
 * 在java代码中,类型(可以简单的理解为class对象)的加载(最常见的情况是从磁盘中将字节码文件加载到内存中),
 * 连接(分为很多阶段,包括class的校验,将类与类之间的符号引用转换成为直接引用等操作)
 * 与初始化(对于静态代码,常量等做赋值等操作)的过程都是在程序运行期间进行完成的
 * <p>
 * 为什么会在运行期间完成这些操作呢? 这样提供了更大的灵活性,增加了更多的可能性
 * <p>
 * 类加载器的深入
 * java虚拟机与程序的生命周期,在以下几种情况,java虚拟机回结束其生命周期
 * 1, 执行了 System.exit() 方法;
 * 2, 程序正常结束;
 * 3, 程序在执行的过程中遇到了异常或错误而终止;
 * 4, 由于操作系统出现错误而导致的java虚拟机进程的终止;
 * <p>
 * 类的加载,连接,初始化,使用与卸载
 * 加载 : 查找并加载类的二进制数据
 * 连接 :
 * 1, 验证 : 确保被加载的类的正确性
 * 2, 准备 : 为类的静态变量分配内存,并将其初始化为默认值(非static还没有被初始化)
 * 3, 解析 : 把类中的符号引用(间接的引用方式,通过符号的表示)转换为直接引用(通过指针的方式直接指向)
 * 解析 : 为类的静态变量赋予正确的初始化值
 * 使用 : 正常使用
 * 卸载 : 使用完毕以后从内存中进行删除,这里的删除是指删除的class,而不是class出来的对象,一旦class被卸载,那么就不可以初始化class对象了
 * <p>
 * java程序对类的使用方式可以分为两种
 * 1, 主动使用, 主动使用包括以下七个方面
 * (1): 创建类的实例,也就是new个对象
 * (2): 访问某个类或者接口的静态变量,或对该静态变量赋值
 * (3): 调用类的静态方法
 * (4): 反射
 * (5): 初始化一个类的子类(初始化子类的同时也会先初始化父类)
 * (6): java虚拟机启动的时候被标明为启动类的类
 * (7): jdk1.7开始提供的动态语言支持: {@link java.lang.invoke.MethodHandle} 实例的解析结果REF_getStatic, REF_putStatic, REF_invokeStatic句柄对应的类没有初始化,则初始化
 * <p>
 * 2, 被动使用,除了主动加载以外的加载都是被动加载,而被动加载都不会导致初始化操作
 * 所有的java虚拟机实现必须在每个类或接口被java程序“首次主动使用”时才初始化它们
 * <p>
 * 类的加载(虚拟机规范中并没有明确规定需要从指定位置去加载class文件,所以可以从任意可以获取到数据的方式进行加载)
 * 1,从本地系统中直接加载
 * 2,通过网络下载.class文件
 * 3,从zip,jar等归档文件中加载.class文件(常用)
 * 4,从专有的数据库中提取.class文件
 * 5,将java源文件动态编译为.class文件(常用,动态代理,spring AOP,jsp动态转换为servlet等)
 * <p>
 * 详细流程图
 * <p>
 *   加载              : 将二进制形式的java类型读取到java虚拟机中
 *    ↓
 *   验证              : 确保被加载的类的正确性
 *    ↓
 *   准备              : 为变量分配内存,设置默认值。但是在达到初始化之前，类变量都没有初始化为正确的初始值
 *    ↓
 *   解析              : 在类型的常量池中寻找类,接口,字段和方法的符号引用,把这些符号引用替换成直接引用的过程
 *    ↓
 *   初始化            : 为类变量赋予正确的初始值
 *    ↓
 *   类实例化          : 为新的对象分配内存,为实例变量赋默认值,为实例变量赋予正确的初始值,java编译器为它编译的每一个类都至少生成一个实例初始化方法
 *    ↓                在java的class文件中,这个实例初始化方法被称之为'<init>'，针对源代码中每一个类的构造方法,java编译器都产生一个<init>方法
 *   垃圾回收与对象终结 :
 * 类的加载的最终产品是位于内存中的class对象,class对象封装了类在方法区内的数据结构,并且向java程序员提供了方法区内的数据结构的接口
 * 有两种类型的类加载器
 * 1) java虚拟机自带的加载器
 *    1, 根类加载器(Bootstrap)
 *    2, 扩展类加载器 (Extension)
 *    3, 系统(应用)类加载器(System)
 * 2) 用户自定义的类加载器
 *     1, {@link java.lang.ClassLoader} 的子类
 *     2, 用户可以定制类的加载方式
 * 类加载器并不需要等到某个类被 '首次主动使用'时再加载它
 * jvm规范允许类加载器在预料某个类将要被使用时就预先加载它,如果在预先加载的过程中遇到了.class文件的缺失或错误,类加载器必须在{程序首次主动}使用该类时才报告错误{@link java.lang.LinkageError}
 * 如果这个类一直没有被程序主动使用,那么类加载器就不会报告错误
 * 类被加载后,进入到连接阶段,就是将已经读到内存中的类的二进制数据合并到虚拟机的运行时环境中去
 * 类的验证包括{1, 类文件结构的检测; 2, 语义检查; 3, 字节码验证; 4 , 二进制兼容性的验证}
 * <p>
 * 类的初始化步骤与细节
 *     1, 假如这个类还没有被加载和连接,那么就先进行加载和连接
 *     2, 假如类存在直接父类,并且这个父类还没有被初始化,那就先初始化直接父类
 *     3, 假如类中存在初始化语句,那就依次执行这些初始化语句
 *     4, 初始化一个类的时候,并不会先初始化它所实现的接口
 *     5, 初始化一个接口的时候,并不会先初始化它的父接口
 *     6, 所以,一个父接口并不会因为它的子接口或实现类的初始化而初始化,只有当程序首次使用特定接口的静态变量的时候,才会导致该接口的初始化
 * 只有当程序访问的静态变量或静态方法确实在当前类或当前接口中定义时,才可以认为是对当前类或接口的主动使用
 * <p>
 * 类加载器的父亲委托机制
 * Bootstrap ClassLoader /启动类加载器, 由$JAVA_HOME中jre/lib/rt.jar里面所有的class,由C++实现,不是classLoader的子类
 * Extension ClassLoader /扩展类加载器, 负责加载Java平台中扩展功能的一些jar,包括$JAVA_HOME中jre/lib/*.jar 或 -Djava.exit.dirs指定目录下的jar包
 * App ClassLoader       /系统类加载器, 负责加载classpath中指定的jar包以及目录中的class
 * 如果一个类加载器能够成功加载类,那么这个加载器被称为定义类加载器,所有能成功返回class对象引用的类加载器(包括定义类加载器)都被称之为初始类加载器
 * <p>
 * 类的命名空间问题:
 * 每个类加载器都有自己的命名空间,命名空间由该类加载器及所有父加载器所加载的类完成,在同一个命名空间中,不会出现类的完整名字(包括类的包名)相同的两个类,
 * 在不同的命名空间中,有可能会出现类的完整名字(包括类的包名)相同的两个类
 *
 *     类的卸载:
 *     当一个类(记为a)被加载、连接和初始化以后,它的生命周期就开始了,当a类的class对象不再被引用,即不可触及时,class对象的生命周期就会结束
 *  a类在方法区内的数据就会被卸载,从而结束a类的生命周期
 *     一个类何时结束生命周期,取决于代表它的class对象何时结束生命周期
 *     由java虚拟机自带的类加载器所加载的类,在虚拟机的生命周期中,始终不会被卸载.因为java虚拟机自带的类加载器包括根类加载器,扩展类加载器
 *  和系统类加载器,java虚拟机本身会始终引用这些类加载器,而这些类加载器则会始终引用它们所加载的类的class对象,因此这些class对象始终是可触及的
 *     所以由用户定义的类加载器所加载的类是可以被卸载的
 *
 * jvm规范允许类加载器在预料某个类将要被使用的时候预先加载它,如果在预先加载的过程中遇到了.class文件的缺失或错误,类加载器必须在程序
 * 首次主动使用该类的时候才报告错误{@link LinkageError},所以如果这个类一直没有被程序主动使用,那么类加载器不会报告错误
 *
 * 双亲委派机制的好处
 *     1,可以确保java核心类库的安全: 所有的java都至少会引用 {@link Object}类 记为a类, 也就是运行期,这个a类会被加载到jvm虚拟机中, 如果这个加载过程
 *  是由用户自己定义的,那么很可能在jvm中存在多个版本的a类,且这些类互不兼容,互不可见(命名空间发挥的作用). 但是借助于双亲委派机制,就可以确保核心类库的
 *  类的加载工作都是由根类加载器统一完成,从而确保java应用和使用者都是同一版本的java核心类库,他们之间就是相互兼容的
 *     2,可以确保java核心类库所提供的类不会被自定义的类所替代
 *     3,不同的类加载器可以为相同名称的类创建额外的命名空间, 相同名称的类可以并存在java虚拟机中,只需要用不同的类加载器来加载他们即可,不同的类加载器
 *  所加载的类之间是不兼容的,这就相当于在java虚拟机内部创建了一个有一个相互隔离的java类空间,这类技术在很多框架中都的到了应用
 * </pre>
 *
 * @author CharlesLee
 */
public class ClassLoader {
    public static void main(String[] args) throws Exception {
        java.lang.ClassLoader loader = new MyClassLoader();
        MyClassLoader.testMyClassLoader(loader, "com.charles.jvm.clazz.MyCat");
    }
}

/**
 * 测试一下主动使用与被动使用
 * 结论: 对于静态字段来说,只有直接定义了该字段的类才会被初始化,
 * C2.c1String() 虽然是通过子类进行调用,但是谁定义的静态变量就代表对谁的主动使用,所以子类的静态不会进行初始化
 * C2.c2String() 对子类的主动使用,所以一定会初始化子类的静态块,但是java程序对类的使用的主动使用的第五点可以知晓,父类一定先与子类全部初始化完毕,所以先输出父类
 */
class C1 {
    static String c1String = "hello world";

    static {
        System.out.println("c1 静态块输出了");
    }

    static void c1String() {
        System.out.println("c1 的string方法输出了");
    }
}

class C2 extends C1 {
    static String c2String = "hello world";

    static {
        System.out.println("c2 静态块输出了");
    }

    static void c2String() {
        System.out.println("c2 的string方法输出了");
    }
}

class C3 {
    /**
     * 在编译阶段,这个常量就会被存入到调用这个常量的这个方法所在的类的常量池之中
     * 本质上,调用类并没有直接引用到定义常量的类,因此并不会触发定义常量的类的初始化,
     * 从本质上来说,在代码编译之后,常量就已经跟原来存放的这个C3没有任何的关系了
     * 做个极端情况下的测试,删除这个c3编译之后的源代码(c3.class文件),我们还是可以正常运行程序
     */
    public static final String c3String = "hello world";

    static {
        System.out.println("c3 静态块输出了");
    }
}

class C4 {

    /**
     * 当一个常量的值并非编译期间可以确定的,那么其值就不会被放到调用类的常量池中
     * 这是在程序运行时,程序会主动去使用常量所在的类,所以会导致这个常量所在的类的初始化
     */
    public static final String c4String = UUID.randomUUID().toString();

    static {
        System.out.println("c4 静态块输出了");
    }
}

class C5 {

    // 创建实例操作,就是对类的主动使用,所以会导致静态代码块的输出,重复创建实例并不会重复导致初始化,因为不是首次使用类
    // 在进行数组的创建操作的时候,不会对类进行主动使用,所以不会初始化该静态代码块
    static {
        System.out.println("c5 静态块输出了");

    }

}

/**
 * 类的初始化与加载的顺序问题
 */
class C6 {
    public static int c61;

    private static C6 c6 = new C6();

    private C6() {
        c61++;
        // 准备阶段的重要意义
        c62++;
    }

    public static int c62 = 0;

    public static C6 getInstance() {
        return c6;
    }
}

interface C7 {
    Thread THREAD = new Thread() {
        {
            System.out.println("C7接口完成了初始化");
        }
    };
}

/**
 * 当一个接口初始化的时候,并不要求其父接口都完成了初始化
 * 只有当真正使用父接口的时候(如引用接口中所定义的常量),才会初始化
 */
class C8 implements C7 {
    public static int c8a = 5;
}

/**
 * 在初始化一个接口的时候，并不要求初始化其父接口
 */
interface C9 extends C7 {
    int C9 = 5;
}

/**
 * 自定义一个简单的自定义类加载器
 */
class MyClassLoader extends java.lang.ClassLoader {

    /**
     * 需要注意的是只要父类加载不了的类才会被我们当前的classLoader进行加载,否则统一为父类加载完成,这是双亲委托机制
     *
     * @param classLoader
     * @param className
     * @throws Exception
     */
    public static void testMyClassLoader(java.lang.ClassLoader classLoader, String className) throws Exception {
        Object o = classLoader.loadClass(className).getDeclaredConstructor().newInstance();
    }

    public MyClassLoader() {
        super();
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        System.out.println("自定义classLoader的findClass方法输出了：" + name);
        byte[] res = loadClassData(name);
        if (res == null) {
            throw new ClassNotFoundException(name);
        }
        return defineClass(name, res, 0, res.length);
    }

    /**
     * 定义读取class字节的方法
     */
    private byte[] loadClassData(String className) {
        className = className.replace("\\.", File.separator);
        System.out.println(className);
        try (FileInputStream in = new FileInputStream(new File(className + ".class"));
             ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            byte[] bytes = new byte[24];
            while (in.read(bytes) != -1) {
                // 这里因为jdk版本问题,家里学习用最细版本12,但是在公司1.8没有该api,所以为了编译通过,就不打开了
//                outputStream.writeBytes(bytes);
            }
            return outputStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}

class MyCat {
    public MyCat() {
        System.out.println(" MyCat " + this.getClass().getClassLoader());
        new MySample();
    }
}

class MySample {
    public MySample() {
        System.out.println(" MySample " + this.getClass().getClassLoader());
    }
}