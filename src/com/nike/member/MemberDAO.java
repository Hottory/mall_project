package com.nike.member;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.nike.page.RowNumber;
import com.nike.util.DBconnector;

public class MemberDAO {
	public int checkSns(String snsid, String sns) throws Exception {
		Connection con = DBconnector.getConnect();
		String sql = "select * from member where " + sns + "id=?";
		int result = 1;

		PreparedStatement st = con.prepareStatement(sql);
		st.setString(1, snsid);
		ResultSet rs = st.executeQuery();

		if (rs.next()) {
			result = 2;
		}

		DBconnector.disConnect(rs, st, con);
		return result;
	}

	public int checkId(String id) throws Exception {
		Connection con = DBconnector.getConnect();
		String sql = "select * from member where id=?";
		int result = 1;

		PreparedStatement st = con.prepareStatement(sql);
		st.setString(1, id);
		ResultSet rs = st.executeQuery();

		if (rs.next()) {
			result = 2;
		}

		DBconnector.disConnect(rs, st, con);
		return result;
	}

	public int insert(MemberDTO memberDTO) throws Exception {
		Connection con = DBconnector.getConnect();
		String sql = "insert into member values(?,?,?,?,?,sysdate,?,?)";

		PreparedStatement st = con.prepareStatement(sql);
		st.setString(1, memberDTO.getId());
		st.setString(2, memberDTO.getPassword());
		st.setString(3, memberDTO.getName());
		st.setString(4, memberDTO.getPhone());
		st.setString(5, memberDTO.getAddress());
		st.setString(6, memberDTO.getKakaoID());
		st.setString(7, memberDTO.getFacebookID());
		int result = st.executeUpdate();

		DBconnector.disConnect(st, con);
		return result;
	}

	public int update(MemberDTO memberDTO) throws Exception {
		Connection con = DBconnector.getConnect();
		String sql = "update member set password=?, name=?, phone=?, address=? where id =?";

		PreparedStatement st = con.prepareStatement(sql);
		st.setString(1, memberDTO.getPassword());
		st.setString(2, memberDTO.getName());
		st.setString(3, memberDTO.getPhone());
		st.setString(4, memberDTO.getAddress());
		st.setString(5, memberDTO.getId());
		int result = st.executeUpdate();

		DBconnector.disConnect(st, con);
		return result;
	}

	public int delete(MemberDTO memberDTO) throws Exception {
		Connection con = DBconnector.getConnect();
		String sql = "delete member where id = ?";

		PreparedStatement st = con.prepareStatement(sql);
		st.setString(1, memberDTO.getId());
		int result = st.executeUpdate();

		DBconnector.disConnect(st, con);
		return result;
	}

	public MemberDTO login(MemberDTO memberDTO) throws Exception {
		Connection con = DBconnector.getConnect();
		String sql = "select * from member where id=? and password=? or id=? and kakaoid=? "
				+ "or id=? and facebookid=?";

		PreparedStatement st = con.prepareStatement(sql);
		st.setString(1, memberDTO.getId());
		st.setString(2, memberDTO.getPassword());
		st.setString(3, memberDTO.getId());
		st.setString(4, memberDTO.getKakaoID());
		st.setString(5, memberDTO.getId());
		st.setString(6, memberDTO.getFacebookID());
		ResultSet rs = st.executeQuery();

		if (rs.next()) {
			memberDTO.setName(rs.getString("name"));
			memberDTO.setPhone(rs.getString("phone"));
			memberDTO.setAddress(rs.getString("address"));
			memberDTO.setJoin_date(rs.getDate("join_date"));
		}
		DBconnector.disConnect(rs, st, con);
		return memberDTO;
	}

	public int snsLogin(MemberDTO memberDTO, String sns) throws Exception {
		Connection con = DBconnector.getConnect();
		String sql = "update member set " + sns + "id=? where id=?";

		PreparedStatement st = con.prepareStatement(sql);
		if (sns.equals("kakao")) {
			st.setString(1, memberDTO.getKakaoID());
		} else if (sns.equals("facebook")) {
			st.setString(1, memberDTO.getFacebookID());
		}
		st.setString(2, memberDTO.getId());

		int result = st.executeUpdate();

		DBconnector.disConnect(st, con);
		return result;
	}

	public List<MemberDTO> seleteList(RowNumber rowNumber) throws Exception {
		Connection con = DBconnector.getConnect();
		String sql = "select * from " + "(select rownum R, m.* from "
				+ "(select * from member order by join_date desc, id desc) m) " + "where R between ? and ?";

		List<MemberDTO> ar = new ArrayList<>();

		PreparedStatement st = con.prepareStatement(sql);
		st.setInt(1, rowNumber.getStartRow());
		st.setInt(2, rowNumber.getLastRow());
		ResultSet rs = st.executeQuery();

		while (rs.next()) {
			MemberDTO memberDTO = new MemberDTO();
			memberDTO.setId(rs.getString("id"));
			memberDTO.setName(rs.getString("name"));
			memberDTO.setPhone(rs.getString("phone"));
			memberDTO.setAddress(rs.getString("address"));
			memberDTO.setJoin_date(rs.getDate("join_date"));
			memberDTO.setKakaoID(rs.getString("kakaoID"));
			memberDTO.setFacebookID(rs.getString("facebookID"));
			ar.add(memberDTO);
		}

		DBconnector.disConnect(rs, st, con);
		return ar;
	}

	public int totalCount() throws Exception {
		Connection con = DBconnector.getConnect();
		String sql = "select count(id) from member";

		PreparedStatement st = con.prepareStatement(sql);
		ResultSet rs = st.executeQuery();
		rs.next();
		int result = rs.getInt(1);

		DBconnector.disConnect(rs, st, con);
		return result;
	}
}
