package com.cheitu.watch;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingDeque;

import jpcap.packet.Packet;
import jpcap.packet.TCPPacket;

public class ResCustomer implements Runnable{
	
	private static final String INSERT_SQL = "INSERT INTO t_response(request_ip,"
			+ "response_content,response_head,response_port,request_port) VALUES(?,?,?,?,?)";
	
	private BlockingDeque<Packet> response;
	private Connection connection = null;
	
	 
	public ResCustomer(BlockingDeque<Packet> response){
		this.response = response;
		
		try {
			connection = JdbcUtils.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		TCPPacket res = null;
		PreparedStatement stmt=null;
		Map<String, Object> resMap = null;
		while(true){
			try {
				res  = (TCPPacket)response.take();
				resMap = getHeadAndContent(new String(res.data,"utf-8"));
					stmt = connection.prepareStatement(INSERT_SQL);
					stmt.setString(1, res.dst_ip.toString().replace("/", ""));
					stmt.setString(3, String.valueOf(resMap.get("head")));
					stmt.setString(2, String.valueOf(resMap.get("content")));
					stmt.setString(4, String.valueOf(res.src_port));
					stmt.setString(5, String.valueOf(res.dst_port));
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
