package com.company.project.utils;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.util.Base64;

/**
 * AES加解密
 *
 * @author DanielQSL
 */
public class AesUtil {

    /**
     * AES算法
     */
    private static final String KEY_ALGORITHM = "AES";

    /**
     * 算法名称/工作模式/填充模式
     * 默认为ECB模式
     */
    private static final String DEFAULT_CIPHER_PADDING = "AES/ECB/PKCS5Padding";
    private static final String CIPHER_CBC_PADDING = "AES/CBC/PKCS5Padding";

    /**
     * 加密密码 128位对应秘钥为16位
     */
    private static final String KEY = "htlZXO0WQfg5Dkv3";

    /**
     * 偏移量 必须为16位
     * CBC工作模式下使用，增加加密算法的强度
     */
    private static final String IV_PARAMETER = "LiCP5YesKOIMwkFR";

    /**
     * AES 加密操作
     *
     * @param content 待加密内容
     * @param key     加密密码
     * @return String 返回Base64转码后的加密数据
     */
    public static String encrypt(String content, String key) {
        try {
            // 获取 AES 密码器 ECB模式
            Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_PADDING);
            // 初始化密码器（加密模型）
            cipher.init(Cipher.ENCRYPT_MODE, getSecretKey(key));
            // 加密数据
            byte[] result = cipher.doFinal(content.getBytes(StandardCharsets.UTF_8));
            // 对加密后数据进行 base64 编码
            return Base64.getEncoder().encodeToString(result);
        } catch (GeneralSecurityException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * AES 解密操作
     *
     * @param content 待解密内容
     * @param key     加密密码
     * @return String
     */
    public static String decrypt(String content, String key) {
        try {
            // 获取 AES 密码器 ECB模式
            Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_PADDING);
            // 初始化密码器（解密模型）
            cipher.init(Cipher.DECRYPT_MODE, getSecretKey(key));
            // 解密数据
            byte[] result = cipher.doFinal(Base64.getDecoder().decode(content));
            return new String(result, StandardCharsets.UTF_8);
        } catch (GeneralSecurityException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * AES CBC模式 加密操作
     *
     * @param content 待加密内容
     * @param key     加密密码
     * @return String 返回Base64转码后的加密数据
     */
    public static String encryptCBC(String content, String key) {
        try {
            // 获取 AES 密码器 CBC模式
            Cipher cipher = Cipher.getInstance(CIPHER_CBC_PADDING);
            // 偏移量
            IvParameterSpec iv = new IvParameterSpec(IV_PARAMETER.getBytes(StandardCharsets.UTF_8));
            // 初始化密码器（加密模型）
            cipher.init(Cipher.ENCRYPT_MODE, getSecretKey(key), iv);
            // 加密数据
            byte[] result = cipher.doFinal(content.getBytes(StandardCharsets.UTF_8));
            // 对加密后数据进行 base64 编码
            return Base64.getEncoder().encodeToString(result);
        } catch (GeneralSecurityException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * AES CBC模式 解密操作
     *
     * @param content 待解密内容
     * @param key     加密密码
     * @return String
     */
    public static String decryptCBC(String content, String key) {
        try {
            // 获取 AES 密码器 CBC模式
            Cipher cipher = Cipher.getInstance(CIPHER_CBC_PADDING);
            // 偏移量
            IvParameterSpec iv = new IvParameterSpec(IV_PARAMETER.getBytes(StandardCharsets.UTF_8));
            // 初始化密码器（解密模型）
            cipher.init(Cipher.DECRYPT_MODE, getSecretKey(key), iv);
            // 解密数据
            byte[] result = cipher.doFinal(Base64.getDecoder().decode(content));
            return new String(result, StandardCharsets.UTF_8);
        } catch (GeneralSecurityException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 生成AES加密秘钥器
     *
     * @param key 加密密码
     * @return SecretKeySpec
     */
    private static SecretKeySpec getSecretKey(final String key) {
        return new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), KEY_ALGORITHM);
    }

    public static void main(String[] args) throws GeneralSecurityException {
        System.out.println(AesUtil.encrypt("张三", KEY));
        System.out.println(AesUtil.decrypt("ldDrZkSahlAOHtWM+VJkvw==", KEY));

        System.out.println(AesUtil.encryptCBC("张三", KEY));
        System.out.println(AesUtil.decryptCBC("L9f9BtDyFH9b80D4Kb+2Sw==", KEY));
    }

}
