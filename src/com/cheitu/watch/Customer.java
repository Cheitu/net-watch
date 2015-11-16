package com.cheitu.watch;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingDeque;

import jpcap.packet.Packet;
import jpcap.packet.TCPPacket;

public class Customer implements Runnable{
	
	private static final String INSERT_SQL = "INSERT INTO t_data(request_ip,response_ip,request_head,request_content,"
			+ "response_head,response_content) VALUES(?,?,?,?,?,?)";
	
	private BlockingDeque<Packet> netData;
	private BlockingDeque<Packet> request;
	private BlockingDeque<Packet> response;
	private Connection connection = null;
	
	public Customer(BlockingDeque<Packet> netData){
		this.netData = netData;
	}
	public Customer(BlockingDeque<Packet> request,
			BlockingDeque<Packet> response){
		this.request = request;
		this.response = response;
		
		try {
			connection = JdbcUtils.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		TCPPacket req = null;
		TCPPacket res = null;
		PreparedStatement stmt=null;
		Map<String, Object> reqMap = null;
		Map<String, Object> resMap = null;
		while(true){
			try {
				req  = (TCPPacket)request.take();
				res = (TCPPacket)response.take();
				System.out.println(1);
				reqMap = getHeadAndContent(new String(req.data,"utf-8"));
				resMap = getHeadAndContent(new String(res.data,"utf-8"));
				System.out.println(res.dst_ip.toString() + " src " + req.src_ip.toString());
				if(res.dst_ip.equals(req.src_ip)){
					stmt = connection.prepareStatement(INSERT_SQL);
					stmt.setString(1, req.src_ip.toString().replace("/", ""));
					stmt.setString(2, req.dst_ip.toString().replace("/", ""));
					stmt.setString(3, String.valueOf(reqMap.get("head")));
					stmt.setString(4, String.valueOf(reqMap.get("content")));
					stmt.setString(5, String.valueOf(resMap.get("head")));
					stmt.setString(6, String.valueOf(resMap.get("content")));
					stmt.executeUpdate();
					stmt.close();
					stmt = null;
				}else
					continue;
				
				
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
