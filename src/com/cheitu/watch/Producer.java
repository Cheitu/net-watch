package com.cheitu.watch;

import java.io.IOException;
import java.util.concurrent.BlockingDeque;

import jpcap.JpcapCaptor;
import jpcap.NetworkInterface;
import jpcap.packet.Packet;

public class Producer implements Runnable{

	private BlockingDeque<Packet> netData;
	
	private NetworkInterface hardWare;
	
	public Producer(NetworkInterface hardWare, BlockingDeque<Packet> netData){
		this.netData = netData;
		
		this.hardWare = hardWare;
		
	}
	
	@Override
	public void run() {
		JpcapCaptor jpcap;
		try {
			jpcap = JpcapCaptor.openDevice(hardWare, 2000, true,20);
			jpcap.setFilter("http", true);
			jpcap.loopPacket(-1, new Receiver(netData));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
