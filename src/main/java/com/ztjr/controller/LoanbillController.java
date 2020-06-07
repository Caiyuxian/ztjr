package com.ztjr.controller;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.ztjr.dao.LoanBillDao;
import com.ztjr.entity.LoanBill;
import com.ztjr.entity.Manager;
import com.ztjr.model.LoanStatus;
import com.ztjr.utils.MD5;

@Controller
@RequestMapping("/admin")
public class LoanbillController {

	@Autowired
	private LoanBillDao loanBillDao;
	
	@RequestMapping("/showloanbill.do")
	public ModelAndView showMessage(HttpSession session) {
		ModelAndView mv = new ModelAndView("html/loanbilllist");
		Manager m = (Manager) session.getAttribute("admin");
		mv.addObject("role", m.getRole());
		return mv; 
	}
	
	@ResponseBody
	@RequestMapping("/getallbill.do")
	public String getBillList(int rows, int page, String billno, String phone, String billstatus){
		String whereStr = "";
		if (!StringUtils.isEmpty(billno)){
			whereStr += " billno = '"+billno+"'";
		}
		if (!StringUtils.isEmpty(phone)){
			if(whereStr.length() > 0){
				whereStr += " and phone ='"+phone+"'";
			}else{
				whereStr += " phone ='"+phone+"'";
			}
		}
		if(!StringUtils.isEmpty(billstatus) && !billstatus.equals("0")){
			if(whereStr.length() > 0){
				whereStr += " and loanstatus ='"+billstatus+"'";
			}else{
				whereStr += " loanstatus ='"+billstatus+"'";
			}
		}
		List<LoanBill> list = loanBillDao.getByPage(rows, page, whereStr);
		int count = loanBillDao.count("t_loanbill");
		Map<String, Object> map = new HashMap<>();
		map.put("total", count);
		map.put("rows", list);
		return JSON.toJSONString(map);
	}

	@ResponseBody
	@RequestMapping("/loan.do")
	public String loanMoney(int billId) {
		LoanBill bill = loanBillDao.getLoanBill(billId);
		if(!LoanStatus.LOANING.getStatus().equals(bill.getLoanStatus())) {
			return "2";
		}
		loanBillDao.updateLoanStatus(billId);
		return "1";
	}

	@ResponseBody
	@RequestMapping("/payback.do")
	public String payback(int billId) {
		LoanBill bill = loanBillDao.getLoanBill(billId);
		if(bill.getIsPayBack().equals("1")) {
			return "3";
		}else if(!LoanStatus.LOANED.getStatus().equals(bill.getLoanStatus())) {
			return "2";
		}
		loanBillDao.updatePayBackStatus(billId, "1");
		return "1";
	}
	
	/**
	 * 再审
	 * 只有再审中状态的才可以进行再审操作
	 * @param billId
	 */
	@ResponseBody
	@RequestMapping("/reaudit.do")
	public String reAudit(int billId, String status, BigDecimal amount, String cycle, HttpSession session) {
		LoanBill bill = loanBillDao.getLoanBill(billId);
		if(LoanStatus.PRE_AUDIT.getStatus().equals(bill.getLoanStatus())) {
			return "3";//还未初审
		}else if(!LoanStatus.RE_AUDIT.getStatus().equals(bill.getLoanStatus())) {
			return "2";//已再审过
		}
		Manager m = (Manager) session.getAttribute("admin");
		loanBillDao.updateLoanStatus(billId, status, amount, cycle, m.getId());
		return "1";
	}
	
	/**
	 * 初审
	 * 只有初审中状态的才可以进行初审
	 * @param billId
	 */
	@ResponseBody
	@RequestMapping("/preaudit.do")
	public String preAudit(int billId, String status, HttpSession session) {
		LoanBill bill = loanBillDao.getLoanBill(billId);
		if(!LoanStatus.PRE_AUDIT.getStatus().equals(bill.getLoanStatus())) {
			return "2";
		}
		Manager m = (Manager) session.getAttribute("admin");
		loanBillDao.updateLoanStatus(billId, status, m.getId());
		return "1";
	}

	public static void  main(String[] args){
		System.out.println(MD5.toMD5("123456"));
	}
}
