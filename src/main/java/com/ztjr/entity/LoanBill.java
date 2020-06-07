package com.ztjr.entity;

import java.math.BigDecimal;
import java.sql.Timestamp;

import com.ztjr.model.BorrowCycle;
import com.ztjr.utils.DateUtils;

public class LoanBill {
    /**
     * 主键id
     */
    private int id;
    /**
     * 用户id
     */
    private int userid;
    /**
     * 用户手机号
     */
    private String phone;
    /**
     * 用户姓名
     */
    private String username;
	/**
     * 订单编码
     */
    private String billno;
    /**
     * 订单创建时间
     */
    private Timestamp createTime;
    /**
     * 借款额度
     */
    private BigDecimal amount;
    /**
     * 借款周期（1:七天-7、2:十天-10、3:半个月-15：4：一个月-30）
     */
    private String borrowingCycle;
    /**
     * 放款时间
     */
    private Timestamp loanTime;
    /**
     * 初审人
     */
    private int preauditor;
    private String preauditName;
    /**
     * 初审时间
     */
    private Timestamp preauditTime;
    /**
     * 再审人
     */
    private String reauditName;
    private int reauditor;
    /**
     * 再审时间
     */
    private Timestamp reauditTime;
    /**
     * 应还款时间
     */
    private String paybackTime;
    /**
     * NO_APPLY("0"), // 未有申请
    PRE_ADDIT("1"), //预审
    RE_ADDIT("2"),  //再审
    LOANING("3"), //放款中
    LOANED("4"), //已放款
    PREADDIT_FAIL("5"), // 初审不通过
    READDIT_FAIL("6");//再审不通过
     */
    private String loanStatus;
    /**
     * 是否还款（0：否 1：是）
     */
    private String isPayBack;
    /**
     * 还款时间
     */
    private Timestamp payBackTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getBorrowingCycle() {
        return borrowingCycle;
    }

    public void setBorrowingCycle(String borrowingCycle) {
        this.borrowingCycle = borrowingCycle;
    }

    public Timestamp getLoanTime() {
        return loanTime;
    }

    public void setLoanTime(Timestamp loanTime) {
        this.loanTime = loanTime;
    }

    public String getLoanStatus() {
        return loanStatus;
    }

    public void setLoanStatus(String loanStatus) {
        this.loanStatus = loanStatus;
    }

    public String getIsPayBack() {
        return isPayBack;
    }

    public void setIsPayBack(String isPayBack) {
        this.isPayBack = isPayBack;
    }


    public String getBillno() {
        return billno;
    }

    public void setBillno(String billno) {
        this.billno = billno;
    }
    public String getPhone() {
		return phone;
	}

	public String getUsername() {
		return username;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Timestamp getPayBackTime() {
		return payBackTime;
	}

	public void setPayBackTime(Timestamp payBackTime) {
		this.payBackTime = payBackTime;
	}

	public int getPreauditor() {
		return preauditor;
	}

	public Timestamp getPreauditTime() {
		return preauditTime;
	}

	public int getReauditor() {
		return reauditor;
	}

	public Timestamp getReauditTime() {
		return reauditTime;
	}

	public void setPreauditor(int preauditor) {
		this.preauditor = preauditor;
	}

	public void setPreauditTime(Timestamp preauditTime) {
		this.preauditTime = preauditTime;
	}

	public void setReauditor(int reauditor) {
		this.reauditor = reauditor;
	}

	public void setReauditTime(Timestamp reauditTime) {
		this.reauditTime = reauditTime;
	}

    public String getPreauditName() {
        return preauditName;
    }

    public void setPreauditName(String preauditName) {
        this.preauditName = preauditName;
    }

    public String getReauditName() {
        return reauditName;
    }

    public void setReauditName(String reauditName) {
        this.reauditName = reauditName;
    }

	public String getPaybackTime() {
		return DateUtils.getRepayDate(loanTime, BorrowCycle.getValue(borrowingCycle));
	}

	public void setPaybackTime(String paybackTime) {
		this.paybackTime = paybackTime;
	}
}
