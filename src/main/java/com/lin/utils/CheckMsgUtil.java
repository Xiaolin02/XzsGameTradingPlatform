package com.lin.utils;

/**
 * @author 林炳昌
 * @desc 检测用户聊天中的敏感信息
 * @date 2023年05月19日 14:18
 */
public class CheckMsgUtil {

    public static boolean checkMsg(String msg) {
        return msg.contains("vx") || msg.contains("微信") || msg.contains("线下") || msg.contains("qq") || msg.contains("QQ");
    }

}
