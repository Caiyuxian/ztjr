package com.ztjr.model;

/**
 * 借款状态
 */
public enum LoanStatus {
    NO_APPLY("0"), // 未有申请
    PRE_AUDIT("1"), //初审中
    RE_AUDIT("2"),  //预审中
    LOANING("3"), //放款中
    LOANED("4"), //已放款
    PREADDIT_FAIL("5"), // 初审不通过
    READDIT_FAIL("6");//再审不通过

    private String mStatus;

    LoanStatus(String status) {
        mStatus = status;
    }

    public String getStatus() {
        return mStatus;
    }

    public static LoanStatus getStatus(String status) {
        for (LoanStatus s : LoanStatus.values()) {
            if (s.getStatus().equals(status)) {
                return s;
            }
        }
        return null;
    }

}
