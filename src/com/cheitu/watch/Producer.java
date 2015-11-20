package com.cheitu.watch;

import java.io.IOException;
import java.util.concurrent.BlockingDeque;

import jpcap.JpcapCaptor;
import jpcap.NetworkInterface;
import jpcap.packet.Packet;

public class Producer implements Runnable{

	private BlockingDeque<Object> request;
	private BlockingDeque<Object> response;
	
	private NetworkInterface hardWare;
	
 
	
	public Producer(NetworkInterface hardWare, BlockingDeque<Object> request,
			BlockingDeque<Object> response){
		this.hardWare = hardWare;
		this.request = request;
		this.response = response;
	}
	
	@Override
	public void run() {
		JpcapCaptor jpcap;
		try {
			jpcap = JpcapCaptor.openDevice(hardWare, 65535, false,20);
			 jpcap.setFilter("ip and tcp and (port 8080 or 8005 or 8009)", true);
			jpcap.loopPacket(-1, new Receiver(request, response));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
