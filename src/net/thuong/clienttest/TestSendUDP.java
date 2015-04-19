package net.thuong.clienttest;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import net.thuong.clientthread.ReceiveUDPThread;
import net.thuong.clientthread.SendUDPThread;
import net.thuong.packetcustom.BasePacketCustom;

public class TestSendUDP {

	public static void main2(String args[]) {
		try {
			int port = 90;

			byte[] message = "Java Source and Support".getBytes();

			// Get the internet address of the specified host
			byte[] ipAddr = new byte[] { 10, 0, 0, 3 };
			InetAddress address = InetAddress.getByAddress(ipAddr);

			System.out.print("IP: " + address.getHostAddress());
			// Initialize a datagram packet with data and address
			DatagramPacket packet = new DatagramPacket(message, message.length,
					address, port);

			// Create a datagram socket, send the packet through it, close it.
			DatagramSocket dsocket = new DatagramSocket();
			dsocket.send(packet);
			System.out.print("Da sen");
			dsocket.close();
		} catch (Exception e) {
			System.err.println(e);
		}
	}

	public static void main(String args[]) throws Exception {

		TestSendUDP testSendUDP = new TestSendUDP();
		testSendUDP.createArpEntry();

		int port = 90;
		byte[] ipAddr = new byte[] { 10, 0, 0, 3 };

		InetAddress IPAddress = InetAddress.getByAddress(ipAddr);

		DatagramSocket clientSocket = new DatagramSocket(port);

		// byte[] sendData = new byte[10];
		// String sentence = "DMMM";
		// System.out.println("SEBD:" + sentence);
		// sendData = sentence.getBytes();
		// DatagramPacket sendPacket = new DatagramPacket(sendData,
		// sendData.length, IPAddress, port);
		// clientSocket.send(sendPacket);

		SendUDPThread b = new SendUDPThread(clientSocket, IPAddress, port);
		b.start();
		ReceiveUDPThread c = new ReceiveUDPThread(clientSocket, IPAddress, port);
		c.start();
		b.addQueue(new BasePacketCustom(1, "thuong".getBytes()));

//		while (true) {
//			byte[] receiveData = new byte[20];
//			DatagramPacket receivePacket = new DatagramPacket(receiveData, 20);
//			clientSocket.receive(receivePacket);
//			String modifiedSentence = new String(receivePacket.getData());
//			System.out.println("\nFROM SERVER:" + modifiedSentence);
//		}
		// clientSocket.close();
	}

	// lenh add Arp Entry
	public void createArpEntry() {
		try {
			Process child = Runtime.getRuntime().exec(
					"arp -s 10.0.0.3 aa:aa:aa:aa:aa:aa");
		} catch (IOException e) {
			System.out.print("Create bi loi");
		}
	}
}
