package com.breakpoint.util.wx;

import org.apache.commons.lang3.StringUtils;

import java.security.MessageDigest;
import java.util.Arrays;

/**
 * @author :breakpoint/赵立刚
 * @date : 2020/03/30
 */
public class SignUtil {

    /**
     * 与开发模式接口配置信息中的Token保持一致
     */
    private static String token = "jshdjhsjhdjshjdhsakjash";

    /**
     * 微信生成的 ASEKey
     */
    private static String encodingAesKey = "62dJGRELj9YFh2wYydzKaBaJ78QBf5JmbvtDxK7fWyC";

    /**
     * 应用的AppId
     */
    private static String appId = "wx3eab8a4abb0da1d6";


    /**
     * 解密微信发过来的密文
     *
     * @return 加密后的内容
     */
    public static String decryptMsg(String msgSignature, String timeStamp, String nonce, String encrypt_msg) {
        WXBizMsgCrypt pc;
        String result = "";
        try {
            pc = new WXBizMsgCrypt(token, encodingAesKey, appId);
            result = pc.decryptMsg(msgSignature, timeStamp, nonce, encrypt_msg);
        } catch (AesException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 加密给微信的消息内容
     *
     * @param replayMsg
     * @param timeStamp
     * @param nonce
     * @return
     */
    public static String ecryptMsg(String replayMsg, String timeStamp, String nonce) {
        WXBizMsgCrypt pc;
        String result = "";
        try {
            pc = new WXBizMsgCrypt(token, encodingAesKey, appId);
            result = pc.encryptMsg(replayMsg, timeStamp, nonce);
        } catch (AesException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return result;
    }

    public static boolean checkSignature(String signature, String timestamp, String nonce) throws AesException {
        String sha1 = getSha1(timestamp, nonce);

        if (StringUtils.equals(sha1, signature)) {
            return true;

        } else {
            return false;
        }
    }

    private static String getSha1(String timestamp, String nonce) throws AesException {
        try {
            String[] array = new String[]{token, timestamp, nonce};
            StringBuffer sb = new StringBuffer();
            // 字符串排序
            Arrays.sort(array);
            for (int i = 0; i < 3; i++) {
                sb.append(array[i]);
            }
            String str = sb.toString();
            // SHA1签名生成
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            md.update(str.getBytes());
            byte[] digest = md.digest();

            StringBuffer hexstr = new StringBuffer();
            String shaHex = "";
            for (int i = 0; i < digest.length; i++) {
                shaHex = Integer.toHexString(digest[i] & 0xFF);
                if (shaHex.length() < 2) {
                    hexstr.append(0);
                }
                hexstr.append(shaHex);
            }
            return hexstr.toString();
        } catch (Exception e) {
            e.printStackTrace();
            throw new AesException(AesException.ComputeSignatureError);
        }
    }


}
