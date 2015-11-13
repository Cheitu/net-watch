package com.cheitu.watch;

import java.util.concurrent.BlockingDeque;

import jpcap.packet.Packet;

public class Customer implements Runnable{
	
	private BlockingDeque<Packet> netData;
	private BlockingDeque<Packet> request;
	private BlockingDeque<Packet> response;
	
	public Customer(BlockingDeque<Packet> netData){
		this.netData = netData;
	}
	public Customer(BlockingDeque<Packet> request,
			BlockingDeque<Packet> response){
		this.request = request;
		this.response = response;
	}

	@Override
	public void run() {
		Packet req = null;
		Packet res = null;
		while(true){
			try {
				req  = request.take();
				res = response.take();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}
	}

}
