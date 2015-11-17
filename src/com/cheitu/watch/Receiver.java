package com.cheitu.watch;

import java.util.concurrent.BlockingDeque;

import jpcap.PacketReceiver;
import jpcap.packet.Packet;
import jpcap.packet.TCPPacket;

public class Receiver implements PacketReceiver {

	private BlockingDeque<Packet> request;
	private BlockingDeque<Packet> response;
	public Receiver(BlockingDeque<Packet> request,
			BlockingDeque<Packet> response) {
		this.request = request;
		this.response = response;
	}

	@Override
	public void receivePacket(Packet packet) {

		try {
			if(packet !=null && packet.data.length > 0){
				System.out.println(new String(packet.data,"utf-8"));
				if(Utils.isResponse(new String(packet.data,"utf-8")))
					response.add(packet);
				else
					if(Utils.isRequest(new String(packet.data,"utf-8")))
						request.add(packet);
			}
				
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
