package com.cheitu.watch;

import java.util.concurrent.BlockingDeque;

import jpcap.PacketReceiver;
import jpcap.packet.Packet;

public class Receiver implements PacketReceiver {

	private BlockingDeque<Packet> netData;

	public Receiver(BlockingDeque<Packet> netData) {
		this.netData = netData;
	}

	@Override
	public void receivePacket(Packet packet) {

		try {
			netData.put(packet);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

}
