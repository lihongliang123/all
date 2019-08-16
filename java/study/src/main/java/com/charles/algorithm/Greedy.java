package com.charles.algorithm;

import java.util.*;

/**
 * Charles Lee original, reprint please indicate the source
 * <p>
 * 贪婪算法运用之一: 集合覆盖问题
 *
 * @author CharlesLee
 */
public class Greedy {

    /**
     * 无限广播电台1
     */
    private static final List<String> RADIO_STATION_1 = new ArrayList<String>() {{
        add("北京");
        add("上海");
        add("天津");
    }};

    /**
     * 无限广播电台2
     */
    private static final List<String> RADIO_STATION_2 = new ArrayList<String>() {{
        add("广州");
        add("北京");
        add("深圳");
    }};

    /**
     * 无限广播电台3
     */
    private static final List<String> RADIO_STATION_3 = new ArrayList<String>() {{
        add("成都");
        add("上海");
        add("杭州");
    }};

    /**
     * 无限广播电台4
     */
    private static final List<String> RADIO_STATION_4 = new ArrayList<String>() {{
        add("上海");
        add("天津");
    }};

    /**
     * 无限广播电台5
     */
    private static final List<String> RADIO_STATION_5 = new ArrayList<String>() {{
        add("杭州");
        add("大连");
    }};

    public static void main(String[] args) {
        // 存放所有的地区,用set集合去重
        Set<String> all = new HashSet<>();
        all.addAll(RADIO_STATION_1);
        all.addAll(RADIO_STATION_2);
        all.addAll(RADIO_STATION_3);
        all.addAll(RADIO_STATION_4);
        all.addAll(RADIO_STATION_5);

        // 保存所有的广播以及地区关系
        Map<String, List<String>> broadcasts = new HashMap<>();
        broadcasts.put("K1", RADIO_STATION_1);
        broadcasts.put("K2", RADIO_STATION_2);
        broadcasts.put("K3", RADIO_STATION_3);
        broadcasts.put("K4", RADIO_STATION_4);
        broadcasts.put("K5", RADIO_STATION_5);

        // 存储结果
        List<String> result = new ArrayList<>();


        // 如果集合不为空,那么就一直处理下去
        while (!all.isEmpty()) {
            int maxTemp = 0;
            int max = 0;
            // 最大的交集的key
            String temp = "";
            for (String s : broadcasts.keySet()) {
                List<String> radioStation = broadcasts.get(s);
                for (String radio : radioStation) {
                    if (all.contains(radio)) {
                        max++;
                    }
                }
                if (max > maxTemp) {
                    maxTemp = max;
                    temp = s;
                }
                max = 0;
            }

            // 到了这里就已经找到了最大的key
            result.add(temp);
            all.removeAll(broadcasts.get(temp));
        }
        System.out.println("处理的结果为: ");
        for (String s : result) {
            System.out.println(s + " = " + broadcasts.get(s));
        }
    }
}
