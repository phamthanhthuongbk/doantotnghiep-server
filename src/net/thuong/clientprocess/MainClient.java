package net.thuong.clientprocess;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;

import javax.swing.JFrame;

import net.thuong.clienttest.TestSendUDP;
import net.thuong.clientthread.ProcessThread;
import net.thuong.clientthread.ReceiveUDPThread;
import net.thuong.clientthread.SendUDPThread;
import net.thuong.packetcustom.BasePacketCustom;

public class MainClient {
	public static void main(String args[]) throws Exception {
		
		MainClient testSendUDP = new MainClient();
		testSendUDP.createArpEntry();

		int port = 6013;
		byte[] ipAddr = new byte[] { 10, 0, 0, 99 };

		InetAddress IPAddress = InetAddress.getByAddress(ipAddr);

		DatagramSocket clientSocket = new DatagramSocket(port);

		SendUDPThread sendUDPThread = new SendUDPThread(clientSocket, IPAddress, port);
		ReceiveUDPThread receiveUDPThread = new ReceiveUDPThread(clientSocket, IPAddress, port);
		ProcessThread processThread = new ProcessThread();

		sendUDPThread.setLogEnable(false);
		receiveUDPThread.setLogEnable(false);

		sendUDPThread.start();
		
		//thiet lap moi lien ket giua cac thread
		processThread.sendUDPThread = sendUDPThread;

		processThread.start();
		
		receiveUDPThread.processThread = processThread;
		receiveUDPThread.start();

//		sendUDPThread.addQueue(new BasePacketCustom(BasePacketCustom.PACKET_TYPE_HELLO, "thuong123".getBytes()));
//		sendUDPThread.addQueue(new BasePacketCustom(BasePacketCustom.PACKET_TYPE_SEND_STRING, "thuong".getBytes()));

		// clientSocket.close();
	}

	// lenh add Arp Entry
	public void createArpEntry() {
		try {
			Process child = Runtime.getRuntime().exec(
					"arp -s 10.0.0.99 aa:aa:aa:aa:aa:aa");
		} catch (IOException e) {
			System.out.print("Create bi loi");
		}
	}
	
	public void closeClient(){
		
	}

}
