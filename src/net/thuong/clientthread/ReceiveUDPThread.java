package net.thuong.clientthread;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.LinkedList;
import java.util.Queue;

import net.thuong.packetcustom.BasePacketCustom;

public class ReceiveUDPThread extends Thread {
	public ProcessThread processThread;

	private DatagramSocket clientSocket;
	private InetAddress IPAddress;
	private int port;


	public ReceiveUDPThread(DatagramSocket clientSocket, InetAddress IPAddress,
			int port) {
		ThreadLog("Khoi tao thread Receive");
		this.clientSocket = clientSocket;
		this.IPAddress = IPAddress;
		this.port = port;
	}

	@Override
	public void run() {
		while (true) {
			ThreadLog("ReceivePacket");
			BasePacketCustom packet = new BasePacketCustom();

			// get type packet
			byte[] receiveData = new byte[8];
			DatagramPacket receiveHeader = new DatagramPacket(receiveData, 8);
			try {
				clientSocket.receive(receiveHeader);
				packet.createHeaderFromDataHeader(receiveHeader.getData());
			} catch (IOException e) {
				ThreadLog("Nhan eo thanh cong");
			}

			// get data
			receiveData = new byte[packet.dataLeng];
			DatagramPacket receiveDatapacket = new DatagramPacket(receiveData,
					packet.dataLeng);
			try {
				clientSocket.receive(receiveDatapacket);
				packet.data = receiveDatapacket.getData();
			} catch (IOException e) {
				ThreadLog("Nhan eo thanh cong");
			}

			processThread.addQueue(packet);
			//
		}
	}

	public void setLogEnable(boolean logEnable) {
		this.logEnable = logEnable;
	}

	private boolean logEnable = true;
	public void ThreadLog(String log) {
		if (logEnable)
			System.out.print("\nReceiveUDPThread: " + log);
	}

}
