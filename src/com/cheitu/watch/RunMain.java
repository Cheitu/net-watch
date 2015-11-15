package com.cheitu.watch;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

import jpcap.JpcapCaptor;
import jpcap.NetworkInterface;
import jpcap.packet.Packet;

public class RunMain {
	 public static final String mac1 = "00-16-3E-00-4D-79";
     public static final String mac2 = "00-16-3E-00-36-84";
	public static void main(String args[]){
	 
		BlockingDeque<Packet> netData = new LinkedBlockingDeque<Packet>();
		BlockingDeque<Packet> req = new LinkedBlockingDeque<Packet>();
		BlockingDeque<Packet> res = new LinkedBlockingDeque<Packet>();
		Customer customer = new Customer(req,res);
		new Thread(customer).start();
//		if(args.length>0){
//			NetworkInterface[] devices = JpcapCaptor.getDeviceList();
//			for(int i=0;i<devices.length;i++)
//				for(int j=0;j<args.length;j++)
//					if(args[j].toUpperCase().equals(Utils.bytesToHexString(devices[i].mac_address))){
//						System.out.println(Utils.bytesToHexString(devices[i].mac_address));
//						new Thread(new Producer(devices[i],req,res)).start();
//					}
//						
//			
//		}
		NetworkInterface[] devices = JpcapCaptor.getDeviceList();
		for(int i=0;i<devices.length;i++)
//			 if (mac1.equals(Utils.bytesToHexString(devices[i].mac_address))
//                     || mac2.equals(Utils.bytesToHexString(devices[i].mac_address)))
			 {
					System.out.println(Utils.bytesToHexString(devices[i].mac_address));
					new Thread(new Producer(devices[i],req,res)).start();
				}
				
	}
	
}
