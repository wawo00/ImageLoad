package com.openup.app2;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

/**
 * @ProjectName: TestApplication
 * @Package: com.openup.app2
 * @ClassName: AESMethod
 * @Description: AESMethod
 * @Author: Roy
 * @CreateDate: 2020/6/29 14:28
 */

public class AESMethod {
    public static void main(String[] args) throws NoSuchAlgorithmException {
        String content = "示例：{\"total\":1,\"stationStatusInfo\":{\"operationID\":\"123456789\",\"stationID\":\"111111111111111\",\"connectorStatusInfos\":{\"connectorID\":1,\"equipmentID\":\"10000000000000000000001\",\"status\":4,\"currentA\":0,\"currentB\":0,\"currentC\":0,\"voltageA\":0,\"voltageB\":0,\"voltageC\":0,\"soc\":10,}";
        String password = "1234567812345678";
        //加密
        System.out.println("加密前：" + content);
        byte[] encryptResult = encrypt(content, password);
        System.out.println("加密后：" + encryptResult);
        //解密
        byte[] decryptResult = decrypt(encryptResult, password);
        System.out.println("解密后：" + new String(decryptResult));
    }

    public static byte[] decrypt(byte[] content, String password) {
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            KeyGenerator kgen = KeyGenerator.getInstance("AES"); //KeyGenerator提供（对称）密钥生成器的功能。使用getInstance 类方法构造密钥生成器。
            kgen.init(128, new SecureRandom(password.getBytes()));//使用用户提供的随机源初始化此密钥生成器，使其具有确定的密钥大小。
            SecretKey secretKey = kgen.generateKey();
            cipher.init(Cipher.DECRYPT_MODE, secretKey, new IvParameterSpec(password.getBytes()));//使用解密模式初始化 密钥
            byte[] decrypt = cipher.doFinal(content);
            return decrypt; // 加密
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static byte[] encrypt(String content, String password) {
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            KeyGenerator kgen = KeyGenerator.getInstance("AES");
            kgen.init(128, new SecureRandom(password.getBytes()));
            SecretKey secretKey = kgen.generateKey();
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, new IvParameterSpec(password.getBytes()));//使用加密模式初始化 密钥
            byte[] encrypt = cipher.doFinal(content.getBytes()); //按单部分操作加密或解密数据，或者结束一个多部分操作。
            return encrypt;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }
        return null;
    }
}