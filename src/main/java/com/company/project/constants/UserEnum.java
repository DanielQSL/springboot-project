package com.company.project.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * 用户枚举（数值转换枚举）
 *
 * @author qianshuailong
 * @date 2021/4/5
 */
public class UserEnum {

    @Getter
    @AllArgsConstructor
    public enum Sex {
        // ========== 性别 ==========
        MAN(1, "男"),
        WOMAN(2, "女"),
        ;

        private final Integer code;
        private final String msg;

        private static Map<Integer, Sex> data = new HashMap<>();

        static {
            for (Sex sex : Sex.values()) {
                data.put(sex.code, sex);
            }
        }

        public static Sex parse(Integer code) {
            if (data.containsKey(code)) {
                return data.get(code);
            }
            return null;
        }
    }

    public static void main(String[] args) {
        //此处假如客户端传入状态码 1
        Integer man = 1;
        UserEnum.Sex parse = UserEnum.Sex.parse(man);
        System.out.println(parse);

        //此处假如客户端传入状态码 1
        UserEnum.Sex parse2 = UserEnum.Sex.parse(man);
        switch (parse2) {
            case MAN:
                // 业务
                break;
            case WOMAN:
                // 业务
                break;
            default:
                break;
        }
    }

}
