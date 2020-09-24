package com.company.project.utils;

import lombok.extern.slf4j.Slf4j;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 文本相似度计算
 * 根据余弦定理计算文本相似度
 *
 * @author danielqsl
 */
@Slf4j
public class StringSimilarityUtil {

    /**
     * 获取文本相似度
     *
     * @param text1 文本1
     * @param text2 文本2
     * @return 相似度
     */
    public static double getSimilarity(String text1, String text2) {
        if (text1 != null && text1.trim().length() > 0 && text2 != null && text2.trim().length() > 0) {
            Map<Integer, int[]> algorithmMap = new HashMap<>();
            //将两个字符串中的中文字符以及出现的总数封装到，AlgorithmMap中
            for (int i = 0; i < text1.length(); i++) {
                char d1 = text1.charAt(i);
                //标点和数字不处理
                if (isHanZi(d1)) {
                    //保存字符对应的GB2312编码
                    int charIndex = getGB2312Id(d1);
                    if (charIndex != -1) {
                        int[] fq = algorithmMap.get(charIndex);
                        if (fq != null && fq.length == 2) {
                            //已有该字符，加1
                            fq[0]++;
                        } else {
                            fq = new int[2];
                            fq[0] = 1;
                            fq[1] = 0;
                            //新增字符入map
                            algorithmMap.put(charIndex, fq);
                        }
                    }
                }
            }

            for (int i = 0; i < text2.length(); i++) {
                char d2 = text2.charAt(i);
                if (isHanZi(d2)) {
                    int charIndex = getGB2312Id(d2);
                    if (charIndex != -1) {
                        int[] fq = algorithmMap.get(charIndex);
                        if (fq != null && fq.length == 2) {
                            fq[1]++;
                        } else {
                            fq = new int[2];
                            fq[0] = 0;
                            fq[1] = 1;
                            algorithmMap.put(charIndex, fq);
                        }
                    }
                }
            }

            Iterator<Integer> iterator = algorithmMap.keySet().iterator();
            double sqdoc1 = 0;
            double sqdoc2 = 0;
            double denominator = 0;
            while (iterator.hasNext()) {
                int[] c = algorithmMap.get(iterator.next());
                denominator += c[0] * c[1];
                sqdoc1 += c[0] * c[0];
                sqdoc2 += c[1] * c[1];
            }
            //余弦计算
            return denominator / Math.sqrt(sqdoc1 * sqdoc2);
        } else {
            throw new NullPointerException(" the Document is null or have not cahrs!!");
        }
    }

    private static boolean isHanZi(char ch) {
        // 判断是否汉字
        return (ch >= 0x4E00 && ch <= 0x9FA5);
    }

    /**
     * 根据输入的Unicode字符，获取它的GB2312编码或者ascii编码，
     *
     * @param ch 输入的GB2312中文字符或者ASCII字符(128个)
     * @return ch在GB2312中的位置，-1表示该字符不认识
     */
    private static short getGB2312Id(char ch) {
        try {
            byte[] buffer = Character.toString(ch).getBytes("GB2312");
            if (buffer.length != 2) {
                // 正常情况下buffer应该是两个字节，否则说明ch不属于GB2312编码，故返回'?'，此时说明不认识该字符
                return -1;
            }
            // 编码从A1开始，因此减去0xA1=161
            int b0 = (buffer[0] & 0x0FF) - 161;
            int b1 = (buffer[1] & 0x0FF) - 161;
            // 第一个字符和最后一个字符没有汉字，因此每个区只收16*6-2=94个汉字
            return (short) (b0 * 94 + b1);
        } catch (UnsupportedEncodingException e) {
            log.error("获取字符的GB2312编码或者ascii编码出错", e);
        }
        return -1;
    }

    public static void main(String[] args) {
        String str1 = "关 注 微 信  公.众.号（ 球  世  界 ）\uD83D\uDD25\uD83D\uDD25\uD83D\uDD25内 部 葒 单 推 见❤️（另 有 荬 球 软 件 首 存 送 彩 金）";
        String str2 = "关 注  微 ❤️ 公 众  号 (  球世界 ) ✔ 内 部 红 单 推 见 ✔（ 另有 买 球 软 件首 存 送 彩 金）\uD83D\uDE18";
        long start = System.currentTimeMillis();
        double Similarity = StringSimilarityUtil.getSimilarity(str1, str2);
        System.out.println("用时:" + (System.currentTimeMillis() - start));
        System.out.println(Similarity);
    }

}
