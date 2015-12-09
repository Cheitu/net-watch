package com.cheitu.watch;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RequestCount implements Runnable {

	public static final String SQL = "select request_ip,count(*)as num "
			+ "from t_request where TIMESTAMPDIFF(SECOND,request_time,now())<1200"
			+ " GROUP BY request_ip HAVING num > 3";
	private Connection connection = null;

	public RequestCount() {
		try {
			connection = JdbcUtils.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		PreparedStatement stmt = null;
		ResultSet result = null;
		String st = "";
		boolean status = false;
		while (true) {
			st = "";
			status = false;
			try {
				stmt = connection.prepareStatement(SQL);
				stmt.executeQuery();
				connection = JdbcUtils.getConnection();
				result = stmt.executeQuery();
				while(result.next()){
					status = true;
					st = st + result.getString(1)+" ";
					st = st + result.getInt(2) + "\n";
				}
				if(status){
					Mail.sendAndCcDefault("warning", st);
				}
				result.close();
				result = null;
				stmt.close();
				stmt = null;
				Thread.sleep(100000);//暂停100s 相当于停2分钟
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
