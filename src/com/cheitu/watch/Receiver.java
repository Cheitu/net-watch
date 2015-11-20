package com.cheitu.watch;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingDeque;

import jpcap.PacketReceiver;
import jpcap.packet.Packet;

public class Receiver implements PacketReceiver {

	private BlockingDeque<Object> request;
	private BlockingDeque<Object> response;
	public Receiver(BlockingDeque<Object> request,
			BlockingDeque<Object> response) {
		this.request = request;
		this.response = response;
	}

	@Override
	public void receivePacket(Packet packet) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("date", Utils.getNow());
		try {
			if(packet !=null && packet.data.length > 0){
				System.out.println(new String(packet.data,"utf-8"));
				if(Utils.isResponse(new String(packet.data,"utf-8"))){
					map.put("packet", packet);
					response.add(map);
				}
					
				else
					if(Utils.isRequest(new String(packet.data,"utf-8"))){
						map.put("packet", packet);
						request.add(map);
					}
						
			}
				
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
