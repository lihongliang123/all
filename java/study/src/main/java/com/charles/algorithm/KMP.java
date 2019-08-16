package com.charles.algorithm;

/**
 * Charles Lee original, reprint please indicate the source
 * <p>
 * kmp算法字符串匹配算法
 *
 * @author CharlesLee
 */
public class KMP {

    public static void main(String[] args) {
        KMP kmp = new KMP();
        String s = "ABCDABD";
        kmp.kmpNext(s);
    }


    public int kmpSearch(String resources, String find) {
        int[] kmpNext = kmpNext(find);

        for (int i = 0, j = 0; i < resources.length(); i++) {

            while (j > 0 && resources.charAt(i) != find.charAt(j)) {
                j = kmpNext[j - 1];
            }

            if (resources.charAt(i) == find.charAt(j)) {
                j++;
            }
            if (j == find.length()) {
                return i - j + 1;
            }
        }
        return -1;
    }

    /**
     * 返回kmp的部分匹配值表
     *
     * @param dest 需要查找的字串的匹配表
     */
    public int[] kmpNext(String dest) {
        int[] next = new int[dest.length()];
        // 如果字符串是长度为1,那么部分匹配值就为0
        if (next.length == 1) {
            next[0] = 0;
            return next;
        }
        // 这里从1开的的目的是因为,如果字符串的长度为1的话,那么下标就只有0, 他是不可能存在匹配表的,所以只有字串大于1才会有匹配表
        // i是字符串下标位置,j是部分匹配值
        for (int i = 1, j = 0; i < next.length; i++) {

            while (j > 0 && dest.charAt(i) != dest.charAt(j)) {
                j = next[j - 1];
            }
            // 部分匹配值+1
            if (dest.charAt(i) == dest.charAt(j)) {
                j++;
            }
            next[i] = j;
        }

        return next;
    }

    /**
     * 暴力匹配,从字符串resources中找寻到find, 如果找寻到返回所在字符串中的开始下标, 否则返回-1;
     * <p>
     * 效率极低
     *
     * @param resources 源字符串
     * @param find      需要查找的字符串
     * @return 返回查找到的下标, 如果不存在则返回-1
     */
    public int violenceMatch(String resources, String find) {
        char[] resourcesChars = resources.toCharArray();
        char[] findChars = find.toCharArray();
        int resourcesIndex = 0;
        int findIndex = 0;
        int temp = 0;
        while (resourcesIndex < resourcesChars.length) {
            if (resourcesChars[resourcesIndex] == findChars[findIndex]) {
                if (temp == 0) {
                    temp = resourcesIndex;
                }
                findIndex++;
                resourcesIndex++;
                if (findIndex == findChars.length) {
                    return temp;
                }
            } else {
                temp = 0;
                resourcesIndex++;
            }
        }
        return -1;
    }
}
