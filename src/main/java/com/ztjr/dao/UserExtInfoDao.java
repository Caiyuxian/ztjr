package com.ztjr.dao;

import com.mysql.jdbc.Statement;
import com.ztjr.entity.UserAddrBook;
import com.ztjr.entity.UserExtInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class UserExtInfoDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public int saveUserExtInfo(final UserExtInfo userExt) {
		if(userExt.getId() != 0){
			//更新
			String sql = "update t_userextinfo set idcardname = ?,  idcard=?,address=?,workaddr=?,idcardurl1=?,idcardurl2=?,idcardurl3=?,ctuser1=?,ctuser2=?," +
					"ctuser3=?,ctphone1=?,ctphone2=?,ctphone3=?,phone=?,company=?,alipayscore=? where id=?";
			jdbcTemplate.update(sql, new Object[]{
					userExt.getIdCardName(), userExt.getIdcard(),userExt.getAddress(),userExt.getWorkAddr(),userExt.getIdCardRight(),userExt.getIdCardBack(),
					userExt.getIdCardByHand(),userExt.getCt_user1(),userExt.getCt_user2(),userExt.getCt_user3(),userExt.getCt_phone1(),
					userExt.getCt_phone2(),userExt.getCt_phone3(),userExt.getPhone(),userExt.getCompany(),userExt.getAlipay_score(),userExt.getId()
			});
			return userExt.getId();
		}else{
			//新增
			// 接受插入数据是返回的主键值
			KeyHolder key = new GeneratedKeyHolder();
			jdbcTemplate.update(new PreparedStatementCreator() {
				@Override
				public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
					String sql = "insert into t_userextinfo"
							+ " (userid,idcardname,idcard,address,workaddr,idcardurl1,idcardurl2,idcardurl3,ctuser1,ctuser2,ctuser3,ctphone1,ctphone2,ctphone3,phone,company,alipayscore)"
							+ " values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
					PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
					ps.setInt(1, userExt.getUserId());
					ps.setString(2, userExt.getIdCardName());
					ps.setString(3, userExt.getIdcard());
					ps.setString(4, userExt.getAddress());
					ps.setString(5, userExt.getWorkAddr());
					ps.setString(6, userExt.getIdCardRight());
					ps.setString(7, userExt.getIdCardBack());
					ps.setString(8, userExt.getIdCardByHand());
					ps.setString(9, userExt.getCt_user1());
					ps.setString(10, userExt.getCt_user2());
					ps.setString(11, userExt.getCt_user3());
					ps.setString(12, userExt.getCt_phone1());
					ps.setString(13, userExt.getCt_phone2());
					ps.setString(14, userExt.getCt_phone3());
					ps.setString(15, userExt.getPhone());
					ps.setString(16, userExt.getCompany());
					ps.setString(17, userExt.getAlipay_score());
					return ps;
				}
			}, key);
			return key.getKey().intValue();
		}
	}

	public boolean delContactAddrBook(int userId){
		String sql = "delete from t_useraddrbook where userid=?";
		jdbcTemplate.update(sql, new Object[]{userId});
		return true;
	}
	
	public boolean saveContactAddrBook(List<UserAddrBook> list) {
		String sql = "insert into t_useraddrbook (userid, phone, username) values(?,?,?)";
		List<Object[]> params = new ArrayList<>();
		for(UserAddrBook udb : list) {
			params.add(new Object[] {udb.getUserId(), udb.getPhone(), udb.getUsername()});
		}
		jdbcTemplate.batchUpdate(sql, params);
		return true;
	}

	public UserExtInfo getExtInfo(int userId) {
		String sql = "select id,idcardname,idcard,address,workaddr,ctuser1,idcardurl1,idcardurl2,idcardurl3,ctuser2,ctuser3,ctphone1,ctphone2,ctphone3,phone,company,alipayscore from t_userextinfo where userid = ? limit 1";
		List<UserExtInfo> list = jdbcTemplate.query(sql, new Object[] {userId}, new RowMapper<UserExtInfo>() {
			@Override
			public UserExtInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
				UserExtInfo extinfo = new UserExtInfo();
				extinfo.setId(rs.getInt("id"));
				extinfo.setIdcard(rs.getString("idcard"));
				extinfo.setIdCardName(rs.getString("idcardname"));
				extinfo.setAddress(rs.getString("address"));
				extinfo.setIdCardRight(rs.getString("idcardurl1"));
				extinfo.setIdCardBack(rs.getString("idcardurl2"));
				extinfo.setIdCardByHand(rs.getString("idcardurl3"));
				extinfo.setWorkAddr(rs.getString("workaddr"));
				extinfo.setCt_user1(rs.getString("ctuser1"));
				extinfo.setCt_user2(rs.getString("ctuser2"));
				extinfo.setCt_user3(rs.getString("ctuser3"));
				extinfo.setCt_phone1(rs.getString("ctphone1"));
				extinfo.setCt_phone2(rs.getString("ctphone2"));
				extinfo.setCt_phone3(rs.getString("ctphone3"));
				extinfo.setPhone(rs.getString("phone"));
				extinfo.setCompany(rs.getString("company"));
				extinfo.setAlipay_score(rs.getString("alipayscore"));
				return extinfo;
			}
		}
		);
		return list.size() == 0 ? null : list.get(0);
	}
}
