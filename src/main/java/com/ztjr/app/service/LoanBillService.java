package com.ztjr.app.service;

import com.ztjr.dao.LoanBillDao;
import com.ztjr.dao.UserExtInfoDao;
import com.ztjr.entity.LoanBill;
import com.ztjr.entity.UserExtInfo;
import com.ztjr.model.*;
import com.ztjr.utils.DateUtils;
import com.ztjr.utils.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Service
public class LoanBillService {
    @Autowired
    private LoanBillDao billDao;
    @Autowired
    private UserExtInfoDao extInfoDao;

    /**
     * 请求借款
     */
    public Msg requestLoan(String amount, String cycle, int userId){
        /**
         * "statu": "",  // 当前状态 1.未提交个人资料 2.已经提交申请 3.已经借贷中（已还款） 4.新创建申请单
         */
        Data data = new Data(Code.RequestSucc.getKey(), Code.RequestSucc.getValue());
        //校验是否填写了信息
        UserExtInfo extInfo = extInfoDao.getExtInfo(userId);
        if(null == extInfo){
            data.put("status", BillStatus.NO_INFO.getStatus());
        }else{
            LoanBill bill = billDao.getUnpayBack(userId);
            if(null != bill){
                //当前有未还款订单，不允许再新增
                LoanStatus loanStatus =  LoanStatus.getStatus(bill.getLoanStatus());
                if(loanStatus == LoanStatus.LOANED){ //已经放款
                    data.put("status", BillStatus.IN_LOAN.getStatus());
                }else if(loanStatus == LoanStatus.LOANING){  //放款中
                    data.put("status", BillStatus.APPLY_COMMIT.getStatus());
                }else{  //审核中
                    data.put("status", BillStatus.APPLY_COMMIT.getStatus());
                }
            }else{
                //新增订单
                bill = new LoanBill();
                bill.setBillno(userId+ FileUtils.newFileName());
                bill.setUserid(userId);
                bill.setCreateTime(new Timestamp(System.currentTimeMillis()));
                bill.setLoanStatus(LoanStatus.PRE_AUDIT.getStatus());
                bill.setIsPayBack("0");
                int userAmount = Integer.valueOf(amount);
                bill.setAmount(BigDecimal.valueOf(userAmount));
                bill.setBorrowingCycle(cycle);
                billDao.saveInfo(bill);
                //返回数据
                data.put("status", BillStatus.OPEN_NEW_BILL.getStatus());
            }
        }
        return data;
    }

    /**
     * 查看进度
     */
    public Msg checkProcess(int userId){
        /**
         * "progress": "", // 0.未有申请 1：初审中 2：再审中 3：放款中 4：已放款  5 : 审核不通过 数字来代表
         * "limit": "", // 再审完成则需要后台也下发额度
         * "billno":""//单据编号
         * "createtime":""//申请时间
         * "audittime":""//审核时间
         * "repayment_date": "" // 当前借贷需还款日期。
         */
        //根据userid查找借款单，根据还款状态过滤
        LoanBill bill = billDao.getUnpayBack(userId);
        Data data = new Data(Code.RequestSucc.getKey(), Code.RequestSucc.getValue());
        if(null == bill){//当前没有申请中的借款记录
            data.put("progress", LoanStatus.NO_APPLY.getStatus());
            data.put("limit", 0);
        }else{
            LoanStatus status = LoanStatus.getStatus(bill.getLoanStatus());
            if (status != null) {
                String createTime = DateUtils.format(bill.getCreateTime());
                String reAuditTime = DateUtils.format(bill.getReauditTime());
                String preAuditTime = DateUtils.format(bill.getPreauditTime());
                switch (status){
                    case PRE_AUDIT:
                    case RE_AUDIT:
                        data.put("progress", status.getStatus());
                        data.put("limit", 0);
                        data.put("billno", bill.getBillno());
                        data.put("createtime",createTime);
                        data.put("audittime","");
                        data.put("repayment_date", "");
                        break;
                    case LOANED:
                        data.put("repayment_date", DateUtils.getRepayDate(bill.getLoanTime(), BorrowCycle.getValue(bill.getBorrowingCycle())));
                    case LOANING:
                        data.put("progress", status.getStatus());
                        data.put("limit", bill.getAmount().setScale(2, BigDecimal.ROUND_UP)); // 保留两位小数
                        data.put("billno", bill.getBillno());
                        data.put("createtime", createTime);
                        data.put("audittime",reAuditTime);
                        String period = BorrowCycle.getCycleValue(bill.getBorrowingCycle());
                        data.put("period", period);
                        break;
                    case PREADDIT_FAIL:
                        data.put("progress", status.getStatus());
                        data.put("limit", 0);
                        data.put("billno", bill.getBillno());
                        data.put("createtime", createTime);
                        data.put("audittime", preAuditTime);
                        data.put("repayment_date", "");
                        break;
                    case READDIT_FAIL:
                        data.put("progress", status.getStatus());
                        data.put("limit", 0);
                        data.put("billno", bill.getBillno());
                        data.put("createtime", createTime);
                        data.put("audittime",reAuditTime);
                        data.put("repayment_date", "");
                        break;
                }
            } else {
                data.put("progress", LoanStatus.NO_APPLY.getStatus());
                data.put("limit", 0);
            }
        }
        return data;
    }

}
