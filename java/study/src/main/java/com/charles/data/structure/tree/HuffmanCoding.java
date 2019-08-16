package com.charles.data.structure.tree;

import java.io.*;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.*;

import static java.lang.Integer.toBinaryString;

/**
 * Charles Lee original, reprint please indicate the source
 * <p>
 * 霍夫曼编码
 * <pre>
 *     1,该编码属于一种编码方式,是一种经典的程序算法
 *     2,霍夫曼编码是霍夫曼树在电讯通信种的经典应用之一
 *     3,霍夫曼编码广泛地用于数据文件的压缩, 其压缩比通常在 20% ~~ 90% 之间
 *     4,霍夫曼码是可变字长编码(VLC)的一种, 是Huffman于1952年提出的一种编码方式,称之为最佳编码
 *     5,霍夫曼树是无损压缩
 * </pre>
 *
 * @author CharlesLee
 */
public class HuffmanCoding {

    public static void main(String[] args) {
        HuffmanCoding coding = new HuffmanCoding();
        String content = "i like like like java do you a java";
//
        Map<Byte, String> huffmanMap = new HashMap<>();
        byte[] compress = coding.huffmanZip(content.getBytes(StandardCharsets.UTF_8), huffmanMap);
//        // 解压霍夫曼压缩过后的数据表
        byte[] result = coding.decode(huffmanMap, compress);
        System.out.println(new String(result, StandardCharsets.UTF_8));
//        coding.zipFile("E:\\myData\\hs_err_pid6020.log", "E:\\myData\\video.zip");
//        1378404549
    }


    /**
     * 霍夫曼树压缩字符串, 返回压缩后的数据信息
     *
     * @param contentBytes 需要压缩的字节码信息
     * @param huffmanMap   存放霍夫曼码表的散列表
     * @return 返回压缩之后的字节信息
     */
    private byte[] huffmanZip(byte[] contentBytes, Map<Byte, String> huffmanMap) {
        if (contentBytes == null) {
            return new byte[]{};
        }
        if (huffmanMap == null) {
            huffmanMap = new HashMap<>();
        }
        huffmanMap.clear();
        // 将比特数组转换为霍夫曼树节点
        List<HuffmanNode> nodes = getNodes(contentBytes);
        // 将霍夫曼树节点从小到大排序
        Collections.sort(nodes);
        // 将所有霍夫曼树节点进行组合,创建成为完整的霍夫曼树
        HuffmanNode root = createHuffmanTree(nodes);
        // 生成霍夫曼数码表
        createHuffmanCodingTable(root, "", new StringBuilder(), huffmanMap);
        return compress(contentBytes, huffmanMap);
    }


    /**
     * 压缩文件, 需要注意的是这里只能压缩小文件,小文件最大不能超过
     *
     * @param sourcesFile 需要压缩的源文件
     * @param outFile     压缩过后输出的目标文件
     */
    private void zipFile(String sourcesFile, String outFile) {
        if (sourcesFile == null || outFile == null || "".equals(sourcesFile) || "".equals(outFile)) {
            return;
        }
        File inputFile = new File(sourcesFile);
        File outputFile = new File(outFile);
        if (inputFile.exists() && inputFile.isFile() && inputFile.length() > Integer.MAX_VALUE) {
            throw new RuntimeException("文件: [" + inputFile.getPath() + "] 过大, 无法压缩!");
        }
        // 创建文件输入输出流
        try (FileInputStream in = new FileInputStream(inputFile);
             ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(outputFile))) {
            // 将文件中的数据读取出来
            byte[] inBytes = new byte[(int) inputFile.length()];

            // 创建霍夫曼编码表
            Map<Byte, String> huffmanMap = new HashMap<>();
            System.out.printf("从文件[%s]中读取到的数据长度为:%d", inputFile.getPath(), in.read(inBytes));

            // 将数据进行压缩
            byte[] compress = huffmanZip(inBytes, huffmanMap);

            // 将压缩的数据进行写出
            outputStream.write(compress);
//            outputStream.writeObject(huffmanMap);
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 将压缩过后的数据进行解码
     *
     * @param huffmanTables 霍夫曼编码表
     * @param huffmanBytes  霍夫曼字节数组
     */
    private byte[] decode(Map<Byte, String> huffmanTables, byte[] huffmanBytes) {
        StringBuilder builder = new StringBuilder();
        for (byte huffmanByte : huffmanBytes) {
            builder.append(byteToBitString(huffmanByte));
        }

        // 将霍夫曼编码表进行反向转换
        Map<String, Byte> map = new HashMap<>(huffmanBytes.length);
        for (Map.Entry<Byte, String> byteStringEntry : huffmanTables.entrySet()) {
            map.put(byteStringEntry.getValue(), byteStringEntry.getKey());
        }

        List<Byte> list = new ArrayList<>();
        for (int i = 0; i < builder.length(); ) {
            int count = 1;
            boolean f = true;
            Byte b = null;
            while (f && i + count <= builder.length()) {
                String key = builder.substring(i, i + count);
                b = map.get(key);
                if (b == null) {
                    count++;
                } else {
                    f = false;
                }
            }
            i += count;
            if (b != null) {
                list.add(b);
            }
        }

        byte[] result = new byte[list.size()];
        for (int i = 0; i < list.size(); i++) {
            result[i] = list.get(i);
        }
        return result;
    }

    /**
     * 将一个byte转换为二进制的字符串数据
     *
     * @param b 需要转换的byte
     * @return 返回转换成功的二进制字符串
     */
    private String byteToBitString(byte b) {
        // 256的二进制为100000000, 一个byte按位与上256只是为了显示补全前面的位数,因为如果前面的位为0,那么0就会被自动舍去,就会导致字符串的长度不足
        // toBinaryString返回的是对应的二进制补码
        String temp = toBinaryString(b | 256);
        return temp.substring(temp.length() - 8);
    }

    /**
     * 将需要压缩的byte数组通过生成的霍夫曼码表,返回一个压缩过后的byte数组
     *
     * @param bs           需要压缩的数据
     * @param huffmanCodes 通过需要压缩的数据而生成的霍夫曼码表
     */
    private byte[] compress(byte[] bs, Map<Byte, String> huffmanCodes) {
        // 利用霍夫曼码表将需要压缩的数据进行转换,生成霍夫曼对应的字符串
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bs.length; i++) {
            sb.append(huffmanCodes.get(bs[i]));
        }
        while (sb.length() % 8 != 0) {
            sb.append("0");
        }
        int len = sb.length() / 8;
        int count = 0;
        byte[] temp = new byte[len];
        // 每8位对应一个byte, 所以步长i应该为8才对
        for (int i = 0; i < sb.length(); i += 8) {
            // 将str转换为byte然后放入临时byte数组
            String st = sb.substring(i, i + 8);
//            System.out.println("截取出来的数据为: " + st + ", 转换之后的数据为: " + new BigInteger(st, 2));
//            System.out.println("截取出来的数据为: " + st + ", 转换之后的数据为: " + (byte) new BigInteger(st, 2).intValue());
            temp[count] = (byte) new BigInteger(st, 2).intValue();
            count++;
        }
        return temp;
    }

    /**
     * 创建霍夫曼编码表, 将树的所有叶子节点生成码表
     *
     * @param node    需要处理的所有子节点
     * @param code    编码规则, 向左走路径定义为0, 向右走路径定义为1;
     * @param builder 临时存放霍夫曼单码的路径
     */
    private void createHuffmanCodingTable(HuffmanNode node, String code, StringBuilder builder, Map<Byte, String> map) {
        // 在当前需要处理的节点不为null的情况下,进行编码
        if (node != null) {
            builder = new StringBuilder(builder).append(code);
            // 进行两步的判断,判断是否为叶子节点, 如果node.data不为null,那么就是非叶子节点
            if (node.data == null) {
                // 向左递归
                createHuffmanCodingTable(node.left, "0", builder, map);
                // 向右递归
                createHuffmanCodingTable(node.right, "1", builder, map);
            } else {
                // 走到了这里就是非叶子节点, 将所有的叶子节点的路径数据存入到码表, 也就是map
                map.put(node.data, builder.toString());
            }
        }
    }

    /**
     * 创建霍夫曼树
     *
     * @param nodes 需要创建的树的集合
     * @return 返回霍夫曼树的根节点
     */
    private HuffmanNode createHuffmanTree(List<HuffmanNode> nodes) {
        while (nodes.size() > 1) {
            // 创建左子节点
            HuffmanNode leftNode = nodes.get(0);
            // 创建右子节点
            HuffmanNode rightNode = nodes.get(1);
            nodes.remove(0);
            nodes.remove(0);
            // 创建左右子节点的父节点,该父节点的值是左右节点值之和
            HuffmanNode root = new HuffmanNode(null, leftNode.weight + rightNode.weight);
            root.left = leftNode;
            root.right = rightNode;
            nodes.add(0, root);
        }
        return nodes.get(0);
    }


    /**
     * 中序遍历
     */
    public void midOrder(HuffmanNode node) {
        if (node != null) {
            midOrder(node.left);
            System.out.println(node.toString());
            midOrder(node.right);
        }
    }

    /**
     * 将一个字符串转换为节点数组
     */
    private List<HuffmanNode> getNodes(byte[] bytes) {
        Map<Byte, Integer> counts = new HashMap<>();
        for (byte aByte : bytes) {
            Integer v = counts.get(aByte);
            if (v == null || v == 0) {
                counts.put(aByte, 1);
            } else {
                counts.put(aByte, v + 1);
            }
        }
        List<HuffmanNode> result = new ArrayList<>(counts.size());
        counts.forEach((k, v) -> {
            result.add(new HuffmanNode(k, v));
        });
        return result;
    }

    private static class HuffmanNode implements Comparable<HuffmanNode> {

        public HuffmanNode() {
        }

        public HuffmanNode(Byte data, int weight) {
            this.data = data;
            this.weight = weight;
        }

        /**
         * 存放数据本身
         */
        private Byte data;

        /**
         * 权值, 表示存放的字符出现的次数
         */
        private int weight;

        /**
         * 左子节点
         */
        private HuffmanNode left;

        /**
         * 右子节点
         */
        private HuffmanNode right;


        @Override
        public String toString() {
            if (data == null) {
                return "HuffmanNode{" +
                        "weight=" + weight +
                        '}';
            } else {
                return "HuffmanNode{" +
                        "data=" + new Character((char) data.byteValue()).toString() +
                        ", weight=" + weight +
                        '}';
            }
        }

        public byte getData() {
            return data;
        }

        public void setData(byte data) {
            this.data = data;
        }

        public int getWeight() {
            return weight;
        }

        public void setWeight(int weight) {
            this.weight = weight;
        }

        public HuffmanNode getLeft() {
            return left;
        }

        public void setLeft(HuffmanNode left) {
            this.left = left;
        }

        public HuffmanNode getRight() {
            return right;
        }

        public void setRight(HuffmanNode right) {
            this.right = right;
        }

        /**
         * 排序是按照权值来进行排序的,从小到大排序
         */
        @Override
        public int compareTo(HuffmanNode o) {
            // 从小到大进行排序
            return this.weight - o.weight;
        }
    }
}
