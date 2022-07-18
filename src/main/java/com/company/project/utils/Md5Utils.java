package com.company.project.utils;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.File;
import java.io.FileInputStream;
import java.security.MessageDigest;

/**
 * 计算MD5值
 *
 * @author DanieQSL
 */
public class Md5Utils {

    /**
     * 生成文件的md5值（可处理大文件）
     *
     * @param file 文件
     * @return md5值
     */
    public static String genFileMd5(File file) {
        try (FileInputStream fileInputStream = new FileInputStream(file)) {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] buffer = new byte[8192];
            int length;
            while ((length = fileInputStream.read(buffer)) != -1) {
                md5.update(buffer, 0, length);
            }
            return new String(Hex.encodeHex(md5.digest()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 生成字符串的md5值（32位小写）
     *
     * @param data 字符串
     * @return md5值
     */
    public static String genMd5(String data) {
        return DigestUtils.md5Hex(data);
    }

    public static void main(String[] args) {
        long beginTime = System.currentTimeMillis();
        String md5 = genFileMd5(new File("D:\\temp\\ideaIU-2020.3.4.win.rar"));
        long endTime = System.currentTimeMillis();
        System.out.println("MD5: " + md5 + " spend: " + (endTime - beginTime) + "ms");
    }

}
