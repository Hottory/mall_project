package com.nike.basket;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import com.nike.util.DBconnector;

public class BasketDAO {

	public int insert(BasketDTO basketDTO) throws Exception {
		Connection con = DBconnector.getConnect();
		String sql = "insert into basket values(basket_seq.nextval,?,?,?,?)";
		PreparedStatement st = con.prepareStatement(sql);
		st.setString(1, basketDTO.getId());
		st.setString(2, basketDTO.getProductCode());
		st.setInt(3, basketDTO.getProductSize());
		st.setString(4, basketDTO.getCookie());
		int result = st.executeUpdate();
		DBconnector.disConnect(st, con);
		return result;
	}

	public List<BasketDTO> selectList(BasketDTO basketDTO) throws Exception {

		Connection con = DBconnector.getConnect();
		String sql = "select fname, productname, productcode, price, basket.productsize "
					+ "from basket "
					+ "INNER JOIN product using(productcode) " 
					+ "JOIN image using(productcode) "
					+ "where id = ? or cookie = ? "
					+ "order by num asc";

		PreparedStatement st = con.prepareStatement(sql);
		st.setString(1, basketDTO.getId());
		st.setString(2, basketDTO.getCookie());
		ResultSet rs = st.executeQuery();
		List<BasketDTO> ar = new ArrayList<>();
		while (rs.next()) {
			basketDTO = new BasketDTO();
			basketDTO.setFname(rs.getString("fname"));
			basketDTO.setProductName(rs.getString("productname"));
			basketDTO.setProductCode(rs.getString("productcode"));
			basketDTO.setPrice(rs.getInt("price"));
			basketDTO.setProductSize(rs.getInt("productsize"));
			ar.add(basketDTO);
		}

		DBconnector.disConnect(rs, st, con);
		return ar;

	}
}