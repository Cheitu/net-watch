package com.cheitu.watch;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingDeque;

import jpcap.packet.Packet;
import jpcap.packet.TCPPacket;

public class ReqCustomer implements Runnable{
	
	private static final String INSERT_SQL = "INSERT INTO t_request(request_ip,"
			+ "request_content,request_head) VALUES(?,?,?)";
	
	private BlockingDeque<Packet> request;
	private Connection connection = null;
	
	 
	public ReqCustomer(BlockingDeque<Packet> request){
		this.request = request;
		
		try {
			connection = JdbcUtils.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		TCPPacket req = null;
		PreparedStatement stmt=null;
		Map<String, Object> reqMap = null;
		while(true){
			try {
				req  = (TCPPacket)request.take();
				reqMap = getHeadAndContent(new String(req.data,"utf-8"));
					stmt = connection.prepareStatement(INSERT_SQL);
					stmt.setString(1, req.src_ip.toString().replace("/", ""));
					stmt.setString(3, String.valueOf(reqMap.get("head")));
					stmt.setString(2, String.valueOf(reqMap.get("content")));
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
