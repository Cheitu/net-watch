package com.cheitu.watch;


import com.sun.snoop.TCPHeader;

import jpcap.JpcapCaptor;
import jpcap.NetworkInterface;
import jpcap.PacketReceiver;
import jpcap.packet.Packet;
import jpcap.packet.TCPPacket;

public class DispalyNetPacket {
 

	public static void main(String args[]) {
		try {
			final NetworkInterface[] devices = JpcapCaptor.getDeviceList();
			for (int i = 0; i < devices.length; i++) {
				NetworkInterface nc = devices[i];
				 
					JpcapCaptor jpcap = JpcapCaptor.openDevice(nc, 65535, false,20);
					System.out.println(bytesToHexString(nc.mac_address));
					 jpcap.setFilter("tcp and port 8080 and (ether dst 00:50:56:C0:00:08 or 74:D4:35:9A:7B:ED)", true);
					startCapThread(jpcap);
				 
			}
		} catch (Exception ef) {
			ef.printStackTrace();
			System.out.println("run error:  " + ef);
		}

	}

	public static String bytesToHexString(byte[] src) {
		StringBuilder stringBuilder = new StringBuilder("");
		if (src == null || src.length <= 0) {
			return null;
		}
		for (int i = 0; i < src.length; i++) {
			int v = src[i] & 0xFF;
			String hv = Integer.toHexString(v);
			if (hv.length() < 2) {
				stringBuilder.append(0);
			}
			stringBuilder.append(hv);
			if (i < src.length - 1) {
				stringBuilder.append("-");
			}
		}
		return stringBuilder.toString().toUpperCase();
	}

	public static void startCapThread(final JpcapCaptor jpcap) {
		JpcapCaptor jp = jpcap;
		java.lang.Runnable rnner = new Runnable() {
			public void run() {
				jpcap.loopPacket(-1, new TestPacketReceiver());
			}
		};
		new Thread(rnner).start();
	}
}

class TestPacketReceiver implements PacketReceiver {

	public void receivePacket(Packet p) {
		 TCPPacket tempPacket = (TCPPacket) p;
				try {
					
					System.out.println(p.data);
				} catch (Exception e) {
					e.printStackTrace();
				}

			}

}