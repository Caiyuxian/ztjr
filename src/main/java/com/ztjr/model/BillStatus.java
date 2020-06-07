package com.ztjr.model;

/**
 * 发起借款返回借款单状态
 * 1.未提交个人资料 2.已经提交申请 3.已经借贷中（已放款） 4.新创建申请单
 */
public enum BillStatus {
    NO_INFO("1"),
    APPLY_COMMIT("2"),
    IN_LOAN("3"),
    OPEN_NEW_BILL("4");

    private String mStatus;

    BillStatus(String mStatus) {
        this.mStatus = mStatus;
    }

    public String getStatus() {
        return mStatus;
    }
}
