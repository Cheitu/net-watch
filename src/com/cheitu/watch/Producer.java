package com.cheitu.watch;

import java.io.IOException;
import java.util.concurrent.BlockingDeque;

import jpcap.JpcapCaptor;
import jpcap.NetworkInterface;
import jpcap.packet.Packet;

public class Producer implements Runnable{

	private BlockingDeque<Packet> request;
	private BlockingDeque<Packet> response;
	
	private NetworkInterface hardWare;
	
 
	
	public Producer(NetworkInterface hardWare, BlockingDeque<Packet> request,
			BlockingDeque<Packet> response){
		this.hardWare = hardWare;
		this.request = request;
		this.response = response;
	}
	
	@Override
	public void run() {
		JpcapCaptor jpcap;
		try {
			jpcap = JpcapCaptor.openDevice(hardWare, 65535, false,20);
			 jpcap.setFilter("ip and tcp", true);
			jpcap.loopPacket(-1, new Receiver(request, response));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
