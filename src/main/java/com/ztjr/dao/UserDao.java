package com.ztjr.dao;

import com.mysql.jdbc.Statement;
import com.ztjr.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


@Repository
public class UserDao extends AbstractBaseDao<User>{
	
	public List<User> getAllUser(){
		String sql = "select * from t_user";
		List<User> users = jdbcTemplate.query(sql, new Object[] {}, new RowMapper<User>() {
			@Override
			public User mapRow(ResultSet rs, int rowNum) throws SQLException {
				User users = new User();
				users.setId(rs.getInt("id"));
				users.setPhone(rs.getString("phone"));
				users.setUsername(rs.getString("username"));
				users.setPassword(rs.getString("password"));
				users.setCreatetime(rs.getTimestamp("createtime"));
				return users;
			}
		}
		);
		return users;
	}
	
	public User getUserByPhone(String phone){
		String sql = "select id,password,phone from t_user where phone = ?";
		List<User> list = jdbcTemplate.query(sql, new Object[] {phone}, new RowMapper<User>() {
			@Override
			public User mapRow(ResultSet rs, int rowNum) throws SQLException {
				User users = new User();
				users.setId(rs.getInt("id"));
				users.setPassword(rs.getString("password"));
				users.setPhone(rs.getString("phone"));
				return users;
			}
		}
		);
		return list.size() == 0 ? null : list.get(0);
	}
	
	public User getUserByTokenId(String tokenId){
		String sql = "select id from t_user where token = ?";
		List<User> list = jdbcTemplate.query(sql, new Object[] {tokenId}, new RowMapper<User>() {
			@Override
			public User mapRow(ResultSet rs, int rowNum) throws SQLException {
				User users = new User();
				users.setId(rs.getInt("id"));
				return users;
			}
		}
		);
		return list.size() == 0 ? null : list.get(0);
	}
	
	public boolean updateToken(int userId, String token){
		String sql = "update t_user set token = ? where id = ? ";
		int count = jdbcTemplate.update(sql, new Object[] {token, userId});
		return count == 1;
	}
	
	public int regiSave(final User u) {
		// 接受插入数据是返回的主键值
        KeyHolder key = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				String sql = "insert into t_user (phone, password, createtime, token) values(?,?,?,?)";
				PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				ps.setString(1, u.getPhone());
				ps.setString(2, u.getPassword());
				ps.setTimestamp(3, u.getCreatetime());
				ps.setString(4, u.getToken());
				return ps;
			}
		}, key);
		return key.getKey().intValue();
	}

	/**
	 * 忘记密码
	 * @param phone phone
	 * @param psw new psw
	 * @return success?
	 */
	public boolean updateUserPsw(String phone, String psw) {
		String sql = "update t_user set password = ? where phone = ? ";
		int count = jdbcTemplate.update(sql, new Object[] {psw, phone});
		return count == 1;
	}
	
	public boolean isExist(String phone) {
		int count = jdbcTemplate.queryForObject("select count(*) from t_user where phone = ?",new Object[] {phone}, Integer.class);
		return count > 0;
	}

	@Override
	public List<User> getByPage(int rows, int page, String whereSql) {
		String sql = "select * from t_user";
		if(!StringUtils.isEmpty(whereSql)){
			sql = sql + " where "+whereSql;
		}
		sql = addFilter(sql, rows, page);
		List<User> users = jdbcTemplate.query(sql, new Object[] {}, new RowMapper<User>() {
			@Override
			public User mapRow(ResultSet rs, int rowNum) throws SQLException {
				User users = new User();
				users.setId(rs.getInt("id"));
				users.setPhone(rs.getString("phone"));
				users.setUsername(rs.getString("username"));
				users.setPassword(rs.getString("password"));
				users.setCreatetime(rs.getTimestamp("createtime"));
				return users;
			}
		}
		);
		return users;
	}
}
