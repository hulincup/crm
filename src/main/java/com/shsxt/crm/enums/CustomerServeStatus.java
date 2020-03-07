package com.shsxt.crm.enums;
/**
 * 客户服务状态枚举类
 */
public enum CustomerServeStatus {
    CREATED("fw_001"),
    ASSIGNED("fw_002"),
    PROCEED("fw_003"),
    FEEDBACK("fw_004"),
    ARCHIVED("fw_005");
    private String state;

    CustomerServeStatus(String state) {
        this.state = state;
    }

    public String getState() {
        return state;
    }
}
