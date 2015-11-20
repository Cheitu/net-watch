package com.cheitu.watch;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingDeque;

import jpcap.packet.TCPPacket;

public class ResCustomer implements Runnable{
	
	private static final String INSERT_SQL = "INSERT INTO t_response(request_ip,"
			+ "response_content,response_head,response_time,response_log) VALUES(?,?,?,?,?)";
	
	private BlockingDeque<Object> response;
	private Connection connection = null;
	
	 
	public ResCustomer(BlockingDeque<Object> response){
		this.response = response;
		
		try {
			connection = JdbcUtils.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void run() {
		Map<String, Object> params = null;
		TCPPacket res = null;
		PreparedStatement stmt = null;
		Map<String, Object> resMap = null;
		while (true) {
			try {
				params = (Map<String, Object>) response.take();
				res = (TCPPacket) params.get("packet");
				resMap = getHeadAndContent(new String(res.data, "utf-8"));
				stmt = connection.prepareStatement(INSERT_SQL);
				stmt.setString(1, res.src_ip.toString().replace("/", ""));
				stmt.setString(2, String.valueOf(resMap.get("content")));
				stmt.setString(3, String.valueOf(resMap.get("head")));
				stmt.setString(4, String.valueOf(params.get("date")));
				stmt.setString(5, "src IP: " + res.src_ip + " dst IP: "
                        + res.dst_ip + " send port: " + res.src_port
                        + " dst port: " + res.dst_port + " protocol:" + res.protocol);
				stmt.executeUpdate();
				stmt.close();
				stmt = null;

			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}
	
	protected Map<String, Object> getHeadAndContent(String data){
		int index = data.indexOf("\r\n\r\n");
		String head = data.substring(0, index).trim();
		String content = data.substring(index).trim();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("head", head);
		map.put("content", content);
		return map;
	}

}
