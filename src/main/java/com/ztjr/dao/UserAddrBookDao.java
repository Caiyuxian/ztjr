package com.ztjr.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.ztjr.entity.UserAddrBook;

@Repository
public class UserAddrBookDao {

	 @Autowired
	 private JdbcTemplate jdbcTemplate;
	 
	 /**
     * 获取当前未还款记录
     * @param userId
     * @return
     */
    public List<UserAddrBook> getAllContact(final int userId){
        String sql = "select id,phone,username from t_useraddrbook where userid = ?";
        List<UserAddrBook> list = jdbcTemplate.query(sql, new Object[] {userId}, new RowMapper<UserAddrBook>() {
                @Override
                public UserAddrBook mapRow(ResultSet rs, int rowNum) throws SQLException {
                	UserAddrBook book = new UserAddrBook(userId);
                	book.setId(rs.getInt("id"));
                	book.setPhone(rs.getString("phone"));
                	book.setUsername(rs.getString("username"));
                    return book;
                }
            }
        );
        return list;
    }

}
