package com.lin.utils;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

/**
 * @author 林炳昌
 * @desc 随机数工具类
 * @date 2023/4/1 18:55
 */
public class RandomUtil {

    private static final Random RANDOM = new Random();

    private static final DecimalFormat FOURDF = new DecimalFormat("0000");

    private static final DecimalFormat SIXDF = new DecimalFormat("000000");

    public static String getFourBitRandom() {
        return FOURDF.format(RANDOM.nextInt(10000));
    }

    public static String getNineBitRandom() {
        return SIXDF.format(RANDOM.nextInt(1000000000));
    }

    public static String getEighteenBitRandom() {
        return SIXDF.format(RANDOM.nextInt(1000000000)) + SIXDF.format(RANDOM.nextInt(1000000000));
    }


    /**
     * 给定数组，抽取n个数据
     *
     * @param list 数组
     * @param n 抽取n个数据
     * @return 抽取的数据
     */
    public static <T> ArrayList<T> getRandom(List<T> list, int n) {

        Random random = new Random();

        HashMap<Object, Object> hashMap = new HashMap<>();

        // 生成随机数字并存入HashMap
        for (int i = 0; i < list.size(); i++) {

            int number = random.nextInt(100) + 1;

            hashMap.put(number, i);
        }

        // 从HashMap导入数组
        Object[] robjs = hashMap.values().toArray();

        ArrayList<T> r = new ArrayList<>();

        // 遍历数组并打印数据
        for (int i = 0; i < n; i++) {
            r.add(list.get((int) robjs[i]));
            System.out.print(list.get((int) robjs[i]) + "\t");
        }
        System.out.print("\n");
        return r;
    }
}