package com.cheitu.watch;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

import jpcap.JpcapCaptor;
import jpcap.NetworkInterface;
import jpcap.packet.Packet;

public class RunMain {

	public static void main(String args[]){
	 
		BlockingDeque<Packet> netData = new LinkedBlockingDeque<Packet>();
		
		if(args.length>0){
			NetworkInterface[] devices = JpcapCaptor.getDeviceList();
			String serverIp = args[args.length - 1];
			for(int i=0;i<devices.length;i++)
				for(int j=0;j<args.length;j++)
					if(args[j].toUpperCase().equals(Utils.bytesToHexString(devices[i].mac_address))){
						System.out.println(Utils.bytesToHexString(devices[i].mac_address));
						new Thread(new Producer(devices[i],netData)).start();
					}
						
			
		}
		
	}
	
}
