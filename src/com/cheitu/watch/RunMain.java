package com.cheitu.watch;

 

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

import jpcap.JpcapCaptor;
import jpcap.NetworkInterface;

public class RunMain {

	public static void main(String args[]){
	 
		BlockingDeque<Object> req = new LinkedBlockingDeque<Object>();
		BlockingDeque<Object> res = new LinkedBlockingDeque<Object>();
	 
		ReqCustomer reqCustomer = new ReqCustomer(req);
		new Thread(reqCustomer).start();
		ResCustomer resCustomer = new ResCustomer(res);
		new Thread(resCustomer).start();
		RequestCount requestCount = new RequestCount();
		new Thread(requestCount).start();
		String ethName = args.length>0?args[0]:"eth0";
	 
		System.out.println("Listening on interface-----" + ethName);
		final NetworkInterface[] devices = JpcapCaptor.getDeviceList();
		for(int i=0;i<devices.length;i++){
			NetworkInterface network = devices[i];
			System.out.println(network.name);
			if(ethName.equals(network.name)){
				System.out.println("begin interface-----" +i+ network.name);
				new Thread(new Producer(network,req,res)).start();
			}
		}
	}
		 
	
}
