package com.cheitu.watch;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;



public final class JdbcUtils {
    static{
    	 try {
			 Class.forName("com.mysql.jdbc.Driver").newInstance();  
			 
		} catch (Exception e) {
			e.printStackTrace();
		} 
    }
    /*
     * 获取连接
     */
    public static Connection getConnection() throws SQLException{
    	String url="jdbc:mysql://**.**.**.227:3306/sniffer?useUnicode=true&amp;characterEncoding=utf-8";
		String user="***";
		String password="***";	
    	 
		return DriverManager.getConnection(url,user,password);
    }
    /**
     * 释放连接等资源
     * @param rs
     * @param st
     * @param conn
     */
    public static void free(ResultSet rs,Statement st,Connection conn){
    	if(rs!=null)
			try {
				rs.close();
			} catch (SQLException e) { 
				e.printStackTrace();
			}
    	if(st!=null)
			try {
				st.close();
			} catch (SQLException e) { 
				e.printStackTrace();
			}
    	if(conn!=null)
			try {
				conn.close();
			} catch (SQLException e) { 
				e.printStackTrace();
			}
    }
    /**
     * 释放连接等资源
     * @param rs
     * @param st
     * @param conn
     */
    public static void free(ResultSet rs,PreparedStatement st,Connection conn){
    	if(rs!=null)
			try {
				rs.close();
			} catch (SQLException e) { 
				e.printStackTrace();
			}
    	if(st!=null)
			try {
				st.close();
			} catch (SQLException e) { 
				e.printStackTrace();
			}
    	if(conn!=null)
			try {
				conn.close();
			} catch (SQLException e) { 
				e.printStackTrace();
			}
    }
}
