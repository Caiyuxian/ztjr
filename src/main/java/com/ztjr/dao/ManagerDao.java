package com.ztjr.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.mysql.jdbc.Statement;
import com.ztjr.entity.Manager;
import com.ztjr.utils.MD5;

@Repository
public class ManagerDao extends AbstractBaseDao<Manager>{
	
	public int saveManager(final Manager m){
		// 接受插入数据是返回的主键值
        KeyHolder key = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				String sql = "insert into t_manager (username, password, createtime, role) values(?,?,?,?)";
				PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				ps.setString(1, m.getUsername());
				ps.setString(2, m.getPassword());
				ps.setTimestamp(3, m.getCreatetime());
				ps.setString(4, m.getRole());
				return ps;
			}
		}, key);
		return key.getKey().intValue();
	}

	public List<Manager> getAllManger() {
		String sql = "select id,password,username,createtime,role from t_manager";
		List<Manager> list = jdbcTemplate.query(sql, new RowMapper<Manager>() {
			@Override
			public Manager mapRow(ResultSet rs, int rowNum) throws SQLException {
				Manager m = new Manager();
				m.setId(rs.getInt("id"));
				m.setPassword(rs.getString("password"));
				m.setRole(rs.getString("role"));
				m.setCreatetime(rs.getTimestamp("createtime"));
				m.setUsername(rs.getString("username"));
				return m;
			}
		}
		);
		return list;
	}

	public int updatePwd(int userid, String pwd) {
		String sql = "update t_manager set password=? where id=?";
		int i = jdbcTemplate.update(sql, new Object[] {MD5.toMD5(pwd), userid});
		return i;
	}
	
	@Override
	public List<Manager> getByPage(int rows, int page, String whereSql) {
		String sql = "select id,password,username,createtime,role from t_manager";
		sql = addFilter(sql, rows, page);
		List<Manager> list = jdbcTemplate.query(sql, new RowMapper<Manager>() {
			@Override
			public Manager mapRow(ResultSet rs, int rowNum) throws SQLException {
				Manager m = new Manager();
				m.setId(rs.getInt("id"));
				m.setPassword(rs.getString("password"));
				m.setRole(rs.getString("role"));
				m.setCreatetime(rs.getTimestamp("createtime"));
				m.setUsername(rs.getString("username"));
				return m;
			}
		}
		);
		return list;
	}

	public Manager getByName(String username){
		String sql = "select id,password,username,role from t_manager where username=? limit 1";
		List<Manager> list = jdbcTemplate.query(sql, new Object[]{username}, new RowMapper<Manager>() {
			@Override
			public Manager mapRow(ResultSet rs, int rowNum) throws SQLException {
				Manager m = new Manager();
				m.setId(rs.getInt("id"));
				m.setPassword(rs.getString("password"));
				m.setUsername(rs.getString("username"));
				m.setRole(rs.getString("role"));
				return m;
			}
		});
		return list.size() == 0 ? null : list.get(0);
	}
	
	public boolean isExist(String username) {
		int count = jdbcTemplate.queryForObject("select count(*) from t_manager where username = ?",new Object[] {username}, Integer.class);
		return count > 0;
	}
}
