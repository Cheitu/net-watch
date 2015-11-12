package com.cheitu.watch;

import jpcap.JpcapCaptor;
import jpcap.NetworkInterface;

public class RunMain {

	public static void main(String args[]){
	 
		if(args.length>0){
			NetworkInterface[] devices = JpcapCaptor.getDeviceList();
			String serverIp = args[args.length - 1];
			
		}
		
	}
	
}
