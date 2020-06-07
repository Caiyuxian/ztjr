package com.ztjr.model;

public enum BorrowCycle {
    // 需要使用天数，客户端直接使用
    seven("1", 7),
    ten("2", 10),
    haldofmount("3", 15),
    onemount("4", 30);

    private String key;
    private int value;

    BorrowCycle(String key, int value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public int getValue() {
        return this.value;
    }

    public static BorrowCycle getValue(String key) {
        for (BorrowCycle o : BorrowCycle.values()) {
            if (o.key.equals(key))
                return o;
        }
        return null;
    }

    public static String getCycleValue(String key) {
        BorrowCycle cycle = getValue(key);
        if (cycle == null) {
            return "";
        } else {
            return cycle.getValue() + "";
        }
    }

}
