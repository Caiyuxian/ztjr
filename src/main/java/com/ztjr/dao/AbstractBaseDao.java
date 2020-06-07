package com.ztjr.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

public abstract class AbstractBaseDao<T> {

	@Autowired
	 public JdbcTemplate jdbcTemplate;

	/**
	 * 分页查询
	 * @param rows
	 * @param page
	 * @param whereStr
	 * @return
	 */
	 public abstract List<T> getByPage(int rows, int page, String whereStr);
	 
	 /**
	  *  拼接limit条件
	  * @param sql
	  * @param rows
	  * @param page
	  * @return
	  */
	 public String addFilter(String sql, int rows, int page) {
		 int start = (page-1)*rows;
		 int end = start + rows;
		 return sql+" limit "+start+","+end;
	 }
	 
	 /**
	  * 计数
	  * @param tableName
	  * @return
	  */
	 public int count(String tableName) {
		 int count = jdbcTemplate.queryForObject("select count(id) from "+tableName, Integer.class);
		 return count;
	 }
}
