package com.Ecommerce.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.print.attribute.Size2DSyntax;

import com.Ecommerce.model.Cart;
import com.Ecommerce.model.Product;

public class ProductDao {
	private Connection con;
	private String query;
	private PreparedStatement pts;
	private ResultSet rs;
	
	public ProductDao(Connection con) {
		this.con=con;
	}
	
	public List<Product>getAllProducts(){
		List<Product> products =new ArrayList<Product>();
		try {
			query ="select * from products";
			pts =this.con.prepareStatement(query);
			rs=pts.executeQuery();
			while(rs.next()) {
				Product row=new Product();
				row.setId(rs.getInt("id"));
				row.setName(rs.getString("name"));
				row.setCategory(rs.getString("category"));
				row.setPrice(rs.getDouble("price"));
				row.setImage(rs.getString("image"));
				
				products.add(row);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return products;
		
	}
	public List<Cart>getcartProducts(ArrayList<Cart> cartList){
		List<Cart> products=new ArrayList<Cart>();
		try {
			if(cartList.size()>0) {
				for(Cart item:cartList) {
					query="select * from products where id=?";
					pts=this.con.prepareStatement(query);
					pts.setInt(1,item.getId());
					rs=pts.executeQuery();
					while(rs.next()) {
						Cart row=new Cart();
						row.setId(rs.getInt("id"));
						row.setName(rs.getString("name"));
						row.setCategory(rs.getString("category"));
						row.setPrice(rs.getDouble("price")*item.getQuantity());
						row.setQuantity(item.getQuantity());
						products.add(row);
					}
					
				}
			}
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return products;
	}
	public Product getSingleProduct(int id) {
		Product row=null;
		try {
			query="select * from products where id=?";
			pts=this.con.prepareStatement(query);
			pts.setInt(1, id);
			rs=pts.executeQuery();
			
			while(rs.next()) {
				row=new Product();
				row.setId(rs.getInt("id"));
				row.setName(rs.getString("name"));
				row.setCategory(rs.getString("category"));
				row.setPrice(rs.getDouble("price"));
				row.setImage(rs.getString("image"));
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return row;
	}
	public double getTotalCartPrice(ArrayList<Cart> cartList) {
		double sum=0;
		try {
			if(cartList.size()>0) {
				for(Cart item:cartList) {
					query="select price from products where id=?";
					pts=this.con.prepareStatement(query);
					pts.setInt(1, item.getId());
					rs=pts.executeQuery();
					
				while(rs.next()) {
					sum+=rs.getDouble("price")*item.getQuantity();
							
				}
				}
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return sum;
	}
	

}
