package com.mind.xzs.domain.enums;


import java.util.HashMap;
import java.util.Map;

public enum UserStatusEnum {

    Enable(0, "启用"),
    Disable(1, "禁用");

    int code;
    String name;

    UserStatusEnum(int code, String name) {
        this.code = code;
        this.name = name;
    }

    private static final Map<Integer, UserStatusEnum> keyMap = new HashMap<>();

    static {
        for (UserStatusEnum item : UserStatusEnum.values()) {
            keyMap.put(item.getCode(), item);
        }
    }

    public static UserStatusEnum fromCode(Integer code) {
        return keyMap.get(code);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
