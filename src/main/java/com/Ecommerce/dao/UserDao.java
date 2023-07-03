package com.Ecommerce.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.Ecommerce.model.User;

public class UserDao {
	private Connection con;
	private String query;
	private PreparedStatement pts;
	private ResultSet rs;
	
	
	public UserDao(Connection con) {
		
		this.con=con;
	}
	public User userLogin(String email,String password) {
		User user=null;
		try {
			query="select * from users where email=? and password=?";
			pts=this.con.prepareStatement(query);
			pts.setString(1, email);
			pts.setString(2, password);
			rs = pts.executeQuery();
			
			if(rs.next()) {
				user=new User();
				user.setId(rs.getInt("id"));
				user.setName(rs.getString("name"));
				user.setEmail(rs.getString("email"));
			}
		} catch (Exception e) {
			
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		return user;
	}
	
	
	

}
