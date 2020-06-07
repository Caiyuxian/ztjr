package com.ztjr.dao;

import com.ztjr.entity.LoanBill;
import com.ztjr.entity.Manager;
import com.ztjr.model.LoanStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class LoanBillDao extends AbstractBaseDao<LoanBill>{

    @Autowired
    private ManagerDao managerDao;
    /**
     * 获取当前未还款记录
     * @param userId
     * @return
     */
    public LoanBill getUnpayBack(int userId){
        String sql = "select id,loanstatus,amount,borrowingcycle,loanTime,billno,createtime,preaudittime,reaudittime from t_loanbill where userid = ? and isPayBack = ? limit 1";
        List<LoanBill> list = jdbcTemplate.query(sql, new Object[] {userId, "0"}, new RowMapper<LoanBill>() {
                @Override
                public LoanBill mapRow(ResultSet rs, int rowNum) throws SQLException {
                    LoanBill bill = new LoanBill();
                    bill.setId(rs.getInt("id"));
                    bill.setAmount(rs.getBigDecimal("amount"));
                    bill.setLoanStatus(rs.getString("loanstatus"));
                    bill.setBorrowingCycle(rs.getString("borrowingcycle"));
                    bill.setLoanTime(rs.getTimestamp("loantime"));
                    bill.setBillno(rs.getString("billno"));
                    bill.setCreateTime(rs.getTimestamp("createtime"));
                    bill.setPreauditTime(rs.getTimestamp("preaudittime"));
                    bill.setReauditTime(rs.getTimestamp("reaudittime"));
                    return bill;
                }
            }
        );
        return list.size() == 0 ? null : list.get(0);
    }

    public void saveInfo(LoanBill bill){
        String sql = "insert into t_loanbill (userid,amount,borrowingcycle,loanstatus,createtime,ispayback,billno) values (?,?,?,?,?,?,?)";
        jdbcTemplate.update(sql, new Object[]{bill.getUserid(), bill.getAmount(), bill.getBorrowingCycle(),
                bill.getLoanStatus(),bill.getCreateTime(),bill.getIsPayBack(),bill.getBillno()});
    }

    public void updateLoanStatus(int billId, String status, int auditId) {
        String loanStatus = null;
       //初审
        String sql = "update t_loanbill set loanstatus = ?, preaudittime=?, preauditor=? where id = ?";
        if(status.equals("1")) {//通过
            loanStatus = LoanStatus.RE_AUDIT.getStatus();
        }else if(status.equals("0")){//不通过
            loanStatus = LoanStatus.PREADDIT_FAIL.getStatus();
        }
        jdbcTemplate.update(sql, new Object[] {loanStatus, new Timestamp(System.currentTimeMillis()),auditId, billId});

    }

    public void updateLoanStatus(int billId, String status, BigDecimal amount, String cycle, int auditId) {
    	String loanStatus = null;
    	//再审
        String sql = "update t_loanbill set loanstatus = ?,amount=?,borrowingcycle=?, reaudittime=?, reauditor=? where id = ?";
        if(status.equals("1")) {//通过
            loanStatus = LoanStatus.LOANING.getStatus();
        }else if(status.equals("0")){//不通过
            loanStatus = LoanStatus.READDIT_FAIL.getStatus();
        }
        jdbcTemplate.update(sql, new Object[] {loanStatus, amount, cycle,new Timestamp(System.currentTimeMillis()), auditId, billId});
    }
    
    public void updateLoanStatus(int billId) {
    	String sql = "update t_loanbill set loanstatus = ?, loantime=? where id = ?";
    	jdbcTemplate.update(sql, new Object[] {LoanStatus.LOANED.getStatus(), new Timestamp(System.currentTimeMillis()), billId});
    }
    
    public void updatePayBackStatus(int billId, String status) {
    	String sql = "update t_loanbill set ispayback = ?, loantime=? where id = ?";
    	jdbcTemplate.update(sql, new Object[] {status,new Timestamp(System.currentTimeMillis()), billId});
    }

    public List<LoanBill> getAllBill(){
    	String sql = "select id,loanstatus,amount,borrowingcycle,loanTime,billno,createtime from t_loanbill";
        List<LoanBill> list = jdbcTemplate.query(sql, new RowMapper<LoanBill>() {
                @Override
                public LoanBill mapRow(ResultSet rs, int rowNum) throws SQLException {
                    LoanBill bill = new LoanBill();
                    bill.setId(rs.getInt("id"));
                    bill.setAmount(rs.getBigDecimal("amount"));
                    bill.setLoanStatus(rs.getString("loanstatus"));
                    bill.setBorrowingCycle(rs.getString("borrowingcycle"));
                    bill.setLoanTime(rs.getTimestamp("loantime"));
                    bill.setBillno(rs.getString("billno"));
                    bill.setCreateTime(rs.getTimestamp("createtime"));
                    return bill;
                }
            }
        );
        return list;
    }
    
    public LoanBill  getLoanBill(int billId) {
    	String sql = "select id,loanstatus,ispayback from t_loanbill where id=? limit 1";
        List<LoanBill> list = jdbcTemplate.query(sql, new Object[]{billId}, new RowMapper<LoanBill>() {
                @Override
                public LoanBill mapRow(ResultSet rs, int rowNum) throws SQLException {
                    LoanBill bill = new LoanBill();
                    bill.setId(rs.getInt("id"));
                    bill.setLoanStatus(rs.getString("loanstatus"));
                    bill.setIsPayBack(rs.getString("ispayback"));
                    return bill;
                }
            }
        );
        return list.size() == 0? null : list.get(0);
    }

    private Map<Integer, String> getManagerName(){
        List<Manager> managers = managerDao.getAllManger();
        Map<Integer, String> name = new HashMap<>();
        for(Manager m : managers){
            name.put(m.getId(), m.getUsername());
        }
        return name;
    }

	@Override
	public List<LoanBill> getByPage(int rows, int page, String whereStr) {
        final Map<Integer, String> name = getManagerName();
		String sql = "select t1.id id,loanstatus,amount,borrowingcycle,ispayback,loanTime,billno,createtime,idcardname,phone,preauditor,preaudittime,reauditor,reaudittime,t1.userid userid "
				+ "from t_loanbill t1 left join t_userextinfo t2 on t1.userid=t2.userid";
		if(!StringUtils.isEmpty(whereStr)){
		    sql = sql +" where "+whereStr;
        }
		sql = addFilter(sql, rows, page);
        List<LoanBill> list = jdbcTemplate.query(sql, new RowMapper<LoanBill>() {
                @Override
                public LoanBill mapRow(ResultSet rs, int rowNum) throws SQLException {
                    LoanBill bill = new LoanBill();
                    bill.setId(rs.getInt("id"));
                    bill.setAmount(rs.getBigDecimal("amount"));
                    bill.setLoanStatus(rs.getString("loanstatus"));
                    bill.setBorrowingCycle(rs.getString("borrowingcycle"));
                    bill.setLoanTime(rs.getTimestamp("loantime"));
                    bill.setBillno(rs.getString("billno"));
                    bill.setCreateTime(rs.getTimestamp("createtime"));
                    bill.setPhone(rs.getString("phone"));
                    bill.setPreauditName(name.get(rs.getInt("preauditor")));
                    bill.setPreauditTime(rs.getTimestamp("preaudittime"));
                    bill.setReauditTime(rs.getTimestamp("reaudittime"));
                    bill.setReauditName(name.get(rs.getInt("reauditor")));
                    bill.setUsername(rs.getString("idcardname"));
                    bill.setUserid(rs.getInt("userid"));
                    bill.setIsPayBack(rs.getString("ispayback"));
                    return bill;
                }
            }
        );
        return list;
	}

}
