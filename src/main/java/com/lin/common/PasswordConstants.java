package com.lin.common;

/**
 * @Author czh
 * @desc 密码相关常量
 * @date 2023/5/21 20:17
 */
public interface PasswordConstants {
    /**
     * 初始密码为123456
     */
    String INIT_PASSWORD = "123456";
    /**
     * 密码规则的正则表达式，密码要求至少一个数字、一个字母，可以有标点等特殊字符，长度8-16位
     */
    String RULE_REGEX = "^(?=.*[0-9])(?=.*[a-zA-Z])(.{8,16})$";
    /**
     * 密码规则的提示信息:"密码至少一个数字、一个字母，可以有标点等特殊字符，长度8-16位"
     */
    String RULE_TIP = "密码至少一个数字、一个字母，可以有标点等特殊字符，长度8-16位";

    /**
     * 密码是否有效合理，符合密码设置规则（用正则表达式），密码要求至少一个数字、一个字母，可以有标点等特殊字符，长度8-16位
     *
     * @param password 待检验的密码
     * @return 是否符合密码设置规则
     * @Author czh
     */
    static boolean isPasswordValid(String password) {
        return password.matches(RULE_REGEX);
    }
}
